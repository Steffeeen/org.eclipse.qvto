/*******************************************************************************
 * Copyright (c) 2007, 2018 Borland Software Corporation and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Borland Software Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.m2m.internal.qvt.oml.editor.ui.hovers;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextHoverExtension;
import org.eclipse.jface.text.ITextHoverExtension2;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.m2m.internal.qvt.oml.QvtPlugin;
import org.eclipse.m2m.internal.qvt.oml.compiler.CompiledUnit;
import org.eclipse.m2m.internal.qvt.oml.editor.ui.Activator;
import org.eclipse.m2m.internal.qvt.oml.editor.ui.CSTHelper;
import org.eclipse.m2m.internal.qvt.oml.editor.ui.QvtDocumentProvider;
import org.eclipse.m2m.qvt.oml.editor.ui.hovers.IElementInfoProvider;
import org.eclipse.ocl.cst.CSTNode;


public class QvtTextHover implements ITextHover, ITextHoverExtension, ITextHoverExtension2 {
	
	private final static String QVT_EDITOR_ELEMENT_INFO_PROVIDERS_EXTENSION_POINT = "org.eclipse.m2m.qvt.oml.editor.ui.qvtEditorElementInfoProviders";

	private final static String ELEMENT_INFO_PROVIDER_TAG = "elementInfoProvider";

	private static final String CLASS_ATTRIBUTE = "class";

	private final static List<IElementInfoProvider> DEFAULT_ELEMENT_INFO_PROVIDERS = List.of(
			new OperationCallInfoProvider(),
    		new PropertyCallInfoProvider(),
    		new VariableExpressionInfoProvider(),
    		new PatternPropertyExpressionInfoProvider(),
    		new PathNameInfoProvider(),
    		new ModuleImportInfoProvider(),
    		new ResolveInMappingInfoProvider(),
    		new ModelTypeInfoProvider()
	);

    private final QvtDocumentProvider myDocumentProvider;
	private final List<IElementInfoProvider> elementInfoProviders;
	
	private IElementInfoProvider lastUsedProvider;

    public QvtTextHover(final QvtDocumentProvider documentProvider) {
        myDocumentProvider = documentProvider;
		elementInfoProviders = new ArrayList<>(DEFAULT_ELEMENT_INFO_PROVIDERS);
		elementInfoProviders.addAll(getInfoProvidersFromExtensionPoint());
		elementInfoProviders.sort(Comparator.comparingInt(IElementInfoProvider::getPriority).reversed());
    }

    public IRegion getHoverRegion(final ITextViewer textViewer, final int offset) {
        return new Region(offset, 0);
    }
        
	public String getHoverInfo(final ITextViewer textViewer, final IRegion hoverRegion) {
		// getHoverInfo2 javadoc:
		// Callers should ignore the text returned by
		// ITextHover.getHoverInfo(ITextViewer, IRegion).
		return "";
    }
    
	public Object getHoverInfo2(final ITextViewer textViewer, final IRegion hoverRegion) {
		lastUsedProvider = null;

        if (checkCompiledUnit(myDocumentProvider.getCompiledModule()) && 
        	textViewer != null && textViewer.getDocument() != null) {
	        
	        Annotation annotation = getAnnotation(textViewer, hoverRegion.getOffset());
        	if (annotation != null) {
        		return annotation.getText();
        	}
        	
        	CSTNode rootCS = myDocumentProvider.getCompiledModule().getUnitCST();
        	List<CSTNode> elements = CSTHelper.selectTargetedElements(rootCS, hoverRegion);
        	if(!elements.isEmpty()) {
        		try {
        			return getElementsInfo(elements, textViewer, hoverRegion);
				} catch (Exception e) {
					Activator.log(e);
				}
        	}
        }
       
        return ""; //$NON-NLS-1$
    }
    
    public Annotation getAnnotation(final ITextViewer textViewer, final int offset) {
    	if (textViewer instanceof ISourceViewer) {
 			final IAnnotationModel annotationModel = ((ISourceViewer) textViewer).getAnnotationModel();
 			if (annotationModel == null) {
 				return null;
 			}
 			ArrayList<Annotation> annotations = new ArrayList<Annotation>();
 			for (Iterator<?> iter = annotationModel.getAnnotationIterator(); iter.hasNext();) {
 				Annotation annotation = (Annotation)iter.next();
 				if (annotation.isPersistent() && !annotation.isMarkedDeleted()) {
	 				Position position = annotationModel.getPosition(annotation);
	 				if (position != null && position.includes(offset)) {
	 					annotations.add(annotation);
	 				}
 				}
 			}
 			if (!annotations.isEmpty()) { 
	 			Collections.sort(annotations, new Comparator<Annotation>() {
					public int compare(final Annotation o1, final Annotation o2) {
						Position p1 = annotationModel.getPosition(o1);
						Position p2 = annotationModel.getPosition(o2);
						return p1.getLength() - p2.getLength();
					}
	 			});
	 			return (Annotation) annotations.get(0);
 			}
 		}
    	return null;
    }

    
	private Object getElementsInfo(final List<CSTNode> elements, ITextViewer textViewer, IRegion hoverRegion) {
    	for (CSTNode nextElement : elements) {
			for (IElementInfoProvider provider : elementInfoProviders) {
    			try {
					Object info = provider.getElementInfo(nextElement, textViewer, hoverRegion);
					if (info != null) {
						lastUsedProvider = provider;
    					return info;
    				}
    			} catch (NullPointerException e) {
    				// ignore
    			}
    		}    		
		}
		return null;
    }
    
    private boolean checkCompiledUnit(final CompiledUnit unit) {
        return unit != null && unit.getUnitCST() != null;
    }    

	private List<IElementInfoProvider> getInfoProvidersFromExtensionPoint() {
		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		List<IConfigurationElement> configurationElements = Arrays.asList(extensionRegistry.getConfigurationElementsFor(QVT_EDITOR_ELEMENT_INFO_PROVIDERS_EXTENSION_POINT));
		return createInfoProviders(configurationElements);
	}

	private List<IElementInfoProvider> createInfoProviders(List<IConfigurationElement> configurationElements) {
		var results = new ArrayList<IElementInfoProvider>();

		for (var element : configurationElements) {
			if (ELEMENT_INFO_PROVIDER_TAG.equals(element.getName())) {
				try {
					var elementInfoProvider = (IElementInfoProvider) element.createExecutableExtension(CLASS_ATTRIBUTE);
					results.add(elementInfoProvider);
				} catch (CoreException e) {
					QvtPlugin.getDefault().log(new Status(IStatus.ERROR, QvtPlugin.ID, "Could not create QVT editor element info provider", e));
				}
			}
		}

		return results;
	}

	@Override
	public IInformationControlCreator getHoverControlCreator() {
		if (lastUsedProvider == null) {
			return parent -> new DefaultInformationControl(parent, (String) null);
		}
		return lastUsedProvider.getHoverControlCreator();
	}
}
