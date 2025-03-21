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

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.m2m.internal.qvt.oml.editor.ui.CSTHelper;
import org.eclipse.m2m.internal.qvt.oml.editor.ui.hyperlinks.ModelTypeHyperlinkDetector;
import org.eclipse.m2m.qvt.oml.editor.ui.hovers.IElementInfoProvider;
import org.eclipse.ocl.cst.CSTNode;
import org.eclipse.ocl.ecore.EcoreEnvironment;


public class ModelTypeInfoProvider implements IElementInfoProvider {

	public Object getElementInfo(final Object element, ITextViewer textViewer, IRegion region) {
		if (element instanceof CSTNode) {
			CSTNode syntaxElement = (CSTNode) element;
			EPackage ePackage = ModelTypeHyperlinkDetector.findReferencedPackageDefinition(syntaxElement);
			
			if(ePackage != null) {
				EcoreEnvironment env = CSTHelper.getEnvironment(syntaxElement);				
				if(env != null) {
					return SignatureUtil.getPackageSignature(env, ePackage);
				}
			}
		}
		
		return null;
	}
}
