// generated with ast extension for cup
// version 0.8
// 19/7/2024 23:37:24


package rs.ac.bg.etf.pp1.ast;

public class AndFactorDerived1 extends AndFactor {

    private AndFactor AndFactor;
    private ConditionFactor ConditionFactor;

    public AndFactorDerived1 (AndFactor AndFactor, ConditionFactor ConditionFactor) {
        this.AndFactor=AndFactor;
        if(AndFactor!=null) AndFactor.setParent(this);
        this.ConditionFactor=ConditionFactor;
        if(ConditionFactor!=null) ConditionFactor.setParent(this);
    }

    public AndFactor getAndFactor() {
        return AndFactor;
    }

    public void setAndFactor(AndFactor AndFactor) {
        this.AndFactor=AndFactor;
    }

    public ConditionFactor getConditionFactor() {
        return ConditionFactor;
    }

    public void setConditionFactor(ConditionFactor ConditionFactor) {
        this.ConditionFactor=ConditionFactor;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AndFactor!=null) AndFactor.accept(visitor);
        if(ConditionFactor!=null) ConditionFactor.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AndFactor!=null) AndFactor.traverseTopDown(visitor);
        if(ConditionFactor!=null) ConditionFactor.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AndFactor!=null) AndFactor.traverseBottomUp(visitor);
        if(ConditionFactor!=null) ConditionFactor.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AndFactorDerived1(\n");

        if(AndFactor!=null)
            buffer.append(AndFactor.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConditionFactor!=null)
            buffer.append(ConditionFactor.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AndFactorDerived1]");
        return buffer.toString();
    }
}
