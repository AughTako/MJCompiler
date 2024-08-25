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
		if(statementPrint.getPrintConst() instanceof PrintConst) {
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
			// At this point, the row size and column size should be already on the stack.
			// The stack top should have: [rowSize, columnSize]

			// Duplicate the row size and place it below column size
			Code.put(Code.dup_x1);  // Stack: [columnSize, rowSize, columnSize]
			Code.put(Code.pop);
			// Create the outer array (array of references for rows)
			Code.put(Code.newarray);
			Code.put(1);  // 1 indicates an array of references (e.g., arrays of integers or chars)
			// Stack: [columnSize, arrayOfRows]

			// Store the outer array reference in a temporary variable
			Obj outerArrayTempVar = new Obj(Obj.Var, "outerArrayTemp", factor.struct, 0, 1);
			Code.store(outerArrayTempVar);  // Stack: [columnSize]

			// Initialize row index to 0
			Code.loadConst(0);
			Obj rowIndexTempVar = new Obj(Obj.Var, "rowIndexTemp", Tab.intType, 0, 1);
			Code.store(rowIndexTempVar);  // Stack: [columnSize]

			// Start loop for allocating each row array
			int loopStart = Code.pc;

			// Load row index and compare with row size (which is now stored in outerArrayTempVar)
			Code.load(rowIndexTempVar);  // Load current row index; Stack: [columnSize, rowIndex]
			Code.load(outerArrayTempVar);  // Load the array of rows reference; Stack: [columnSize, rowIndex, arrayOfRows]
			Code.put(Code.dup2);  // Duplicate row count and row index for comparison and loop continuation; Stack: [columnSize, rowIndex, arrayOfRows, columnSize, rowIndex]
			Code.putFalseJump(Code.lt, 0);  // Conditional jump (exit loop if row index >= row count)
			int loopEndJump = Code.pc - 2;  // Placeholder for jump back-patch

			// Load the outer array reference again
			Code.load(outerArrayTempVar);  // Stack: [columnSize, rowIndex, arrayOfRows]

			// Load the row index to store the new inner array
			Code.load(rowIndexTempVar);  // Stack: [columnSize, rowIndex, arrayOfRows, rowIndex]

			// Load the column size (stack top has row size, below it is column size)
			Code.put(Code.dup_x2);  // Duplicate column size (beneath row size in stack) to top of stack; Stack: [columnSize, rowIndex, arrayOfRows, rowIndex, columnSize]

			// Create the inner array for the current row
			Code.put(Code.newarray);
			if (factor.struct.getElemType() == Tab.charType) {
			    Code.put(0);  // Array of chars; Stack: [columnSize, rowIndex, arrayOfRows, rowIndex, arrayOfChars]
			} else {
			    Code.put(1);  // Array of ints or other types; Stack: [columnSize, rowIndex, arrayOfRows, rowIndex, arrayOfInts]
			}

			// Store the new inner array reference in the outer array at the current row index
			Code.put(Code.astore);  // Stack: [columnSize, rowIndex]

			// Increment row index
			Code.load(rowIndexTempVar);  // Stack: [columnSize, rowIndex, rowIndex]
			Code.loadConst(1);  // Stack: [columnSize, rowIndex, rowIndex, 1]
			Code.put(Code.add);  // Increment rowIndex; Stack: [columnSize, rowIndex]
			Code.store(rowIndexTempVar);  // Stack: [columnSize]

			// Jump back to the start of the loop
			Code.putJump(loopStart);

			// Fix the address for the loop end jump
			Code.fixup(loopEndJump);

			// Clean up the duplicated row count and row index after loop
			Code.put(Code.pop);  // Remove extra row index; Stack: [columnSize]
			Code.put(Code.pop);  // Remove extra column size; Stack: []
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
		Code.load(desigAssign.getDesignator().obj);
		if(desigAssign.getDesignator().obj.getKind() == Obj.Elem) {
			if(desigAssign.getDesignator().obj.getType().getKind() == Struct.Char) {
				Code.put(Code.bastore);
			}
			else if(desigAssign.getDesignator().obj.getType().getKind() == Struct.Int) {
				Code.put(Code.astore);
			}
	
		}
		else {
			Code.store(desigAssign.getDesignator().obj);
		}
//		System.out.println("VAR ASSIGN: " + desigAssign.getDesignator().obj.getName());
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
	
	public void visit(DesignatorIdent designator) {
		if(designator.obj.getType().getKind() == Struct.Array && designator.getOptionBracketExpr() instanceof NoBracketExpr) {
			return;
		}
		if(designator.obj.getType().getKind() == Obj.Var && designator.obj.getType().getKind() != Struct.Array) {
			return;
		}
		Code.load(designator.obj);
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
