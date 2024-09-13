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
		if(!(stmntPrint.getExpr().struct.assignableTo(Tab.intType) ||
			stmntPrint.getExpr().struct.assignableTo(Tab.charType) ||
			stmntPrint.getExpr().struct.assignableTo(TabExtended.boolType))) {
			report_error("Nedozvoljen tip u printu", null);
		}
		printCallCount++;
	}
	
	public void visit(StatementRead stmntRead) {
		Designator designator = stmntRead.getDesignator();

		if (designator instanceof DesignatorIdent) {

			Obj object = Tab.find(((DesignatorIdent) designator).getMyObj().getName());
			if (object == Tab.noObj) {
				report_error("READ: Ne postoji promenljiva, na liniji " + stmntRead.getLine(), null);
			}

			if (object.getKind() == Obj.Var) {

			} else {
				report_error("READ: Identifikator ne postoji u tabeli simbola, na liniji " + stmntRead.getLine(), null);
			}

		} else if (designator instanceof DesignatorArrayElem) {

			Obj object = Tab.find(((DesignatorArrayElem) designator).getMyObj().getName());
			if (object == Tab.noObj) {
				report_error("READ: Ne postoji promenljiva", null);
			}
			if (object.getKind() == Obj.Var && object.getType().getKind() == Struct.Array) {
				if (object.getType().getElemType().assignableTo(Tab.intType) || object.getType().getElemType().assignableTo(Tab.charType) || object.getType().getElemType().assignableTo(TabExtended.boolType)) {

				} else {
					report_error("READ: Nekompatibilan tip na liniji " + stmntRead.getLine(), null);
				}
			}

		} else if (designator instanceof DesignatorMatrixElem) {
			
			Obj object = Tab.find(((DesignatorMatrixElem) designator).getMyObj().getName());
			if (object == Tab.noObj) {
				report_error("READ: Ne postoji promenljiva", null);
			}
			if (object.getType().getKind() == Struct.Array && object.getType().getElemType().getKind() == Struct.Array) {
				if (object.getType().getElemType().getElemType().assignableTo(Tab.intType) || object.getType().getElemType().getElemType().assignableTo(Tab.charType) || object.getType().getElemType().getElemType().assignableTo(TabExtended.boolType)) {

				} else {
					report_error("READ: Identifikator ne postoji u tabeli simbola, na liniji " + stmntRead.getLine(), null);
				}
			} else {
				report_error("READ: Ne postoji u tabeli simbola, na liniji " + stmntRead.getLine(), null);
			}
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

	public void visit(DesignatorStatementAssign desAssign) {
		Designator designator = desAssign.getDesignator();
		String name;
		Obj object;
		if(designator instanceof DesignatorIdent) {
			name = ((DesignatorIdent) designator).getMyObj().getName();
			object = Tab.find(name);
			if(object == Tab.noObj) {
				report_error("Objekat sa imenom " + name + " ne postoji", null);
			}
			if(object.getKind() == Obj.Var) {
				if(!(object.getType().assignableTo(desAssign.getExpr().struct))) {
					report_error("Nekompatibilni tipovi na liniji " + desAssign.getLine(), null);
				}
			}
			else {
				report_error("Objekat nije promenljiva za dodelu na liniji " + desAssign.getLine(), null);
			}
		}
		else if(designator instanceof DesignatorArrayElem) {
			name = ((DesignatorArrayElem) designator).getMyObj().getName();
			object = Tab.find(name);
			if(object == Tab.noObj) {
				report_error("Objekat sa imenom " + name + " ne postoji", null);
			}
			if(object.getKind() == Obj.Var && object.getType().getKind() == Struct.Array && object.getType().getElemType().assignableTo(desAssign.getExpr().struct)) {
	
			}
			else {
				report_error("Objekat nije promenljiva za dodelu na liniji " + desAssign.getLine(), null);
			}
		}
		else if(designator instanceof DesignatorMatrixElem) {
			name = ((DesignatorMatrixElem) designator).getMyObj().getName();
			object = Tab.find(name);
			if(object == Tab.noObj) {
				report_error("Objekat sa imenom " + name + " se koristi ali nije definisan", null);
			}
			if(object.getKind() == Obj.Var && object.getType().getKind() == Struct.Array && object.getType().getElemType().getKind() == Struct.Array) {
				if(!(desAssign.getExpr().struct.assignableTo(object.getType().getElemType().getElemType()))){
					report_error("Nekompatibilni tipovi za dodelu na liniji " + desAssign.getLine(), null);
				}
			}
			else {
				report_error("Objekat nije promenljiva za dodelu na liniji " + desAssign.getLine(), null);
			}
		}
	}
	public void visit(DesignatorStatementInc desInc) {
		Designator designator = desInc.getDesignator();
		String name;
		Obj object;

		if (designator instanceof DesignatorIdent) {
			name = ((DesignatorIdent) designator).getMyObj().getName();
			object = Tab.find(name);
			if (object == Tab.noObj) {
				report_error("Objekat " + name + " ne postoji u tabeli simbola", null);
			}

			if (object.getKind() == Obj.Var) {

				if (!(object.getType().assignableTo(Tab.intType))) {
					report_error("Nekompatibilni tipovi " + desInc.getLine(), null);
				}
			} else {
				report_error("Koristi se kao promenljiva, a nije " + desInc.getLine(), null);
			}

		} else if (designator instanceof DesignatorArrayElem) {
			name = ((DesignatorArrayElem) designator).getMyObj().getName();
			object = Tab.find(name);
			if (object == Tab.noObj) {
				report_error("Objekat " + name + " ne postoji u tabeli simbola", null);
			}

			if (object.getKind() == Obj.Var && object.getType().getKind() == Struct.Array && object.getType().getElemType().getKind() != Struct.Array && object.getType().getElemType().assignableTo(Tab.intType)) {
				if ((((DesignatorArrayElem) designator).getExpr().struct.assignableTo(Tab.intType))) {
					
				} else {
					report_error("Nekompatibilni tipovi " + desInc.getLine(), null);
				}

			} else {
				report_error("Objekat se koristi kao niz a nije niz " + desInc.getLine(), null);
			}
		}

		else if (designator instanceof DesignatorMatrixElem) {
			name = ((DesignatorMatrixElem) designator).getMyObj().getName();
			object = Tab.find(name);
			if (object == Tab.noObj) {
				report_error("Objekat " + name + " ne postoji u tabeli simbola", null);
			}
			if (object.getKind() == Obj.Var && object.getType().getKind() == Struct.Array && object.getType().getElemType().getKind() == Struct.Array) {
				if (((DesignatorMatrixElem) designator).getExpr().struct.assignableTo(Tab.intType) && ((DesignatorMatrixElem) designator).getExpr1().struct.assignableTo(Tab.intType)) {

				} else {
					report_error("Semanticka greska objekat sa imenom " + name + "nije kompaktibilan sa int tipom", null);
				}
			} else {
				report_error("Objekat se koristi kao matrica, a nije matrica " + desInc.getLine(), null);
			}
		}
	}
	
	public void visit(DesignatorStatementDec desDec) {
		Designator designator = desDec.getDesignator();
		String name;
		Obj object;

		if (designator instanceof DesignatorIdent) {
			name = ((DesignatorIdent) designator).getMyObj().getName();
			object = Tab.find(name);
			if (object == Tab.noObj) {
				report_error("Semanticka greska objekat sa imenom " + name + "koji nije definisan se koristi kao promenljiva", null);
			}

			if (object.getKind() == Obj.Var) {

				if (!(object.getType().assignableTo(Tab.intType))) {
					report_error("Semanticka greska, objekat sa imenom " + name + " jeste promenljiva, ali nije odgovarajuceg tipa koriscenog u izrazu, promenljiva koja se inkrementira mora biti tipa int", null);
				}
			} else {
				report_error("Semanticka greska, objekat sa imenom " + name + " se koristi kao promenljiva, a nije tipa promenljive", null);
			}

		} else if (designator instanceof DesignatorArrayElem) {
			name = ((DesignatorArrayElem) designator).getMyObj().getName();
			object = Tab.find(name);
			if (object == Tab.noObj) {
				report_error("Greska: Semanticka greska, objekat sa imenom " + name + "koji nije definisan se koristi kao promenljiva", null);
			}

			if (object.getKind() == Obj.Var && object.getType().getKind() == Struct.Array && object.getType().getElemType().getKind() != Struct.Array && object.getType().getElemType().assignableTo(Tab.intType)) {
				if ((((DesignatorArrayElem) designator).getExpr().struct.assignableTo(Tab.intType))) {
					report_info("Pristup elementu niza " + name, null);
				} else {
					report_error("Semanticka greska objekat sa imenom " + name + " se koristi kao element niza sa tipom integer u izrazu u kombinaciji sa operatorom inkrementiranja, a ne predstavlja niz", null);
				}

			} else {
				report_error("Semanticka greska objekat sa imenom " + name + " se koristi kao element niza sa tipom integer u izrazu u kombinaciji sa operatorom inkrementiranja, a ne predstavlja niz", null);
			}
		}

		else if (designator instanceof DesignatorMatrixElem) {
			name = ((DesignatorMatrixElem) designator).getMyObj().getName();
			object = Tab.find(name);
			if (object == Tab.noObj) {
				report_error("Semanticka greska objekat sa imenom " + name + "koji nije definisan se koristi kao promenljiva", null);
			}
			if (object.getKind() == Obj.Var && object.getType().getKind() == Struct.Array && object.getType().getElemType().getKind() == Struct.Array) {
				if (((DesignatorMatrixElem) designator).getExpr().struct.assignableTo(Tab.intType) && ((DesignatorMatrixElem) designator).getExpr1().struct.assignableTo(Tab.intType)) {
					
				} else {
					report_error("Semanticka greska objekat sa imenom " + name + "nije kompaktibilan sa celobrojnim tipom", null);
				}
			} else {
				report_error("Semanticka greska objekat sa imenom " + name + " se koristi kao matrica a ne predstavlja matricu", null);
			}
		}
	}
	
	
	public void visit(DesignatorIdent designatorIdent) {
		String name = designatorIdent.getMyObj().getName();
		Obj object = Tab.find(name);
		designatorIdent.getMyObj().obj = object;
		if (object == Tab.noObj) {
			report_error("Identifikator koji koristite ne postoji u tabeli simbola", null);
		}
		designatorIdent.struct = object.getType();
	}
	
	public void visit(DesignatorArrayElem arrayElem) {
		String name = arrayElem.getMyObj().getName();
		Obj objWithName = Tab.find(name);
		arrayElem.getMyObj().obj = objWithName;
		if (objWithName == Tab.noObj) {
			report_error("Identifikator niza koji koristite ne postoji u tabeli simbola", null);
		}
		if (objWithName.getType().getKind() == Struct.Array && arrayElem.getExpr().struct.assignableTo(Tab.intType)) {
			arrayElem.struct = objWithName.getType().getElemType();
		} else {
			report_error("Pogresno prosledjen identifikator ocekuje se niz", arrayElem);
			arrayElem.struct = Tab.noType;
		}
	}

	public void visit(DesignatorMatrixElem matrixElem) {
		String name = matrixElem.getMyObj().getName();
		Obj objWithName = Tab.find(name);
		matrixElem.getMyObj().obj = objWithName;
		if (objWithName == Tab.noObj) {
			report_error("Identifikator matrice koji koristite ne postoji u tabeli simbola", null);
		}
		if (objWithName.getType().getKind() == Struct.Array && objWithName.getType().getElemType().getKind() == Struct.Array) {
			matrixElem.struct = objWithName.getType().getElemType().getElemType();
		} else {
			report_error("Pogresno prosledjen identifikator, ocekivana je matrica", null);
			matrixElem.struct = Tab.noType;
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
		Designator designator = factor.getDesignator();
		String name;
		Obj object;
		if(designator instanceof DesignatorIdent) {
			name = ((DesignatorIdent) designator).getMyObj().getName();
			object = Tab.find(name);
			if(object == Tab.noObj) {
				report_error("Semanticka greska, ime " + name + " se koristi a nije definisan", null);
				factor.struct = Tab.noType;
			}
			else if(object.getKind() == Obj.Var || object.getKind() == Obj.Con) {
				factor.struct = object.getType();
			}
			else {
				report_error("Semanticka greska, ime " + name + " se koristi kao promenljiva ali nije", null);
				factor.struct = Tab.noType;
			}
		}
		else if(designator instanceof DesignatorArrayElem) {
			name = ((DesignatorArrayElem) designator).getMyObj().getName();
			object = Tab.find(name);
			if(Tab.find(name) == Tab.noObj) {
				report_error("Semanticka greska, ime " + name + " se koristi a nije definisan", null);
				factor.struct = Tab.noType;
			}
			else if(object.getKind() == Obj.Var && object.getType().getKind() == Struct.Array) {
				factor.struct = object.getType().getElemType();
			}
			else {
				report_error("Semanticka greska ime " + name + " se koristi kao element niza a ne predstavlja niz", null);
				factor.struct = Tab.noType;
			}
		}
		else if(designator instanceof DesignatorMatrixElem) {
			name = ((DesignatorMatrixElem) designator).getMyObj().getName();
			object = Tab.find(name);
			if (object == Tab.noObj) {
				report_error("Greska: Semanticka greska, objekat sa imenom " + name + "koji nije definisan se koristi kao promenljiva", null);
				factor.struct = Tab.noType;
			}

			if (object.getKind() == Obj.Var && object.getType().getKind() == Struct.Array && object.getType().getElemType().getKind() == Struct.Array) {
				factor.struct = object.getType().getElemType().getElemType();
			} else {
				report_error("Greska: Semanticka greska, objekat sa imenom " + name + " se koristi kao matrica a ne predstavlja matricu", null);
				factor.struct = Tab.noType;
			}
		}
	}
	
	public void visit(FactorNewArray factorNewArray) {
		if (!(factorNewArray.getExpr().struct.assignableTo(Tab.intType))) {
			report_error("Semanticka greska izraz nije odgovarajuc sa tipom int", factorNewArray);
			factorNewArray.struct = Tab.noType;
		} else {
			Struct newArrayType = new Struct(Struct.Array, factorNewArray.getType().struct);
			factorNewArray.struct = newArrayType;
		}
	}
	
	public void visit(FactorNewMatrix factorNewMatrix) {
		if (!(factorNewMatrix.getExpr().struct.assignableTo(Tab.intType) && factorNewMatrix.getExpr1().struct.assignableTo(Tab.intType))) {
			factorNewMatrix.struct = Tab.noType;
			report_error("Semanticka greska izraz nije odgovarajuc sa tipom int", factorNewMatrix);
		} else {

			Struct newArrayRow = new Struct(Struct.Array, factorNewMatrix.getType().struct);
			Struct newMatrixType = new Struct(Struct.Array, newArrayRow);

			factorNewMatrix.struct = newMatrixType;
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
