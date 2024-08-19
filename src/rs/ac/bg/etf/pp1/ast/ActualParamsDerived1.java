// generated with ast extension for cup
// version 0.8
// 19/7/2024 23:37:24


package rs.ac.bg.etf.pp1.ast;

public class ActualParamsDerived1 extends ActualParams {

    private ExprComma ExprComma;

    public ActualParamsDerived1 (ExprComma ExprComma) {
        this.ExprComma=ExprComma;
        if(ExprComma!=null) ExprComma.setParent(this);
    }

    public ExprComma getExprComma() {
        return ExprComma;
    }

    public void setExprComma(ExprComma ExprComma) {
        this.ExprComma=ExprComma;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ExprComma!=null) ExprComma.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ExprComma!=null) ExprComma.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ExprComma!=null) ExprComma.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ActualParamsDerived1(\n");

        if(ExprComma!=null)
            buffer.append(ExprComma.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ActualParamsDerived1]");
        return buffer.toString();
    }
}
