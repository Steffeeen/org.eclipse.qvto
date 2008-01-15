/*******************************************************************************
 * Copyright (c) 2007 Borland Software Corporation
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 *     Borland Software Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.m2m.qvt.oml.ast.environment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.m2m.qvt.oml.QvtMessage;
import org.eclipse.m2m.qvt.oml.ast.parser.QvtOperationalTypesUtil;
import org.eclipse.m2m.qvt.oml.ast.parser.QvtOperationalUtil;
import org.eclipse.m2m.qvt.oml.emf.util.EmfException;
import org.eclipse.m2m.qvt.oml.emf.util.mmregistry.IMetamodelDesc;
import org.eclipse.m2m.qvt.oml.emf.util.mmregistry.MetamodelRegistry;
import org.eclipse.m2m.qvt.oml.expressions.DirectionKind;
import org.eclipse.m2m.qvt.oml.expressions.ImperativeOperation;
import org.eclipse.m2m.qvt.oml.expressions.MappingOperation;
import org.eclipse.m2m.qvt.oml.expressions.ModelParameter;
import org.eclipse.m2m.qvt.oml.expressions.ModelType;
import org.eclipse.m2m.qvt.oml.expressions.Module;
import org.eclipse.m2m.qvt.oml.expressions.ResolveInExp;
import org.eclipse.m2m.qvt.oml.expressions.VarParameter;
import org.eclipse.m2m.qvt.oml.expressions.VariableInitExp;
import org.eclipse.m2m.qvt.oml.internal.ast.parser.MappingsMapKey;
import org.eclipse.m2m.qvt.oml.internal.ast.parser.QvtOperationalParserUtil;
import org.eclipse.m2m.qvt.oml.internal.ast.parser.ValidationMessages;
import org.eclipse.m2m.qvt.oml.internal.cst.adapters.ModelTypeMetamodelsAdapter;
import org.eclipse.m2m.qvt.oml.ocl.transformations.Library;
import org.eclipse.m2m.qvt.oml.ocl.transformations.LibraryCreationException;
import org.eclipse.m2m.qvt.oml.ocl.transformations.LibraryOperation;
import org.eclipse.ocl.Environment;
import org.eclipse.ocl.EnvironmentFactory;
import org.eclipse.ocl.cst.CSTNode;
import org.eclipse.ocl.ecore.CallOperationAction;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.EcoreFactory;
import org.eclipse.ocl.ecore.SendSignalAction;
import org.eclipse.ocl.expressions.ExpressionsFactory;
import org.eclipse.ocl.expressions.Variable;
import org.eclipse.ocl.lpg.AbstractParser;
import org.eclipse.ocl.lpg.AbstractProblemHandler;
import org.eclipse.ocl.lpg.ProblemHandler;
import org.eclipse.ocl.util.TypeUtil;
import org.eclipse.ocl.utilities.ASTNode;
import org.eclipse.ocl.utilities.TypedElement;
import org.eclipse.ocl.utilities.UMLReflection;
import org.eclipse.osgi.util.NLS;


public class QvtOperationalEnv extends QvtEnvironmentBase { //EcoreEnvironment {

	public static final String THIS = "this"; //$NON-NLS-1$	
	public static final String MAIN = "main"; //$NON-NLS-1$	
	
	public static final String MAPPING_OPERATION_STEREOTYPE = "mapping_operation"; //$NON-NLS-1$
	public static final String IMPERATIVE_OPERATION_STEREOTYPE = "imperative_operation"; //$NON-NLS-1$
	public static final String RENAMED_PROPERTY_STEREOTYPE = "renamed_property"; //$NON-NLS-1$
	
	public static final String METAMODEL_COMPLIANCE_KIND_STRICT = "strict"; //$NON-NLS-1$
	
    /*
     * List of declared variables and implicit variables, including "self".
     * Implicit variables are generated when there is an iterator without any
     * iteration variable specified.
     */
    private List<QvtVariableEntry> myNamedElements = new java.util.ArrayList<QvtVariableEntry>();
	
	protected QvtOperationalEnv(QvtOperationalEnv parent, EPackage.Registry eRegistry) {
		// Set our own package registry to be populated by imported metamodels
		super(eRegistry);
		setParent(parent);
		myRootEnvironment = parent != null ? parent.myRootEnvironment : null;

		myWarningsList = new ArrayList<QvtMessage>(2);
		myErrorsList = new ArrayList<QvtMessage>(2);
		myErrorRecordFlag = true;

		defineStandartOperations();

		ePackageRegistry = eRegistry;		
		myModelTypeRegistry = parent != null ? parent.myModelTypeRegistry : new LinkedHashMap<String, ModelType>(1);
	}
	
	protected QvtOperationalEnv(QvtOperationalEnv parent) {
		this(parent, parent != null ? parent.getEPackageRegistry() : new EPackageRegistryImpl());
	}
	
	/**
	* Gets the package registry used in this environment EClassifier lookup.
	*/
	public EPackage.Registry getEPackageRegistry() {
		return ePackageRegistry;
	}
	
    public Map<String, ModelType> getModelTypeRegistry() {
        return myModelTypeRegistry;
    }

	public MetamodelRegistry getMetamodelRegistry() {
		if(getParent() instanceof QvtOperationalEnv) {
			return ((QvtOperationalEnv)getParent()).getMetamodelRegistry();
		}
		return MetamodelRegistry.getInstance();
	}

	public QvtOperationalStdLibrary getQVTStandartLibrary() {
		return QvtOperationalStdLibrary.INSTANCE;
	}
	
    /**
     * Registers metamodel for use with this environment.
     * 
     * @return the metamodel package denoted by the given <code>URI</code> or <code>null</code>
     * 	if no package was resolved 
     */
	public List<EPackage> registerMetamodel(String metamodelUri, List<String> path) {
        List<EPackage> metamodels = new ArrayList<EPackage>(1);
		try {
			MetamodelRegistry registry = getMetamodelRegistry();
			IMetamodelDesc[] desc;
			if (path.isEmpty()) {
				desc = new IMetamodelDesc[] { registry.getMetamodelDesc(metamodelUri) };
			}
			else {
				desc = registry.getMetamodelDesc(path);
			}

			for(IMetamodelDesc nextDesc : desc) {
	        	EPackage model = nextDesc.getModel();
	            // register metamodel for EClassifier lookup
	        	if (model.getNsURI() == null) {
					while (true) {
						if (model.getESuperPackage() == null) {
							break;
						}
						model = model.getESuperPackage();
					}
	        	}
	        	
	        	metamodels.add(model);
	            getEPackageRegistry().put(model.getNsURI(), model);
	            break;
/*
	            EPackage[] all = EmfMmUtil.getRegisterableModels(model, true);            
	            for (EPackage pack : all) {
//		            getEPackageRegistry().put(pack.getNsURI(), pack);
	            }
*/
	        }			
		} catch (EmfException e) {
			// It's legal situation of unresolved metamodels
		}
		return metamodels;
	}
	
	public void registerLibrary(Library lib) throws LibraryCreationException {
		for (LibraryOperation libOp : lib.getLibraryOperations()) {
	        QvtLibraryOperation qvtLibOp = new QvtLibraryOperation(this, libOp);

	        getQVTStandartLibrary().defineOperation(this, libOp,
	        		qvtLibOp.getContextType(), qvtLibOp.getReturnType(),
	        		libOp.getName(), qvtLibOp.getParamTypes());	        
		}
	}

	@Override
	public QvtOperationalEnvFactory getFactory() {
		return myFactory;
	}
	
	@Override
	protected void setFactory(EnvironmentFactory<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral,
			EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> factory) {
		super.setFactory(factory);
	}
	
	@Override
	public EOperation lookupOperation(EClassifier owner, String name,
			List<? extends TypedElement<EClassifier>> args) {
		getQVTStandartLibrary().lookupOperation(this, owner, name, args);
		
		// first try to lookup imperative operation with param's exact matching  
        UMLReflection<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint> uml = getUMLReflection();
		List<EOperation> lookupMappingOperations = lookupMappingOperations(owner, name);
		nextOpLabel:for (EOperation op : lookupMappingOperations) {
	        List<EParameter> params = op.getEParameters();
	        if (params.size() != args.size()) {
	            continue;
	        }
	        
	        for (int i = 0, n = params.size(); i < n; ++i) {
				EClassifier argType = args.get(i).getType();
				EClassifier popType = uml.getOCLType(params.get(i));
	            
	            if (!QvtOperationalParserUtil.isTypeEquals(this, argType, popType)) {
	                continue nextOpLabel;
	            }
	        }
	        return op;
		}
		// pass to super in case exact imperative operation wasn't found
		return super.lookupOperation(owner, name, args);
	}
	
    public List<EOperation> lookupMappingOperations(EClassifier owner, String name) {
        if (owner == null) {
            owner = getModuleContextType();//getOCLStandardLibrary().getOclVoid();
        }

        UMLReflection<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint> uml = getUMLReflection();
        List<EOperation> operations = TypeUtil.getOperations(this, owner);
        List<EOperation> result = new ArrayList<EOperation>();
        for (EOperation operation : operations) {
            if (uml.getName(operation).equals(name) && QvtOperationalUtil.isMappingOperation(operation)) {
                result.add(operation);
            }
        }

        return result;
    } 
    
    @Override
    protected void addedVariable(String name,
            Variable<EClassifier, EParameter> elem, boolean isExplicit) {
        super.addedVariable(name, elem, isExplicit);
        if (!getOCLStandardLibrary().getOclVoid().getName().equals(name)) {
            QvtVariableEntry newelem = new QvtVariableEntry(name, elem, isExplicit);
            myNamedElements.add(newelem);
        }
    }

    @Override
    public void deleteElement(String name) {
        for (Iterator<QvtVariableEntry> iter = myNamedElements.iterator(); iter.hasNext();) {
            QvtVariableEntry elem = iter.next();
            
            if (elem.getName().equals(name)) {
                iter.remove();
            }
        }
        super.deleteElement(name);
    }

    public Variable<EClassifier, EParameter> lookupImplicitSource() {
        for (int i = myNamedElements.size() - 1; i >= 0; i--) {
            QvtVariableEntry element = myNamedElements.get(i);
            Variable<EClassifier, EParameter> vdcl = element.getVariable();
            
            if (!element.isExplicit) {
                return vdcl;
            }
        }
        return null;
    }
    
	public void reportError(String message, int startOffset, int endOffset) {
		if (!myErrorRecordFlag) {
			return;
		}
		QvtOperationalEnv parent = this;
		while (parent.getInternalParent() != null) {
			parent = (QvtOperationalEnv) parent.getInternalParent();
		}
		parent.myErrorsList.add(new QvtMessage(message, QvtMessage.SEVERITY_ERROR, startOffset, endOffset-startOffset+1));
		
		// TODO #199408  Use traces in QVTParser instead of System.xxx output facilities
		//System.err.println("Error: " + message + ", Pos: " + startOffset + "-" + endOffset);
	}

	public void reportWarning(String message, int startOffset, int endOffset) {
		if (!myErrorRecordFlag) {
			return;
		}
		QvtOperationalEnv parent = this;
		while (parent.getInternalParent() != null) {
			parent = (QvtOperationalEnv) parent.getInternalParent();
		}
		parent.myWarningsList.add(new QvtMessage(message, QvtMessage.SEVERITY_WARNING, startOffset, endOffset-startOffset+1));

		// TODO #199408  Use traces in QVTParser instead of System.xxx output facilities
		//System.err.println("Warning: " + message + ", Pos: " + startOffset + "-" + endOffset);
	}

	public void reportError(String message, CSTNode node) {
		int startOffset = (node != null) ? node.getStartOffset() : 0;
		int endOffset = (node != null) ? node.getEndOffset() : 0;
		reportError(message, startOffset, endOffset);
	}

	public void reportWarning(String message, CSTNode node) {
		int startOffset = (node != null) ? node.getStartOffset() : 0;
		int endOffset = (node != null) ? node.getEndOffset() : 0;
		reportWarning(message, startOffset, endOffset);
	}

	public boolean hasErrors() {
		return myErrorsList != null && myErrorsList.isEmpty() == false;
	}
	
	public boolean hasWarnings() {
		return myWarningsList != null && myWarningsList.isEmpty() == false;
	}	
		
	public List<QvtMessage> getErrorsList() {
		return myErrorsList;
	}

	public List<QvtMessage> getWarningsList() {
		return myWarningsList;
	}
	
	public List<QvtMessage> getAllProblemMessages() {
		if(hasErrors() || hasWarnings()) {
			List<QvtMessage> result = new ArrayList<QvtMessage>();
			result.addAll(getErrorsList());
			result.addAll(getWarningsList());			
			return result;
		}
		
		return Collections.emptyList();
	}	
	
	public void registerModelParameters(Module module) {
		List<Variable<EClassifier, EParameter>> modelParameters = new ArrayList<Variable<EClassifier,EParameter>>(module.getModelParameter().size());
		for (ModelParameter modelParam : module.getModelParameter()) {
	        if (lookupLocal(modelParam.getName()) != null) {
	            reportError(NLS.bind(ValidationMessages.SemanticUtil_15,
	                    new Object[] { modelParam.getName() }),
	                    modelParam.getStartPosition(), modelParam.getEndPosition());
	        } else {
                Variable<EClassifier, EParameter> var = ExpressionsFactory.eINSTANCE.createVariable();
                var.setName(modelParam.getName());
                var.setType(getQVTStandartLibrary().getModelType());
                var.setRepresentedParameter(modelParam);
                modelParameters.add(var);
	        }
		}
		registerModelParametersImpl(modelParameters);
	}
	
	private void registerModelParametersImpl(List<Variable<EClassifier, EParameter>> modelParameters) {
		myModelParameters = modelParameters;
		for (Variable<EClassifier, EParameter> var : modelParameters) {
            addElement(var.getName(), var, true);
		}
	}
	

	public ModelParameter lookupModelParameter(String name, DirectionKind directionKind) {
		if(name == null) {
			return null;
		}
		
		for (Variable<EClassifier, EParameter> var : myModelParameters) {
			ModelParameter modelParam = (ModelParameter) var.getRepresentedParameter();
			if (directionKind == DirectionKind.OUT) {
				if (modelParam.getKind() == DirectionKind.IN) {
					continue;
				}
			}

			String nextParamName = modelParam.getName();
			if (nextParamName != null) {
				if (nextParamName.equals(name)) {
					return modelParam;
				}
			}
		}
		
		return null;
	}    
	
	/**
	 * Get names of all available extents of given direction kind in this
	 * environments.
	 * 
	 * @param directionKind
	 *            filtering condition to be satisfied by returned extents or
	 *            <code>null</code> if all kinds are acceptable
	 * @return list of corresponding model parameter names
	 */
	public List<String> getAllExtentNames(DirectionKind directionKind) {
		List<String> result = new ArrayList<String>(myModelParameters.size());
		for (Variable<EClassifier, EParameter> var : myModelParameters) {
			ModelParameter modelParam = (ModelParameter) var.getRepresentedParameter();
			if (directionKind == DirectionKind.OUT) {
				if (modelParam.getKind() == DirectionKind.IN) {
					continue;
				}
			}

			String nextParam = modelParam.getName();
			if(nextParam != null && nextParam.length() > 0) {
				result.add(nextParam);
			}
		}
		
		return Collections.unmodifiableList(result);
	}
	
	public ModelParameter resolveModelParameter(EClassifier type, DirectionKind directionKind) {
		if (!isMayBelongToExtent(type)) {
			return null;
		}
		EObject rootContainer = EcoreUtil.getRootContainer(type);
		
		// lookup explicit extent 
		for (Variable<EClassifier, EParameter> var : myModelParameters) {
			ModelParameter modelParam = (ModelParameter) var.getRepresentedParameter();
			if (directionKind == DirectionKind.OUT) {
				if (modelParam.getKind() == DirectionKind.IN) {
					continue;
				}
			}
			List<EPackage> metamodels = ModelTypeMetamodelsAdapter.getMetamodels(modelParam.getEType());
			if (!metamodels.isEmpty() && rootContainer == metamodels.get(0)) {
				return modelParam;
			}
		}
		
		// lookup implicit extent 
		for (Variable<EClassifier, EParameter> var : myModelParameters) {
			ModelParameter modelParam = (ModelParameter) var.getRepresentedParameter();
			if (directionKind == DirectionKind.OUT) {
				if (modelParam.getKind() == DirectionKind.IN) {
					continue;
				}
			}
			return modelParam;
		}
		
		return null;
	}    
	
	/**
	 * Register given modeltype in the Environment. Modeltype's registry is used in
	 * override {@link #lookupPackage(List)} and {@link #lookupClassifier(List)}
	 *  
	 */
	public void registerModelType(ModelType modelType, boolean isCheckDuplicates) {
		if (myModelTypeRegistry.containsKey(modelType.getName())) {
			if (isCheckDuplicates) {
				reportError(NLS.bind(ValidationMessages.QvtOperationalVisitorCS_modeltypeAlreadyDefined,
						new Object[] { modelType.getName() }),
						modelType.getStartPosition(), modelType.getEndPosition());
			}
		}
		else {
			myModelTypeRegistry.put(modelType.getName(), modelType);
		}
	}

	public ModelType getModelType(List<String> path) {
		if (path.isEmpty()) {
			return null;
		}
		return myModelTypeRegistry.get(path.get(0));
	}
	
	// TODO This stub fixes stack overflow in recurrent calls. Should be fixed in OCL
	private final Set<String> myLookupOperationNames = new HashSet<String>(1);
	@Override
	public Variable<EClassifier, EParameter> lookupImplicitSourceForOperation(
			String name, List<? extends TypedElement<EClassifier>> args) {
		if (myLookupOperationNames.contains(name)) {
			return null;
		}
		// propagate implict source lookup to parent, allowing to reach the module-wide 'this'
		Variable<EClassifier, EParameter> result = null;
		try {
			myLookupOperationNames.add(name);
			result = super.lookupImplicitSourceForOperation(name, args);
			if(result == null && getParent() != null) {
				result = getParent().lookupImplicitSourceForOperation(name, args);
			}
		}
		finally {
			myLookupOperationNames.remove(name);
		}
		return result;
	}
	
	// TODO This stub fixes stack overflow in recurrent calls. Should be fixed in OCL
	private final Set<String> myLookupPropertyNames = new HashSet<String>(1);
	@Override
	public Variable<EClassifier, EParameter> lookupImplicitSourceForProperty(
			String name) {
		if (myLookupPropertyNames.contains(name)) {
			return null;
		}
		Variable<EClassifier, EParameter> implicitSource = null;
		try {
			myLookupPropertyNames.add(name);
			implicitSource = super.lookupImplicitSourceForProperty(name);
			if(implicitSource == null && getParent() != null) {
				implicitSource = getParent().lookupImplicitSourceForProperty(name);
			}			
		}
		finally {
			myLookupPropertyNames.remove(name);
		}
		return implicitSource;
	}
	
	// TODO This stub fixes stack overflow in recurrent calls. Should be fixed in OCL
	private final Set<String> myLookupAssocClassNames = new HashSet<String>(1);
	@Override
	public Variable<EClassifier, EParameter> lookupImplicitSourceForAssociationClass(
			String name) {
		if (myLookupAssocClassNames.contains(name)) {
			return null;
		}
		Variable<EClassifier, EParameter> implicitSource = null;
		try {
			myLookupAssocClassNames.add(name);
			implicitSource = super.lookupImplicitSourceForAssociationClass(name);
		}
		finally {
			myLookupAssocClassNames.remove(name);
		}
		return implicitSource;
	}
	
	@Override
	public EClassifier lookupClassifier(List<String> names) {

		if (names.size() == 1) {
			// Unqualified type. lookup rules:
			// - Firstly a type definition existing at the level of the current module (a transformation or a library) is searched.
			// - If not found, all the packages of the model types declared in the module are recursively visited to found a type with the same name.  
			
			// TODO support intermediate classes (hosted in implicit '_INTERMEDIATE' package)
			
			if (myModelTypeRegistry.containsKey(names.get(0))) {
				return myModelTypeRegistry.get(names.get(0));
			}

			for (ModelType modelType : myModelTypeRegistry.values()) {
				EClassifier lookupClassifier = doLookupModeltypeClassifier(modelType, names);
				if (lookupClassifier != null) {
					return lookupClassifier;
				}
			}
		}
		
		if (names.size() > 1) {
			// Qualified type 
			// It is possible to either qualify the type name with a model type or a package name

			if (myModelTypeRegistry.containsKey(names.get(0))) {
				EClassifier lookupClassifier = doLookupModeltypeClassifier(
						myModelTypeRegistry.get(names.get(0)), names.subList(1, names.size()));
				if (lookupClassifier != null) {
					return lookupClassifier;
				}
			}
		}
		
		return super.lookupClassifier(names);
	}
	
	@Override
	public EPackage lookupPackage(List<String> path) {
        if (path.size() > 1) {
            // Qualified type 
            // It is possible to either qualify the type name with a model type or a package name

            if (myModelTypeRegistry.containsKey(path.get(0))) {
                EPackage lookupPackage = doLookupModeltypePackage(
                        myModelTypeRegistry.get(path.get(0)), path.subList(1, path.size()));
                if (lookupPackage != null) {
                    return lookupPackage;
                }
            }
        }

        return super.lookupPackage(path);
	}

    private EPackage doLookupModeltypePackage(ModelType modelType, List<String> path) {
        return lookupPackageableElement(modelType, path, LOOKUP_PACKAGE_DELEGATE);
    }
    
    private EClassifier doLookupModeltypeClassifier(ModelType modelType, List<String> path) {
		return lookupPackageableElement(modelType, path, LOOKUP_CLASSIFIER_DELEGATE);
	}
	
    private <T extends EObject> T lookupPackageableElement(ModelType modelType, List<String> path, LookupPackageableElementDelegate<T> lookupPackageableElementDelegate) {
        EPackage oldContext = super.getContextPackage();
        T result = null;
        List<EPackage> metamodels = ModelTypeMetamodelsAdapter.getMetamodels(modelType);
        for (EPackage pkg : metamodels) {
            super.setContextPackage(pkg);
            result = lookupPackageableElementDelegate.lookupPackageableElement(path);
            EObject eContainer = result;
            for (int i = 0, n = path.size(); i < n; ++i) {
            	if (eContainer == null) {
            		break;
            	}
            	eContainer = eContainer.eContainer();
            }
            if (eContainer != pkg) {
                result = null;
            }
            if (result != null) {
                break;
            }
        }
        super.setContextPackage(oldContext);
        
        return result;
    }
    
	public EOperation defineImperativeOperation(ImperativeOperation operation, boolean isMappingOperation,
			boolean isCheckDuplicates) {
		EClassifier contextType = operation.getContext().getEType();
		if (contextType == null) {
			contextType = getModuleContextType();
		}
		
		Constraint constraint = EcoreFactory.eINSTANCE.createConstraint();
		constraint.setStereotype(isMappingOperation ?
				QvtOperationalEnv.MAPPING_OPERATION_STEREOTYPE : QvtOperationalEnv.IMPERATIVE_OPERATION_STEREOTYPE);

		EOperation newOperation = operation;
		EOperation addOperation = addOperation(contextType, newOperation, isCheckDuplicates);
		
		if (isCheckDuplicates) {
			CollisionStatus collidingOperStatus = findCollidingOperation(contextType, newOperation);
			if(collidingOperStatus != null) {
				if(collidingOperStatus.getCollisionKind() == CollisionStatus.ALREADY_DEFINED) {
					reportError(NLS.bind(ValidationMessages.SemanticUtil_0, new Object[] {
									operation.getName(), contextType.getName() }),
									operation.getStartPosition(), operation.getEndPosition());
				} 
				else if(collidingOperStatus.getCollisionKind() == CollisionStatus.VIRTUAL_METHOD_RETURNTYPE) {
					reportError(NLS.bind(ValidationMessages.ReturnTypeMismatch,  
							operation.getName(), QvtOperationalTypesUtil.getTypeFullName(collidingOperStatus.getOperation().getEType())), 
							operation.getStartPosition(), operation.getEndPosition());
				}
				return null;
			} 
		}
		return addOperation;
	}
	
	@Override
	public EClass getModuleContextType() {
		return getParent() instanceof QvtOperationalEnv ? ((QvtOperationalEnv)getParent()).getModuleContextType() : null;
	}	
		
	public void defineOperationParameters(ImperativeOperation operation) {
		for (EParameter parameter : operation.getEParameters()) {
	        if (lookupLocal(parameter.getName()) != null) {
	            reportError(NLS.bind(ValidationMessages.SemanticUtil_15,
	                    new Object[] { parameter.getName() }),
	                    ((VarParameter) parameter).getStartPosition(), ((VarParameter) parameter).getEndPosition());
	        } else {
				Variable<EClassifier, EParameter> var = ExpressionsFactory.eINSTANCE.createVariable();
				var.setName(parameter.getName());
				var.setType(parameter.getEType());
				var.setRepresentedParameter(parameter);
				addElement(parameter.getName(), var, true);
	        }
		}
	}
	
	public QvtOperationalEnv createOperationEnvironment(VarParameter context) {
		QvtOperationalEnv newEnvironment = new QvtOperationalEnv(this);

		if(context.getEType() != getModuleContextType()) {
			// define self implicit source only in case if context-less operations
			Variable<EClassifier, EParameter> var = ExpressionsFactory.eINSTANCE.createVariable();
			var.setName(Environment.SELF_VARIABLE_NAME);
			var.setType(context.getEType());
			var.setRepresentedParameter(context);
			newEnvironment.addElement(var.getName(), var, false);
		}		
		if (context != getOCLStandardLibrary().getOclVoid()) {
			Variable<EClassifier, EParameter> mainVar = ExpressionsFactory.eINSTANCE.createVariable();
			mainVar.setName(getOCLStandardLibrary().getOclVoid().getName());
			mainVar.setType(getOCLStandardLibrary().getOclVoid());
			newEnvironment.addElement(mainVar.getName(), mainVar, false);
		}
		newEnvironment.registerModelParametersImpl(myModelParameters);
		return newEnvironment;
	}
    
	public void addInitVariable(VariableInitExp varInit) {
		if (varInit.getName() != null) {
			Variable<EClassifier, EParameter> var = ExpressionsFactory.eINSTANCE.createVariable();
			var.setName(varInit.getName());
			var.setType(varInit.getType());
			addElement(varInit.getName(), var, true);
		}
	}

	public void setErrorRecordFlag(boolean isRecordError) {
		myErrorRecordFlag = isRecordError;
	}
	
	public void registerMappingOperation(MappingOperation operation) {
	    if (getParent() != null) {
	        ((QvtOperationalEnv) getParent()).registerMappingOperation(operation);
	    } else {
            MappingsMapKey key = new MappingsMapKey(operation.getContext().getEType(), operation.getName());
            List<MappingOperation> sameNameAndContextOperations = myMappingsMap.get(key);
            if (sameNameAndContextOperations == null) {
                sameNameAndContextOperations = new ArrayList<MappingOperation>();
                myMappingsMap.put(key, sameNameAndContextOperations);
            }
            sameNameAndContextOperations.add(operation);
	    }
	}
	
	public void registerResolveInExp(ResolveInExp resolveInExp, EClassifier referredMappingContextType, String mappingName) {
        if (getParent() != null) {
            ((QvtOperationalEnv) getParent()).registerResolveInExp(resolveInExp, referredMappingContextType, mappingName);
        } else {
            myResolveInExps.put(resolveInExp, new MappingsMapKey(referredMappingContextType, mappingName));
        }
	}
	
	public void resolveResolveInExpInMappings() {
	    if (getParent() == null) {
	        for (Map.Entry<ResolveInExp, MappingsMapKey> entry : myResolveInExps.entrySet()) {
	            MappingsMapKey mappingsMapKey = entry.getValue();
	            List<MappingOperation> sameNameAndContextOperations = myMappingsMap.get(mappingsMapKey);
	            if (sameNameAndContextOperations != null) {
	                for (MappingOperation mappingOperation : sameNameAndContextOperations) {
	                    ResolveInExp resolveInExp = entry.getKey();
	                    resolveInExp.getInMappings().add(mappingOperation);
	                }
	            }
	        }
	    } else {
	    	((QvtOperationalEnv)getParent()).resolveResolveInExpInMappings();
	    }
	}
	
	@Override
	protected ProblemHandler createDefaultProblemHandler(AbstractParser parser) {
		return new AbstractProblemHandler(parser) {
			@Override
			public void handleProblem(Severity problemSeverity, Phase processingPhase, String problemMessage,					
					String processingContext, int startOffset, int endOffset) {
				
				if(problemSeverity == Severity.INFO || problemSeverity == Severity.OK || problemSeverity == Severity.WARNING) {
					reportWarning(problemMessage, startOffset, endOffset);
				} else {
					reportError(problemMessage, startOffset, endOffset);					
				}
			}
		};
	}
	

	private boolean isMayBelongToExtent(EClassifier myType) {
		return myType != null 
			&& getOCLStandardLibrary().getOclVoid() != myType
			&& getOCLStandardLibrary().getOclInvalid() != myType;
	}
	
	private void defineStandartOperations() {
		getQVTStandartLibrary().defineStandartOperations(this);
	}

	private EOperation addOperation(EClassifier owner, EOperation operation, boolean fake) {
		if (getParent() instanceof QvtOperationalEnv) {
			// propagate additional operations as high as possible so that they
			//    will be accessible to all child environments of the root
			return ((QvtOperationalEnv) getParent()).addOperation(owner, operation, fake);
		} else {
			return getTypeResolver().resolveAdditionalOperation(owner, operation);
		}
	}	

	
	protected final QvtOperationalEnv myRootEnvironment;
	
	private final List<QvtMessage> myWarningsList;
	private final List<QvtMessage> myErrorsList;
	private boolean myErrorRecordFlag;

	private Map<String, ModelType> myModelTypeRegistry;
	private List<Variable<EClassifier, EParameter>> myModelParameters = Collections.emptyList();
	
	private final EPackage.Registry ePackageRegistry;
    private final Map<MappingsMapKey, List<MappingOperation>> myMappingsMap = new HashMap<MappingsMapKey, List<MappingOperation>>();
    private final Map<ResolveInExp, MappingsMapKey> myResolveInExps = new HashMap<ResolveInExp, MappingsMapKey>();

    private static final QvtOperationalEnvFactory myFactory = new QvtOperationalEnvFactory();
    
    private interface LookupPackageableElementDelegate<T> {
        public T lookupPackageableElement(List<String> names);
    };

    private final LookupPackageableElementDelegate<EClassifier> LOOKUP_CLASSIFIER_DELEGATE = new LookupPackageableElementDelegate<EClassifier>() {
        public EClassifier lookupPackageableElement(List<String> names) {
            return QvtOperationalEnv.super.lookupClassifier(names);
        }
    };

    private final LookupPackageableElementDelegate<EPackage> LOOKUP_PACKAGE_DELEGATE = new LookupPackageableElementDelegate<EPackage>() {
        public EPackage lookupPackageableElement(List<String> names) {
            return QvtOperationalEnv.super.lookupPackage(names);
        }
    };
    
    /*
     * The constructor of VariableEntry (which was duplicated in the code below) in AbstractEnvironment  
     * is package visible and something like that must be created in QvtOperationalEnv.
     * The whole construction (QvtVariableEntry, addedVariable, removedVariable and myNamedElements) 
     * is necessary for lookup for implicit source in ResolveExps.
     */
    protected final class QvtVariableEntry {
        private final String myName;
        private final Variable<EClassifier, EParameter> myVariable;
        private final boolean isExplicit;
        
        public QvtVariableEntry(String name, Variable<EClassifier, EParameter> variable, boolean isExplicit) {
            this.myName = name;
            this.myVariable = variable;
            this.isExplicit = isExplicit;
        }

        public String getName() {
            return myName;
        }

        public Variable<EClassifier, EParameter> getVariable() {
            return myVariable;
        }

        public boolean isExplicit() {
            return isExplicit;
        }
    }
    
	@Override
	public void analyzerError(String problemMessage, String problemContext, Object problemObject) {
		CSTNode cstNode = getASTMapping(problemObject);
		int startOffset = cstNode != null ? cstNode.getStartOffset() : -1;
		int endOffset = cstNode != null ? cstNode.getEndOffset() : -1;
		
		if(cstNode == null && problemObject instanceof ASTNode) {
			ASTNode astNode = (ASTNode) problemObject;
			startOffset = astNode.getStartPosition();
			endOffset = astNode .getEndPosition();
		}
		
		analyzerError(problemMessage, problemContext, startOffset, endOffset);
	}
	
	@Override
	public void initASTMapping(Object astNode, CSTNode cstNode) {	
		if(astNode instanceof ASTNode) {
			ASTNode castNode = (ASTNode) astNode;
			if(castNode.getEndPosition() < 0) {
				castNode.setStartPosition(cstNode.getStartOffset());
				castNode.setEndPosition(cstNode.getEndOffset());				
			}
		}
		super.initASTMapping(astNode, cstNode);
	}
    
}
