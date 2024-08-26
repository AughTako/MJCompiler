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
		if(statementPrint.getPrintConst() instanceof PrintConstExists) {
			if(statementPrint.getExpr().struct == Tab.charType)
				Code.put(Code.bprint);
			else
				Code.put(Code.print);
		}
		else {
//			if(statementPrint.getExpr().struct.get)
			if(statementPrint.getExpr().struct == Tab.charType) {
//				System.out.println("CHAR" + statementPrint.getExpr().getLine());
				Code.loadConst(1);
				Code.put(Code.bprint);
			}
			else {
				System.out.println("PRINT INT " + statementPrint.getExpr().getLine());
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
	 * FACTORS
	 * */
	
	public void visit(FactorNum factor) {
		Code.loadConst(factor.getNum());
	}
	public void visit(FactorChar factor) {
		Obj tmp = new Obj(Obj.Con, "charValue", Tab.charType);
		tmp.setAdr(factor.getCh());
		Code.load(tmp);
	}
	public void visit(FactorBool fBool) { 
		if("true".equalsIgnoreCase(fBool.getBl())) {
			Code.loadConst(1);
		} else {
			Code.loadConst(0);
		}
	}
	
	public void visit(FactorNew factor) {
		if(factor.getOptionBracketExpr() instanceof MatrixBracketExpr) {
			Designator designator =  ((DesignatorStatementAssign)factor.getParent().getParent().getParent()).getDesignator();
			Code.put(Code.dup_x1);
			Code.put(Code.pop);
			Code.put(Code.dup);
			
			
			Code.put(Code.newarray);
			Code.loadConst(1);
			
			Code.store(((DesignatorIdent)designator).obj);
			Code.loadConst(-1);
			int loop1 = Code.pc;
			Code.loadConst(1);
			Code.put(Code.add);
			Code.put(Code.dup2);	
			Code.putFalseJump(Code.ne, 0);
			int adrToPatch = Code.pc - 2;
			Code.put(Code.dup_x2);
			Code.put(Code.dup_x2);
			Code.put(Code.pop);
			Code.put(Code.dup_x2);
			Code.put(Code.pop);
			Code.put(Code.dup_x2);
			Code.load(((DesignatorIdent)designator).obj);
			Code.put(Code.dup_x2);
			Code.put(Code.pop);
			
			Code.put(Code.newarray);
			if(factor.getType().struct.assignableTo(Tab.charType)){
				Code.loadConst(0);
			}
			else{
				Code.loadConst(1);
			}
			
			
			Code.put(Code.astore);
			Code.put(Code.dup_x2);
			Code.put(Code.pop);
			Code.put(Code.dup_x2);
			Code.put(Code.pop);
			Code.putJump(loop1);
			Code.fixup(adrToPatch);
			Code.put(Code.pop);
			Code.put(Code.pop);
			Code.put(Code.pop);
			Code.put(Code.pop);
//			Code.load(((DesignatorIdent)designator).obj);
		}
		else {
			Code.put(Code.newarray);
			if(factor.getType().struct == Tab.charType)
				Code.put(0);
			else
				Code.put(1);
			
		}
		
	}
	
	/*
	 * DESIGNATOR STMTS
	 * */
	public void visit(DesignatorStatementAssign desigAssign) {
		Designator designator = desigAssign.getDesignator();
		Obj designatorObject = desigAssign.getDesignator().obj;
		if(designator.getParent() instanceof DesignatorStatementAssign && !designator.obj.getName().equalsIgnoreCase("matrixElem")) {
			Code.load(designatorObject);
//			Code.load();
//			Code.load();
		}
		else if (designator instanceof DesignatorIdent && !designator.obj.getName().equalsIgnoreCase("matrixElem")) {
			Code.store(designatorObject);
		}
		else{
			if(designatorObject.getType().getElemType().assignableTo(Tab.charType)){
				Code.put(Code.bastore);
			}
			else{
				Code.put(Code.astore);
			}
		}
	}
	
	public void visit(DesignatorStatementInc dsStmt) {
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(dsStmt.getDesignator().obj);

	}
	
	public void visit(DesignatorStatementDec dsStmt) {
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(dsStmt.getDesignator().obj);
	}
	// NOT WORKING
	/*TODO: 
	 * FIX MATRIXES
	 * */
	public void visit(DesignatorIdent designator) {
		SyntaxNode parent = designator.getParent();
		
		Obj designatorObj = designator.obj;
		if(parent instanceof FactorNew && designator.getOptionBracketExpr() instanceof MatrixBracketExpr
				&& !(parent instanceof StatementPrint)) {
			Code.load(designatorObj);
			Code.put(Code.dup_x2);
			Code.put(Code.pop);
			Code.put(Code.dup_x2);
			Code.put(Code.pop);
			Code.put(Code.aload);
			Code.put(Code.dup_x1);
			Code.put(Code.pop);
			
			if(designatorObj.getType().assignableTo(Tab.charType)){
				Code.put(Code.baload);
			}
			else{
				Code.put(Code.aload);
			}
		}
		else if(
				(
					parent instanceof DesignatorStatementAssign ||
					parent instanceof StatementRead ||
					parent instanceof DesignatorStatementInc ||
					parent instanceof DesignatorStatementDec
				) 
				&& designator.getOptionBracketExpr() instanceof MatrixBracketExpr
				&& designator.getOptionBracketExpr().getParent().getParent() instanceof FactorNew
				) {
			Code.load(designatorObj);
			Code.put(Code.dup_x2);
			Code.put(Code.pop);
			Code.put(Code.dup_x2);
			Code.put(Code.pop);
			Code.put(Code.aload);
			Code.put(Code.dup_x1);
			Code.put(Code.pop);
		}
		else if(!(designator.obj.getName().equalsIgnoreCase("matrixElem"))){
			Code.load(designator.obj);
			
		}

//		SyntaxNode parent = designator.getParent();
//		if(parent instanceof DesignatorStatementAssign && designator.obj.getKind() == Obj.Elem) {
//			Code.load(designator.obj);
//		}
//		else if(!(parent instanceof DesignatorStatementAssign) && !(parent instanceof StatementRead)) {
//			Code.load(designator.obj);
//		}
	}
	
	/* EXPR */
	
	public void visit(ExprMinus exprMinus) {
		Code.put(Code.neg);
	}
	
	public void visit(ExprAddopTerm exprAddopTerm) {
		if(exprAddopTerm.getAddOp() instanceof AddOpPlus) {
			Code.put(Code.add);
		}
		if(exprAddopTerm.getAddOp() instanceof AddOpMinus) {
			Code.put(Code.sub);
		}
	}
	
	public void visit(TermMulOpFactor termMulopFactor) {
		if(termMulopFactor.getMulOp() instanceof MulOpMul) {
			Code.put(Code.mul);
		}
		if(termMulopFactor.getMulOp() instanceof MulOpDiv) {
			Code.put(Code.div);
		}
		if(termMulopFactor.getMulOp() instanceof MulOpMod) {
			Code.put(Code.rem);
		}
	}
	
	public void visit(BracketExpression bExpr) {
		SyntaxNode parent = bExpr.getParent();
		if(parent instanceof OptionBracketExpr && parent instanceof MatrixBracketExpr && !(parent.getParent() instanceof FactorNew ) ) {
			if(((MatrixBracketExpr) parent).getBracketExpression() == bExpr) {
				Code.put(Code.aload);
			}
		}
	}
	
}
