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
    	progName.obj = Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
    	Tab.openScope();
    }
    
    /*
     * Rules for declarations of consts, vars, arrays and matrixes
     */
    public void visit(ConstDeclarations constDeclarations) {
    	constDeclCount++;
    }
    
    public void visit(ConstType constType) {
    	this.currentConstType = constType.getType();
    }
    
    public void visit(FormalConstDeclr formalConst) {
    	if(Tab.find(formalConst.getConstName()) != Tab.noObj) {
    		report_error("Semanticka greska na liniji" + formalConst.getLine() + " konstanta vec deklarisana!", null);
    	}
    	else {
    		if(!checkType(formalConst.getConstVals(), currentConstType)) {
    			report_error("Semanticka greska na liniji" + formalConst.getLine() + " neslaganje tipova!", null);
    		}
    		else {
    			formalConst.obj = Tab.insert(Obj.Con, formalConst.getConstName(), currentConstType.struct);
    			report_info("Deklarisana konstanta " + formalConst.getConstName() + " na liniji " + formalConst.getLine(), null);
    			
    		}
    	}
    }
    
    public void visit(MoreConstValsExist moreConsts) {
    	if(Tab.find(moreConsts.getConstName()) != Tab.noObj) {
    		report_error("Semanticka greska na liniji" + moreConsts.getLine() + " konstanta vec deklarisana!", null);
    	}
    	else {
    		if(!checkType(moreConsts.getConstVals(), currentConstType)) {
    			report_error("Semanticka greska na liniji" + moreConsts.getLine() + " neslaganje tipova!", null);
    		}
    		else {
    			moreConsts.obj = Tab.insert(Obj.Con, moreConsts.getConstName(), currentConstType.struct);
    			report_info("Deklarisana konstanta " + moreConsts.getConstName() + " na liniji " + moreConsts.getLine(), null);
    			
    		}
    	}
    }
    
    public void visit(VarType varType) {
    	this.currentVarType = varType.getType();
    }
    
    public void visit(VarDeclVar varDeclVar) {
    	varDeclCount++;
    	if(Tab.find(varDeclVar.getVarName()) != Tab.noObj) {
    		report_error("Semanticka greska na liniji" + varDeclVar.getLine() + " promenljiva vec deklarisana!", null);
    	}
    	else {
    		varDeclVar.obj = Tab.insert(Obj.Var, varDeclVar.getVarName(), this.currentVarType.struct);
    	}
    }
    
    public void visit(VarDeclArray varDeclArray) {
    	arrayDeclCount++;
    	if(Tab.find(varDeclArray.getArrayName()) != Tab.noObj) {
    		report_error("Semanticka greska na liniji" + varDeclArray.getLine() + " promenljiva vec deklarisana!", null);

    	}
    	else {
    		if(varDeclArray.getBrackets() instanceof BracketsMatrix) {
    			report_info("Deklarisana matrica " + varDeclArray.getArrayName() + " na liniji " + varDeclArray.getLine(), null);
    			Struct innerArr = new Struct(Struct.Array, new Struct(Struct.Array, this.currentVarType.struct));
    			varDeclArray.obj = Tab.insert(Obj.Var, varDeclArray.getArrayName(), innerArr);
    		}else {
    			report_info("Deklarisan niz " + varDeclArray.getArrayName() + " na liniji " + varDeclArray.getLine(), null);
    			varDeclArray.obj = Tab.insert(Obj.Var, varDeclArray.getArrayName(), new Struct(Struct.Array, this.currentVarType.struct));
    		}
    	}
    }
    
    public void visit(MoreVarDeclsArray moreVarArrays) {
    	if(Tab.find(moreVarArrays.getArrayName()) != Tab.noObj) {
    		report_error("Semanticka greska na liniji" + moreVarArrays.getLine() + " niz/matrica vec deklarisan!", null);
    	}
    	else {
    		if(moreVarArrays.getBrackets() instanceof BracketsMatrix) {
    			report_info("Deklarisana matrica " + moreVarArrays.getArrayName() + " na liniji " + moreVarArrays.getLine(), null);
    			Struct innerArr = new Struct(Struct.Array, new Struct(Struct.Array, this.currentVarType.struct));
    			moreVarArrays.obj = Tab.insert(Obj.Var, moreVarArrays.getArrayName(), innerArr);
    		}
    	}
    }
    
    
    /*
     * Rules for methods 
     */
    
    public void visit(MethodDeclNameVOID methodDeclName) {
		currentMethod = Tab.insert(Obj.Meth, methodDeclName.getMethodName(), Tab.noType);
		methodDeclName.obj = currentMethod;
		Tab.openScope();
		report_info("Obradjuje se funkcija " + methodDeclName.getMethodName(), methodDeclName);
	}
    
    public void visit(MethodDeclNameType methodDeclNameType) {
    	currentMethod = Tab.insert(Obj.Meth, methodDeclNameType.getMethodName(), methodDeclNameType.getType().struct);
    	methodDeclNameType.obj = currentMethod;
		Tab.openScope();
		report_info("Obradjuje se funkcija " + methodDeclNameType.getMethodName(), methodDeclNameType);
		
    }
    
	public void visit(MethodDecl methodDecl) {
		
//		if(!returnFound && currentMethod.getType() != Tab.noType) {
//			report_error("Semanticka greska na liniji " + methodDecl.getLine() + ": funkcija " + currentMethod.getName() + " nema return iskaz!", null);
//		}
		
		Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();
		
		currentMethod = null;
		returnFound = false;
	}
	
	
	public void visit(VarDeclInFunctionArray varFuncArr) {
		if(Tab.find(varFuncArr.getArrName()) != Tab.noObj) {
			report_error("Semanticka greska na liniji" + varFuncArr.getLine() + " promenljiva vec deklarisana!", null);
		}
		else {
			report_info("Niz deklarisan kod metode" + currentMethod.getName() + " sa imenom " + varFuncArr.getArrName(), null);
			varFuncArr.obj = Tab.insert(Struct.Array, varFuncArr.getArrName(), varFuncArr.getType().struct);
		}
	}
	
	public void visit(VarDeclInFunctionVars varFuncVar) {
		if(Tab.find(varFuncVar.getVarName()) != Tab.noObj) {
			report_error("Semanticka greska na liniji" + varFuncVar.getLine() + " promenljiva vec deklarisana!", null);
		}
		else {
			report_info("Promenljiva deklarisana kod metode" + currentMethod.getName() + " sa imenom " + varFuncVar.getVarName(), null);
			varFuncVar.obj = Tab.insert(Obj.Var, varFuncVar.getVarName(), varFuncVar.getType().struct);
		}
	}

	/*
	 * Rules for statements
	 * */
	
	
	/*
	 * Rules for Designators
	 * */
	
	public void visit(DesignatorIdent designator) {
//		report_info("Radim na dezignatoru: " + designator.getDesName(),null);
		if(Tab.find(designator.getDesName()) == Tab.noObj) {
			report_error("Nije pronadjen dezignator " + designator.getDesName() + " u tabeli simbola! ", null);
		}
		else {
			designator.obj = Tab.find(designator.getDesName());
		}
	}
	
	public void visit(BracketExpression bracketExpr) {
		
	}
	
	public void visit(FactorNew factorNew) {

	}
	
	public void visit(OptionBracketExpr optBrack) {
		if(optBrack instanceof ArrayBracketExpr) {
			((ArrayBracketExpr) optBrack).getBracketExpression();
		}
	}
	
	
//	public void visit
	
	
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
