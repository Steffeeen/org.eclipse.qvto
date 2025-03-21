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

import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.m2m.internal.qvt.oml.editor.ui.hyperlinks.ImportHyperlinkDetector;
import org.eclipse.m2m.qvt.oml.editor.ui.hovers.IElementInfoProvider;
import org.eclipse.ocl.cst.CSTNode;


public class ModuleImportInfoProvider implements IElementInfoProvider {

	public Object getElementInfo(final Object element, ITextViewer textViewer, IRegion region) {
		if (element instanceof CSTNode) {
			CSTNode syntaxElement = (CSTNode) element;
			URI unitURI = ImportHyperlinkDetector.findDefinition(syntaxElement);
			if(unitURI != null) {
				if(unitURI.isPlatformResource()) {
					return unitURI.toPlatformString(true);
				}
				
				return unitURI.toString();
			}
		}
		
		return null;
	}
}
