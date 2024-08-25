package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor{
	private int mainPc;
	public int getMainPc() {
		return mainPc;
	}
	
	private boolean checkIfMatrix(Obj o) {
		/*
		 * Helper function ->
		 * Proverava da li je objekat matrica
		 * */
		if(o.getType().getKind() == Struct.Array) {
			if(o.getType().getElemType() != null && o.getType().getElemType().getKind() == Struct.Array) {
				return true;
			}
		}
		return false;
	}
	
	private int checkIfElemIntOrChar(Obj o) {
		/*
		 * Helper function -> 
		 * Vraca da li je elem niza/matrice
		 * tipa INT ili CHAR ili ako nije nista od toga
		 * */
		switch(o.getType().getKind()) {
		case Struct.Int:
			return 1;
		case Struct.Char:
			return 2;
		case Struct.Array:
			switch(o.getType().getElemType().getElemType().getKind()) {
			case Struct.Int:
				return 1;
			case Struct.Char:
				return 2;
			default:
				return -1;
			}
		default:
			return -1;
		}
	}
	
	private int getConstVal(SyntaxNode node) {
		/*
		 * Helper function ->
		 * Vraca vrednost konstanti
		 * */
		if(node instanceof ConstNum)
			return ((ConstNum) node).getNumVal();
		if(node instanceof ConstChar)
			return ((ConstChar) node).getCharVal().charValue();
		if(node instanceof ConstBool) {
			
			if("true".equalsIgnoreCase(((ConstBool) node).getBoolVal()))
				return 1;
			else
				return 0;
		}
		return -1;
	}
	
	/*
	 * METHODS
	 * */
	
	public void visit(MethodDeclNameVOID method) {
		if("main".equalsIgnoreCase(method.getMethodName())) {
			mainPc = Code.pc;
		}
		
		method.obj.setAdr(Code.pc);
		
		SyntaxNode methodNode = method.getParent();
		VarCounter varCounter = new VarCounter();
		method.traverseTopDown(varCounter);
		
		Code.put(Code.enter);
		Code.put(0);
		Code.put(varCounter.getCount());
	}
	
	public void visit(MethodDecl methodDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	/*
	 * STATEMENTS
	 * */
	
	public void visit(StatementPrint statementPrint) {
		if(statementPrint.getPrintConst().getClass() == PrintConst.class) {
			if(statementPrint.getExpr().struct == Tab.charType)
				Code.put(Code.bprint);
			else
				Code.put(Code.print);
		}
		else {
			if(statementPrint.getExpr().struct == Tab.charType) {
//				System.out.println("CHAR" + statementPrint.getExpr().getLine());
				Code.loadConst(1);
				Code.put(Code.bprint);
			}
			else {
//				System.out.println("INT" + statementPrint.getExpr().getLine());
				Code.loadConst(5);
				Code.put(Code.print);
			}
		}
	}
	
	public void visit(StatementRead stRead) { 
//		System.out.println(stRead.getDesignator().obj.getType().getKind());
		if(checkIfElemIntOrChar(stRead.getDesignator().obj) == 1) {
			Code.put(Code.read);
		}
		else if(checkIfElemIntOrChar(stRead.getDesignator().obj) == 2) {
			Code.put(Code.bread);
		}
		Code.store(stRead.getDesignator().obj);
	}

	public void visit(PrintConstExists pConst) {
		Code.loadConst(pConst.getNum());
	}
	
	/*
	 * CONSTANTS
	 * */
	
	public void visit(FormalConstDeclr constDeclr) {
		constDeclr.obj.setAdr(getConstVal(constDeclr.getConstVals()));
	}
	public void visit(MoreConstValsExist moreConstDeclr) {
		moreConstDeclr.obj.setAdr(getConstVal(moreConstDeclr.getConstVals()));
	}
	public void visit(ConstNum constNum) {
		Code.loadConst(constNum.getNumVal());
	}
	public void visit(ConstChar constChar) {
		Code.loadConst(constChar.getCharVal());
	}
	public void visit(ConstBool constBool) {
		if("true".equalsIgnoreCase(constBool.getBoolVal())) {
			Code.loadConst(1);
		}
		else {
			Code.loadConst(0);
		}
	}
	
	/*
	 * 
	 * */
	
}
