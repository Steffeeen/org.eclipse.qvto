package org.eclipse.m2m.internal.tests.qvt.oml.debugger;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.Launch;
import org.eclipse.m2m.internal.qvt.oml.common.io.FileUtil;
import org.eclipse.m2m.internal.qvt.oml.common.launch.IQvtLaunchConstants;
import org.eclipse.m2m.internal.qvt.oml.emf.util.URIUtils;
import org.eclipse.m2m.internal.qvt.oml.project.QVTOProjectPlugin;
import org.eclipse.m2m.internal.qvt.oml.runtime.launch.QvtLaunchConfigurationDelegate;
import org.eclipse.m2m.qvt.oml.debug.core.QVTOBreakpoint;
import org.eclipse.m2m.qvt.oml.debug.core.QVTODebugCore;
import org.eclipse.m2m.qvt.oml.debug.core.launch.QVTODebugConfiguration;
import org.eclipse.m2m.qvt.oml.debug.core.srclookup.QVTOSourceLookupDirector;
import org.eclipse.m2m.tests.qvt.oml.AllTests;
import org.eclipse.m2m.tests.qvt.oml.util.TestUtil;
import org.eclipse.ui.PlatformUI;
import org.junit.After;
import org.junit.BeforeClass;

public class DebuggerTest {

	private static IFile simpleUmlToRdbTransformationFile;
	private static IFile libraryWithPropertiesTransformationFile;

	protected interface TestModel {
		IFile transformationFile();

		String targetModel1();

		String targetModel2();

		static final TestModel SIMPLE_UML_TO_RDB = new TestModel() {

			@Override
			public IFile transformationFile() {
				return simpleUmlToRdbTransformationFile;
			}

			@Override
			public String targetModel1() {
				return "platform:/resource/SimpleUMLToRDB/pim.simpleuml";
			}

			@Override
			public String targetModel2() {
				return "platform:/resource/SimpleUMLToRDB/Simpleuml_To_Rdb.rdb";
			}
		};

		static final TestModel LIBRARY_WITH_PROPERTIES = new TestModel() {

			@Override
			public IFile transformationFile() {
				return libraryWithPropertiesTransformationFile;
			}

			@Override
			public String targetModel1() {
				return "platform:/resource/libraryWithProperties/source.ecore";
			}

			@Override
			public String targetModel2() {
				return "platform:/resource/libraryWithProperties/target.ecore";
			}
		};
	}

	@BeforeClass
	public static void beforeClass() throws CoreException, IOException {
		simpleUmlToRdbTransformationFile = setupSimpleUMLToRDBProject();
		libraryWithPropertiesTransformationFile = setupDebuggerTestDataProject("libraryWithProperties", "LibraryWithProperties");
	}

	@After
	public void after() throws Exception {
		removeBreakpoints();
	}

	interface DebugEventConsumer {
		void accept(DebugEvent event) throws DebugException;
	}

	record WithBreakpoints(TestModel testModel) {
		protected void check(DebugEventConsumer consumer) throws CoreException, InterruptedException {
			runDebugger(consumer, testModel);
		}
	}

	record SelectedModel(TestModel testModel) {
		protected WithBreakpoints withBreakPoints(int... lineNumbers) {
			for (int breakpoint : lineNumbers) {
				try {
					addBreakpoint(testModel.transformationFile(), breakpoint);
				} catch (CoreException e) {
					fail("Failed to add breakpoint: " + e.getMessage());
				}
			}

			return new WithBreakpoints(testModel);
		}
	}

	protected SelectedModel testWithModel(TestModel testModel) {
		return new SelectedModel(testModel);
	}

	private static void runDebugger(DebugEventConsumer consumer, TestModel testModel) throws CoreException, InterruptedException {
		var launchConfig = createLaunchConfig(testModel);
		var debugConfig = new QVTODebugConfiguration();
		var launch = new Launch(launchConfig, ILaunchManager.DEBUG_MODE, new QVTOSourceLookupDirector());

		var errors = new ArrayList<Throwable>();

		IDebugEventSetListener listener = events -> {
			for (var event : events) {
				try {
					consumer.accept(event);
				} catch (Throwable e) {
					errors.add(e);
				}
			}
		};

		DebugPlugin.getDefault().addDebugEventListener(listener);

		debugConfig.launch(launchConfig, ILaunchManager.DEBUG_MODE, launch, new NullProgressMonitor());

		while (!launch.isTerminated()) {
			while (PlatformUI.getWorkbench().getDisplay().readAndDispatch()) {
			}
			Thread.sleep(10);
		}

		if (!errors.isEmpty()) {
			StringBuilder message = new StringBuilder("Multiple assertions failed:\n");
			for (Throwable error : errors) {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				error.printStackTrace(pw);
				message.append(sw.toString()).append("\n");
			}
			fail(message.toString());
		}

		DebugPlugin.getDefault().removeDebugEventListener(listener);
	}

