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
		Designator designator = stRead.getDesignator();
		Obj dObj;
		if(designator instanceof DesignatorIdent)
			dObj = ((DesignatorIdent) designator).getMyObj().obj;
		else if(designator instanceof DesignatorArrayElem)
			dObj = ((DesignatorArrayElem) designator).getMyObj().obj;
		else
			dObj = ((DesignatorMatrixElem) designator).getMyObj().obj;
		
//		System.out.println("READ" + stRead.getLine());
		
		if (stRead.getDesignator().struct.assignableTo(Tab.charType)) {
			Code.put(Code.bread);
		} else {
			Code.put(Code.read);
		}
		
		
		if (designator instanceof DesignatorIdent) {
			Code.store(dObj);
		}
		else{
			if(dObj.getType().getElemType().assignableTo(Tab.charType)){
				Code.put(Code.bastore);
			}
			else{
				Code.put(Code.astore);
			}
		}
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
	
	public void visit(FactorNewArray factorNewArray){
		Code.put(Code.newarray);
		if(factorNewArray.getType().struct.assignableTo(Tab.charType)){
			Code.loadConst(0);
		}
		else{
			Code.loadConst(1);
		}
	}
	
public void visit(FactorNewMatrix factorNewMatrix) {
		// System.out.println("NEW USED ON MATRIX" + factorNewMatrix.getLine());
		Designator designator =  ((DesignatorStatementAssign)factorNewMatrix.getParent().getParent().getParent()).getDesignator();
		// matrix = new int[2][3];
		// 2 3
		Code.put(Code.dup_x1);	// 3 2 3
		Code.put(Code.pop);		// 3 2
		Code.put(Code.dup);		// 3 2 2 
		
		
		Code.put(Code.newarray);
		Code.loadConst(1);
		// Napravljen niz za redove 3 2 2 newarray 1 -> 3 2 ADRESA
		Code.store(((DesignatorIdent)designator).getMyObj().obj);
		// Upisi mi gde se nalaze redovi u promenljivu
		// 3 2
		Code.loadConst(-1); 			// Za brojac 3 2 -1
		int loop1 = Code.pc;
		Code.loadConst(1);				// Sa ovim cemo da inkrementiramo
		Code.put(Code.add); 			// Inicijalizujemo brojac i = 0
		Code.put(Code.dup2); 			// 3 2 0 2 0
		Code.putFalseJump(Code.ne, 0);	// Izlaz iz pravljenja ali skida sa vrha steka dve vr
		int adrToPatch = Code.pc - 2;	// Pokazuje na stavljanje add na stack
		Code.put(Code.dup_x2);			// 0 3 2 0
		Code.put(Code.dup_x2);			// 0 0 3 2 0
		Code.put(Code.pop);				// 0 0 3 2
		Code.put(Code.dup_x2);			// 0 2 0 3 2
		Code.put(Code.pop);				// 0 2 0 3
		Code.put(Code.dup_x2);			// 0 3 2 0 3
		Code.load(((DesignatorIdent)designator).getMyObj().obj);
		Code.put(Code.dup_x2);			// 0 3 2 ADRESA 0 3 ADRESA
		Code.put(Code.pop);				// 0 3 2 ADRESA 0 3 <- velicina niza na vrhu steka
										// priprema za newarray 1
		
		Code.put(Code.newarray);	
		if(factorNewMatrix.getType().struct.assignableTo(Tab.charType)){
			Code.loadConst(0);
		}
		else{
			Code.loadConst(1);
		}
		
		Code.put(Code.astore);
		// Napravi mi novi niz od 3 elem i upisi u prvi red
		// 0 3 2 ADRESA_OKR 0 ADRESA_UNUTR astore -> 0 3 2 -> u adresu okr na index=0 stavi adresu prvog niza od 3 elem
		Code.put(Code.dup_x2);			// 2 0 3 2 VRACAMO NA OBLIK 3 2 i
		Code.put(Code.pop);				// 2 0 3
		Code.put(Code.dup_x2);			// 3 2 0 3
		Code.put(Code.pop);				// 3 2 0
		Code.putJump(loop1);			// Skoci na adresu za loop
		Code.fixup(adrToPatch);
		Code.put(Code.pop);				//
		Code.put(Code.pop);
		Code.put(Code.pop);
		Code.load(((DesignatorIdent)designator).getMyObj().obj);
	}

	
	/*
	 * DESIGNATORS
	 * */
	
	public void visit(DesignatorIdent designatorIdent) {
		SyntaxNode parent = designatorIdent.getParent();
		Obj dObj = designatorIdent.getMyObj().obj;
		if(parent instanceof FactorDesignator ||
			parent instanceof DesignatorStatementInc ||
			parent instanceof DesignatorStatementDec) {
			Code.load(dObj);
		}
	}
	public void visit(DesignatorArrayElem arrayElem) {
		Obj dObj = arrayElem.getMyObj().obj;
		
		if(arrayElem.getParent() instanceof FactorDesignator) {
			Code.load(dObj);
			Code.put(Code.dup_x1);
			Code.put(Code.pop);
			if(dObj.getType().getElemType().assignableTo(Tab.charType) ) {
				Code.put(Code.baload);
			}
			else {
				Code.put(Code.aload);
			}
		}
		else if(arrayElem.getParent() instanceof DesignatorStatementAssign ||
				arrayElem.getParent() instanceof StatementRead ||
				arrayElem.getParent() instanceof DesignatorStatementInc ||
				arrayElem.getParent() instanceof DesignatorStatementDec) {
			Code.load(dObj);
			Code.put(Code.dup_x1);
			Code.put(Code.pop);
		}
	}
	
	public void visit(DesignatorMatrixElem matrixElem) {
		Obj dObj = matrixElem.getMyObj().obj;
		if (matrixElem.getParent() instanceof FactorDesignator) {
			Code.load(dObj);
			Code.put(Code.dup_x2);
			Code.put(Code.pop);
			Code.put(Code.dup_x2);
			Code.put(Code.pop);
			Code.put(Code.aload);
			Code.put(Code.dup_x1);
			Code.put(Code.pop);
			
			if(dObj.getType().getElemType().assignableTo(Tab.charType)){
				Code.put(Code.baload);
			}
			else{
				Code.put(Code.aload);
			}
		} else if(matrixElem.getParent() instanceof DesignatorStatementAssign ||
				matrixElem.getParent() instanceof StatementRead ||
				matrixElem.getParent() instanceof DesignatorStatementInc ||
				matrixElem.getParent() instanceof DesignatorStatementDec) {
			Code.load(dObj);
			Code.put(Code.dup_x2);
			Code.put(Code.pop);
			Code.put(Code.dup_x2);
			Code.put(Code.pop);
			Code.put(Code.aload);
			Code.put(Code.dup_x1);
			Code.put(Code.pop);
		}
	}
	/*
	 * DESIGNATOR STMTS
	 * */
	public void visit(DesignatorStatementAssign desigAssign) {
		Designator designator = desigAssign.getDesignator();
		Obj dObj;
		if(designator instanceof DesignatorIdent)
			dObj = ((DesignatorIdent) designator).getMyObj().obj;
		else if(designator instanceof DesignatorArrayElem)
			dObj = ((DesignatorArrayElem) designator).getMyObj().obj;
		else
			dObj = ((DesignatorMatrixElem) designator).getMyObj().obj;

		if (designator instanceof DesignatorIdent) {
			Code.store(dObj);
		}
		else{
			if(dObj.getType().getElemType().assignableTo(Tab.charType)){
				Code.put(Code.bastore);
			}
			else{
				Code.put(Code.astore);
			}
		}
	}
	
	public void visit(DesignatorStatementInc dsStmt) {
		Designator designator = dsStmt.getDesignator();
		Obj dObj;
		if(designator instanceof DesignatorIdent)
			dObj = ((DesignatorIdent) designator).getMyObj().obj;
		else if(designator instanceof DesignatorArrayElem)
			dObj = ((DesignatorArrayElem) designator).getMyObj().obj;
		else
			dObj = ((DesignatorMatrixElem) designator).getMyObj().obj;
		if(designator instanceof DesignatorIdent) {
			Code.loadConst(1);
			Code.put(Code.add);
			Code.store(dObj);
		}
		else {
			Code.put(Code.dup2);
			Code.put(Code.aload);
			Code.loadConst(1);
			Code.put(Code.add);
			Code.put(Code.astore);
		}
	}
	
	public void visit(DesignatorStatementDec dsStmt) {
		Designator designator = dsStmt.getDesignator();
		Obj dObj;
		if(designator instanceof DesignatorIdent)
			dObj = ((DesignatorIdent) designator).getMyObj().obj;
		else if(designator instanceof DesignatorArrayElem)
			dObj = ((DesignatorArrayElem) designator).getMyObj().obj;
		else
			dObj = ((DesignatorMatrixElem) designator).getMyObj().obj;
		if(designator instanceof DesignatorIdent) {
			Code.loadConst(1);
			Code.put(Code.sub);
			Code.store(dObj);
		}
		else {
			Code.put(Code.dup2);
			Code.put(Code.aload);
			Code.loadConst(1);
			Code.put(Code.sub);
			Code.put(Code.astore);
		}
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
	
}
