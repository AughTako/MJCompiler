// generated with ast extension for cup
// version 0.8
// 22/7/2024 18:13:43


package rs.ac.bg.etf.pp1.ast;

public interface Visitor { 

    public void visit(VarDeclInFunction VarDeclInFunction);
    public void visit(OtherFormParamsList OtherFormParamsList);
    public void visit(StatementList StatementList);
    public void visit(MoreConstVals MoreConstVals);
    public void visit(PrintConst PrintConst);
    public void visit(Factor Factor);
    public void visit(OptionBracketExpr OptionBracketExpr);
    public void visit(MoreVarDecls MoreVarDecls);
    public void visit(Term Term);
    public void visit(MulOp MulOp);
    public void visit(RelOp RelOp);
    public void visit(DesignatorName DesignatorName);
    public void visit(Brackets Brackets);
    public void visit(ExprMaybe ExprMaybe);
    public void visit(Declarations Declarations);
    public void visit(Expr Expr);
    public void visit(AddOp AddOp);
    public void visit(ConstVals ConstVals);
    public void visit(DesignatorStatement DesignatorStatement);
    public void visit(VarDeclr VarDeclr);
    public void visit(MethodDeclName MethodDeclName);
    public void visit(Statement Statement);
    public void visit(MethodDeclList MethodDeclList);
    public void visit(FormPars FormPars);
    public void visit(RelOpNE RelOpNE);
    public void visit(RelOpE RelOpE);
    public void visit(RelOpLE RelOpLE);
    public void visit(RelOpGE RelOpGE);
    public void visit(RelOpL RelOpL);
    public void visit(RelOpG RelOpG);
    public void visit(MulOpMod MulOpMod);
    public void visit(MulOpDiv MulOpDiv);
    public void visit(MulOpMul MulOpMul);
    public void visit(AddOpPlus AddOpPlus);
    public void visit(AddOpMinus AddOpMinus);
    public void visit(NoBrackets NoBrackets);
    public void visit(BracketsMatrix BracketsMatrix);
    public void visit(Type Type);
    public void visit(BracketExpression BracketExpression);
    public void visit(NoBracketExpr NoBracketExpr);
    public void visit(MatrixBracketExpr MatrixBracketExpr);
    public void visit(ArrayBracketExpr ArrayBracketExpr);
    public void visit(DesignatorIdent DesignatorIdent);
    public void visit(Designator Designator);
    public void visit(DesignatorStatementDec DesignatorStatementDec);
    public void visit(DesignatorStatementInc DesignatorStatementInc);
    public void visit(DesignatorStatementAssign DesignatorStatementAssign);
    public void visit(FactorNew FactorNew);
    public void visit(FactorDesignator FactorDesignator);
    public void visit(FactorBool FactorBool);
    public void visit(FactorExpr FactorExpr);
    public void visit(FactorChar FactorChar);
    public void visit(FactorNum FactorNum);
    public void visit(FactorNoTerm FactorNoTerm);
    public void visit(TermMulOpFactor TermMulOpFactor);
    public void visit(ExprMaybeNo ExprMaybeNo);
    public void visit(ExprMaybeYes ExprMaybeYes);
    public void visit(ExprAddopTerm ExprAddopTerm);
    public void visit(ExprTerm ExprTerm);
    public void visit(ExprMinus ExprMinus);
    public void visit(NoAndConstPrint NoAndConstPrint);
    public void visit(PrintConstExists PrintConstExists);
    public void visit(ErrorStmt ErrorStmt);
    public void visit(StatementPrint StatementPrint);
    public void visit(StatementReturn StatementReturn);
    public void visit(StatementRead StatementRead);
    public void visit(StatementContinue StatementContinue);
    public void visit(StatementBreak StatementBreak);
    public void visit(StatementDesignatorStatement StatementDesignatorStatement);
    public void visit(NoStmtList NoStmtList);
    public void visit(StatementListExists StatementListExists);
    public void visit(NoVarDeclInFunction NoVarDeclInFunction);
    public void visit(VarDeclInFunctionVars VarDeclInFunctionVars);
    public void visit(VarDeclInFunctionArray VarDeclInFunctionArray);
    public void visit(FormalParamsExist FormalParamsExist);
    public void visit(NoOtherFormParamsList NoOtherFormParamsList);
    public void visit(ErrorFormsPList ErrorFormsPList);
    public void visit(OtherFormParamsListExist OtherFormParamsListExist);
    public void visit(FormParam FormParam);
    public void visit(NoFormPars NoFormPars);
    public void visit(ErrorForms ErrorForms);
    public void visit(FormParsExist FormParsExist);
    public void visit(MethodDeclNameType MethodDeclNameType);
    public void visit(MethodDeclNameVOID MethodDeclNameVOID);
    public void visit(MethodDecl MethodDecl);
    public void visit(NoMethodDeclList NoMethodDeclList);
    public void visit(MethodDeclListExist MethodDeclListExist);
    public void visit(NoMoreVarDecls NoMoreVarDecls);
    public void visit(MoreVarDeclsVars MoreVarDeclsVars);
    public void visit(MoreVarDeclsArray MoreVarDeclsArray);
    public void visit(VarType VarType);
    public void visit(ErrorStmtVar ErrorStmtVar);
    public void visit(VarDeclVar VarDeclVar);
    public void visit(VarDeclArray VarDeclArray);
    public void visit(NoMoreConstVals NoMoreConstVals);
    public void visit(MoreConstValsExist MoreConstValsExist);
    public void visit(ConstBool ConstBool);
    public void visit(ConstChar ConstChar);
    public void visit(ConstNum ConstNum);
    public void visit(FormalConstDeclr FormalConstDeclr);
    public void visit(ConstType ConstType);
    public void visit(ConstDeclr ConstDeclr);
    public void visit(NoDeclarations NoDeclarations);
    public void visit(VarDeclarations VarDeclarations);
    public void visit(ConstDeclarations ConstDeclarations);
    public void visit(ProgName ProgName);
    public void visit(Program Program);

}
