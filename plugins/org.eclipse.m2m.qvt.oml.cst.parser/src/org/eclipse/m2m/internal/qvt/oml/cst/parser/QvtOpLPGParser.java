/**
* <copyright>
*
* Copyright (c) 2005, 2008 IBM Corporation and others.
* All rights reserved.   This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*   IBM - Initial API and implementation
*   E.D.Willink - Elimination of some shift-reduce conflicts
*   E.D.Willink - Remove unnecessary warning suppression
*   E.D.Willink - 225493 Need ability to set CSTNode offsets
*
* </copyright>
*
* $Id: QvtOpLPGParser.java,v 1.28 2008/12/02 12:00:21 aigdalov Exp $
*/
/**
* <copyright>
*
* Copyright (c) 2006, 2007 Borland Inc.
* All rights reserved.   This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*   Borland - Initial API and implementation
*
* </copyright>
*
* $Id: QvtOpLPGParser.java,v 1.28 2008/12/02 12:00:21 aigdalov Exp $
*/
/**
* <copyright>
*
* Copyright (c) 2006, 2007 Borland Inc.
* All rights reserved.   This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*   Borland - Initial API and implementation
*
* </copyright>
*
* $Id: QvtOpLPGParser.java,v 1.28 2008/12/02 12:00:21 aigdalov Exp $
*/
/**
* <copyright>
*
* Copyright (c) 2006, 2007 Borland Inc.
* All rights reserved.   This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*   Borland - Initial API and implementation
*
* </copyright>
*
* $Id: QvtOpLPGParser.java,v 1.28 2008/12/02 12:00:21 aigdalov Exp $
*/

package org.eclipse.m2m.internal.qvt.oml.cst.parser;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.ocl.cst.CSTNode;
import org.eclipse.ocl.cst.CallExpCS;
import org.eclipse.ocl.cst.CollectionTypeIdentifierEnum;
import org.eclipse.ocl.cst.DotOrArrowEnum;
import org.eclipse.ocl.cst.IntegerLiteralExpCS;
import org.eclipse.ocl.cst.IsMarkedPreCS;
import org.eclipse.ocl.cst.MessageExpCS;
import org.eclipse.ocl.cst.OCLExpressionCS;
import org.eclipse.ocl.cst.OCLMessageArgCS;
import org.eclipse.ocl.cst.OperationCallExpCS;
import org.eclipse.ocl.cst.PathNameCS;
import org.eclipse.ocl.cst.SimpleNameCS;
import org.eclipse.ocl.cst.SimpleTypeEnum;
import org.eclipse.ocl.cst.StateExpCS;
import org.eclipse.ocl.cst.TypeCS;
import org.eclipse.ocl.cst.VariableCS;
import org.eclipse.ocl.util.OCLStandardLibraryUtil;
import org.eclipse.ocl.utilities.PredefinedType;

import lpg.lpgjavaruntime.BadParseException;
import lpg.lpgjavaruntime.BadParseSymFileException;
import lpg.lpgjavaruntime.DeterministicParser;
import lpg.lpgjavaruntime.DiagnoseParser;
import lpg.lpgjavaruntime.IToken;
import lpg.lpgjavaruntime.Monitor;
import lpg.lpgjavaruntime.NotDeterministicParseTableException;
import lpg.lpgjavaruntime.ParseTable;
import lpg.lpgjavaruntime.RuleAction;


import org.eclipse.ocl.cst.StringLiteralExpCS;
import org.eclipse.ocl.ParserException;		
import lpg.lpgjavaruntime.Token;
import lpg.lpgjavaruntime.BacktrackingParser;
import lpg.lpgjavaruntime.PrsStream;
import lpg.lpgjavaruntime.NotBacktrackParseTableException;
import lpg.lpgjavaruntime.NullExportedSymbolsException;
import lpg.lpgjavaruntime.NullTerminalSymbolsException;
import lpg.lpgjavaruntime.UndefinedEofSymbolException;
import lpg.lpgjavaruntime.UnimplementedTerminalsException;
import org.eclipse.m2m.internal.qvt.oml.cst.AssertExpCS;	
import org.eclipse.m2m.internal.qvt.oml.cst.LogExpCS;
import org.eclipse.m2m.internal.qvt.oml.cst.BlockExpCS;	
import org.eclipse.m2m.internal.qvt.oml.cst.ReturnExpCS;	
import org.eclipse.m2m.internal.qvt.oml.cst.SwitchAltExpCS;
import org.eclipse.m2m.internal.qvt.oml.cst.temp.ScopedNameCS;
import org.eclipse.m2m.internal.qvt.oml.cst.temp.TempFactory;

