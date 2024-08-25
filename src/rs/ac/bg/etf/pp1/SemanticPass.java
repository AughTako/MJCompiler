package rs.ac.bg.etf.pp1;


import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticPass extends VisitorAdaptor {
	int printCallCount = 0;
	int varDeclCount = 0;
	int arrayDeclCount = 0;
	int constDeclCount = 0;
	Struct currentTypeForVarOrConstDecl = null;
	Obj currentMethod = null;
	Obj currentNamespace = null;
	Type currentConstType = null;
	Type currentVarType = null;
	int nVars;
	
	boolean returnExpected = false;
	boolean returnFound = false;
	boolean errorDetected = false;
	boolean matrixDetected = false;
	
	Logger log = Logger.getLogger(getClass());
	
    public boolean passed(){
    	return !errorDetected;
    }
	
	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}
	
	private boolean checkType(ConstVals constVal, Type t) {
		if(constVal instanceof ConstNum && t.getTypeName().equals("int")) 
			return true;
		else if (constVal instanceof ConstChar && t.getTypeName().equals("char"))
			return true;
		else if (constVal instanceof ConstBool && t.getTypeName().equals("bool"))
			return true;
		return false;
	}
	/*
	 * Rules for program
	 * */
	
    public void visit(Program program) { 
    	nVars = Tab.currentScope.getnVars();
    	Tab.chainLocalSymbols(program.getProgName().obj);
    	Tab.closeScope();
    }
    
    public void visit(ProgName progName) {
    	progName.obj = TabExtended.insert(Obj.Prog, progName.getProgName(), Tab.noType);
    	Tab.openScope();
    }
    
    /*
     * Rules for declarations of consts, vars, arrays and matrixes
     */
