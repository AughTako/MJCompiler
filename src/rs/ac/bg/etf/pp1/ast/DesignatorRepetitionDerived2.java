// generated with ast extension for cup
// version 0.8
// 19/7/2024 23:37:24


package rs.ac.bg.etf.pp1.ast;

public class DesignatorRepetitionDerived2 extends DesignatorRepetition {

    private DesignatorRepetition DesignatorRepetition;
    private Expr Expr;

    public DesignatorRepetitionDerived2 (DesignatorRepetition DesignatorRepetition, Expr Expr) {
        this.DesignatorRepetition=DesignatorRepetition;
        if(DesignatorRepetition!=null) DesignatorRepetition.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public DesignatorRepetition getDesignatorRepetition() {
        return DesignatorRepetition;
    }

    public void setDesignatorRepetition(DesignatorRepetition DesignatorRepetition) {
        this.DesignatorRepetition=DesignatorRepetition;
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
        if(DesignatorRepetition!=null) DesignatorRepetition.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorRepetition!=null) DesignatorRepetition.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorRepetition!=null) DesignatorRepetition.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorRepetitionDerived2(\n");

        if(DesignatorRepetition!=null)
            buffer.append(DesignatorRepetition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorRepetitionDerived2]");
        return buffer.toString();
    }
}