	private static ILaunchConfiguration createLaunchConfig(TestModel testModel) throws CoreException {
		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType type = manager.getLaunchConfigurationType(QvtLaunchConfigurationDelegate.LAUNCH_CONFIGURATION_TYPE_ID);
		var workingCopy = type.newInstance(null, "DebugLaunchConfig");

		workingCopy.setAttribute(IQvtLaunchConstants.CONFIGURATION_PROPERTIES, Map.of());
		var transformationFilePath = testModel.transformationFile().getFullPath().toString();
		workingCopy.setAttribute(IQvtLaunchConstants.MODULE, transformationFilePath);
		workingCopy.setAttribute(IQvtLaunchConstants.ELEM_COUNT, 2);
		workingCopy.setAttribute(IQvtLaunchConstants.TARGET_MODEL + "1", testModel.targetModel1());
		workingCopy.setAttribute(IQvtLaunchConstants.TARGET_MODEL + "2", testModel.targetModel2());

		return workingCopy.doSave();
	}

	private static void addBreakpoint(IFile transformationFile, int lineNumber) throws CoreException {
		var uri = URIUtils.getResourceURI(transformationFile);
		DebugPlugin.getDefault().getBreakpointManager().addBreakpoint(new QVTOBreakpoint(uri, lineNumber));
	}

	private static IFile setupSimpleUMLToRDBProject() throws CoreException, IOException {
		// create project
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		var project = root.getProject("SimpleUMLToRDB");
		if (project.exists()) {
			project.delete(true, true, null);
		}
		project.create(null);
		project.open(null);

		// add QVTO nature
		var description = project.getDescription();
		description.setNatureIds(new String[] { QVTOProjectPlugin.NATURE_ID });
		project.setDescription(description, null);

		// copy simpleuml2rdb example to project
		var test = TestUtil.getPluginRelativeFile(AllTests.BUNDLE_ID + ".ui", "debuggerTestData");
		var srcFolder = test.toPath().resolve("../../../plugins/org.eclipse.m2m.qvt.oml.samples/projects/org.eclipse.m2m.qvt.oml.samples.simpleuml2rdb").normalize();
		var destFolder = project.getLocation().toFile();
		FileUtil.copyFolder(srcFolder.toFile(), destFolder);
		project.refreshLocal(IResource.DEPTH_INFINITE, null);
		project.getFile("Simpleuml_To_Rdb.qvto").move(project.getFile("transformations/Simpleuml_To_Rdb.qvto").getFullPath(), true, null);

		return project.getFile("transformations/Simpleuml_To_Rdb.qvto");
	}

	private static IFile setupDebuggerTestDataProject(String projectName, String transformationName) throws CoreException, IOException {
		// create project
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		var project = root.getProject(projectName);
		if (project.exists()) {
			project.delete(true, true, null);
		}
		project.create(null);
		project.open(null);

		// add QVTO nature
		var description = project.getDescription();
		description.setNatureIds(new String[] { QVTOProjectPlugin.NATURE_ID });
		project.setDescription(description, null);

		// copy simpleuml2rdb example to project
		var test = TestUtil.getPluginRelativeFile(AllTests.BUNDLE_ID + ".ui", "debuggerTestData");
		var srcFolder = test.toPath().resolve("models/" + projectName).normalize();
		var destFolder = project.getLocation().toFile();
		FileUtil.copyFolder(srcFolder.toFile(), destFolder);
		project.refreshLocal(IResource.DEPTH_INFINITE, null);

		return project.getFile("transformations/" + transformationName + ".qvto");
	}

	private static void removeBreakpoints() throws CoreException {
		var breakpointManager = DebugPlugin.getDefault().getBreakpointManager();
		var breakpoints = breakpointManager.getBreakpoints(QVTODebugCore.MODEL_ID);
		breakpointManager.removeBreakpoints(breakpoints, true);
	}
}
