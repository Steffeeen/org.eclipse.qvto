/**
 * <copyright>
 * 
 * </copyright>
 *
 * This code is auto-generated
 * from: model/ImperativeOCLCS.genmodel
 * by: org.eclipse.ocl.examples.build.acceleo.GenerateVisitor
 * defined by: org.eclipse.ocl.examples.build.acceleo.generateVisitors.mtl
 * invoked by: org.eclipse.ocl.examples.build.utilities.*
 * from: org.eclipse.ocl.examples.build.*.mwe2
 *
 * Do not edit it.
 *
 * $Id$
 */
package	org.eclipse.qvto.examples.xtext.imperativeocl.imperativeoclcs.util;

import org.eclipse.jdt.annotation.NonNull;

/*
 * An AbstractImperativeOCLCSVisitor provides a default implementation of the visitor framework
 * but n implementations of the visitXXX methods..
 */
public abstract class AbstractImperativeOCLCSVisitor<R, C>
	extends org.eclipse.ocl.examples.xtext.essentialocl.util.AbstractEssentialOCLCSVisitor<R, C>
	implements ImperativeOCLCSVisitor<R>
{
	/**
	 * Initializes me with an initial value for my result.
	 * 
	 * @param context my initial result value
	 */
	protected AbstractImperativeOCLCSVisitor(@NonNull C context) {
		super(context);
	}
}
