// generated with ast extension for cup
// version 0.8
// 19/7/2024 23:37:24


package rs.ac.bg.etf.pp1.ast;

public class OrConditionDerived1 extends OrCondition {

    private OrCondition OrCondition;
    private AndCondition AndCondition;

    public OrConditionDerived1 (OrCondition OrCondition, AndCondition AndCondition) {
        this.OrCondition=OrCondition;
        if(OrCondition!=null) OrCondition.setParent(this);
        this.AndCondition=AndCondition;
        if(AndCondition!=null) AndCondition.setParent(this);
    }

    public OrCondition getOrCondition() {
        return OrCondition;
    }

    public void setOrCondition(OrCondition OrCondition) {
        this.OrCondition=OrCondition;
    }

    public AndCondition getAndCondition() {
        return AndCondition;
    }

    public void setAndCondition(AndCondition AndCondition) {
        this.AndCondition=AndCondition;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(OrCondition!=null) OrCondition.accept(visitor);
        if(AndCondition!=null) AndCondition.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OrCondition!=null) OrCondition.traverseTopDown(visitor);
        if(AndCondition!=null) AndCondition.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OrCondition!=null) OrCondition.traverseBottomUp(visitor);
        if(AndCondition!=null) AndCondition.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("OrConditionDerived1(\n");

        if(OrCondition!=null)
            buffer.append(OrCondition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AndCondition!=null)
            buffer.append(AndCondition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [OrConditionDerived1]");
        return buffer.toString();
    }
}