import org.eclipse.m2m.internal.qvt.oml.cst.CompleteSignatureCS;
import org.eclipse.m2m.internal.qvt.oml.cst.DirectionKindCS;
import org.eclipse.m2m.internal.qvt.oml.cst.DirectionKindEnum;
import org.eclipse.m2m.internal.qvt.oml.cst.ImportCS;
import org.eclipse.m2m.internal.qvt.oml.cst.MappingBodyCS;
import org.eclipse.m2m.internal.qvt.oml.cst.MappingDeclarationCS;
import org.eclipse.m2m.internal.qvt.oml.cst.MappingEndCS;
import org.eclipse.m2m.internal.qvt.oml.cst.MappingInitCS;
import org.eclipse.m2m.internal.qvt.oml.cst.MappingExtensionCS;
import org.eclipse.m2m.internal.qvt.oml.cst.MappingModuleCS;
import org.eclipse.m2m.internal.qvt.oml.cst.MappingRuleCS;	
import org.eclipse.m2m.internal.qvt.oml.cst.MappingQueryCS;
import org.eclipse.m2m.internal.qvt.oml.cst.MappingSectionsCS;
import org.eclipse.m2m.internal.qvt.oml.cst.OutExpCS;
import org.eclipse.m2m.internal.qvt.oml.cst.ModelTypeCS;
import org.eclipse.m2m.internal.qvt.oml.cst.SimpleSignatureCS;
import org.eclipse.m2m.internal.qvt.oml.cst.temp.ResolveOpArgsExpCS;
import org.eclipse.m2m.internal.qvt.oml.cst.temp.ScopedNameCS;
import org.eclipse.m2m.internal.qvt.oml.cst.ModuleKindEnum;
import org.eclipse.m2m.internal.qvt.oml.cst.ModuleKindCS;
import org.eclipse.m2m.internal.qvt.oml.cst.ModuleRefCS;
import org.eclipse.m2m.internal.qvt.oml.cst.ImportKindEnum;
import org.eclipse.m2m.internal.qvt.oml.cst.ParameterDeclarationCS;
import org.eclipse.m2m.internal.qvt.oml.cst.TransformationRefineCS;
import org.eclipse.m2m.internal.qvt.oml.cst.TransformationHeaderCS;
import org.eclipse.m2m.internal.qvt.oml.cst.TypeSpecCS;

	public class QvtOpLPGParser extends AbstractQVTParser implements RuleAction {
	protected static ParseTable prs = new QvtOpLPGParserprs();
	private BacktrackingParser dtParser;
	// (to be uncommented for use in DEBUG mode)
	//private static Map<Integer, String> ruleTexts;

	public QvtOpLPGParser(QvtOpLexer lexer) {
		super(lexer);

		try {
			super.remapTerminalSymbols(orderedTerminalSymbols(), QvtOpLPGParserprs.EOFT_SYMBOL);
		}
		catch(NullExportedSymbolsException e) {
			throw new RuntimeException(e.getLocalizedMessage());
		}
		catch(NullTerminalSymbolsException e) {
			throw new RuntimeException(e.getLocalizedMessage());
		}
		catch(UnimplementedTerminalsException e) {
			java.util.ArrayList<?> unimplemented_symbols = e.getSymbols();
			String error = "The Lexer will not scan the following token(s):";
			for (int i = 0; i < unimplemented_symbols.size(); i++) {
				Integer id = (Integer) unimplemented_symbols.get(i);
				error += "\t" + QvtOpLPGParsersym.orderedTerminalSymbols[id.intValue()];			   
			}
			throw new RuntimeException(error + "\n");						
		}
		catch(UndefinedEofSymbolException e) {
			throw new RuntimeException("The Lexer does not implement the Eof symbol " +
				 QvtOpLPGParsersym.orderedTerminalSymbols[QvtOpLPGParserprs.EOFT_SYMBOL]);
		} 
	}
	 
	@Override
	public String[] orderedTerminalSymbols() { return QvtOpLPGParsersym.orderedTerminalSymbols; }
	@Override		
	public String getTokenKindName(int kind) { return QvtOpLPGParsersym.orderedTerminalSymbols[kind]; }			
	public int getEOFTokenKind() { return QvtOpLPGParserprs.EOFT_SYMBOL; }
	public PrsStream getParseStream() { return this; }

	protected CSTNode parser() throws ParserException {
		return parseTokensToCST(null, 0);
	}
		
	protected CSTNode parser(Monitor monitor) throws ParserException {
		return parseTokensToCST(monitor, 0);
	}
		
	protected CSTNode parser(int error_repair_count) throws ParserException {
		return parseTokensToCST(null, error_repair_count);
	}
		
	@SuppressWarnings("nls")
	@Override
	public CSTNode parseTokensToCST(Monitor monitor, int error_repair_count) {
		ParseTable prsTable = new QvtOpLPGParserprs();

		try {
			dtParser = new BacktrackingParser(monitor, this, prsTable, this);
		}
		catch (NotBacktrackParseTableException e) {
			throw new RuntimeException("****Error: Regenerate QvtOpLPGParserprs.java with -NOBACKTRACK option");
		}
		catch (BadParseSymFileException e) {
			throw new RuntimeException("****Error: Bad Parser Symbol File -- QvtOpLPGParsersym.java. Regenerate QvtOpLPGParserprs.java");
		}

		try {
		    workaroundEOFErrors();
			return (CSTNode) dtParser.parse(error_repair_count);
		}
		catch (BadParseException e) {
			OnParseError(e);

			reset(e.error_token); // point to error token
			DiagnoseParser diagnoseParser = new DiagnoseParser(this, prsTable);
			diagnoseParser.diagnose(e.error_token);
		}

		return null;
	}

    private void workaroundEOFErrors() {
	    IToken lastT = (IToken) getTokens().get((getTokens().size() - 1));
    	int trailingEOFsAmount = 100;
    	int someHugeOffset = 100000;
    	for (int i  = 0; i < trailingEOFsAmount; i++) {
            makeToken(lastT.getEndOffset() + i + someHugeOffset, lastT.getEndOffset() + i + someHugeOffset + 1, QvtOpLPGParsersym.TK_EOF_TOKEN);
    	}
	}

	protected void OnParseError(BadParseException e) {
		System.err.println(getFileName());
		java.util.ArrayList<?> tokens = getTokens();
		String result = getName(e.error_token) + " ~~ ";
		for (int i = Math.max(0, e.error_token-5), n = Math.min(tokens.size(), e.error_token+5); i < n; ++i) {
			result += tokens.get(i).toString();
			result += " ";
		}
		System.err.println(result);
	}



	/**
	 * 
	 * QVT Operational specific part
	 *
	 */
	
	
	@SuppressWarnings("unchecked")
	private static final EList ourEmptyEList = new BasicEList.UnmodifiableEList(0, new Object[0]);								
							
	
	private void diagnozeErrorToken(int token_index) {
		IToken token = getIToken(token_index);
		if (token instanceof lpg.lpgjavaruntime.ErrorToken) {
			token = ((lpg.lpgjavaruntime.ErrorToken) token).getErrorToken();
		}
		
		reportError(lpg.lpgjavaruntime.ParseErrorCodes.MISPLACED_CODE, "", token.getTokenIndex(), token.getTokenIndex(), //$NON-NLS-1$ 
				"'" + token.toString() + "'"); //$NON-NLS-1$ //$NON-NLS-2$
		reset(token.getTokenIndex()); // point to error token
		DiagnoseParser diagnoseParser = new DiagnoseParser(this, prs);
		diagnoseParser.diagnose(token.getTokenIndex());
		dtParser.setSym1(null);
	}

	@SuppressWarnings("unchecked")
	public void ruleAction(int ruleNumber)
	{
		switch (ruleNumber) {
		
 
			//
			// Rule 28:  impliesExpCS ::= impliesExpCS implies andOrXorExpCS
			//
			case 28:
 
			//
			// Rule 29:  impliesWithLet ::= impliesExpCS implies andOrXorWithLet
			//
			case 29:
 
			//
			// Rule 32:  andOrXorExpCS ::= andOrXorExpCS and equalityExpCS
			//
			case 32:
 
			//
			// Rule 33:  andOrXorExpCS ::= andOrXorExpCS or equalityExpCS
			//
			case 33:
 
			//
			// Rule 34:  andOrXorExpCS ::= andOrXorExpCS xor equalityExpCS
			//
			case 34:
 
			//
			// Rule 35:  andOrXorWithLet ::= andOrXorExpCS and equalityWithLet
			//
			case 35:
 
			//
			// Rule 36:  andOrXorWithLet ::= andOrXorExpCS or equalityWithLet
			//
			case 36:
 
			//
			// Rule 37:  andOrXorWithLet ::= andOrXorExpCS xor equalityWithLet
			//
			case 37: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.STRING_LITERAL,
							getTokenText(dtParser.getToken(2))
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(2)));
				EList args = new BasicEList();
				args.add(dtParser.getSym(3));
				CSTNode result = createOperationCallExpCS(
						(OCLExpressionCS)dtParser.getSym(1),
						simpleNameCS,
						args
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1), (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 40:  equalityExpCS ::= equalityExpCS = relationalExpCS
			//
			case 40:
 
			//
			// Rule 41:  equalityWithLet ::= equalityExpCS = relationalWithLet
			//
			case 41: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.STRING_LITERAL,
							OCLStandardLibraryUtil.getOperationName(PredefinedType.EQUAL)
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(2)));
				EList args = new BasicEList();
				args.add(dtParser.getSym(3));
				CSTNode result = createOperationCallExpCS(
						(OCLExpressionCS)dtParser.getSym(1),
						simpleNameCS,
						args
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1), (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 42:  equalityExpCS ::= equalityExpCS <> relationalExpCS
			//
			case 42:
 
			//
			// Rule 43:  equalityWithLet ::= equalityExpCS <> relationalWithLet
			//
			case 43: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.STRING_LITERAL,
							OCLStandardLibraryUtil.getOperationName(PredefinedType.NOT_EQUAL)
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(2)));
				EList args = new BasicEList();
				args.add(dtParser.getSym(3));
				CSTNode result = createOperationCallExpCS(
						(OCLExpressionCS)dtParser.getSym(1),
						simpleNameCS,
						args
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1), (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 46:  relationalExpCS ::= relationalExpCS > ifExpCSPrec
			//
			case 46:
 
			//
			// Rule 47:  relationalWithLet ::= relationalExpCS > additiveWithLet
			//
			case 47: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.STRING_LITERAL,
							OCLStandardLibraryUtil.getOperationName(PredefinedType.GREATER_THAN)
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(2)));
				EList args = new BasicEList();
				args.add(dtParser.getSym(3));
				CSTNode result = createOperationCallExpCS(
						(OCLExpressionCS)dtParser.getSym(1),
						simpleNameCS,
						args
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1), (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 48:  relationalExpCS ::= relationalExpCS < ifExpCSPrec
			//
			case 48:
 
			//
			// Rule 49:  relationalWithLet ::= relationalExpCS < additiveWithLet
			//
			case 49: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.STRING_LITERAL,
							OCLStandardLibraryUtil.getOperationName(PredefinedType.LESS_THAN)
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(2)));
				EList args = new BasicEList();
				args.add(dtParser.getSym(3));
				CSTNode result = createOperationCallExpCS(
						(OCLExpressionCS)dtParser.getSym(1),
						simpleNameCS,
						args
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1), (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 50:  relationalExpCS ::= relationalExpCS >= ifExpCSPrec
			//
			case 50:
 
			//
			// Rule 51:  relationalWithLet ::= relationalExpCS >= additiveWithLet
			//
			case 51: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.STRING_LITERAL,
							OCLStandardLibraryUtil.getOperationName(PredefinedType.GREATER_THAN_EQUAL)
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(2)));
				EList args = new BasicEList();
				args.add(dtParser.getSym(3));
				CSTNode result = createOperationCallExpCS(
						(OCLExpressionCS)dtParser.getSym(1),
						simpleNameCS,
						args
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1), (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 52:  relationalExpCS ::= relationalExpCS <= ifExpCSPrec
			//
			case 52:
 
			//
			// Rule 53:  relationalWithLet ::= relationalExpCS <= additiveWithLet
			//
			case 53: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.STRING_LITERAL,
							OCLStandardLibraryUtil.getOperationName(PredefinedType.LESS_THAN_EQUAL)
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(2)));
				EList args = new BasicEList();
				args.add(dtParser.getSym(3));
				CSTNode result = createOperationCallExpCS(
						(OCLExpressionCS)dtParser.getSym(1),
						simpleNameCS,
						args
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1), (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 57:  additiveExpCS ::= additiveExpCS + multiplicativeExpCS
			//
			case 57:
 
			//
			// Rule 58:  additiveWithLet ::= additiveExpCS + multiplicativeWithLet
			//
			case 58: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.STRING_LITERAL,
							OCLStandardLibraryUtil.getOperationName(PredefinedType.PLUS)
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(2)));
				EList args = new BasicEList();
				args.add(dtParser.getSym(3));
				CSTNode result = createOperationCallExpCS(
						(OCLExpressionCS)dtParser.getSym(1),
						simpleNameCS,
						args
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1), (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 59:  additiveExpCS ::= additiveExpCS - multiplicativeExpCS
			//
			case 59:
 
			//
			// Rule 60:  additiveWithLet ::= additiveExpCS - multiplicativeWithLet
			//
			case 60: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.STRING_LITERAL,
							OCLStandardLibraryUtil.getOperationName(PredefinedType.MINUS)
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(2)));
				EList args = new BasicEList();
				args.add(dtParser.getSym(3));
				CSTNode result = createOperationCallExpCS(
						(OCLExpressionCS)dtParser.getSym(1),
						simpleNameCS,
						args
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1), (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 63:  multiplicativeExpCS ::= multiplicativeExpCS * unaryExpCS
			//
			case 63:
 
			//
			// Rule 64:  multiplicativeWithLet ::= multiplicativeExpCS * unaryWithLet
			//
			case 64: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.STRING_LITERAL,
							OCLStandardLibraryUtil.getOperationName(PredefinedType.TIMES)
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(2)));
				EList args = new BasicEList();
				args.add(dtParser.getSym(3));
				CSTNode result = createOperationCallExpCS(
						(OCLExpressionCS)dtParser.getSym(1),
						simpleNameCS,
						args
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1), (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 65:  multiplicativeExpCS ::= multiplicativeExpCS / unaryExpCS
			//
			case 65:
 
			//
			// Rule 66:  multiplicativeWithLet ::= multiplicativeExpCS / unaryWithLet
			//
			case 66: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.STRING_LITERAL,
							OCLStandardLibraryUtil.getOperationName(PredefinedType.DIVIDE)
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(2)));
				EList args = new BasicEList();
				args.add(dtParser.getSym(3));
				CSTNode result = createOperationCallExpCS(
						(OCLExpressionCS)dtParser.getSym(1),
						simpleNameCS,
						args
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1), (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 69:  unaryExpCS ::= - unaryExpCS
			//
			case 69: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.STRING_LITERAL,
							OCLStandardLibraryUtil.getOperationName(PredefinedType.MINUS)
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(1)));
				CSTNode result = createOperationCallExpCS(
						(OCLExpressionCS)dtParser.getSym(2),
						simpleNameCS,
						new BasicEList()
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode)dtParser.getSym(2));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 70:  unaryExpCS ::= not unaryExpCS
			//
			case 70: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.STRING_LITERAL,
							getTokenText(dtParser.getToken(1))
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(1)));
				CSTNode result = createOperationCallExpCS(
						(OCLExpressionCS)dtParser.getSym(2),
						simpleNameCS,
						new BasicEList()
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode)dtParser.getSym(2));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 72:  dotArrowExpCS ::= dotArrowExpCS callExpCS
			//
			case 72: {
				
				CallExpCS result = (CallExpCS)dtParser.getSym(2);
				result.setSource((OCLExpressionCS)dtParser.getSym(1));
				setOffsets(result, (CSTNode)dtParser.getSym(1), result);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 73:  dotArrowExpCS ::= dotArrowExpCS messageExpCS
			//
			case 73: {
				
				MessageExpCS result = (MessageExpCS)dtParser.getSym(2);
				result.setTarget((OCLExpressionCS)dtParser.getSym(1));
				setOffsets(result, (CSTNode)dtParser.getSym(1), (CSTNode)dtParser.getSym(2));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 74:  dotArrowExpCS ::= NUMERIC_OPERATION ( argumentsCSopt )
			//
			case 74: {
				
				// NUMERIC_OPERATION -> Integer '.' Identifier
				String text = getTokenText(dtParser.getToken(1));
				int index = text.indexOf('.');
				String integer = text.substring(0, index);
				String simpleName = text.substring(index + 1);

				// create the IntegerLiteralExpCS
				int startOffset = getIToken(dtParser.getToken(1)).getStartOffset();
				int endOffset = startOffset + integer.length() - 1; // inclusive

				IntegerLiteralExpCS integerLiteralExpCS = createIntegerLiteralExpCS(integer);
				integerLiteralExpCS.setStartOffset(startOffset);
				integerLiteralExpCS.setEndOffset(endOffset);

				startOffset = endOffset + 2; // end of integerLiteral + 1('.') + 1(start of simpleName)
				endOffset = getIToken(dtParser.getToken(1)).getEndOffset();

				// create the SimpleNameCS
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.IDENTIFIER_LITERAL,
							simpleName
						);
				simpleNameCS.setStartOffset(startOffset);
				simpleNameCS.setEndOffset(endOffset);

				// create the OperationCallExpCS
				CSTNode result = createOperationCallExpCS(
						integerLiteralExpCS,
						simpleNameCS,
						(EList)dtParser.getSym(3)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(4)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 75:  dotArrowExpCS ::= pathNameCS :: simpleNameCS ( argumentsCSopt )
			//
			case 75: {
				
				OperationCallExpCS result = createOperationCallExpCS(
						(PathNameCS)dtParser.getSym(1),
						(SimpleNameCS)dtParser.getSym(3),
						(EList)dtParser.getSym(5)
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1), getIToken(dtParser.getToken(6)));
				result.setAccessor(DotOrArrowEnum.DOT_LITERAL);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 80:  oclExpCS ::= ( oclExpressionCS )
			//
			case 80: {
				
				CSTNode result = (CSTNode)dtParser.getSym(2);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(3)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 81:  variableExpCS ::= simpleNameCS isMarkedPreCS
			//
			case 81: {
				
				IsMarkedPreCS isMarkedPreCS = (IsMarkedPreCS)dtParser.getSym(2);
				CSTNode result = createVariableExpCS(
						(SimpleNameCS)dtParser.getSym(1),
						new BasicEList(),
						isMarkedPreCS
					);
				if (isMarkedPreCS.isPre()) {
					setOffsets(result, (CSTNode)dtParser.getSym(1), (CSTNode)dtParser.getSym(2));
				} else {
					setOffsets(result, (CSTNode)dtParser.getSym(1));
				}
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 82:  variableExpCS ::= keywordAsIdentifier1 isMarkedPreCS
			//
			case 82: {
				
				IsMarkedPreCS isMarkedPreCS = (IsMarkedPreCS)dtParser.getSym(2);
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.IDENTIFIER_LITERAL,
							getTokenText(dtParser.getToken(1))
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(1)));
				CSTNode result = createVariableExpCS(
						simpleNameCS,
						new BasicEList(),
						isMarkedPreCS
					);
				if (isMarkedPreCS.isPre()) {
					setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode)dtParser.getSym(2));
				} else {
					setOffsets(result, getIToken(dtParser.getToken(1)));
				}
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 84:  simpleNameCS ::= self
			//
			case 84: {
				
				CSTNode result = createSimpleNameCS(
						SimpleTypeEnum.SELF_LITERAL,
						getTokenText(dtParser.getToken(1))
					);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 85:  simpleNameCS ::= IDENTIFIER
			//
			case 85: {
				
				CSTNode result = createSimpleNameCS(
						SimpleTypeEnum.IDENTIFIER_LITERAL,
						getTokenText(dtParser.getToken(1))
					);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 86:  primitiveTypeCS ::= Integer
			//
			case 86: {
				
				CSTNode result = createPrimitiveTypeCS(
						SimpleTypeEnum.INTEGER_LITERAL,
						getTokenText(dtParser.getToken(1))
					);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 87:  primitiveTypeCS ::= UnlimitedNatural
			//
			case 87: {
				
				CSTNode result = createPrimitiveTypeCS(
						SimpleTypeEnum.UNLIMITED_NATURAL_LITERAL,
						getTokenText(dtParser.getToken(1))
					);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 88:  primitiveTypeCS ::= String
			//
			case 88: {
				
				CSTNode result = createPrimitiveTypeCS(
						SimpleTypeEnum.STRING_LITERAL,
						getTokenText(dtParser.getToken(1))
					);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 89:  primitiveTypeCS ::= Real
			//
			case 89: {
				
				CSTNode result = createPrimitiveTypeCS(
						SimpleTypeEnum.REAL_LITERAL,
						getTokenText(dtParser.getToken(1))
					);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 90:  primitiveTypeCS ::= Boolean
			//
			case 90: {
				
				CSTNode result = createPrimitiveTypeCS(
						SimpleTypeEnum.BOOLEAN_LITERAL,
						getTokenText(dtParser.getToken(1))
					);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 91:  primitiveTypeCS ::= OclAny
			//
			case 91: {
				
				CSTNode result = createPrimitiveTypeCS(
						SimpleTypeEnum.OCL_ANY_LITERAL,
						getTokenText(dtParser.getToken(1))
					);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 92:  primitiveTypeCS ::= OclVoid
			//
			case 92: {
				
				CSTNode result = createPrimitiveTypeCS(
						SimpleTypeEnum.OCL_VOID_LITERAL,
						getTokenText(dtParser.getToken(1))
					);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 93:  primitiveTypeCS ::= Invalid
			//
			case 93: {
				
				CSTNode result = createPrimitiveTypeCS(
						SimpleTypeEnum.INVALID_LITERAL,
						getTokenText(dtParser.getToken(1))
					);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 94:  primitiveTypeCS ::= OclMessage
			//
			case 94: {
				
				CSTNode result = createPrimitiveTypeCS(
						SimpleTypeEnum.OCL_MESSAGE_LITERAL,
						getTokenText(dtParser.getToken(1))
					);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 95:  pathNameCS ::= IDENTIFIER
			//
			case 95: {
				
				CSTNode result = createPathNameCS(getTokenText(dtParser.getToken(1)));
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 96:  pathNameCS ::= pathNameCS :: simpleNameCS
			//
			case 96: {
				
				PathNameCS result = (PathNameCS)dtParser.getSym(1);
				result = extendPathNameCS(result, getTokenText(dtParser.getToken(3)));
				setOffsets(result, result, (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 97:  pathNameCSOpt ::= $Empty
			//
			case 97: {
				
				CSTNode result = createPathNameCS();
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 105:  enumLiteralExpCS ::= pathNameCS :: keywordAsIdentifier
			//
			case 105: {
				
				CSTNode result = createEnumLiteralExpCS(
						(PathNameCS)dtParser.getSym(1),
						getTokenText(dtParser.getToken(3))
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1), getIToken(dtParser.getToken(3)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 106:  enumLiteralExpCS ::= pathNameCS :: simpleNameCS
			//
			case 106: {
				
				CSTNode result = createEnumLiteralExpCS(
						(PathNameCS)dtParser.getSym(1),
						(SimpleNameCS)dtParser.getSym(3)
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1), (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 107:  collectionLiteralExpCS ::= collectionTypeIdentifierCS { collectionLiteralPartsCSopt }
			//
			case 107: {
				
				Object[] objs = (Object[])dtParser.getSym(1);
				CSTNode result = createCollectionLiteralExpCS(
						(CollectionTypeIdentifierEnum)objs[1],
						(EList)dtParser.getSym(3)
					);
				setOffsets(result, (IToken)objs[0], getIToken(dtParser.getToken(4)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 108:  collectionTypeIdentifierCS ::= Set
			//
			case 108: {
				
				dtParser.setSym1(new Object[]{getIToken(dtParser.getToken(1)), CollectionTypeIdentifierEnum.SET_LITERAL});
	  		  break;
			}
	 
			//
			// Rule 109:  collectionTypeIdentifierCS ::= Bag
			//
			case 109: {
				
				dtParser.setSym1(new Object[]{getIToken(dtParser.getToken(1)), CollectionTypeIdentifierEnum.BAG_LITERAL});
	  		  break;
			}
	 
			//
			// Rule 110:  collectionTypeIdentifierCS ::= Sequence
			//
			case 110: {
				
				dtParser.setSym1(new Object[]{getIToken(dtParser.getToken(1)), CollectionTypeIdentifierEnum.SEQUENCE_LITERAL});
	  		  break;
			}
	 
			//
			// Rule 111:  collectionTypeIdentifierCS ::= Collection
			//
			case 111: {
				
				dtParser.setSym1(new Object[]{getIToken(dtParser.getToken(1)), CollectionTypeIdentifierEnum.COLLECTION_LITERAL});
	  		  break;
			}
	 
			//
			// Rule 112:  collectionTypeIdentifierCS ::= OrderedSet
			//
			case 112: {
				
				dtParser.setSym1(new Object[]{getIToken(dtParser.getToken(1)), CollectionTypeIdentifierEnum.ORDERED_SET_LITERAL});
	  		  break;
			}
	 
			//
			// Rule 113:  collectionLiteralPartsCSopt ::= $Empty
			//
			case 113:
				dtParser.setSym1(new BasicEList());
				break;
 
			//
			// Rule 115:  collectionLiteralPartsCS ::= collectionLiteralPartCS
			//
			case 115: {
				
				EList result = new BasicEList();
				result.add(dtParser.getSym(1));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 116:  collectionLiteralPartsCS ::= collectionLiteralPartsCS , collectionLiteralPartCS
			//
			case 116: {
				
				EList result = (EList)dtParser.getSym(1);
				result.add(dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 118:  collectionLiteralPartCS ::= oclExpressionCS
			//
			case 118: {
				
				CSTNode result = createCollectionLiteralPartCS(
						(OCLExpressionCS)dtParser.getSym(1)
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 119:  collectionRangeCS ::= - INTEGER_RANGE_START oclExpressionCS
			//
			case 119: {
				
				OCLExpressionCS rangeStart = createRangeStart(
						getTokenText(dtParser.getToken(2)), true);
				CSTNode result = createCollectionRangeCS(
						rangeStart,
						(OCLExpressionCS)dtParser.getSym(3)
					);
				setOffsets(result, rangeStart, (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 120:  collectionRangeCS ::= INTEGER_RANGE_START oclExpressionCS
			//
			case 120: {
				
				OCLExpressionCS rangeStart = createRangeStart(
						getTokenText(dtParser.getToken(1)), false);
				CSTNode result = createCollectionRangeCS(
						rangeStart,
						(OCLExpressionCS)dtParser.getSym(2)
					);
				setOffsets(result, rangeStart, (CSTNode)dtParser.getSym(2));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 121:  collectionRangeCS ::= oclExpressionCS .. oclExpressionCS
			//
			case 121: {
				
				CSTNode result = createCollectionRangeCS(
						(OCLExpressionCS)dtParser.getSym(1),
						(OCLExpressionCS)dtParser.getSym(3)
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1), (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 127:  tupleLiteralExpCS ::= Tuple { variableListCS2 }
			//
			case 127: {
				
				CSTNode result = createTupleLiteralExpCS((EList)dtParser.getSym(3));
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(4)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 128:  integerLiteralExpCS ::= INTEGER_LITERAL
			//
			case 128: {
				
				CSTNode result = createIntegerLiteralExpCS(getTokenText(dtParser.getToken(1)));
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 129:  unlimitedNaturalLiteralExpCS ::= *
			//
			case 129: {
				
				CSTNode result = createUnlimitedNaturalLiteralExpCS(getTokenText(dtParser.getToken(1)));
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 130:  realLiteralExpCS ::= REAL_LITERAL
			//
			case 130: {
				
				CSTNode result = createRealLiteralExpCS(getTokenText(dtParser.getToken(1)));
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 131:  stringLiteralExpCS ::= STRING_LITERAL
			//
			case 131: {
				
				CSTNode result = createStringLiteralExpCS(getTokenText(dtParser.getToken(1)));
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 132:  booleanLiteralExpCS ::= true
			//
			case 132: {
				
				CSTNode result = createBooleanLiteralExpCS(getTokenText(dtParser.getToken(1)));
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 133:  booleanLiteralExpCS ::= false
			//
			case 133: {
				
				CSTNode result = createBooleanLiteralExpCS(getTokenText(dtParser.getToken(1)));
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 134:  nullLiteralExpCS ::= null
			//
			case 134: {
				
				CSTNode result = createNullLiteralExpCS(getTokenText(dtParser.getToken(1)));
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 135:  invalidLiteralExpCS ::= OclInvalid
			//
			case 135: {
				
				CSTNode result = createInvalidLiteralExpCS(getTokenText(dtParser.getToken(1)));
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 136:  callExpCS ::= -> featureCallExpCS
			//
			case 136:
 
			//
			// Rule 137:  callExpCS ::= -> loopExpCS
			//
			case 137: {
				
				CallExpCS result = (CallExpCS)dtParser.getSym(2);
				result.setAccessor(DotOrArrowEnum.ARROW_LITERAL);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 138:  callExpCS ::= . keywordOperationCallExpCS
			//
			case 138:
 
			//
			// Rule 139:  callExpCS ::= . featureCallExpCS
			//
			case 139: {
				
				CallExpCS result = (CallExpCS)dtParser.getSym(2);
				result.setAccessor(DotOrArrowEnum.DOT_LITERAL);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 142:  iteratorExpCS ::= forAll ( iterContents )
			//
			case 142:
 
			//
			// Rule 143:  iteratorExpCS ::= exists ( iterContents )
			//
			case 143:
 
			//
			// Rule 144:  iteratorExpCS ::= isUnique ( iterContents )
			//
			case 144:
 
			//
			// Rule 145:  iteratorExpCS ::= one ( iterContents )
			//
			case 145:
 
			//
			// Rule 146:  iteratorExpCS ::= any ( iterContents )
			//
			case 146:
 
			//
			// Rule 147:  iteratorExpCS ::= collect ( iterContents )
			//
			case 147:
 
			//
			// Rule 148:  iteratorExpCS ::= select ( iterContents )
			//
			case 148:
 
			//
			// Rule 149:  iteratorExpCS ::= reject ( iterContents )
			//
			case 149:
 
			//
			// Rule 150:  iteratorExpCS ::= collectNested ( iterContents )
			//
			case 150:
 
			//
			// Rule 151:  iteratorExpCS ::= sortedBy ( iterContents )
			//
			case 151:
 
			//
			// Rule 152:  iteratorExpCS ::= closure ( iterContents )
			//
			case 152: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.KEYWORD_LITERAL,
							getTokenText(dtParser.getToken(1))
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(1)));
				Object[] iterContents = (Object[])dtParser.getSym(3);
				CSTNode result = createIteratorExpCS(
						simpleNameCS,
						(VariableCS)iterContents[0],
						(VariableCS)iterContents[1],
						(OCLExpressionCS)iterContents[2]
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(4)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 153:  iterContents ::= oclExpressionCS
			//
			case 153: {
				
				dtParser.setSym1(new Object[] {
						null,
						null,
						dtParser.getSym(1)
					});
	  		  break;
			}
	 
			//
			// Rule 154:  iterContents ::= variableCS | oclExpressionCS
			//
			case 154: {
				
				dtParser.setSym1(new Object[] {
						dtParser.getSym(1),
						null,
						dtParser.getSym(3)
					});
	  		  break;
			}
	 
			//
			// Rule 155:  iterContents ::= variableCS , variableCS | oclExpressionCS
			//
			case 155: {
				
				dtParser.setSym1(new Object[] {
						dtParser.getSym(1),
						dtParser.getSym(3),
						dtParser.getSym(5)
					});
	  		  break;
			}
	 
			//
			// Rule 156:  iterateExpCS ::= iterate ( variableCS | oclExpressionCS )
			//
			case 156: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.KEYWORD_LITERAL,
							getTokenText(dtParser.getToken(1))
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(1)));
				CSTNode result = createIterateExpCS(
						simpleNameCS,
						(VariableCS)dtParser.getSym(3),
						null,
						(OCLExpressionCS)dtParser.getSym(5)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(6)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 157:  iterateExpCS ::= iterate ( variableCS ; variableCS | oclExpressionCS )
			//
			case 157: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.KEYWORD_LITERAL,
							getTokenText(dtParser.getToken(1))
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(1)));
				CSTNode result = createIterateExpCS(
						simpleNameCS,
						(VariableCS)dtParser.getSym(3),
						(VariableCS)dtParser.getSym(5),
						(OCLExpressionCS)dtParser.getSym(7)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(8)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 158:  variableCS ::= IDENTIFIER
			//
			case 158: {
				
				CSTNode result = createVariableCS(
						getTokenText(dtParser.getToken(1)),
						null,
						null
					);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 159:  variableCS ::= IDENTIFIER : typeCS
			//
			case 159: {
				
				CSTNode result = createVariableCS(
						getTokenText(dtParser.getToken(1)),
						(TypeCS)dtParser.getSym(3),
						null
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 160:  variableCS ::= IDENTIFIER : typeCS = oclExpressionCS
			//
			case 160: {
				
				CSTNode result = createVariableCS(
						getTokenText(dtParser.getToken(1)),
						(TypeCS)dtParser.getSym(3),
						(OCLExpressionCS)dtParser.getSym(5)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode)dtParser.getSym(5));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 161:  variableCS2 ::= IDENTIFIER = oclExpressionCS
			//
			case 161: {
				
				CSTNode result = createVariableCS(
						getTokenText(dtParser.getToken(1)),
						null,
						(OCLExpressionCS)dtParser.getSym(3)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 166:  collectionTypeCS ::= collectionTypeIdentifierCS ( typeCS )
			//
			case 166: {
				
				Object[] objs = (Object[])dtParser.getSym(1);
				CSTNode result = createCollectionTypeCS(
						(CollectionTypeIdentifierEnum)objs[1],
						(TypeCS)dtParser.getSym(3)
					);
				setOffsets(result, (IToken)objs[0], getIToken(dtParser.getToken(4)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 167:  tupleTypeCS ::= Tuple ( variableListCSopt )
			//
			case 167: {
				
				CSTNode result = createTupleTypeCS((EList)dtParser.getSym(3));
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(4)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 168:  variableListCSopt ::= $Empty
			//
			case 168:
				dtParser.setSym1(new BasicEList());
				break;
 
			//
			// Rule 170:  variableListCS ::= variableCS
			//
			case 170: {
				
				EList result = new BasicEList();
				result.add(dtParser.getSym(1));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 171:  variableListCS ::= variableListCS , variableCS
			//
			case 171: {
				
				EList result = (EList)dtParser.getSym(1);
				result.add(dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 172:  variableListCS2 ::= variableCS2
			//
			case 172:
 
			//
			// Rule 173:  variableListCS2 ::= variableCS
			//
			case 173: {
				
				EList result = new BasicEList();
				result.add(dtParser.getSym(1));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 174:  variableListCS2 ::= variableListCS2 , variableCS2
			//
			case 174:
 
			//
			// Rule 175:  variableListCS2 ::= variableListCS2 , variableCS
			//
			case 175: {
				
				EList result = (EList)dtParser.getSym(1);
				result.add(dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 178:  featureCallExpCS ::= MINUS isMarkedPreCS ( argumentsCSopt )
			//
			case 178:
 
			//
			// Rule 179:  featureCallExpCS ::= not isMarkedPreCS ( argumentsCSopt )
			//
			case 179: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.IDENTIFIER_LITERAL,
							getTokenText(dtParser.getToken(1))
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(1)));
				CSTNode result = createOperationCallExpCS(
						simpleNameCS,
						(IsMarkedPreCS)dtParser.getSym(2),
						(EList)dtParser.getSym(4)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(5)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 180:  operationCallExpCS ::= simpleNameCS isMarkedPreCS ( argumentsCSopt )
			//
			case 180: {
				
				CSTNode result = createOperationCallExpCS(
						(SimpleNameCS)dtParser.getSym(1),
						(IsMarkedPreCS)dtParser.getSym(2),
						(EList)dtParser.getSym(4)
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1), getIToken(dtParser.getToken(5)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 181:  operationCallExpCS ::= oclIsUndefined isMarkedPreCS ( argumentsCSopt )
			//
			case 181:
 
			//
			// Rule 182:  operationCallExpCS ::= oclIsInvalid isMarkedPreCS ( argumentsCSopt )
			//
			case 182:
 
			//
			// Rule 183:  operationCallExpCS ::= oclIsNew isMarkedPreCS ( argumentsCSopt )
			//
			case 183:
 
			//
			// Rule 184:  operationCallExpCS ::= EQUAL isMarkedPreCS ( argumentsCSopt )
			//
			case 184:
 
			//
			// Rule 185:  operationCallExpCS ::= NOT_EQUAL isMarkedPreCS ( argumentsCSopt )
			//
			case 185:
 
			//
			// Rule 186:  operationCallExpCS ::= PLUS isMarkedPreCS ( argumentsCSopt )
			//
			case 186:
 
			//
			// Rule 187:  operationCallExpCS ::= MULTIPLY isMarkedPreCS ( argumentsCSopt )
			//
			case 187:
 
			//
			// Rule 188:  operationCallExpCS ::= DIVIDE isMarkedPreCS ( argumentsCSopt )
			//
			case 188:
 
			//
			// Rule 189:  operationCallExpCS ::= GREATER isMarkedPreCS ( argumentsCSopt )
			//
			case 189:
 
			//
			// Rule 190:  operationCallExpCS ::= LESS isMarkedPreCS ( argumentsCSopt )
			//
			case 190:
 
			//
			// Rule 191:  operationCallExpCS ::= GREATER_EQUAL isMarkedPreCS ( argumentsCSopt )
			//
			case 191:
 
			//
			// Rule 192:  operationCallExpCS ::= LESS_EQUAL isMarkedPreCS ( argumentsCSopt )
			//
			case 192:
 
			//
			// Rule 193:  operationCallExpCS ::= and isMarkedPreCS ( argumentsCSopt )
			//
			case 193:
 
			//
			// Rule 194:  operationCallExpCS ::= or isMarkedPreCS ( argumentsCSopt )
			//
			case 194:
 
			//
			// Rule 195:  operationCallExpCS ::= xor isMarkedPreCS ( argumentsCSopt )
			//
			case 195:
 
			//
			// Rule 196:  keywordOperationCallExpCS ::= keywordAsIdentifier isMarkedPreCS ( argumentsCSopt )
			//
			case 196: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.IDENTIFIER_LITERAL,
							getTokenText(dtParser.getToken(1))
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(1)));
				CSTNode result = createOperationCallExpCS(
						simpleNameCS,
						(IsMarkedPreCS)dtParser.getSym(2),
						(EList)dtParser.getSym(4)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(5)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 197:  operationCallExpCS ::= oclIsInState isMarkedPreCS ( pathNameCSOpt )
			//
			case 197: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.KEYWORD_LITERAL,
							getTokenText(dtParser.getToken(1))
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(1)));

				PathNameCS pathNameCS = (PathNameCS) dtParser.getSym(4);
				StateExpCS stateExpCS = createStateExpCS(pathNameCS);
				setOffsets(stateExpCS, pathNameCS);
			
				CSTNode result = createOperationCallExpCS(
						simpleNameCS,
						(IsMarkedPreCS)dtParser.getSym(2),
						stateExpCS
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(5)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 198:  attrOrNavCallExpCS ::= simpleNameCS isMarkedPreCS
			//
			case 198: {
				
				IsMarkedPreCS isMarkedPreCS = (IsMarkedPreCS)dtParser.getSym(2);
				CSTNode result = createFeatureCallExpCS(
						(SimpleNameCS)dtParser.getSym(1),
						new BasicEList(),
						isMarkedPreCS
					);
				if (isMarkedPreCS.isPre()) {
					setOffsets(result, (CSTNode)dtParser.getSym(1), (CSTNode)dtParser.getSym(2));
				} else {
					setOffsets(result, (CSTNode)dtParser.getSym(1));
				}
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 199:  attrOrNavCallExpCS ::= keywordAsIdentifier isMarkedPreCS
			//
			case 199: {
				
				IsMarkedPreCS isMarkedPreCS = (IsMarkedPreCS)dtParser.getSym(2);
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.IDENTIFIER_LITERAL,
							getTokenText(dtParser.getToken(1))
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(1)));
				CSTNode result = createFeatureCallExpCS(
						simpleNameCS,
						new BasicEList(),
						isMarkedPreCS
					);
				if (isMarkedPreCS.isPre()) {
					setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode)dtParser.getSym(2));
				} else {
					setOffsets(result, getIToken(dtParser.getToken(1)));
				}
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 200:  isMarkedPreCS ::= $Empty
			//
			case 200: {
				
				CSTNode result = createIsMarkedPreCS(false);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 201:  isMarkedPreCS ::= @pre
			//
			case 201: {
				
				CSTNode result = createIsMarkedPreCS(true);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 202:  argumentsCSopt ::= $Empty
			//
			case 202:
				dtParser.setSym1(new BasicEList());
				break;
 
			//
			// Rule 204:  argumentsCS ::= oclExpressionCS
			//
			case 204: {
				
				EList result = new BasicEList();
				result.add(dtParser.getSym(1));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 205:  argumentsCS ::= argumentsCS , oclExpressionCS
			//
			case 205: {
				
				EList result = (EList)dtParser.getSym(1);
				result.add(dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 206:  letExpCS ::= let variableCS letExpSubCSopt in oclExpressionCS
			//
			case 206: {
				
				EList variables = (EList)dtParser.getSym(3);
				variables.add(0, dtParser.getSym(2));
				CSTNode result = createLetExpCS(
						variables,
						(OCLExpressionCS)dtParser.getSym(5)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode)dtParser.getSym(5));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 207:  letExpSubCSopt ::= $Empty
			//
			case 207:
				dtParser.setSym1(new BasicEList());
				break;
 
			//
			// Rule 209:  letExpSubCS ::= , variableCS
			//
			case 209: {
				
				EList result = new BasicEList();
				result.add(dtParser.getSym(2));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 210:  letExpSubCS ::= letExpSubCS , variableCS
			//
			case 210: {
				
				EList result = (EList)dtParser.getSym(1);
				result.add(dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 211:  messageExpCS ::= ^ simpleNameCS ( oclMessageArgumentsCSopt )
			//
			case 211:
 
			//
			// Rule 212:  messageExpCS ::= ^^ simpleNameCS ( oclMessageArgumentsCSopt )
			//
			case 212: {
				
				CSTNode result = createMessageExpCS(
						getIToken(dtParser.getToken(1)).getKind() == QvtOpLPGParsersym.TK_CARET,
						(SimpleNameCS)dtParser.getSym(2),
						(EList<OCLMessageArgCS>)dtParser.getSym(4)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(5)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 213:  oclMessageArgumentsCSopt ::= $Empty
			//
			case 213:
				dtParser.setSym1(new BasicEList());
				break;
 
			//
			// Rule 215:  oclMessageArgumentsCS ::= oclMessageArgCS
			//
			case 215: {
				
				EList result = new BasicEList();
				result.add(dtParser.getSym(1));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 216:  oclMessageArgumentsCS ::= oclMessageArgumentsCS , oclMessageArgCS
			//
			case 216: {
				
				EList result = (EList)dtParser.getSym(1);
				result.add(dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 217:  oclMessageArgCS ::= oclExpressionCS
			//
			case 217: {
				
				CSTNode result = createOCLMessageArgCS(
						null,
						(OCLExpressionCS)dtParser.getSym(1)
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 218:  oclMessageArgCS ::= ?
			//
			case 218: {
				
				CSTNode result = createOCLMessageArgCS(
						null,
						null
					);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 219:  oclMessageArgCS ::= ? : typeCS
			//
			case 219: {
				
				CSTNode result = createOCLMessageArgCS(
						(TypeCS)dtParser.getSym(3),
						null
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 220:  qualifierList ::= $Empty
			//
			case 220:
				dtParser.setSym1(new BasicEList());
				break;
 
			//
			// Rule 221:  qualifierList ::= qualifierList qualifier
			//
			case 221: {
				
				EList result = (EList) dtParser.getSym(1);
				result.add(dtParser.getSym(2));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 222:  qualifier ::= blackbox
			//
			case 222:
 
			//
			// Rule 223:  qualifier ::= abstract
			//
			case 223:
 
			//
			// Rule 224:  qualifier ::= static
			//
			case 224: {
				
				CSTNode result = createSimpleNameCS(SimpleTypeEnum.KEYWORD_LITERAL, getTokenText(dtParser.getToken(1)));
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 225:  colon_param_listOpt ::= $Empty
			//
			case 225:
				dtParser.setSym1(new BasicEList());
				break;
 
			//
			// Rule 226:  colon_param_listOpt ::= : param_list
			//
			case 226: {
				
				dtParser.setSym1(dtParser.getSym(2));
	  		  break;
			}
	 
			//
			// Rule 227:  complete_signature ::= simple_signature colon_param_listOpt
			//
			case 227: {
				
				SimpleSignatureCS simpleSignatureCS = (SimpleSignatureCS)dtParser.getSym(1);
				EList<ParameterDeclarationCS> resultList = (EList<ParameterDeclarationCS>)dtParser.getSym(2);
				CSTNode result = createCompleteSignatureCS(simpleSignatureCS, resultList);
				result.setStartOffset(simpleSignatureCS.getStartOffset());
				result.setEndOffset(getEndOffset(simpleSignatureCS.getEndOffset(), resultList));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 228:  simple_signature ::= ( param_listOpt )
			//
			case 228: {
				
				CSTNode result = createSimpleSignatureCS((EList<ParameterDeclarationCS>)dtParser.getSym(2));
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(3)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 229:  param_listOpt ::= $Empty
			//
			case 229:
				dtParser.setSym1(new BasicEList());
				break;
 
			//
			// Rule 231:  param_list ::= param_list , param
			//
			case 231: {
				
				EList result = (EList)dtParser.getSym(1);
				result.add(dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 232:  param_list ::= param
			//
			case 232: {
				
				EList result = new BasicEList();
				result.add(dtParser.getSym(1));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 233:  param ::= param_directionOpt IDENTIFIER : typespec
			//
			case 233: {
				
				CSTNode result = createParameterDeclarationCS(
						(DirectionKindCS)dtParser.getSym(1),
						getIToken(dtParser.getToken(2)),
						(TypeSpecCS)dtParser.getSym(4)
					);
				setOffsets(result, getIToken(dtParser.getToken(2)), (CSTNode)dtParser.getSym(4));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 234:  param ::= typespec
			//
			case 234: {
				
				CSTNode result = createParameterDeclarationCS(
						null, null, (TypeSpecCS)dtParser.getSym(1)
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 235:  param_directionOpt ::= $Empty
			//
			case 235:
				dtParser.setSym1(null);
				break;
 
			//
			// Rule 237:  param_direction ::= in
			//
			case 237: {
				
				CSTNode result = createDirectionKindCS(
						DirectionKindEnum.IN
					);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 238:  param_direction ::= out
			//
			case 238: {
				
				CSTNode result = createDirectionKindCS(
						DirectionKindEnum.OUT
					);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 239:  param_direction ::= inout
			//
			case 239: {
				
				CSTNode result = createDirectionKindCS(
						DirectionKindEnum.INOUT
					);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 240:  declarator ::= IDENTIFIER : typeCS
			//
			case 240: {
				
				CSTNode result = createVariableCS(
						getTokenText(dtParser.getToken(1)),
						(TypeCS)dtParser.getSym(3),
						null
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 241:  declarator ::= IDENTIFIER : typeCS = oclExpressionCS
			//
			case 241: {
				
				CSTNode result = createVariableCS(
						getTokenText(dtParser.getToken(1)),
						(TypeCS)dtParser.getSym(3),
						(OCLExpressionCS)dtParser.getSym(5)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode)dtParser.getSym(5));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 242:  declarator ::= IDENTIFIER : typeCS := oclExpressionCS
			//
			case 242: {
				
				CSTNode result = createVariableCS(
						getTokenText(dtParser.getToken(1)),
						(TypeCS)dtParser.getSym(3),
						(OCLExpressionCS)dtParser.getSym(5)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode)dtParser.getSym(5));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 243:  declarator ::= IDENTIFIER := oclExpressionCS
			//
			case 243: {
				
				CSTNode result = createVariableCS(
						getTokenText(dtParser.getToken(1)),
						null,
						(OCLExpressionCS)dtParser.getSym(3)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 244:  typespec ::= typeCS
			//
			case 244: {
				
				CSTNode result = createTypeSpecCS(
					(TypeCS)dtParser.getSym(1),
					null
					);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 245:  typespec ::= typeCS @ IDENTIFIER
			//
			case 245: {
				
				CSTNode result = createTypeSpecCS(
					(TypeCS)dtParser.getSym(1),
					getIToken(dtParser.getToken(3))
					);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 249:  scoped_identifier ::= typeCS2 :: IDENTIFIER
			//
			case 249: {
				
				ScopedNameCS result = createScopedNameCS((TypeCS)dtParser.getSym(1), getTokenText(dtParser.getToken(3)));		
				setOffsets(result, (CSTNode) dtParser.getSym(1), getIToken(dtParser.getToken(3)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 250:  scoped_identifier ::= typeCS2 :: qvtErrorToken
			//
			case 250: {
				
				ScopedNameCS result = createScopedNameCS((TypeCS)dtParser.getSym(1), ""); //$NON-NLS-1$		
				setOffsets(result, (CSTNode) dtParser.getSym(1), getIToken(dtParser.getToken(2)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 251:  scoped_identifier ::= scoped_identifier2
			//
			case 251: {
				
				PathNameCS pathNameCS = (PathNameCS)dtParser.getSym(1);
                                    String name = pathNameCS.getSequenceOfNames().remove(pathNameCS.getSequenceOfNames().size() - 1);
				TypeCS typeCS = pathNameCS.getSequenceOfNames().isEmpty() ? null : pathNameCS;

				ScopedNameCS result = createScopedNameCS(typeCS, name);		

				setOffsets(result, pathNameCS);

                                    // reduce the region by the removed name element
				pathNameCS.setEndOffset(pathNameCS.getEndOffset() - (name != null ? name.length() : 0) - 2);
				
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 252:  scoped_identifier2 ::= IDENTIFIER
			//
			case 252: {
				
				CSTNode result = createPathNameCS(getTokenText(dtParser.getToken(1)));
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 253:  scoped_identifier2 ::= main
			//
			case 253: {
				
				CSTNode result = createPathNameCS(getTokenText(dtParser.getToken(1)));
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 254:  scoped_identifier2 ::= scoped_identifier2 :: IDENTIFIER
			//
			case 254: {
				
				PathNameCS result = (PathNameCS)dtParser.getSym(1);
				result = extendPathNameCS(result, getTokenText(dtParser.getToken(3)));
				setOffsets(result, result, getIToken(dtParser.getToken(3)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 255:  scoped_identifier2 ::= scoped_identifier2 :: qvtErrorToken
			//
			case 255: {
				
				PathNameCS result = (PathNameCS)dtParser.getSym(1);
				result = extendPathNameCS(result, "");
				setOffsets(result, result, getIToken(dtParser.getToken(2)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 256:  scoped_identifier_list ::= scoped_identifier
			//
			case 256: {
				
				EList result = new BasicEList();
				result.add(dtParser.getSym(1));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 257:  scoped_identifier_list ::= scoped_identifier_list , scoped_identifier
			//
			case 257: {
				
				EList result = (EList)dtParser.getSym(1);
				result.add(dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 261:  expression_semi_list ::= statementCS
			//
			case 261: {
				
				EList result = new BasicEList();
				result.add(dtParser.getSym(1));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 262:  expression_semi_list ::= expression_semi_list ; statementCS
			//
			case 262: {
				
				EList result = (EList)dtParser.getSym(1);
				result.add(dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 263:  expression_block ::= { statementListOpt }
			//
			case 263: {
				
			EList bodyList = (EList) dtParser.getSym(2);
			CSTNode result = createBlockExpCS(
				bodyList
			);
			
			setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(3)));
			dtParser.setSym1(result);
      		  break;
			}
	 
			//
			// Rule 267:  qualifiedNameCS ::= qvtIdentifierCS
			//
			case 267: {
				
				CSTNode result = createPathNameCS(getTokenText(dtParser.getToken(1)));
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 268:  qualifiedNameCS ::= qualifiedNameCS . qvtIdentifierCS
			//
			case 268: {
				
				PathNameCS result = (PathNameCS)dtParser.getSym(1);
				result = extendPathNameCS(result, getTokenText(dtParser.getToken(3)));
				setOffsets(result, result, getIToken(dtParser.getToken(3)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 269:  qualifiedNameCS ::= qualifiedNameCS . qvtErrorToken
			//
			case 269: {
				
				PathNameCS result = (PathNameCS)dtParser.getSym(1);
				result = extendPathNameCS(result, "");
				setOffsets(result, result, getIToken(dtParser.getToken(2)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 270:  qualifiedNameCS ::= qualifiedNameCS qvtErrorToken
			//
			case 270: {
				
				PathNameCS result = (PathNameCS)dtParser.getSym(1);
				dtParser.setSym1(result);
	  		  break;
			}	
	 
			//
			// Rule 272:  oclExpressionCSOpt ::= $Empty
			//
			case 272:
				dtParser.setSym1(null);
				break;
 
			//
			// Rule 273:  statementListOpt ::= $Empty
			//
			case 273:
				dtParser.setSym1(new BasicEList());
				break;
 
			//
			// Rule 275:  statementList ::= qvtErrorToken
			//
			case 275: {
				
				EList result = new BasicEList();
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 278:  statementInnerList ::= statementCS
			//
			case 278: {
				
				EList result = new BasicEList();
				result.add(dtParser.getSym(1));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 279:  statementInnerList ::= statementInnerList ; statementCS
			//
			case 279: {
				
				EList result = (EList)dtParser.getSym(1);
				result.add(dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 280:  statementInnerList ::= statementInnerList qvtErrorToken
			//
			case 280: {
				
				EList result = (EList)dtParser.getSym(1);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 281:  statementCS ::= oclExpressionCS
			//
			case 281: {
				
				CSTNode result = createExpressionStatementCS(
						(OCLExpressionCS)dtParser.getSym(1)
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 282:  statementCS ::= primaryOCLExpressionCS
			//
			case 282: {
				
				CSTNode result = createExpressionStatementCS(
						(OCLExpressionCS)dtParser.getSym(1)
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 285:  ifExpCS ::= if oclExpressionCS then ifExpBodyCS else ifExpBodyCS endif
			//
			case 285: {
				
				CSTNode result = createIfExpCS(
						(OCLExpressionCS)dtParser.getSym(2),
						(OCLExpressionCS)dtParser.getSym(4),
						(OCLExpressionCS)dtParser.getSym(6)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(7)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 286:  ifExpCS ::= if oclExpressionCS then ifExpBodyCS endif
			//
			case 286: {
				
				CSTNode result = createIfExpCS(
						(OCLExpressionCS)dtParser.getSym(2),
						(OCLExpressionCS)dtParser.getSym(4),
						null
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(5)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 287:  ifExpCS ::= if oclExpressionCS then ifExpBodyCS else ifExpBodyCS qvtErrorToken
			//
			case 287: {
				
				CSTNode result = createIfExpCS(
						(OCLExpressionCS)dtParser.getSym(2),
						(OCLExpressionCS)dtParser.getSym(4),
						(OCLExpressionCS)dtParser.getSym(6)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode)dtParser.getSym(6));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 288:  ifExpCS ::= if oclExpressionCS then ifExpBodyCS else qvtErrorToken
			//
			case 288: {
				
				CSTNode result = createIfExpCS(
						(OCLExpressionCS)dtParser.getSym(2),
						(OCLExpressionCS)dtParser.getSym(4),
						null
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(5)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 289:  ifExpCS ::= if oclExpressionCS then ifExpBodyCS qvtErrorToken
			//
			case 289: {
				
				CSTNode result = createIfExpCS(
						(OCLExpressionCS)dtParser.getSym(2),
						(OCLExpressionCS)dtParser.getSym(4),
						null
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode)dtParser.getSym(4));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 290:  ifExpCS ::= if oclExpressionCS then qvtErrorToken
			//
			case 290: {
				
				CSTNode result = createIfExpCS(
						(OCLExpressionCS)dtParser.getSym(2),
						null,
						null
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(3)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 291:  ifExpCS ::= if oclExpressionCS qvtErrorToken
			//
			case 291: {
				
				CSTNode result = createIfExpCS(
						(OCLExpressionCS)dtParser.getSym(2),
						null,
						null
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode)dtParser.getSym(2));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 292:  ifExpCS ::= if qvtErrorToken
			//
			case 292: {
				
				CSTNode result = createIfExpCS(
						null,
						null,
						null
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 294:  letExpSubCS3 ::= variableCS2
			//
			case 294: {
				
				EList result = new BasicEList();
				result.add(dtParser.getSym(1));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 295:  letExpSubCS3 ::= letExpSubCS3 , variableCS2
			//
			case 295: {
				
				EList result = (EList)dtParser.getSym(1);
				result.add(dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 296:  letExpCS ::= let letExpSubCS3 in oclExpressionCS
			//
			case 296: {
				
				EList variables = (EList)dtParser.getSym(2);
				CSTNode result = createLetExpCS(
						variables,
						(OCLExpressionCS)dtParser.getSym(4)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode)dtParser.getSym(4));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 297:  letExpCS ::= let letExpSubCS3 in qvtErrorToken
			//
			case 297: {
				
				EList variables = (EList)dtParser.getSym(2);
				CSTNode result = createLetExpCS(
						variables,
						createSimpleNameCS(SimpleTypeEnum.IDENTIFIER_LITERAL, "") //$NON-NLS-1$
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 298:  iterContents ::= variableCS | qvtErrorToken
			//
			case 298: {
				
				CSTNode fakeCS = createSimpleNameCS(SimpleTypeEnum.IDENTIFIER_LITERAL, ""); //$NON-NLS-1$
				setOffsets(fakeCS, getIToken(dtParser.getToken(3)));
				dtParser.setSym1(new Object[] {
						dtParser.getSym(1),
						null,
						fakeCS
					});
	  		  break;
			}
	 
			//
			// Rule 299:  callExpCS ::= . qvtErrorToken
			//
			case 299: {
				
				CallExpCS result = TempFactory.eINSTANCE.createErrorCallExpCS();
	 			result.setAccessor(DotOrArrowEnum.DOT_LITERAL);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 300:  callExpCS ::= -> qvtErrorToken
			//
			case 300: {
				
				CallExpCS result = TempFactory.eINSTANCE.createErrorCallExpCS();
	 			result.setAccessor(DotOrArrowEnum.ARROW_LITERAL);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 301:  argumentsCS ::= qvtErrorToken
			//
			case 301:
				dtParser.setSym1(new BasicEList());
				break;
 
			//
			// Rule 314:  iteratorExpCS ::= iteratorExpCSToken ( qvtErrorToken
			//
			case 314: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.KEYWORD_LITERAL,
							getTokenText(dtParser.getToken(1))
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(1)));
				CSTNode result = createIteratorExpCS(
						simpleNameCS,
						null,
						null,
						null
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(2)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 315:  operationCallExpCS ::= oclAsType isMarkedPreCS ( typeCS )
			//
			case 315:
 
			//
			// Rule 316:  operationCallExpCS ::= oclIsKindOf isMarkedPreCS ( typeCS )
			//
			case 316:
 
			//
			// Rule 317:  operationCallExpCS ::= oclIsTypeOf isMarkedPreCS ( typeCS )
			//
			case 317: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.IDENTIFIER_LITERAL,
							getTokenText(dtParser.getToken(1))
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(1)));
				EList params = new BasicEList();
				params.add(dtParser.getSym(4));
				CSTNode result = createOperationCallExpCS(
						simpleNameCS,
						(IsMarkedPreCS)dtParser.getSym(2),
						params
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(5)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 320:  qvtErrorToken ::= ERROR_TOKEN
			//
			case 320: {
				
				diagnozeErrorToken(dtParser.getToken(1));
	  		  break;
			}
	 
			//
			// Rule 321:  equalityExpCS ::= equalityExpCS != relationalExpCS
			//
			case 321:
 
			//
			// Rule 322:  equalityWithLet ::= equalityExpCS != relationalWithLet
			//
			case 322: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.STRING_LITERAL,
							OCLStandardLibraryUtil.getOperationName(PredefinedType.NOT_EQUAL)
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(2)));
				EList args = new BasicEList();
				args.add(dtParser.getSym(3));
				CSTNode result = createOperationCallExpCS(
						(OCLExpressionCS)dtParser.getSym(1),
						simpleNameCS,
						args
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1), (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 323:  _import ::= import library unit ;
			//
			case 323: {
				
				CSTNode result = createLibraryImportCS(
						(PathNameCS)dtParser.getSym(3)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(4)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 325:  renaming ::= rename typeCS . qvtIdentifierCS = stringLiteralExpCS ;
			//
			case 325: {
				
				CSTNode result = createRenameCS(
						(TypeCS)dtParser.getSym(2),
						getIToken(dtParser.getToken(4)),
						(StringLiteralExpCS)dtParser.getSym(6)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(7)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 327:  legacyWhileExpCS ::= while ( oclExpressionCS ; oclExpressionCS ) whileBodyCS
			//
			case 327: {
				
				CSTNode result = createLegacyWhileExpCS(
						(OCLExpressionCS)dtParser.getSym(3),
						(OCLExpressionCS)dtParser.getSym(5),
						(BlockExpCS)dtParser.getSym(7)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode)dtParser.getSym(7));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 329:  returnExpCS ::= return oclExpressionCSOpt
			//
			case 329: {
				
			ReturnExpCS returnExpCS = createReturnExpCS((OCLExpressionCS)dtParser.getSym(2));
			CSTNode result = createExpressionStatementCS(returnExpCS);
			if(returnExpCS.getValue() != null) {
				setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode)dtParser.getSym(2));			
			} else {
				setOffsets(result, getIToken(dtParser.getToken(1)));
			}
			setOffsets(returnExpCS, result);
			dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 333:  variableInitializationCSCorrect ::= var IDENTIFIER : typeCS := oclExpressionCS
			//
			case 333: {
				
				CSTNode result = createVariableInitializationCS(
						getIToken(dtParser.getToken(2)),
						(TypeCS)dtParser.getSym(4),
						(OCLExpressionCS)dtParser.getSym(6)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode)dtParser.getSym(6));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 334:  variableInitializationCS ::= var IDENTIFIER : typeCS := qvtErrorToken
			//
			case 334: {
				
				CSTNode result = createVariableInitializationCS(
						getIToken(dtParser.getToken(2)),
						(TypeCS)dtParser.getSym(4),
						null
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(5)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 335:  variableInitializationCSCorrect ::= var IDENTIFIER := oclExpressionCS
			//
			case 335: {
				
				CSTNode result = createVariableInitializationCS(
						getIToken(dtParser.getToken(2)),
						null,
						(OCLExpressionCS)dtParser.getSym(4)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode)dtParser.getSym(4));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 336:  variableInitializationCS ::= var IDENTIFIER := qvtErrorToken
			//
			case 336: {
				
				CSTNode result = createVariableInitializationCS(
						getIToken(dtParser.getToken(2)),
						null,
						null
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(3)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 337:  variableInitializationCSCorrect ::= var IDENTIFIER : typeCS
			//
			case 337: {
				
				CSTNode result = createVariableInitializationCS(
						getIToken(dtParser.getToken(2)),
						(TypeCS)dtParser.getSym(4),
						null
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode)dtParser.getSym(4));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 338:  variableInitializationCS ::= var IDENTIFIER : qvtErrorToken
			//
			case 338: {
				
				CSTNode result = createVariableInitializationCS(
						getIToken(dtParser.getToken(2)),
						null,
						null
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(3)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 339:  assignStatementCS ::= oclExpressionCS := oclExpressionCS
			//
			case 339: {
				
				CSTNode result = createAssignStatementCS(
						(OCLExpressionCS)dtParser.getSym(1),
						(OCLExpressionCS)dtParser.getSym(3),
						false
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1), (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 340:  assignStatementCS ::= oclExpressionCS := qvtErrorToken
			//
			case 340: {
				
				CSTNode result = createAssignStatementCS(
						(OCLExpressionCS)dtParser.getSym(1),
						createSimpleNameCS(SimpleTypeEnum.IDENTIFIER_LITERAL, ""), //$NON-NLS-1$
						false
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1), getIToken(dtParser.getToken(2)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 341:  assignStatementCS ::= oclExpressionCS += oclExpressionCS
			//
			case 341: {
				
				CSTNode result = createAssignStatementCS(
						(OCLExpressionCS)dtParser.getSym(1),
						(OCLExpressionCS)dtParser.getSym(3),
						true
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1), (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 342:  assignStatementCS ::= oclExpressionCS += qvtErrorToken
			//
			case 342: {
				
				CSTNode result = createAssignStatementCS(
						(OCLExpressionCS)dtParser.getSym(1),
						createSimpleNameCS(SimpleTypeEnum.IDENTIFIER_LITERAL, ""), //$NON-NLS-1$
						true
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1), getIToken(dtParser.getToken(2)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 344:  whileExpCS ::= while ( declarator ; oclExpressionCS ) whileBodyCS
			//
			case 344: {
				
				CSTNode result = createWhileExpCS(
						(VariableCS)dtParser.getSym(3),
						(OCLExpressionCS)dtParser.getSym(5),
						(BlockExpCS)dtParser.getSym(7)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode)dtParser.getSym(7));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 345:  whileExpCS ::= while ( oclExpressionCS ) whileBodyCS
			//
			case 345: {
				
				CSTNode result = createWhileExpCS(
						null,
						(OCLExpressionCS)dtParser.getSym(3),
						(BlockExpCS)dtParser.getSym(5)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode)dtParser.getSym(5));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 349:  forExpDeclaratorList ::= IDENTIFIER
			//
			case 349: {
				
		EList result = new BasicEList();
		result.add(getIToken(dtParser.getToken(1)));
		dtParser.setSym1(result);
          		  break;
			}
    	 
			//
			// Rule 350:  forExpDeclaratorList ::= forExpDeclaratorList , IDENTIFIER
			//
			case 350: {
				
		EList result = (EList)dtParser.getSym(1);
		result.add(getIToken(dtParser.getToken(3)));
		dtParser.setSym1(result);
          		  break;
			}
    	 
			//
			// Rule 351:  forExpConditionOpt ::= $Empty
			//
			case 351:
				dtParser.setSym1(null);
				break;
 
			//
			// Rule 352:  forExpConditionOpt ::= | oclExpressionCS
			//
			case 352: {
				
            	    dtParser.setSym1((OCLExpressionCS)dtParser.getSym(2));
          		  break;
			}
    	 
			//
			// Rule 353:  forExpConditionOpt ::= | qvtErrorToken
			//
			case 353:
				dtParser.setSym1(null);
				break;
 
			//
			// Rule 354:  forExpCS ::= forOpCode ( forExpDeclaratorList forExpConditionOpt ) expression_block
			//
			case 354: {
				
				CSTNode result = createForExpCS(
						getIToken(dtParser.getToken(1)),
						(EList)dtParser.getSym(3),
						(OCLExpressionCS)dtParser.getSym(4),
						(BlockExpCS)dtParser.getSym(6)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode)dtParser.getSym(6));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 355:  forExpCS ::= forOpCode qvtErrorToken
			//
			case 355: {
				
				CSTNode result = createForExpCS(
						getIToken(dtParser.getToken(1)),
						null,
						null,
						null
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 359:  switchExpCS ::= switch switchBodyExpCS
			//
			case 359: {
				
				Object[] switchBody = (Object[]) dtParser.getSym(2);

				CSTNode result = createSwitchExpCS(
						(EList<SwitchAltExpCS>) switchBody[0],
						(OCLExpressionCS) switchBody[1]
					);
				if (switchBody[2] instanceof IToken) { // In case of correct and incorrect syntax
					setOffsets(result, getIToken(dtParser.getToken(1)), (IToken) switchBody[2]);
				} else { // In case of errors in switchBody
					setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode) switchBody[2]);
				}
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 361:  switchDeclaratorCS ::= IDENTIFIER
			//
			case 361: {
				
				CSTNode result = createVariableCS(
						getTokenText(dtParser.getToken(1)),
						null,
						null
					);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 362:  switchDeclaratorCS ::= IDENTIFIER = oclExpressionCS
			//
			case 362: {
				
				CSTNode result = createVariableCS(
						getTokenText(dtParser.getToken(1)),
						null,
						(OCLExpressionCS)dtParser.getSym(3)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode)dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 363:  iterateSwitchExpCS ::= switch ( switchDeclaratorCS ) switchBodyExpCS
			//
			case 363: {
				
				Object[] switchBody = (Object[]) dtParser.getSym(5);

				OCLExpressionCS switchExpCS = (OCLExpressionCS) createSwitchExpCS(
						(EList<SwitchAltExpCS>) switchBody[0],
						(OCLExpressionCS) switchBody[1]							
					);
				if (switchBody[2] instanceof IToken) { // In case of correct and incorrect syntax
					setOffsets(switchExpCS, getIToken(dtParser.getToken(1)), (IToken) switchBody[2]);
				} else if (switchBody[2] instanceof CSTNode) { // In case of errors in switchBody
					setOffsets(switchExpCS, getIToken(dtParser.getToken(1)), (CSTNode) switchBody[2]);
				} else { // In case of errors in switchBody
					setOffsets(switchExpCS, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(4)));
				}

				EList<VariableCS> iterators = new BasicEList<VariableCS>();
				iterators.add((VariableCS) dtParser.getSym(3));
				CSTNode result = createImperativeIterateExpCS(
						createSimpleNameCS(SimpleTypeEnum.IDENTIFIER_LITERAL, "xcollect"), //$NON-NLS-1$
						iterators,
						null,
						switchExpCS,
						null
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(5)));
				
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 364:  switchExpCS ::= switch qvtErrorToken
			//
			case 364: {
				
				CSTNode result = createSwitchExpCS(
						new BasicEList(),
						null
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 365:  switchBodyExpCS ::= { switchAltExpCSList switchElseExpCSOpt }
			//
			case 365: {
				
				Object[] result = new Object[] {dtParser.getSym(2), dtParser.getSym(3), getIToken(dtParser.getToken(4))};
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 366:  switchBodyExpCS ::= { switchAltExpCSList switchElseExpCSOpt qvtErrorToken
			//
			case 366: {
				
				Object[] result = new Object[] {dtParser.getSym(2), dtParser.getSym(3), dtParser.getSym(3)};
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 367:  switchBodyExpCS ::= { qvtErrorToken
			//
			case 367: {
				
				Object[] result = new Object[] {new BasicEList(), null, getIToken(dtParser.getToken(1))};
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 368:  switchAltExpCSList ::= switchAltExpCS
			//
			case 368: {
				
				EList result = new BasicEList();
				result.add(dtParser.getSym(1));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 369:  switchAltExpCSList ::= switchAltExpCSList switchAltExpCS
			//
			case 369: {
				
				EList result = (EList)dtParser.getSym(1);
				result.add(dtParser.getSym(2));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 370:  switchAltExpCS ::= ( oclExpressionCS ) ? statementCS ;
			//
			case 370: {
				
				CSTNode result = createSwitchAltExpCS(
						(OCLExpressionCS) dtParser.getSym(2),
						(OCLExpressionCS) dtParser.getSym(5)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(6)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 371:  switchAltExpCS ::= case ( oclExpressionCS ) expressionStatementCS
			//
			case 371: {
				
				CSTNode result = createSwitchAltExpCS(
						(OCLExpressionCS) dtParser.getSym(3),
						(OCLExpressionCS) dtParser.getSym(5)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(5)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 372:  switchAltExpCS ::= ( oclExpressionCS ) ? statementCS qvtErrorToken
			//
			case 372: {
				
				CSTNode result = createSwitchAltExpCS(
						(OCLExpressionCS) dtParser.getSym(2),
						(OCLExpressionCS) dtParser.getSym(5)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode) dtParser.getSym(5));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 373:  switchAltExpCS ::= ( oclExpressionCS ) qvtErrorToken
			//
			case 373: {
				
				CSTNode result = createSwitchAltExpCS(
						(OCLExpressionCS) dtParser.getSym(2),
						null
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(3)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 374:  switchAltExpCS ::= ( qvtErrorToken
			//
			case 374: {
				
				CSTNode result = createSwitchAltExpCS(
						null,
						null
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 375:  switchElseExpCSOpt ::= $Empty
			//
			case 375:
				dtParser.setSym1(null);
				break;
 
			//
			// Rule 377:  switchElseExpCS ::= else ? statementCS ;
			//
			case 377: {
				
				dtParser.setSym1((CSTNode)dtParser.getSym(3));
	  		  break;
			}
	 
			//
			// Rule 378:  switchElseExpCS ::= else expressionStatementCS
			//
			case 378: {
				
				dtParser.setSym1((CSTNode)dtParser.getSym(2));
	  		  break;
			}
	 
			//
			// Rule 379:  switchElseExpCS ::= else ? statementCS qvtErrorToken
			//
			case 379: {
				
				dtParser.setSym1((CSTNode)dtParser.getSym(3));
	  		  break;
			}
	 
			//
			// Rule 380:  switchElseExpCS ::= else qvtErrorToken
			//
			case 380: {
				
				dtParser.setSym1(null);
	  		  break;
			}
	 
			//
			// Rule 382:  logWhenExp ::= when oclExpressionCS
			//
			case 382: {
				
			OCLExpressionCS condition = (OCLExpressionCS) dtParser.getSym(2);
			dtParser.setSym1(condition);
      		  break;
			}
     
			//
			// Rule 384:  logWhenExpOpt ::= $Empty
			//
			case 384:
				dtParser.setSym1(null);
				break;
 
			//
			// Rule 385:  logExpCS ::= log ( argumentsCSopt ) logWhenExpOpt
			//
			case 385: {
				
			OCLExpressionCS condition = (OCLExpressionCS) dtParser.getSym(5);
			LogExpCS result = (LogExpCS)createLogExpCS((EList<OCLExpressionCS>)dtParser.getSym(3), condition);
			if(condition != null) {
				setOffsets(result, getIToken(dtParser.getToken(1)), condition);
			} else {
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(4)));
			}
			dtParser.setSym1(result);
      		  break;
			}
     
			//
			// Rule 387:  severityKindCS ::= simpleNameCS
			//
			case 387: {
				
			dtParser.setSym1(dtParser.getSym(1));
	  		  break;
			}
	 
			//
			// Rule 389:  severityKindCSOpt ::= $Empty
			//
			case 389:
				dtParser.setSym1(null);
				break;
 
			//
			// Rule 390:  assertWithLogExp ::= with logExpCS
			//
			case 390: {
				
			LogExpCS logExp = (LogExpCS) dtParser.getSym(2);
			setOffsets(logExp, getIToken(dtParser.getToken(2)), logExp);
			dtParser.setSym1(logExp);
      		  break;
			}
     
			//
			// Rule 392:  assertWithLogExpOpt ::= $Empty
			//
			case 392:
				dtParser.setSym1(null);
				break;
 
			//
			// Rule 393:  assertExpCS ::= assert severityKindCSOpt ( oclExpressionCS ) assertWithLogExpOpt
			//
			case 393: {
				
			LogExpCS logExpCS = (LogExpCS)dtParser.getSym(6);
			OCLExpressionCS condition = (OCLExpressionCS)dtParser.getSym(4);
			AssertExpCS result = (AssertExpCS)createAssertExpCS(condition, (SimpleNameCS)dtParser.getSym(2), logExpCS);
	
			CSTNode end = logExpCS != null ? logExpCS : condition; 
			setOffsets(result, getIToken(dtParser.getToken(1)), end);
			dtParser.setSym1(result);
      		  break;
			}
     
			//
			// Rule 394:  computeExpCS ::= compute ( declarator ) expression_block
			//
			case 394: {
				
				CSTNode result = createComputeExpCS(
					(VariableCS) dtParser.getSym(3),
					(OCLExpressionCS) dtParser.getSym(5)
				);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(5)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 405:  imperativeIterateExpCS ::= imperativeIteratorExpCSToken12 ( imperativeIterContents12 )
			//
			case 405:
 
			//
			// Rule 406:  imperativeIterateExpCS ::= imperativeIteratorExpCSToken3 ( imperativeIterContents3 )
			//
			case 406: {
				
				String opCode = getTokenText(dtParser.getToken(1));
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.KEYWORD_LITERAL,
							opCode
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(1)));
				Object[] iterContents = (Object[]) dtParser.getSym(3);
				OCLExpressionCS bodyCS = null;
				OCLExpressionCS conditionCS = null;
				if ("xcollect".equals(opCode) || "collectOne".equals(opCode)) { //$NON-NLS-1$ //$NON-NLS-2$ 
				    bodyCS = (OCLExpressionCS) iterContents[2];
				} else {
				    conditionCS = (OCLExpressionCS) iterContents[2];
				}
				CSTNode result = createImperativeIterateExpCS(
						simpleNameCS,
						(EList<VariableCS>)iterContents[0],
						(VariableCS)iterContents[1],
						bodyCS,
						conditionCS
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(4)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 407:  imperativeIterateExpCS ::= imperativeIteratorExpCSToken qvtErrorToken
			//
			case 407: {
				
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.KEYWORD_LITERAL,
							getTokenText(dtParser.getToken(1))
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(1)));
				CSTNode result = createImperativeIterateExpCS(
						simpleNameCS,
						ourEmptyEList,
						null,
						null,
						null
					);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 408:  imperativeIterContents12 ::= oclExpressionCS
			//
			case 408: {
				
				dtParser.setSym1(new Object[] {
						ourEmptyEList,
						null,
						dtParser.getSym(1)
					});
	  		  break;
			}
	 
			//
			// Rule 409:  imperativeIterContents12 ::= variableListCS | oclExpressionCS
			//
			case 409: {
				
				dtParser.setSym1(new Object[] {
						dtParser.getSym(1),
						null,
						dtParser.getSym(3)
					});
	  		  break;
			}
	 
			//
			// Rule 410:  imperativeIterContents3 ::= variableListCS ; variableCS2 | oclExpressionCS
			//
			case 410: {
				
				dtParser.setSym1(new Object[] {
						dtParser.getSym(1),
						dtParser.getSym(3),
						dtParser.getSym(5)
					});
	  		  break;
			}
	 
			//
			// Rule 411:  exclamationOpt ::= $Empty
			//
			case 411:
				dtParser.setSym1(null);
				break;
 
			//
			// Rule 413:  declarator_vsep ::= IDENTIFIER |
			//
			case 413: {
				
		CSTNode result = createVariableCS(
					getTokenText(dtParser.getToken(1)),
                                            null,
					null
					);
                    setOffsets(result, getIToken(dtParser.getToken(1)));
                    dtParser.setSym1(result);
          		  break;
			}
    	 
			//
			// Rule 414:  declarator_vsepOpt ::= $Empty
			//
			case 414:
				dtParser.setSym1(null);
				break;
 
			//
			// Rule 416:  callExpCS ::= -> featureCallExpCS exclamationOpt [ declarator_vsepOpt oclExpressionCS ]
			//
			case 416: {
				
	        String opCode = isTokenOfType(getIToken(dtParser.getToken(3)), QvtOpLPGParsersym.TK_EXCLAMATION_MARK) ?  "collectselectOne" : "collectselect"; //$NON-NLS-1$ //$NON-NLS-2$ 
		SimpleNameCS simpleNameCS = createSimpleNameCS(
				SimpleTypeEnum.KEYWORD_LITERAL,
				opCode
				);
		setOffsets(simpleNameCS, getIToken(dtParser.getToken(4)), getIToken(dtParser.getToken(7)));
		VariableCS variableCS = (VariableCS) dtParser.getSym(5);
		CSTNode result = createImperativeIterateExpCS(
					simpleNameCS,
					ourEmptyEList,
					variableCS,
					(OCLExpressionCS) dtParser.getSym(2),
					(OCLExpressionCS) dtParser.getSym(6)
				);
		setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(7)));
		dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 417:  oclExpCS ::= oclExpCS exclamationOpt [ oclExpressionCS ]
			//
			case 417: {
				
			        String opCode = isTokenOfType(getIToken(dtParser.getToken(2)), QvtOpLPGParsersym.TK_EXCLAMATION_MARK) ?  "selectOne" : "xselect"; //$NON-NLS-1$ //$NON-NLS-2$ 
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.KEYWORD_LITERAL,
							opCode
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(3)), getIToken(dtParser.getToken(5)));
				CallExpCS result = createImperativeIterateExpCS(
						simpleNameCS,
						ourEmptyEList,
						null,
						null,
						(OCLExpressionCS) dtParser.getSym(4)
					);
				result.setSource((OCLExpressionCS)dtParser.getSym(1));
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(5)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 418:  dotArrowExpCS ::= dotArrowExpCS . featureCallExpCS exclamationOpt [ oclExpressionCS ]
			//
			case 418: {
				
				CallExpCS callExpCS = (CallExpCS)dtParser.getSym(3);
				callExpCS.setSource((OCLExpressionCS)dtParser.getSym(1));
				callExpCS.setAccessor(DotOrArrowEnum.DOT_LITERAL);
				setOffsets(callExpCS, (CSTNode)dtParser.getSym(1), callExpCS);


			        String opCode = isTokenOfType(getIToken(dtParser.getToken(4)), QvtOpLPGParsersym.TK_EXCLAMATION_MARK) ?  "selectOne" : "xselect"; //$NON-NLS-1$ //$NON-NLS-2$ 
				SimpleNameCS simpleNameCS = createSimpleNameCS(
							SimpleTypeEnum.KEYWORD_LITERAL,
							opCode
						);
				setOffsets(simpleNameCS, getIToken(dtParser.getToken(5)), getIToken(dtParser.getToken(7)));
				CallExpCS result = createImperativeIterateExpCS(
						simpleNameCS,
						ourEmptyEList,
						null,
						null,
						(OCLExpressionCS) dtParser.getSym(6)
					);
				result.setSource(callExpCS);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(7)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 420:  newExpCS ::= new pathNameCS ( argumentsCSopt )
			//
			case 420: {
				
			OCLExpressionCS result = createNewRuleCallExpCS((PathNameCS)dtParser.getSym(2),(EList)dtParser.getSym(4));
			setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(5)));
			dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 421:  topLevel ::= unit_elementList
			//
			case 421: {
				
				EList<CSTNode> unitElements = (EList<CSTNode>)dtParser.getSym(1);
				dtParser.setSym1(setupTopLevel(unitElements));
	  		  break;
			}
	 
			//
			// Rule 423:  _import ::= import unit ;
			//
			case 423: {
				
				CSTNode result = createModuleImportCS(
						(PathNameCS)dtParser.getSym(2)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(3)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 424:  _import ::= import qvtErrorToken
			//
			case 424: {
				
				CSTNode result = createLibraryImportCS(
						createPathNameCS()
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 426:  identifier_list ::= IDENTIFIER
			//
			case 426: {
				
				EList result = new BasicEList();
				result.add(getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 427:  identifier_list ::= identifier_list , IDENTIFIER
			//
			case 427: {
				
				EList result = (EList) dtParser.getSym(1);
				result.add(getIToken(dtParser.getToken(3)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 428:  unit_elementList ::= unit_elementList unit_element
			//
			case 428: {
				
				EList list = (EList)dtParser.getSym(1);
				list.add(dtParser.getSym(2));
				dtParser.setSym1(list);
	  		  break;
			}
	 
			//
			// Rule 429:  unit_elementList ::= $Empty
			//
			case 429:
				dtParser.setSym1(new BasicEList());
				break;
 
			//
			// Rule 440:  transformation_decl ::= transformation_h ;
			//
			case 440: {
				
				TransformationHeaderCS headerCS = (TransformationHeaderCS) dtParser.getSym(1);
				setOffsets(headerCS, headerCS, getIToken(dtParser.getToken(2)));
				MappingModuleCS moduleCS = createMappingModuleCS(headerCS, ourEmptyEList);
				setOffsets(moduleCS, headerCS);
				dtParser.setSym1(moduleCS);
	  		  break;
			}
	 
			//
			// Rule 441:  transformation_def ::= transformation_h { module_elementList } semicolonOpt
			//
			case 441: {
				
				TransformationHeaderCS headerCS = (TransformationHeaderCS) dtParser.getSym(1);
				MappingModuleCS moduleCS = createMappingModuleCS(headerCS, (EList) dtParser.getSym(3));
				setOffsets(moduleCS, headerCS, getIToken(dtParser.getToken(4)));
				dtParser.setSym1(moduleCS);
	  		  break;
			}
	 
			//
			// Rule 443:  transformation_h ::= qualifierList transformation qualifiedNameCS
			//
			case 443: {
				
				EList qualifierList = (EList) dtParser.getSym(1);
				CSTNode result = createTransformationHeaderCS(
						qualifierList,
						(PathNameCS)dtParser.getSym(3),
						ourEmptyEList,
						ourEmptyEList,
						null
					);
				if (qualifierList.isEmpty()) {
					setOffsets(result, getIToken(dtParser.getToken(2)), getIToken(dtParser.getToken(4)));
				}
				else {
					setOffsets(result, (CSTNode) qualifierList.get(qualifierList.size()-1), getIToken(dtParser.getToken(4)));
				}
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 444:  transformation_h ::= qualifierList transformation qualifiedNameCS ( transfParamListOpt ) moduleUsageListOpt transformationRefineCSOpt
			//
			case 444: {
				
				EList qualifierList = (EList) dtParser.getSym(1);
				EList transfUsages = (EList) dtParser.getSym(7);
				TransformationRefineCS transfRefine = (TransformationRefineCS) dtParser.getSym(8);
				CSTNode result = createTransformationHeaderCS(
						qualifierList,
						(PathNameCS)dtParser.getSym(3),
						(EList)dtParser.getSym(5),
						transfUsages,
						transfRefine
					);
				if (qualifierList.isEmpty()) {
					setOffsets(result, getIToken(dtParser.getToken(2)));
				}
				else {
					setOffsets(result, (CSTNode) qualifierList.get(qualifierList.size()-1));
				}
				if (transfRefine == null) {
					if (transfUsages.isEmpty()) {
						setOffsets(result, result, getIToken(dtParser.getToken(6)));
					}
					else {
						setOffsets(result, result, (CSTNode) transfUsages.get(transfUsages.size()-1));
					}
				}
				else {
					setOffsets(result, result, transfRefine);
				}
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 445:  transformationRefineCSOpt ::= $Empty
			//
			case 445:
				dtParser.setSym1(null);
				break;
 
			//
			// Rule 446:  transformationRefineCSOpt ::= refines moduleRefCS enforcing IDENTIFIER
			//
			case 446: {
				
				CSTNode result = createTransformationRefineCS(
						(ModuleRefCS)dtParser.getSym(2),
						getIToken(dtParser.getToken(4))
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(4)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 447:  libraryHeaderCS ::= library qualifiedNameCS
			//
			case 447: {
				
				org.eclipse.m2m.internal.qvt.oml.cst.LibraryCS result = createLibraryCS(
						(PathNameCS)dtParser.getSym(2),
						ourEmptyEList,
						ourEmptyEList,
						ourEmptyEList,
						ourEmptyEList,
						ourEmptyEList
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), (PathNameCS)dtParser.getSym(2));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 448:  libraryHeaderCS ::= library qvtErrorToken
			//
			case 448: {
				
				CSTNode result = createLibraryCS(
						createPathNameCS(),
						ourEmptyEList,
						ourEmptyEList,
						ourEmptyEList,
						ourEmptyEList,
						ourEmptyEList
					);
				setOffsets(result, getIToken(dtParser.getToken(3)), getIToken(dtParser.getToken(3)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 449:  moduleUsageListOpt ::= $Empty
			//
			case 449:
				dtParser.setSym1(new BasicEList());
				break;
 
			//
			// Rule 451:  moduleUsageList ::= moduleUsageCS
			//
			case 451: {
				
				EList result = new BasicEList();
				result.add(dtParser.getSym(1));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 452:  moduleUsageList ::= moduleUsageList moduleUsageCS
			//
			case 452: {
				
				EList result = (EList) dtParser.getSym(1);
				result.add(dtParser.getSym(2));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 453:  moduleUsageCS ::= access moduleKindOpt moduleRefList
			//
			case 453: {
				
				EList moduleRefList = (EList)dtParser.getSym(3);
				CSTNode result = createModuleUsageCS(
						ImportKindEnum.ACCESS,
						(ModuleKindCS)dtParser.getSym(2),
						moduleRefList
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode)moduleRefList.get(moduleRefList.size()-1));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 454:  moduleUsageCS ::= extends moduleKindOpt moduleRefList
			//
			case 454: {
				
				EList moduleRefList = (EList)dtParser.getSym(3);
				CSTNode result = createModuleUsageCS(
						ImportKindEnum.EXTENSION,
						(ModuleKindCS)dtParser.getSym(2),
						moduleRefList
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode)moduleRefList.get(moduleRefList.size()-1));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 455:  moduleKindOpt ::= $Empty
			//
			case 455:
				dtParser.setSym1(null);
				break;
 
			//
			// Rule 457:  moduleKindCS ::= transformation
			//
			case 457: {
				
				CSTNode result = createModuleKindCS(
						ModuleKindEnum.TRANSFORMATION
					);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 458:  moduleKindCS ::= library
			//
			case 458: {
				
				CSTNode result = createModuleKindCS(
						ModuleKindEnum.LIBRARY
					);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 459:  moduleRefList ::= moduleRefCS
			//
			case 459: {
				
				EList result = new BasicEList();
				result.add(dtParser.getSym(1));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 460:  moduleRefList ::= moduleRefList , moduleRefCS
			//
			case 460: {
				
				EList result = (EList) dtParser.getSym(1);
				result.add(dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 461:  moduleRefList ::= moduleRefList qvtErrorToken
			//
			case 461: {
				
				EList result = (EList) dtParser.getSym(1);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 462:  moduleRefCS ::= pathNameCS
			//
			case 462: {
				
				CSTNode result = createModuleRefCS(
						(PathNameCS)dtParser.getSym(1),
						ourEmptyEList
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 463:  moduleRefCS ::= pathNameCS ( transfParamListOpt )
			//
			case 463: {
				
				CSTNode result = createModuleRefCS(
						(PathNameCS)dtParser.getSym(1),
						(EList)dtParser.getSym(3)
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1), getIToken(dtParser.getToken(4)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 464:  transfParamListOpt ::= $Empty
			//
			case 464:
				dtParser.setSym1(new BasicEList());
				break;
 
			//
			// Rule 466:  transfParamList ::= transfParamCS
			//
			case 466: {
				
				EList result = new BasicEList();
				result.add(dtParser.getSym(1));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 467:  transfParamList ::= transfParamList , transfParamCS
			//
			case 467: {
				
				EList result = (EList) dtParser.getSym(1);
				result.add(dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 468:  transfParamList ::= transfParamList qvtErrorToken
			//
			case 468: {
				
				EList result = (EList) dtParser.getSym(1);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 469:  transfParamCS ::= param_directionOpt pathNameCS
			//
			case 469: {
				
				DirectionKindCS directionKind = (DirectionKindCS) dtParser.getSym(1);
				TypeSpecCS typeSpecCS = createTypeSpecCS((PathNameCS)dtParser.getSym(2), null);
				CSTNode result = createParameterDeclarationCS(
						directionKind,
						null,
						typeSpecCS
					);
				setOffsets(result, directionKind == null ? (CSTNode)dtParser.getSym(2) : directionKind, (CSTNode)dtParser.getSym(2));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 470:  transfParamCS ::= param_directionOpt IDENTIFIER : pathNameCS
			//
			case 470: {
				
				DirectionKindCS directionKind = (DirectionKindCS) dtParser.getSym(1);
				TypeSpecCS typeSpecCS = createTypeSpecCS((PathNameCS)dtParser.getSym(4), null);
				CSTNode result = createParameterDeclarationCS(
						directionKind,
						getIToken(dtParser.getToken(2)),
						typeSpecCS
					);
				if (directionKind == null) {
					setOffsets(result, getIToken(dtParser.getToken(2)), (CSTNode)dtParser.getSym(4));
				}
				else {
					setOffsets(result, directionKind, (CSTNode)dtParser.getSym(4));
				}
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 471:  module_elementList ::= module_elementList module_element
			//
			case 471: {
				
				EList list = (EList)dtParser.getSym(1);
				list.add(dtParser.getSym(2));
				dtParser.setSym1(list);
	  		  break;
			}
	 
			//
			// Rule 472:  module_elementList ::= $Empty
			//
			case 472:
				dtParser.setSym1(new BasicEList());
				break;
 
			//
			// Rule 479:  _modeltype ::= metamodel stringLiteralExpCS ;
			//
			case 479: {
				
				CSTNode packageRefCS = createPackageRefCS(
						null,
						(StringLiteralExpCS)dtParser.getSym(2)
					);
				setOffsets(packageRefCS, (CSTNode)dtParser.getSym(2));
				
				EList packageRefList = new BasicEList();
				packageRefList.add(packageRefCS);
				ModelTypeCS result = createModelTypeCS(
						new Token(0, 0, 0),
						createStringLiteralExpCS("'strict'"),
						packageRefList,
						ourEmptyEList
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(3)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 480:  _modeltype ::= metamodel qvtErrorToken
			//
			case 480: {
				
				ModelTypeCS result = createModelTypeCS(
						new Token(0, 0, 0),
						createStringLiteralExpCS(""),
						ourEmptyEList,
						ourEmptyEList
					);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 481:  complianceKindCSOpt ::= $Empty
			//
			case 481: {
				
				CSTNode result = createStringLiteralExpCS("''");
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 484:  qvtStringLiteralExpCS ::= QUOTE_STRING_LITERAL
			//
			case 484: {
				
				CSTNode result = createStringLiteralExpCS("'" + unquote(getTokenText(dtParser.getToken(1))) + "'"); //$NON-NLS-1$ //$NON-NLS-2$
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 485:  modelTypeExpCS ::= modeltype IDENTIFIER complianceKindCSOpt uses packageRefList modelTypeWhereCSOpt ;
			//
			case 485: {
				
				EList whereList = (EList)dtParser.getSym(6);
				EList packageRefList = (EList)dtParser.getSym(5);
				ModelTypeCS result = createModelTypeCS(
						getIToken(dtParser.getToken(2)),
						(StringLiteralExpCS)dtParser.getSym(3),
						packageRefList,
						whereList
					);
				if (whereList.isEmpty()) {
					setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(7)));
				}
				else {
					CSTNode lastPackageRefCS = (CSTNode)packageRefList.get(packageRefList.size()-1);
					setOffsets(result, getIToken(dtParser.getToken(1)), lastPackageRefCS);
					setBodyOffsets(result, lastPackageRefCS, getIToken(dtParser.getToken(7)));
				}
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 486:  modelTypeExpCS ::= modeltype qvtErrorToken
			//
			case 486: {
				
				ModelTypeCS result = createModelTypeCS(
						new Token(0, 0, 0),
						createStringLiteralExpCS(""),
						ourEmptyEList,
						ourEmptyEList
					);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 487:  packageRefList ::= packageRefCS
			//
			case 487: {
				
				EList result = new BasicEList();
				result.add(dtParser.getSym(1));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 488:  packageRefList ::= packageRefList , packageRefCS
			//
			case 488: {
				
				EList result = (EList)dtParser.getSym(1);
				result.add(dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 489:  packageRefCS ::= pathNameCS
			//
			case 489: {
				
				CSTNode result = createPackageRefCS(
						(PathNameCS)dtParser.getSym(1),
						null
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 490:  packageRefCS ::= pathNameCS ( qvtStringLiteralExpCS )
			//
			case 490: {
				
				CSTNode result = createPackageRefCS(
						(PathNameCS)dtParser.getSym(1),
						(StringLiteralExpCS)dtParser.getSym(3)
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1), getIToken(dtParser.getToken(4)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 491:  packageRefCS ::= qvtStringLiteralExpCS
			//
			case 491: {
				
				CSTNode result = createPackageRefCS(
						null,
						(StringLiteralExpCS)dtParser.getSym(1)
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 492:  modelTypeWhereCSOpt ::= $Empty
			//
			case 492:
				dtParser.setSym1(new BasicEList());
				break;
 
			//
			// Rule 493:  modelTypeWhereCSOpt ::= where { statementListOpt }
			//
			case 493: {
				
				EList result = (EList)dtParser.getSym(3);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 495:  classifierDefCS ::= intermediate class qvtIdentifierCS classifierExtensionOpt { classifierFeatureListOpt } semicolonOpt
			//
			case 495: {
				
				SimpleNameCS classifierName = createSimpleNameCS(SimpleTypeEnum.IDENTIFIER_LITERAL, getTokenText(dtParser.getToken(3)));
				setOffsets(classifierName, getIToken(dtParser.getToken(3)));
				CSTNode result = createClassifierDefCS(
						classifierName,
						(EList) dtParser.getSym(4),
						(EList) dtParser.getSym(6)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(7)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 496:  classifierExtensionOpt ::= $Empty
			//
			case 496:
				dtParser.setSym1(new BasicEList());
				break;
 
			//
			// Rule 497:  classifierExtensionOpt ::= extends type_list
			//
			case 497: {
				
				EList result = (EList)dtParser.getSym(2);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 498:  type_list ::= typeCS
			//
			case 498: {
				
				EList result = new BasicEList();
				result.add(dtParser.getSym(1));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 499:  type_list ::= type_list , typeCS
			//
			case 499: {
				
				EList result = (EList)dtParser.getSym(1);
				result.add(dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 500:  classifierFeatureListOpt ::= $Empty
			//
			case 500:
				dtParser.setSym1(new BasicEList());
				break;
 
			//
			// Rule 502:  classifierFeatureList ::= classifierFeatureCS semicolonOpt
			//
			case 502: {
				
				EList result = new BasicEList();
				result.add(dtParser.getSym(1));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 503:  classifierFeatureList ::= classifierFeatureList ; classifierFeatureCS semicolonOpt
			//
			case 503: {
				
				EList result = (EList)dtParser.getSym(1);
				result.add(dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 504:  classifierFeatureList ::= classifierFeatureList qvtErrorToken
			//
			case 504: {
				
				EList result = (EList)dtParser.getSym(1);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 505:  classifierFeatureCS ::= qvtIdentifierCS : typeCS
			//
			case 505: {
				
				CSTNode result = createLocalPropertyCS(
						getIToken(dtParser.getToken(1)),
						(TypeCS) dtParser.getSym(3),
						null
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode) dtParser.getSym(3));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 506:  classifierFeatureCS ::= qvtIdentifierCS : typeCS = oclExpressionCS
			//
			case 506: {
				
				CSTNode result = createLocalPropertyCS(
						getIToken(dtParser.getToken(1)),
						(TypeCS) dtParser.getSym(3),
						(OCLExpressionCS) dtParser.getSym(5)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), (CSTNode) dtParser.getSym(5));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 508:  modulePropertyCS ::= configuration property qvtIdentifierCS : typeCS ;
			//
			case 508: {
				
				CSTNode result = createConfigPropertyCS(
						getIToken(dtParser.getToken(3)),
						(TypeCS)dtParser.getSym(5)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(6)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 509:  modulePropertyCS ::= configuration property qvtIdentifierCS : typeCS qvtErrorToken
			//
			case 509: {
				
				CSTNode result = createConfigPropertyCS(
						getIToken(dtParser.getToken(3)),
						(TypeCS)dtParser.getSym(5)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(5)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 510:  modulePropertyCS ::= property qvtIdentifierCS : typeCS = oclExpressionCS ;
			//
			case 510: {
				
				CSTNode result = createLocalPropertyCS(
						getIToken(dtParser.getToken(2)),
						(TypeCS)dtParser.getSym(4),
						(OCLExpressionCS)dtParser.getSym(6)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(7)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 511:  modulePropertyCS ::= property qvtIdentifierCS = oclExpressionCS ;
			//
			case 511: {
				
				CSTNode result = createLocalPropertyCS(
						getIToken(dtParser.getToken(2)),
						null,
						(OCLExpressionCS)dtParser.getSym(4)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(5)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 512:  modulePropertyCS ::= intermediate property scoped_identifier : typeCS ;
			//
			case 512: {
				
				CSTNode result = createContextualPropertyCS(
						(ScopedNameCS)dtParser.getSym(3),
						(TypeCS)dtParser.getSym(5),
						null
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(6)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 513:  modulePropertyCS ::= intermediate property scoped_identifier : typeCS = oclExpressionCS ;
			//
			case 513: {
				
				CSTNode result = createContextualPropertyCS(
						(ScopedNameCS)dtParser.getSym(3),
						(TypeCS)dtParser.getSym(5),
						(OCLExpressionCS)dtParser.getSym(7)
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(8)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 517:  helper_header ::= helper_info scoped_identifier complete_signature
			//
			case 517: {
				
				CompleteSignatureCS completeSignature = (CompleteSignatureCS)dtParser.getSym(3);
				Object[] helperInfo = (Object[])dtParser.getSym(1);
				MappingDeclarationCS mappingDeclarationCS = createMappingDeclarationCS(
					null,
					(ScopedNameCS)dtParser.getSym(2),
					completeSignature.getSimpleSignature().getParams(),
					completeSignature.getResultParams()
				);
				setOffsets(mappingDeclarationCS, (CSTNode)dtParser.getSym(2), (CSTNode)dtParser.getSym(3));

				EList<SimpleNameCS> qualifiers = (EList<SimpleNameCS>) helperInfo[0];
				if(!qualifiers.isEmpty()) {
					mappingDeclarationCS.getQualifiers().addAll(createQualifiersListCS(qualifiers));
				}

				IToken helperKind = (IToken) helperInfo[1];
				mappingDeclarationCS.setIsQuery(helperKind.getKind() == QvtOpLPGParsersym.TK_query);

				dtParser.setSym1(mappingDeclarationCS);
	  		  break;
			}
	 
			//
			// Rule 518:  helper_header ::= helper_info qvtErrorToken
			//
			case 518: {
				
				Object[] helperInfo = (Object[])dtParser.getSym(1);
				MappingDeclarationCS mappingDeclarationCS = createMappingDeclarationCS(
					null,
					createScopedNameCS(null, ""), //$NON-NLS-1$
					ourEmptyEList,
					ourEmptyEList
				);
				setOffsets(mappingDeclarationCS, (IToken) helperInfo[1]);

				EList<SimpleNameCS> qualifiers = (EList<SimpleNameCS>) helperInfo[0];
				if(!qualifiers.isEmpty()) {
					mappingDeclarationCS.getQualifiers().addAll(createQualifiersListCS(qualifiers));
				}

				IToken helperKind = (IToken) helperInfo[1];
				mappingDeclarationCS.setIsQuery(helperKind.getKind() == QvtOpLPGParsersym.TK_query);

				dtParser.setSym1(mappingDeclarationCS);
	  		  break;
			}
	 
			//
			// Rule 519:  helper_info ::= qualifierList helper_kind
			//
			case 519: {
				
				dtParser.setSym1(new Object[] {dtParser.getSym(1), getIToken(dtParser.getToken(2))});
	  		  break;
			}
	 
			//
			// Rule 522:  helper_decl ::= helper_header ;
			//
			case 522: {
				
				MappingDeclarationCS mappingDecl = (MappingDeclarationCS)dtParser.getSym(1);
				MappingQueryCS result = createMappingQueryCS(
						mappingDecl,
						ourEmptyEList
					);
				result.setBlackBox(true);
				setOffsets(result, mappingDecl, getIToken(dtParser.getToken(2)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 523:  helper_decl ::= helper_header qvtErrorToken
			//
			case 523: {
				
				MappingDeclarationCS mappingDecl = (MappingDeclarationCS)dtParser.getSym(1);
				MappingQueryCS result = createMappingQueryCS(
						mappingDecl,
						ourEmptyEList
					);
				result.setBlackBox(true);
				setOffsets(result, mappingDecl);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 524:  helper_simple_def ::= helper_header = statementCS ;
			//
			case 524: {
				
				MappingDeclarationCS mappingDecl = (MappingDeclarationCS)dtParser.getSym(1);
				OCLExpressionCS expression = (OCLExpressionCS)dtParser.getSym(3);
				EList<OCLExpressionCS> expressionList = new BasicEList();
				expressionList.add(expression);
				MappingQueryCS result = createMappingQueryCS(
						mappingDecl,
						expressionList
					);
				result.setIsSimpleDefinition(true);
				setOffsets(result, mappingDecl, getIToken(dtParser.getToken(4)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 525:  helper_compound_def ::= helper_header expression_block semicolonOpt
			//
			case 525: {
				
				MappingDeclarationCS mappingDecl = (MappingDeclarationCS)dtParser.getSym(1);
				BlockExpCS blockExpCS = (BlockExpCS)dtParser.getSym(2);
				CSTNode result = createMappingQueryCS(
						mappingDecl,
						blockExpCS.getBodyExpressions()
					);
				setOffsets(result, mappingDecl, blockExpCS);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 528:  mapping_decl ::= mapping_full_header ;
			//
			case 528: {
				
	                        Object[] mappingFullHeader = (Object[])dtParser.getSym(1);
				MappingRuleCS result = createMappingRuleCS(
						(MappingDeclarationCS)mappingFullHeader[0],
						(OCLExpressionCS)mappingFullHeader[1],
						null
					);
				result.setBlackBox(true);
				setOffsets(result, (MappingDeclarationCS)mappingFullHeader[0], getIToken(dtParser.getToken(2)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 529:  mapping_def ::= mapping_full_header { mapping_body } semicolonOpt
			//
			case 529: {
				
				MappingSectionsCS mappingSections = (MappingSectionsCS)dtParser.getSym(3);
				setOffsets(mappingSections, getIToken(dtParser.getToken(2)), getIToken(dtParser.getToken(4)));

				MappingBodyCS mappingBodyCS = mappingSections.getMappingBodyCS();
				if (mappingBodyCS != null) {
					if (mappingBodyCS.getStartOffset() < 0) {
						mappingBodyCS.setStartOffset(mappingSections.getStartOffset());
					}
					if (mappingBodyCS.getEndOffset() < 0) {
						mappingBodyCS.setEndOffset(mappingSections.getEndOffset());
					}
				}

	                        Object[] mappingFullHeader = (Object[])dtParser.getSym(1);
				MappingRuleCS result = createMappingRuleCS(
						(MappingDeclarationCS)mappingFullHeader[0],
						(OCLExpressionCS)mappingFullHeader[1],
						mappingSections
					);
				result.setBlackBox(false);
				setOffsets(result, (MappingDeclarationCS)mappingFullHeader[0], getIToken(dtParser.getToken(4)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 530:  mapping_def ::= mapping_full_header { qvtErrorToken
			//
			case 530: {
				
	                        Object[] mappingFullHeader = (Object[])dtParser.getSym(1);
				MappingRuleCS result = createMappingRuleCS(
						(MappingDeclarationCS)mappingFullHeader[0],
						null,
						null
					);
				setOffsets(result, (MappingDeclarationCS)mappingFullHeader[0], getIToken(dtParser.getToken(2)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 531:  mapping_full_header ::= mapping_header _whenOpt
			//
			case 531: {
				
				dtParser.setSym1(new Object[] {dtParser.getSym(1), dtParser.getSym(2)});
	  		  break;
			}
	 
			//
			// Rule 532:  mapping_header ::= qualifierList mapping param_directionOpt scoped_identifier complete_signature mapping_extraList
			//
			case 532: {
				
				DirectionKindCS directionKind = (DirectionKindCS)dtParser.getSym(3);
				CompleteSignatureCS completeSignature = (CompleteSignatureCS)dtParser.getSym(5);
				MappingDeclarationCS mappingDeclarationCS = createMappingDeclarationCS(
					directionKind,
					(ScopedNameCS)dtParser.getSym(4),
					completeSignature.getSimpleSignature().getParams(),
					completeSignature.getResultParams()
				);
				mappingDeclarationCS.setStartOffset((directionKind == null ? (CSTNode)dtParser.getSym(4) : directionKind).getStartOffset());

				mappingDeclarationCS.setEndOffset(completeSignature.getEndOffset());

				EList<SimpleNameCS> qualifiers = (EList<SimpleNameCS>)dtParser.getSym(1);
				if(!qualifiers.isEmpty()) {
					mappingDeclarationCS.getQualifiers().addAll(createQualifiersListCS(qualifiers));
				}

				mappingDeclarationCS.getMappingExtension().addAll(((EList<MappingExtensionCS>)dtParser.getSym(6)));

				dtParser.setSym1(mappingDeclarationCS);
	  		  break;
			}
	 
			//
			// Rule 533:  mapping_header ::= qualifierList mapping param_directionOpt scoped_identifier qvtErrorToken
			//
			case 533: {
				
				DirectionKindCS directionKind = (DirectionKindCS)dtParser.getSym(3);
				MappingDeclarationCS mappingDeclarationCS = createMappingDeclarationCS(
					directionKind,
					(ScopedNameCS)dtParser.getSym(4),
					ourEmptyEList,
					ourEmptyEList
				);
				mappingDeclarationCS.setStartOffset((directionKind == null ? (CSTNode)dtParser.getSym(4) : directionKind).getStartOffset());

				mappingDeclarationCS.setEndOffset(((CSTNode)dtParser.getSym(4)).getEndOffset());

				EList<SimpleNameCS> qualifiers = (EList<SimpleNameCS>)dtParser.getSym(1);
				if(!qualifiers.isEmpty()) {
					mappingDeclarationCS.getQualifiers().addAll(createQualifiersListCS(qualifiers));
				}

				dtParser.setSym1(mappingDeclarationCS);
	  		  break;
			}
	 
			//
			// Rule 534:  mapping_header ::= qualifierList mapping qvtErrorToken
			//
			case 534: {
				
				MappingDeclarationCS mappingDeclarationCS = createMappingDeclarationCS(
					null,
					createScopedNameCS(null, ""), //$NON-NLS-1$
					ourEmptyEList,
					ourEmptyEList
				);
				setOffsets(mappingDeclarationCS, getIToken(dtParser.getToken(2)), getIToken(dtParser.getToken(2)));

				EList<SimpleNameCS> qualifiers = (EList<SimpleNameCS>)dtParser.getSym(1);
				if(!qualifiers.isEmpty()) {
					mappingDeclarationCS.getQualifiers().addAll(createQualifiersListCS(qualifiers));
				}

				dtParser.setSym1(mappingDeclarationCS);
	  		  break;
			}
	 
			//
			// Rule 535:  mapping_extraList ::= mapping_extraList mapping_extra
			//
			case 535: {
				
				EList<MappingExtensionCS> extensionList = (EList<MappingExtensionCS>)dtParser.getSym(1);
				extensionList.add((MappingExtensionCS)dtParser.getSym(2));
				dtParser.setSym1(extensionList);
	  		  break;
			}
	 
			//
			// Rule 536:  mapping_extraList ::= $Empty
			//
			case 536:
				dtParser.setSym1(new BasicEList());
				break;
 
			//
			// Rule 538:  mapping_extension ::= mapping_extension_key scoped_identifier_list
			//
			case 538: {
				
				MappingExtensionCS result = createMappingExtension(getTokenText(dtParser.getToken(1)), (EList<ScopedNameCS>)dtParser.getSym(2));

				result.setStartOffset(getIToken(dtParser.getToken(1)).getStartOffset());
				result.setEndOffset(getEndOffset(getIToken(dtParser.getToken(1)), (EList<ScopedNameCS>)dtParser.getSym(2)));
			
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 543:  _whenOpt ::= $Empty
			//
			case 543:
				dtParser.setSym1(null);
				break;
 
			//
			// Rule 544:  _when ::= when { oclExpressionCS }
			//
			case 544: {
				
				OCLExpressionCS result = (OCLExpressionCS)dtParser.getSym(3);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 545:  _when ::= when qvtErrorToken
			//
			case 545:
				dtParser.setSym1(null);
				break;
 
			//
			// Rule 546:  mapping_body ::= init_sectionOpt population_sectionOpt end_sectionOpt
			//
			case 546: {
				
	                        MappingInitCS mappingInitCS = (MappingInitCS)dtParser.getSym(1);
				MappingBodyCS mappingBodyCS = (MappingBodyCS)dtParser.getSym(2);
				MappingEndCS mappingEndCS = (MappingEndCS)dtParser.getSym(3);

				if (mappingBodyCS != null) {
					if ((mappingBodyCS.getStartOffset() < 0) && (mappingInitCS != null)) {
						mappingBodyCS.setStartOffset(mappingInitCS.getEndOffset() + 1);
					}
					if ((mappingBodyCS.getEndOffset() < 0) && (mappingEndCS != null)) {
						mappingBodyCS.setEndOffset(mappingEndCS.getStartOffset() - 1);
					}
					if (mappingBodyCS.getStartOffset() > mappingBodyCS.getEndOffset()) {
						mappingBodyCS.setEndOffset(mappingBodyCS.getStartOffset());
					}
				}
				
				CSTNode result = createMappingSectionsCS(
						mappingInitCS,
						mappingBodyCS,
						mappingEndCS
					);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 547:  init_sectionOpt ::= $Empty
			//
			case 547:
				dtParser.setSym1(null);
				break;
 
			//
			// Rule 549:  init_section ::= init expression_block
			//
			case 549: {
				
				BlockExpCS blockExpCS = (BlockExpCS) dtParser.getSym(2);
				CSTNode result = createMappingInitCS(
						blockExpCS.getBodyExpressions(),
						blockExpCS.getStartOffset(),
						blockExpCS.getEndOffset()
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), blockExpCS);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 550:  init_section ::= init qvtErrorToken
			//
			case 550: {
				
				CSTNode result = createMappingInitCS(
						ourEmptyEList,
						getIToken(dtParser.getToken(1)).getEndOffset(),
						getIToken(dtParser.getToken(1)).getStartOffset()
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 551:  population_sectionOpt ::= $Empty
			//
			case 551: {
				
				MappingBodyCS result = createMappingBodyCS(
						ourEmptyEList,
						false
					);
				// offsets will be updated further in parent non-terminals
				result.setStartOffset(-1); 
				result.setEndOffset(-1);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 553:  population_section ::= expression_list
			//
			case 553: {
				
				EList<OCLExpressionCS> expressionList = (EList<OCLExpressionCS>) dtParser.getSym(1);
				MappingBodyCS result = createMappingBodyCS(
						expressionList,
						false
					);
				CSTNode startExp = expressionList.get(0);
				CSTNode endExp = expressionList.get(expressionList.size() - 1);
				setOffsets(result, startExp, endExp);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 554:  population_section ::= population expression_block
			//
			case 554: {
				
				BlockExpCS blockExpCS = (BlockExpCS) dtParser.getSym(2);
				MappingBodyCS result = createMappingBodyCS(
						blockExpCS.getBodyExpressions(),
						true
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), blockExpCS);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 555:  population_section ::= population qvtErrorToken
			//
			case 555: {
				
				CSTNode result = createMappingBodyCS(
						ourEmptyEList,
						true
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 556:  end_sectionOpt ::= $Empty
			//
			case 556:
				dtParser.setSym1(null);
				break;
 
			//
			// Rule 558:  end_section ::= end expression_block
			//
			case 558: {
				
				BlockExpCS blockExpCS = (BlockExpCS) dtParser.getSym(2);
				CSTNode result = createMappingEndCS(
						blockExpCS.getBodyExpressions(),
						blockExpCS.getStartOffset(),
						blockExpCS.getEndOffset()
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), blockExpCS);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 559:  end_section ::= end qvtErrorToken
			//
			case 559: {
				
				CSTNode result = createMappingEndCS(
						ourEmptyEList,
						getIToken(dtParser.getToken(1)).getEndOffset(),
						getIToken(dtParser.getToken(1)).getStartOffset()
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 560:  entry ::= entryDeclarationCS { statementListOpt }
			//
			case 560: {
				
				MappingQueryCS result = createMappingQueryCS(
						(MappingDeclarationCS)dtParser.getSym(1),
						(EList)dtParser.getSym(3)
					);
				setOffsets(result, (CSTNode)dtParser.getSym(1), getIToken(dtParser.getToken(4)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 561:  entryDeclarationCS ::= main ( param_listOpt )
			//
			case 561: {
				
				IToken nameToken = getIToken(dtParser.getToken(1));				
				ScopedNameCS nameCS = createScopedNameCS(null, getTokenText(dtParser.getToken(1)));								
				nameCS.setStartOffset(nameToken.getStartOffset());
				nameCS.setEndOffset(nameToken.getEndOffset());
	
				CSTNode result = createMappingDeclarationCS(
						null,
						nameCS,
						(EList)dtParser.getSym(3),
						null
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(4)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 562:  entryDeclarationCS ::= main qvtErrorToken
			//
			case 562: {
				
				CSTNode result = createMappingDeclarationCS(
						null,
						createScopedNameCS(null, getTokenText(dtParser.getToken(1))),
						ourEmptyEList,
						null
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 563:  typespecOpt ::= $Empty
			//
			case 563:
				dtParser.setSym1(null);
				break;
 
			//
			// Rule 565:  objectDeclCS ::= typespec
			//
			case 565: {
				
				CSTNode result = createOutExpCS(null, (TypeSpecCS)dtParser.getSym(1));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 569:  objectDeclCS ::= objectIdentifierCS : typespecOpt
			//
			case 569: {
				
			SimpleNameCS varName = createSimpleNameCS(SimpleTypeEnum.IDENTIFIER_LITERAL, getTokenText(dtParser.getToken(1)));
			setOffsets(varName, getIToken(dtParser.getToken(1)));
			CSTNode result = createOutExpCS(varName,(TypeSpecCS)dtParser.getSym(3));					
			dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 570:  outExpCS ::= object objectDeclCS expression_block
			//
			case 570: {
				
				BlockExpCS blockExpCS = (BlockExpCS) dtParser.getSym(3);
				CSTNode result = setupOutExpCS(
						(OutExpCS)dtParser.getSym(2),					
						blockExpCS.getBodyExpressions(),
						// passing body positions
						blockExpCS.getStartOffset(),
						blockExpCS.getEndOffset()
					); 
				setOffsets(result, getIToken(dtParser.getToken(1)), blockExpCS);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 571:  outExpCS ::= object objectDeclCS qvtErrorToken
			//
			case 571: {
				
				OutExpCS objectDeclCS = ((OutExpCS)dtParser.getSym(2));  
				CSTNode result = createOutExpCS(
						objectDeclCS.getSimpleNameCS(),						
						objectDeclCS.getTypeSpecCS()
					);
				if (objectDeclCS  == null) {
				    setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(1)));
				} else {
				    setOffsets(result, getIToken(dtParser.getToken(1)), objectDeclCS);
				}
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 572:  featureMappingCallExpCS ::= map simpleNameCS ( argumentsCSopt )
			//
			case 572: {
				
				CSTNode result = createMappingCallExpCS(
						(SimpleNameCS)dtParser.getSym(2),
						(EList)dtParser.getSym(4),
						false
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(5)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 573:  featureMappingCallExpCS ::= xmap simpleNameCS ( argumentsCSopt )
			//
			case 573: {
				
				CSTNode result = createMappingCallExpCS(
						(SimpleNameCS)dtParser.getSym(2),
						(EList)dtParser.getSym(4),
						true
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(5)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 574:  mappingCallExpCS ::= map pathNameCS ( argumentsCSopt )
			//
			case 574: {
				
				CSTNode result = createMappingCallExpCS(
						(PathNameCS)dtParser.getSym(2),
						(EList)dtParser.getSym(4),
						false
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(5)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 575:  mappingCallExpCS ::= xmap pathNameCS ( argumentsCSopt )
			//
			case 575: {
				
				CSTNode result = createMappingCallExpCS(
						(PathNameCS)dtParser.getSym(2),
						(EList)dtParser.getSym(4),
						true
					);
				setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(5)));
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 576:  resolveConditionOpt ::= $Empty
			//
			case 576:
				dtParser.setSym1(null);
				break;
 
			//
			// Rule 577:  resolveConditionOpt ::= | oclExpressionCS
			//
			case 577: {
				
                dtParser.setSym1((OCLExpressionCS)dtParser.getSym(2));
      		  break;
			}
     
			//
			// Rule 578:  resolveConditionOpt ::= | qvtErrorToken
			//
			case 578:
				dtParser.setSym1(null);
				break;
 
			//
			// Rule 579:  IDENTIFIEROpt ::= $Empty
			//
			case 579:
				dtParser.setSym1(null);
				break;
 
			//
			// Rule 580:  IDENTIFIEROpt ::= IDENTIFIER :
			//
			case 580: {
				
                dtParser.setSym1(getIToken(dtParser.getToken(1)));
      		  break;
			}
     
			//
			// Rule 581:  resolveOpArgsExpCSOpt ::= $Empty
			//
			case 581:
				dtParser.setSym1(null);
				break;
 
			//
			// Rule 583:  resolveOpArgsExpCS ::= IDENTIFIEROpt typeCS resolveConditionOpt
			//
			case 583: {
				
                CSTNode result = createResolveOpArgsExpCS(
                        getIToken(dtParser.getToken(1)),      // target_type_variable?
                        (TypeCS)dtParser.getSym(2),           // type?
                        (OCLExpressionCS)dtParser.getSym(3)); // condition?
                        setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(3)));
                dtParser.setSym1(result);
      		  break;
			}
     
			//
			// Rule 588:  lateOpt ::= $Empty
			//
			case 588:
				dtParser.setSym1(null);
				break;
 
			//
			// Rule 590:  resolveExpCS ::= lateOpt resolveOp ( resolveOpArgsExpCSOpt )
			//
			case 590: {
				
                CSTNode result = createResolveExpCS(
                        getIToken(dtParser.getToken(1)),
                        getIToken(dtParser.getToken(2)),
                        (ResolveOpArgsExpCS)dtParser.getSym(4));
                        setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(5)));
                dtParser.setSym1(result);
      		  break;
			}
     
			//
			// Rule 591:  resolveExpCS ::= lateOpt resolveOp ( resolveOpArgsExpCSOpt qvtErrorToken
			//
			case 591: {
				
                CSTNode result = createResolveExpCS(
                        getIToken(dtParser.getToken(1)),
                        getIToken(dtParser.getToken(2)),
                        (ResolveOpArgsExpCS)dtParser.getSym(4));
                        setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(4)));
                dtParser.setSym1(result);
      		  break;
			}
     
			//
			// Rule 592:  resolveExpCS ::= lateOpt resolveOp qvtErrorToken
			//
			case 592: {
				
                CSTNode result = createResolveExpCS(
                        getIToken(dtParser.getToken(1)),
                        getIToken(dtParser.getToken(2)),
                        null);
                        setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(2)));
                dtParser.setSym1(result);
      		  break;
			}
     
			//
			// Rule 593:  resolveExpCS ::= late qvtErrorToken
			//
			case 593: {
				
    			IToken lateToken = getIToken(dtParser.getToken(1));
                CSTNode result = createResolveExpCS(
                        lateToken,
                        new Token(lateToken.getEndOffset(), lateToken.getEndOffset(), QvtOpLPGParsersym.TK_resolve),
                        null);
                        setOffsets(result, getIToken(dtParser.getToken(1)));
                dtParser.setSym1(result);
      		  break;
			}
     
			//
			// Rule 598:  resolveInExpCS ::= lateOpt resolveInOp ( scoped_identifier , resolveOpArgsExpCS )
			//
			case 598: {
				
                CSTNode result = createResolveInExpCS(
                        getIToken(dtParser.getToken(1)),
                        getIToken(dtParser.getToken(2)),
                        (ScopedNameCS)dtParser.getSym(4),
                        (ResolveOpArgsExpCS)dtParser.getSym(6));
                        setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(7)));
                dtParser.setSym1(result);
      		  break;
			}
     
			//
			// Rule 599:  resolveInExpCS ::= lateOpt resolveInOp ( scoped_identifier )
			//
			case 599: {
				
                CSTNode result = createResolveInExpCS(
                        getIToken(dtParser.getToken(1)),
                        getIToken(dtParser.getToken(2)),
                        (ScopedNameCS)dtParser.getSym(4),
                        null);
                        setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(5)));
                dtParser.setSym1(result);
      		  break;
			}
     
			//
			// Rule 600:  resolveInExpCS ::= lateOpt resolveInOp ( scoped_identifier , resolveOpArgsExpCSOpt qvtErrorToken
			//
			case 600: {
				
                CSTNode result = createResolveInExpCS(
                        getIToken(dtParser.getToken(1)),
                        getIToken(dtParser.getToken(2)),
                        (ScopedNameCS)dtParser.getSym(4),
                        (ResolveOpArgsExpCS)dtParser.getSym(6));
                        setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(6)));
                dtParser.setSym1(result);
      		  break;
			}
     
			//
			// Rule 601:  resolveInExpCS ::= lateOpt resolveInOp ( scoped_identifier qvtErrorToken
			//
			case 601: {
				
                CSTNode result = createResolveInExpCS(
                        getIToken(dtParser.getToken(1)),
                        getIToken(dtParser.getToken(2)),
                        (ScopedNameCS)dtParser.getSym(4),
                        null);
                        setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(4)));
                dtParser.setSym1(result);
      		  break;
			}
     
			//
			// Rule 602:  resolveInExpCS ::= lateOpt resolveInOp ( qvtErrorToken
			//
			case 602: {
				
                CSTNode result = createResolveInExpCS(
                        getIToken(dtParser.getToken(1)),
                        getIToken(dtParser.getToken(2)),
						createScopedNameCS(null, ""), //$NON-NLS-1$
                        null);
                        setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(3)));
                dtParser.setSym1(result);
      		  break;
			}
     
			//
			// Rule 603:  resolveInExpCS ::= lateOpt resolveInOp qvtErrorToken
			//
			case 603: {
				
                CSTNode result = createResolveInExpCS(
                        getIToken(dtParser.getToken(1)),
                        getIToken(dtParser.getToken(2)),
						createScopedNameCS(null, ""), //$NON-NLS-1$
                        null);
                        setOffsets(result, getIToken(dtParser.getToken(1)), getIToken(dtParser.getToken(2)));
                dtParser.setSym1(result);
      		  break;
			}
     
			//
			// Rule 606:  callExpCS ::= . resolveResolveInExpCS
			//
			case 606: {
				
				CallExpCS result = (CallExpCS)dtParser.getSym(2);
				result.setAccessor(DotOrArrowEnum.DOT_LITERAL);
				dtParser.setSym1(result);
	  		  break;
			}
	 
			//
			// Rule 611:  simpleNameCS ::= this
			//
			case 611:
 
			//
			// Rule 612:  simpleNameCS ::= result
			//
			case 612: {
				
				CSTNode result = createSimpleNameCS(
						SimpleTypeEnum.IDENTIFIER_LITERAL,
						getTokenText(dtParser.getToken(1))
					);
				setOffsets(result, getIToken(dtParser.getToken(1)));
				dtParser.setSym1(result);
	  		  break;
			}
	
	
			default:
				break;
		}
		return;
	}
}

