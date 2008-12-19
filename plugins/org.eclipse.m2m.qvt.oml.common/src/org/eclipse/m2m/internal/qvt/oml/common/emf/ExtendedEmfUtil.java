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
package org.eclipse.m2m.internal.qvt.oml.common.emf;

import java.io.IOException;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.m2m.internal.qvt.oml.common.CommonPlugin;
import org.eclipse.m2m.internal.qvt.oml.common.MdaException;
import org.eclipse.m2m.internal.qvt.oml.common.io.CFile;
import org.eclipse.m2m.internal.qvt.oml.emf.util.EmfException;
import org.eclipse.m2m.internal.qvt.oml.emf.util.EmfUtil;
import org.eclipse.m2m.internal.qvt.oml.emf.util.ModelContent;


/**
 * @author pkobiakov
 */
public class ExtendedEmfUtil {
    private ExtendedEmfUtil() {}
    
	public static ModelContent loadModel(CFile modelFile) {
		try {
			URI uri = URI.createURI(modelFile.getFileStore().toURI().toString());
			return EmfUtil.loadModel(uri);
		} catch (IOException e) {
			return null;
		}
    }
    
    public static void saveModel(Resource extent, CFile file) throws MdaException {
    	Map options = EmfUtil.DEFAULT_SAVE_OPTIONS;
        try {
        	URI uri = URI.createURI(file.getFileStore().toURI().toString());
			EmfUtil.saveModel(extent, uri, options);
		} catch (EmfException e) {
			throw new MdaException(e);
		} catch (IOException e) {
			throw new MdaException(e);
		}
        
        try {
            file.refresh();
        } 
        catch (Exception e) {
            throw new MdaException(CommonPlugin.getResourceString("EmfUtil.0", new Object[] {file}), e); //$NON-NLS-1$
        }
    }

    public static void saveModel(EObject eObject, CFile file) throws MdaException {
        saveModel(eObject, file, EmfUtil.DEFAULT_SAVE_OPTIONS);
    }

    public static void saveModel(EObject eObject, CFile file, Map options) throws MdaException {
        try {
        	URI uri = URI.createURI(file.getFileStore().toURI().toString());
			EmfUtil.saveModel(eObject, uri, options);
		} catch (EmfException e) {
			throw new MdaException(e);
		} catch (IOException e) {
			throw new MdaException(e);
		}
        
        try {
            file.refresh();
        } 
        catch (Exception e) {
            throw new MdaException(CommonPlugin.getResourceString("EmfUtil.0", new Object[] {file}), e); //$NON-NLS-1$
        }
    }
}
