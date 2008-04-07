/*******************************************************************************
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 *     Borland Software Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.m2m.internal.qvt.oml.editor.ui.completion;

import org.eclipse.m2m.internal.qvt.oml.ast.binding.ASTBindingHelper;
import org.eclipse.m2m.internal.qvt.oml.ast.env.QvtOperationalEnv;
import org.eclipse.m2m.internal.qvt.oml.ast.env.QvtOperationalFileEnv;
import org.eclipse.m2m.internal.qvt.oml.ast.parser.QvtOperationalVisitorCS;
import org.eclipse.m2m.internal.qvt.oml.compiler.ParsedModuleCS;
import org.eclipse.m2m.internal.qvt.oml.compiler.QvtCompiler;
import org.eclipse.m2m.internal.qvt.oml.compiler.QvtCompilerOptions;
import org.eclipse.m2m.internal.qvt.oml.cst.MappingMethodCS;
import org.eclipse.m2m.internal.qvt.oml.expressions.ImperativeOperation;
import org.eclipse.m2m.internal.qvt.oml.expressions.Module;
import org.eclipse.ocl.SemanticException;
import org.eclipse.ocl.ecore.EcoreEnvironment;
import org.eclipse.ocl.parser.OCLLexer;
import org.eclipse.ocl.utilities.ASTNode;

/**
 * @author aigdalov
 * Created on Nov 7, 2007
 */
public class QvtCompletionVisitorCS extends QvtOperationalVisitorCS {
    private final QvtCompletionData myData;
    private QvtOperationalEnv myEnv; 

    public QvtCompletionVisitorCS(
            OCLLexer lexStream,
            QvtOperationalFileEnv environment,
            QvtCompilerOptions options,
            QvtCompletionData data) {
        super(lexStream, environment, options);
        myData = data;
        myEnv = environment;
    }

    public QvtOperationalEnv getEnv() {
        return myEnv;
    }
    
    @Override
    public Module visitMappingModule(ParsedModuleCS parsedModuleCS, QvtOperationalFileEnv env, QvtCompiler compiler) throws SemanticException {
        myEnv = env;
        return super.visitMappingModule(parsedModuleCS, env, compiler);
    }

    @Override
    protected void visitMappingMethodCS(MappingMethodCS methodCS, QvtOperationalEnv env, ImperativeOperation operation) throws SemanticException {
        if ((methodCS.getMappingDeclarationCS() != null) && !methodCS.getMappingDeclarationCS().isBlackBox()) {
            super.visitMappingMethodCS(methodCS, env, operation);
            ASTNode astNode = ASTBindingHelper.resolveASTNode(methodCS);
            if (astNode != null) {
                EcoreEnvironment resolvedEnvironment = ASTBindingHelper.resolveEnvironment(astNode);
                if (resolvedEnvironment instanceof QvtOperationalEnv) {
                    myEnv = (QvtOperationalEnv) resolvedEnvironment;
                }
            }
        }
    }
}