//    public void visit(ConstDeclarations constDeclarations) {
//    	constDeclCount++;
//    }
    public void visit(ConstType constType) {
    	this.currentConstType = constType.getType();
    }
    
    public void visit(FormalConstDeclr formalConst) {
    	if(TabExtended.find(formalConst.getConstName()) != Tab.noObj) {
    		report_error("Semanticka greska na liniji" + formalConst.getLine() + " konstanta vec deklarisana!", null);
    	}
    	else {
    		if(!checkType(formalConst.getConstVals(), currentConstType)) {
    			report_error("Semanticka greska na liniji" + formalConst.getLine() + " neslaganje tipova!", null);
    		}
    		else {
    			constDeclCount++;
    			Obj insertedFormalConst = TabExtended.insert(Obj.Con, formalConst.getConstName(), currentConstType.struct);
    			formalConst.obj = insertedFormalConst;
    			report_info("\tCONST DECLARED: " + formalConst.getConstName() + " LINE: " + formalConst.getLine() + " TYPE: " + currentConstType.struct.getKind(), null);
    			
    		}
    	}
    }
    
    public void visit(MoreConstValsExist moreConsts) {
    	if(TabExtended.find(moreConsts.getConstName()) != Tab.noObj) {
    		report_error("Semanticka greska na liniji" + moreConsts.getLine() + " konstanta vec deklarisana!", null);
    	}
    	else {
    		constDeclCount++;
    		if(!checkType(moreConsts.getConstVals(), currentConstType)) {
    			report_error("Semanticka greska na liniji" + moreConsts.getLine() + " neslaganje tipova!", null);
    		}
    		else {
    			Obj insertedMoreConst = TabExtended.insert(Obj.Con, moreConsts.getConstName(), currentConstType.struct);
    			moreConsts.obj = insertedMoreConst;
    			report_info("\tCONST DECLARED:  " + moreConsts.getConstName() + " LINE: " + moreConsts.getLine() + " TYPE: " + currentConstType.struct.getKind(), null);
    			
    		}
    	}
    }
    
    public void visit(VarType varType) {
    	this.currentVarType = varType.getType();
    }
    
    public void visit(VarDeclVar varDeclVar) {
    	if(TabExtended.find(varDeclVar.getVarName()) != Tab.noObj) {
    		report_error("Semanticka greska na liniji" + varDeclVar.getLine() + " promenljiva vec deklarisana!", null);
    	}
    	else {
    		varDeclCount++;
    		report_info("\tVAR DECLARED: " + varDeclVar.getVarName() + " LINE: " + varDeclVar.getLine(), null);
    		Obj insertedVar = TabExtended.insert(Obj.Var, varDeclVar.getVarName(), this.currentVarType.struct);
    		varDeclVar.obj = insertedVar;
    	}
    }
    
    public void visit(VarDeclArray varDeclArray) {
    	varDeclCount++;
    	if(TabExtended.find(varDeclArray.getArrayName()) != Tab.noObj) {
    		report_error("Semanticka greska na liniji" + varDeclArray.getLine() + " promenljiva vec deklarisana!", null);

    	}
    	else {
//    		varDeclCount++;
    		if(varDeclArray.getBrackets() instanceof BracketsMatrix) {
    			report_info("\tMATRIX DECLARED: " + varDeclArray.getArrayName() + " LINE: " + varDeclArray.getLine(), null);
    			Struct arrOfType = new Struct(Struct.Array, this.currentVarType.struct);
    			Struct innerArr = new Struct(Struct.Array, arrOfType);
    			Obj insertedVarDeclMatrix = TabExtended.insert(Obj.Var, varDeclArray.getArrayName(), innerArr);
    			varDeclArray.obj = insertedVarDeclMatrix;
    		}else {
    			report_info("\tARR DECLARED: " + varDeclArray.getArrayName() + " LINE: " + varDeclArray.getLine(), null);
    			Struct arrOfType = new Struct(Struct.Array, this.currentVarType.struct);
    			Obj insertedVarDeclArray = TabExtended.insert(Obj.Var, varDeclArray.getArrayName(), arrOfType);
    			varDeclArray.obj = insertedVarDeclArray;
    		}
    	}
    }
    
    public void visit(MoreVarDeclsArray moreVarArrays) {
    	if(Tab.find(moreVarArrays.getArrayName()) != Tab.noObj) {
    		report_error("Semanticka greska na liniji" + moreVarArrays.getLine() + " niz/matrica vec deklarisan!", null);
    	}
    	else {
    		if(moreVarArrays.getBrackets() instanceof BracketsMatrix) {
    			report_info("\tMATRIX DECLARED: " + moreVarArrays.getArrayName() + " LINE: " + moreVarArrays.getLine(), null);
    			Struct innerArr = new Struct(Struct.Array, new Struct(Struct.Array, this.currentVarType.struct));
    			Obj insertedMoreVarArrays = TabExtended.insert(Obj.Var, moreVarArrays.getArrayName(), innerArr);
    			moreVarArrays.obj = insertedMoreVarArrays;
    		}
    		else {
    			report_info("\tARR DECLARED: " + moreVarArrays.getArrayName() + " LINE: " + moreVarArrays.getLine(), null);
    			Struct arrOfType = new Struct(Struct.Array, this.currentVarType.struct);
    			Obj insertedVarDeclArray = TabExtended.insert(Obj.Var, moreVarArrays.getArrayName(), arrOfType);
    			moreVarArrays.obj = insertedVarDeclArray;
    		}
    	}
    }
    
    
    /*
     * Rules for methods 
     */
    
    public void visit(MethodDeclNameVOID methodDeclName) {
		currentMethod = TabExtended.insert(Obj.Meth, methodDeclName.getMethodName(), Tab.noType);
		methodDeclName.obj = currentMethod;
		Tab.openScope();
		report_info("\n\t------------------ START METHOD: " + methodDeclName.getMethodName() + " ------------------", null);
	}
    
    public void visit(MethodDeclNameType methodDeclNameType) {
    	currentMethod = TabExtended.insert(Obj.Meth, methodDeclNameType.getMethodName(), methodDeclNameType.getType().struct);
    	methodDeclNameType.obj = currentMethod;
		Tab.openScope();
		report_info("\n\t------------------ START METHOD: " + methodDeclNameType.getMethodName() + " ------------------", null);
		
    }
    
	public void visit(MethodDecl methodDecl) {
		
//		if(!returnFound && currentMethod.getType() != Tab.noType) {
//			report_error("Semanticka greska na liniji " + methodDecl.getLine() + ": funkcija " + currentMethod.getName() + " nema return iskaz!", null);
//		}
		
		Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();
		report_info("\n\t------------------ METHOD END: " + currentMethod.getName() + " ------------------", null);
		currentMethod = null;
		returnFound = false;
	}
	
	
	public void visit(VarDeclInFunctionArray varFuncArr) {
		if(Tab.find(varFuncArr.getArrName()) != Tab.noObj) {
			report_error("Semanticka greska na liniji" + varFuncArr.getLine() + " promenljiva vec deklarisana!", null);
		}
		else {
			varDeclCount++;
			report_info("\tMETHOD ARR DECLARED: " + currentMethod.getName() + " NAME: " + varFuncArr.getArrName(), null);
			Obj insertedVarFuncArr = TabExtended.insert(Struct.Array, varFuncArr.getArrName(), varFuncArr.getType().struct);
			varFuncArr.obj = insertedVarFuncArr;
		}
	}
	
	public void visit(VarDeclInFunctionVars varFuncVar) {
		if(Tab.find(varFuncVar.getVarName()) != Tab.noObj) {
			report_error("Semanticka greska na liniji" + varFuncVar.getLine() + " promenljiva vec deklarisana!", null);
		}
		else {
			varDeclCount++;
			report_info("\tMETHOD VAR DECLARED: " + currentMethod.getName() + " NAME: " + varFuncVar.getVarName() + " TYPE: " + varFuncVar.getType().struct.getKind(), null);
			Obj insertedVarFuncVar = TabExtended.insert(Obj.Var, varFuncVar.getVarName(), varFuncVar.getType().struct);
			varFuncVar.obj = insertedVarFuncVar;
		}
	}

	/*
	 * Rules for statements
	 * */
	
	/*
	 * Rules for print & read
	 */
	
	public void visit(StatementPrint stmntPrint) {
		if(stmntPrint.getExpr().struct.getKind() == Struct.Array) {
			if(stmntPrint.getExpr().struct.getElemType().getKind() == Struct.Array) {
				if(stmntPrint.getExpr().struct.getElemType().getElemType().getKind() != Struct.Int &&
					stmntPrint.getExpr().struct.getElemType().getElemType().getKind() != Struct.Char &&
					stmntPrint.getExpr().struct.getElemType().getElemType() != TabExtended.boolType) {
					// Matrica sa nepoznatim tipom
					report_error("Semanticka greska na liniji " + stmntPrint.getLine() + " print ocekuje int, char, bool matrix", null);
				}
			}
			else {
				if(stmntPrint.getExpr().struct.getElemType().getKind() != Struct.Int &&
					stmntPrint.getExpr().struct.getElemType().getKind() != Struct.Char &&
					stmntPrint.getExpr().struct.getElemType() != TabExtended.boolType) {
					// Niz sa nepoznatim tipom
					report_error("Semanticka greska na liniji " + stmntPrint.getLine() + " print ocekuje int, char, bool arr", null);
				}
			}
		}
		else if(stmntPrint.getExpr().struct.getKind() != Struct.Int &&
			stmntPrint.getExpr().struct.getKind() != Struct.Char &&
			stmntPrint.getExpr().struct != TabExtended.boolType) {
			report_error("Semanticka greska na liniji " + stmntPrint.getLine() + " print ocekuje int, char, bool", null);
		}
		else {
			printCallCount++;
		}
	}
	
	public void visit(StatementRead stmntRead) {
		if(stmntRead.getDesignator().obj.getKind() != Obj.Var && 
			stmntRead.getDesignator().obj.getKind() != Obj.Elem) {
			report_error("Greska na liniji " + stmntRead.getLine() + " designator nije ni promenljiva ni element niza/matrice.",null);
		}
		if(stmntRead.getDesignator().obj.getType() != Tab.intType &&
			stmntRead.getDesignator().obj.getType() != Tab.charType &&
			stmntRead.getDesignator().obj.getType() != TabExtended.boolType) {
			report_error("Greska na liniji " + stmntRead.getLine() + " designator nije tipa int, char ili bool.",null);
		}
		
	}
	
	/*
	 * Rules for Designators
	 * */
	
	private int isMatrixOrArray(Obj o) {
		if(o.getType().getKind() == Struct.Array) {
			if(o.getType().getElemType().getKind() == Struct.Array) {
				return 1;
			}
			else {
				return 0;
			}
		}
		return -1;
	}
	public void visit(DesignatorIdent designator) {
		if(TabExtended.find(designator.getDesName()) == null) {
			report_error("Nije pronadjen dezignator " + designator.getDesName() + " u tabeli simbola! ", null);
		}
		else {
			Obj foundDesignator = TabExtended.find(designator.getDesName());
			report_info("" + designator.getParent().getClass(), null);
			if(designator.getOptionBracketExpr() instanceof ArrayBracketExpr) {
				designator.obj = new Obj(Obj.Var, "arrayElem", foundDesignator.getType().getElemType());
			}
			else if(designator.getOptionBracketExpr() instanceof MatrixBracketExpr) {
				designator.obj = new Obj(Obj.Var, "matrixElem", foundDesignator.getType().getElemType().getElemType());
			}
			else
				designator.obj = foundDesignator;
		}
	}
	
	public void visit(DesignatorStatementAssign desAssign) {
//		report_info("EXPR: " + desAssign.getExpr().getClass() + " DESIGNATOR TYPE: " + desAssign.getDesignator().getClass() + " LINE: " + desAssign.getLine(), null);
		
		if(desAssign.getDesignator().obj.getType().getKind() == Struct.Array &&
				(desAssign.getDesignator().obj.getType().getKind() != desAssign.getExpr().struct.getKind())) {
			if(desAssign.getDesignator().obj.getType().getElemType().getKind() == Struct.Array) {
				// MATRIX
				if(desAssign.getExpr().struct.getKind() != Struct.Int &&
						desAssign.getExpr().struct.getKind() != Struct.Bool &&
						desAssign.getExpr().struct.getKind() != Struct.Char) {
						report_error("Nekompatibilni tipovi za dodelu na liniji " + desAssign.getLine(), null);
						return;
				}
			}
			// ARRAY
			if(desAssign.getExpr().struct.getKind() != Struct.Int &&
				desAssign.getExpr().struct.getKind() != Struct.Bool &&
				desAssign.getExpr().struct.getKind() != Struct.Char) {
				report_error("Nekompatibilni tipovi za dodelu na liniji " + desAssign.getLine(), null);
				return;
			}
			
		}
		else if(!desAssign.getExpr().struct.assignableTo(desAssign.getDesignator().obj.getType())){
			report_error("Nekompatibilni tipovi za dodelu na liniji " + desAssign.getLine(), null);
			return;
		}
	}
	public void visit(DesignatorStatementInc desInc) {
		if(desInc.getDesignator().obj.getKind() != Obj.Var &&
			desInc.getDesignator().obj.getKind() != Obj.Elem) {
			report_error("Semanticka greska na liniji " + desInc.getLine() + " dezignator u ++ nije promenljiva ni element niza/matrice ", null);
		}
		else if(desInc.getDesignator().obj.getType().getKind() == Struct.Array) {
			if(desInc.getDesignator().obj.getType().getElemType().getKind() == Struct.Array) {
				if(desInc.getDesignator().obj.getType().getElemType().getElemType().getKind() != Struct.Int)
					report_error("Semanticka greska na liniji " + desInc.getLine() + " matrica u ++ nije int tipa", null);	
				else
					report_info("\tINC MATRIX ELEM -> " + desInc.getDesignator().obj.getName(), null);
			}
			else if(desInc.getDesignator().obj.getType().getElemType().getKind() != Struct.Int ) {
				report_error("Semanticka greska na liniji " + desInc.getLine() + " niz u ++ nije int tipa", null);
			}
			else
				report_info("\tINC ARR ELEM -> " + desInc.getDesignator().obj.getName(), null);
		}
		else if(desInc.getDesignator().obj.getType() != Tab.intType) {
			report_error("Semanticka greska na liniji " + desInc.getLine() + " dezignator u ++ nije tipa int ", null);
		}
		else {
			report_info("\tINC VAR -> " + desInc.getDesignator().obj.getName(), null);
		}
	}
	
	public void visit(DesignatorStatementDec desDec) {
		if(desDec.getDesignator().obj.getKind() != Obj.Var &&
				desDec.getDesignator().obj.getKind() != Obj.Elem) {
				report_error("Semanticka greska na liniji " + desDec.getLine() + " dezignator u ++ nije promenljiva ni element niza/matrice ", null);
			}
			else if(desDec.getDesignator().obj.getType().getKind() == Struct.Array) {
				if(desDec.getDesignator().obj.getType().getElemType().getKind() == Struct.Array) {
					if(desDec.getDesignator().obj.getType().getElemType().getElemType().getKind() != Struct.Int)
						report_error("Semanticka greska na liniji " + desDec.getLine() + " matrica u +--= nije int tipa", null);	
					else
						report_info("\tDEC MATRIX ELEM -> " + desDec.getDesignator().obj.getName(), null);
				}
				else if(desDec.getDesignator().obj.getType().getElemType().getKind() != Struct.Int ) {
					report_error("Semanticka greska na liniji " + desDec.getLine() + " niz u -- nije int tipa", null);
				}
				else
					report_info("\tDEC ARR ELEM -> " + desDec.getDesignator().obj.getName(), null);
			}
			else if(desDec.getDesignator().obj.getType() != Tab.intType) {
				report_error("Semanticka greska na liniji " + desDec.getLine() + " dezignator u -- nije tipa int ", null);
			}
			else {
				report_info("\tDEC VAR -> " + desDec.getDesignator().obj.getName(), null);
			}
	}
	/*
	 * Bracket shenanigans
	 * */
	
	public void visit(BracketExpression optBrack) {
		if(optBrack.getExpr().struct != Tab.intType) {
			report_error("Izraz u uglastim zagradama na liniji "+optBrack.getLine()+" nije tipa int!", null);
		}
	}
	
	public void visit(ArrayBracketExpr expr) {
		this.matrixDetected = false;
	}
	
	public void visit(MatrixBracketExpr expr) {
		this.matrixDetected = true;
	}

	/*
	 * Expressions
	 * */
	

	public void visit(ExprMinus exprMinus) {
		if(exprMinus.getTerm().struct != Tab.intType) {
			report_error("Semanticka greska na liniji " + exprMinus.getLine() + " tip nije int", null);
			exprMinus.struct = Tab.noType;
		}
		else {
			exprMinus.struct = exprMinus.getTerm().struct;
		}
	}
	public void visit(ExprTerm exprTerm) {
		exprTerm.struct = exprTerm.getTerm().struct;
	}
	
	public void visit(ExprAddopTerm exprAddOpTerm) {
		Struct expr = exprAddOpTerm.getExpr().struct;
		Struct term = exprAddOpTerm.getTerm().struct;
//		report_info("ADDOP: EXPR: " + exprAddOpTerm.getExpr().struct + " TERM: " + exprAddOpTerm.getTerm().struct, null);
		if(expr.compatibleWith(term)) {
			if(expr.equals(term) && expr == Tab.intType) {
				exprAddOpTerm.struct = expr;
			}
			else {
				exprAddOpTerm.struct = Tab.noType;
				report_error("ADDOP Semanticka greska na liniji " + exprAddOpTerm.getLine() + " sabirci nisu tipa int", null);
			}
		}
		else {
			exprAddOpTerm.struct = Tab.noType;
			report_error("ADDOP Semanticka greska na liniji " + exprAddOpTerm.getLine() + " nekompatibilni tipovi!", null);
		}
	}
	
	/*
	 * Rules for terms 
	 * */
	
	public void visit(TermMulOpFactor termMulOp) {
		Struct factor = termMulOp.getFactor().struct;
		Struct term = termMulOp.getTerm().struct;
//		report_info("MULOP FACTOR: " + termMulOp.getFactor().struct + " TERM: " + termMulOp.getTerm().struct, null);
		
		if(factor.compatibleWith(term)) {
			if(factor.equals(term) && factor == Tab.intType) {
				termMulOp.struct = factor;
				
			}
			else {
				termMulOp.struct = Tab.noType;
				report_error("MULOP Semanticka greska na liniji " + termMulOp.getLine() + " cinioci nisu tipa int", null);
			}
		}
		else {
			termMulOp.struct = Tab.noType;
			report_error("MULOP Semanticka greska na liniji " + termMulOp.getLine() + " nekompatibilni tipovi!", null);
		}
	}
	
	public void visit(FactorNoTerm factorNoTerm) {
		factorNoTerm.struct = factorNoTerm.getFactor().struct;
//		report_info("FactorNoTerm je tipa " + factorNoTerm.getFactor().struct.getKind() + " LINE: " + factorNoTerm.getLine(), null);
	}
	
	public void visit(FactorNum factor) {
		factor.struct = Tab.intType;
		
//		report_info("Faktor na liniji " + factor.getLine() + " je tipa INT i broj je " + factor.getNum(), null);
	}
	
	public void visit(FactorChar factor) {
		factor.struct = Tab.charType;
//		report_info("Faktor na liniji " + factor.getLine() + " je tipa CHAR i vr je " + factor.getCh(), null);
	}
	
	public void visit(FactorBool factor) {
		factor.struct = TabExtended.boolType;
//		report_info("Faktor na liniji " + factor.getLine() + " je tipa BOOL", null);
	}
	
	public void visit(FactorExpr factorExpr) {
		factorExpr.struct = factorExpr.getExpr().struct;
	}
	public void visit(FactorDesignator factor) {
		if(factor.getDesignator().obj.getType().getKind() == Struct.Array) {
			if(factor.getDesignator().obj.getType().getElemType().getKind() == Struct.Array)
				factor.struct = factor.getDesignator().obj.getType().getElemType().getElemType();
			else
				factor.struct = factor.getDesignator().obj.getType().getElemType();
		}
		else
		{
			factor.struct = factor.getDesignator().obj.getType();
//			report_info("Designator: " + factor.getDesignator().obj.getName() + " TYPE: " + factor.getDesignator().obj.getType().getKind(), null);
		}
	}
	
	public void visit(FactorNew factor) {
//		report_info("FACTOR NEW", null);
		if(matrixDetected) {
			report_info("\t->NEW MATRIX ON LINE: " + factor.getLine() + " FACTOR TYPE: " + factor.getType().getTypeName(), null);
			factor.struct = new Struct(Struct.Array, new Struct(Struct.Array, factor.getType().struct));
		}
		else {
			report_info("\t->NEW ARRAY ON LINE " + factor.getLine() + " FACTOR TYPE: " + factor.getType().getTypeName(), null);
			factor.struct = new Struct(Struct.Array, factor.getType().struct);
		}
	}
	
	
	
	/*
	 * Rules for operations
	 * */
	
	
	
	
	/*
	 * Rules for types
	 * */
	public void visit(Type type) {
		Obj typeNode = TabExtended.find(type.getTypeName());
		
		if(typeNode == TabExtended.noObj) {
    		report_error("Nije pronadjen tip " + type.getTypeName() + " u tabeli simbola! ", null);
			type.struct = Tab.noType;
		} else {
			if(Obj.Type == typeNode.getKind()) {
				type.struct = typeNode.getType();
				currentTypeForVarOrConstDecl = typeNode.getType();
			} else {
    			report_error("Greska: Ime " + type.getTypeName() + " ne predstavlja tip!", type);
    			type.struct = Tab.noType;
			}
		}
	}
}
