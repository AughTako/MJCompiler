// generated with ast extension for cup
// version 0.8
// 19/7/2024 23:37:24


package rs.ac.bg.etf.pp1.ast;

public class ExprCommaDerived1 extends ExprComma {

    private ExprComma ExprComma;
    private Expr Expr;

    public ExprCommaDerived1 (ExprComma ExprComma, Expr Expr) {
        this.ExprComma=ExprComma;
        if(ExprComma!=null) ExprComma.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public ExprComma getExprComma() {
        return ExprComma;
    }

    public void setExprComma(ExprComma ExprComma) {
        this.ExprComma=ExprComma;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ExprComma!=null) ExprComma.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ExprComma!=null) ExprComma.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ExprComma!=null) ExprComma.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ExprCommaDerived1(\n");

        if(ExprComma!=null)
            buffer.append(ExprComma.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ExprCommaDerived1]");
        return buffer.toString();
    }
}
