/*******************************************************************************
 * Copyright (c) 2009, 2018 R.Dvorak and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 *     Radek Dvorak - initial API and implementation
 *******************************************************************************/
package org.eclipse.m2m.qvt.oml.debug.core.vm;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.m2m.internal.qvt.oml.ast.env.InternalEvaluationEnv;
import org.eclipse.m2m.internal.qvt.oml.ast.env.QvtOperationalEnv;
import org.eclipse.m2m.internal.qvt.oml.ast.env.QvtOperationalEvaluationEnv;
import org.eclipse.m2m.internal.qvt.oml.evaluator.ModuleInstance;
import org.eclipse.m2m.internal.qvt.oml.evaluator.TransformationInstance;
import org.eclipse.m2m.internal.qvt.oml.expressions.ContextualProperty;
import org.eclipse.m2m.internal.qvt.oml.expressions.Library;
import org.eclipse.m2m.internal.qvt.oml.expressions.Module;
import org.eclipse.m2m.internal.qvt.oml.expressions.OperationalTransformation;
import org.eclipse.ocl.Environment;
import org.eclipse.ocl.ecore.CallOperationAction;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.SendSignalAction;
import org.eclipse.ocl.types.OCLStandardLibrary;

public class UnitLocationExecutionContext implements VMFrameExecutionContext {

	private final Environment<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral,
			EParameter,EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> fEnv;
	private final QvtOperationalEvaluationEnv fEvalEnv;

	UnitLocationExecutionContext(
			Environment<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral,
					EParameter,EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> env,
			QvtOperationalEvaluationEnv evalEnv) {
		if (env == null || evalEnv == null) {
			throw new IllegalArgumentException();
		}
		
		fEnv = env;
		fEvalEnv = evalEnv;
	}
	
	public EClassifier getOCLType(EStructuralFeature feature) {
		return fEnv.getUMLReflection().getOCLType(feature);
	}
	
	public OCLStandardLibrary<EClassifier> getStandardLibrary() {	
		return fEnv.getOCLStandardLibrary();
	}
	
	public List<EStructuralFeature> getAllFeatures(EClass eClass) {
        List<EStructuralFeature> features = new ArrayList<>(eClass.getEAllStructuralFeatures());

		if (eClass instanceof Module module) {
            features.removeIf(feature -> feature instanceof ContextualProperty);

			collectFeaturesInImports(features, module);
		}
		
		collectIntermediateProperties(features, eClass);
		return features;
	}

	public QvtOperationalEvaluationEnv getEvalEnv() {
		return fEvalEnv;
	}

	public Object getValue(EStructuralFeature feature, EObject target) {
		return fEvalEnv.navigateProperty(feature, null, target);
	}

	private void collectFeaturesInImports(List<EStructuralFeature> features, Module module) {
		for (var moduleImport : module.getModuleImport()) {
			var importedModule = moduleImport.getImportedModule();
			var featuresInModule = importedModule.getEAllStructuralFeatures();
			features.addAll(featuresInModule);
			
			collectFeaturesInImports(features, importedModule);
		}
	}

	private void collectIntermediateProperties(List<EStructuralFeature> properties, EClass targetClass) {
        InternalEvaluationEnv internEvalEnv = fEvalEnv.getAdapter(InternalEvaluationEnv.class);
		
		ModuleInstance currentModule = internEvalEnv.getCurrentModule();
		// check if we are in transformation execution context
		if(currentModule instanceof TransformationInstance) {
			collectIntermediateProperties(properties, targetClass, (TransformationInstance) currentModule);
		}
	}
	
	// TODO - build a cache for all target types to avoid repeated lookups
	private void collectIntermediateProperties(List<EStructuralFeature> properties, EClass targetClass, TransformationInstance scopeModule) {
		OperationalTransformation operationalTransformation = scopeModule.getTransformation();
		for (EStructuralFeature nextProperty : operationalTransformation.getIntermediateProperty()) {
			if(nextProperty instanceof ContextualProperty ctxProperty) {
                if(ctxProperty.getContext().isSuperTypeOf(targetClass)) {
		        	properties.add(ctxProperty);
				}
			}
		}

		for (ModuleInstance importedModule : scopeModule.getImportedModules()) {
			if(importedModule instanceof TransformationInstance) {
				// Note: only transformation imported by <extends> have implicit module instances
				collectIntermediateProperties(properties, targetClass, (TransformationInstance) importedModule);
			}
		}
	}	
}
