/*******************************************************************************
 * Copyright (c) 2009, 2018 Borland Software Corporation and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *   
 * Contributors:
 *     Borland Software Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.m2m.tests.qvt.oml.perf;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.m2m.qvt.oml.BasicModelExtent;
import org.eclipse.m2m.qvt.oml.ExecutionContextImpl;
import org.eclipse.m2m.qvt.oml.ExecutionDiagnostic;
import org.eclipse.m2m.qvt.oml.TransformationExecutor;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * @author dvorak
 */
public class ExecutionTimeGuardTest extends TestCase {
	
	private static boolean IS_QVTO_INITIALIZED;
	
	public ExecutionTimeGuardTest(String name) {
		super(name);
	}
	
	@Override
	@Before
	protected void setUp() throws Exception {	
		super.setUp();
		
		if(!IS_QVTO_INITIALIZED) {
			assertSuccessExecutionTime("WarmUp.qvto", 8000); //$NON-NLS-1$
			IS_QVTO_INITIALIZED = true;
		}
	}
	
	@Test
	public void testTraceLookup_287589() throws Exception {
		assertSuccessExecutionTime("traceLookup_287589.qvto", 5000); //$NON-NLS-1$
	}
	
	private static void assertSuccessExecutionTime(String transfPath, long limitInMs) {
		URI uri = URI.createPlatformPluginURI("org.eclipse.m2m.tests.qvt.oml/deployed/perf/" + transfPath, false); //$NON-NLS-1$
		TransformationExecutor executor = new TransformationExecutor(uri);
		ExecutionContextImpl context = new ExecutionContextImpl();
		
		executor.loadTransformation();
		long start = System.currentTimeMillis();		
		ExecutionDiagnostic result = executor.execute(context, new BasicModelExtent());		
		long end = System.currentTimeMillis();
		
		assertTrue(result.getSeverity() == Diagnostic.OK);
		long elapsedTime = end - start;
		assertTrue("Expected time less then:" + limitInMs + " actual time: " + elapsedTime, //$NON-NLS-1$ //$NON-NLS-2$
			limitInMs >= elapsedTime); 
	}
}
