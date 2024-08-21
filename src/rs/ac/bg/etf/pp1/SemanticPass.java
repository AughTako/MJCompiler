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
	int nVars;
	
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
    
    public void visit(VarDeclVar varDeclVar) {
    	varDeclCount++;
    	if(Tab.find(varDeclVar.getVarName()) != Tab.noObj) {
    		report_error("Semanticka greska na liniji" + varDeclVar.getLine() + " promenljiva vec deklarisana!", null);
    	}
    	else {
    		varDeclVar.obj = Tab.insert(Obj.Var, varDeclVar.getVarName(), varDeclVar.getType().struct);
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
    			Struct innerArr = new Struct(Struct.Array, new Struct(Struct.Array, varDeclArray.getType().struct));
    			varDeclArray.obj = Tab.insert(Obj.Var, varDeclArray.getArrayName(), innerArr);
    		}else {
    			report_info("Deklarisan niz " + varDeclArray.getArrayName() + " na liniji " + varDeclArray.getLine(), null);
    			varDeclArray.obj = Tab.insert(Obj.Var, varDeclArray.getArrayName(), new Struct(Struct.Array, varDeclArray.getType().struct));
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
    	currentMethod = Tab.insert(Obj.Meth, methodDeclNameType.getMethodName(), Tab.noType);
    	methodDeclNameType.obj = currentMethod;
		Tab.openScope();
		report_info("Obradjuje se funkcija " + methodDeclNameType.getMethodName(), methodDeclNameType);
    }
    
	public void visit(MethodDecl methodDecl) {
		
		if(!returnFound && currentMethod.getType() != Tab.noType) {
			report_error("Semanticka greska na liniji " + methodDecl.getLine() + ": funkcija " + currentMethod.getName() + " nema return iskaz!", null);
		}
		
		Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();
		
		currentMethod = null;
		returnFound = false;
	} 
	
	/*
	 * Rules for types
	 * */
	public void visit(Type type) {
		Obj typeNode = Tab.find(type.getTypeName());
		if(typeNode == Tab.noObj) {
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
