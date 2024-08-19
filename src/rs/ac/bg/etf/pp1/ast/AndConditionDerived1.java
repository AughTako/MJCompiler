// generated with ast extension for cup
// version 0.8
// 19/7/2024 23:37:24


package rs.ac.bg.etf.pp1.ast;

public class AndConditionDerived1 extends AndCondition {

    private ConditionFactor ConditionFactor;
    private AndFactor AndFactor;

    public AndConditionDerived1 (ConditionFactor ConditionFactor, AndFactor AndFactor) {
        this.ConditionFactor=ConditionFactor;
        if(ConditionFactor!=null) ConditionFactor.setParent(this);
        this.AndFactor=AndFactor;
        if(AndFactor!=null) AndFactor.setParent(this);
    }

    public ConditionFactor getConditionFactor() {
        return ConditionFactor;
    }

    public void setConditionFactor(ConditionFactor ConditionFactor) {
        this.ConditionFactor=ConditionFactor;
    }

    public AndFactor getAndFactor() {
        return AndFactor;
    }

    public void setAndFactor(AndFactor AndFactor) {
        this.AndFactor=AndFactor;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConditionFactor!=null) ConditionFactor.accept(visitor);
        if(AndFactor!=null) AndFactor.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConditionFactor!=null) ConditionFactor.traverseTopDown(visitor);
        if(AndFactor!=null) AndFactor.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConditionFactor!=null) ConditionFactor.traverseBottomUp(visitor);
        if(AndFactor!=null) AndFactor.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AndConditionDerived1(\n");

        if(ConditionFactor!=null)
            buffer.append(ConditionFactor.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AndFactor!=null)
            buffer.append(AndFactor.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AndConditionDerived1]");
        return buffer.toString();
    }
}
