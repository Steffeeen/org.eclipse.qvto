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
package org.eclipse.m2m.tests.qvt.oml.traces;

import java.util.Arrays;

import org.eclipse.m2m.internal.qvt.oml.trace.Trace;
import org.eclipse.m2m.tests.qvt.oml.transform.FileToFileData;
import org.eclipse.m2m.tests.qvt.oml.transform.ModelTestData;
import org.eclipse.m2m.tests.qvt.oml.transform.TestQvtInterpreter;
import org.eclipse.m2m.tests.qvt.oml.transform.TestTransformation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


@RunWith(Parameterized.class)
public class TestTraceFileForMyUml extends TestTransformation {
	
	public TestTraceFileForMyUml(ModelTestData data) {
		super(data);
	}
	
	@Parameters(name="{0}")
	public static Iterable<ModelTestData> data() {
		return Arrays.<ModelTestData>asList(
			new FileToFileData("multipletracerecords"), //$NON-NLS-1$
			new FileToFileData("mappingWithNoResultTrace_266854") //$NON-NLS-1$
		);
	}
	
	@Test
	public void testHasTraces() throws Exception {
		ITransformer transformer = TestQvtInterpreter.getDefaultTransformer(getData());
        Trace trace = TraceUtil.transform(getData(), getProject(), transformer);
		assertTrue("No traces", !trace.getTraceRecords().isEmpty()); //$NON-NLS-1$
	}
}
