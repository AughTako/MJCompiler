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
	
	public void visit(StatementPrint statementPrint) {
		if(statementPrint.getPrintConst().getClass() == PrintConst.class) {
			if(statementPrint.getExpr().struct == Tab.charType)
				Code.put(Code.bprint);
			else
				Code.put(Code.print);
		}
		else {
			if(statementPrint.getExpr().struct == Tab.charType) {
				Code.loadConst(1);
				Code.put(Code.bprint);
			}
			else {
				Code.loadConst(5);
				Code.put(Code.print);
			}
		}
	}
	
	public void visit(PrintConstExists pConst) {
		Code.loadConst(pConst.getNum());
	}
	
	
}
