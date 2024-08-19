// generated with ast extension for cup
// version 0.8
// 19/7/2024 23:37:24


package rs.ac.bg.etf.pp1.ast;

public class FuncCallDerived1 extends FuncCall {

    private Designator Designator;
    private ActualParamsMaybe ActualParamsMaybe;

    public FuncCallDerived1 (Designator Designator, ActualParamsMaybe ActualParamsMaybe) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.ActualParamsMaybe=ActualParamsMaybe;
        if(ActualParamsMaybe!=null) ActualParamsMaybe.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public ActualParamsMaybe getActualParamsMaybe() {
        return ActualParamsMaybe;
    }

    public void setActualParamsMaybe(ActualParamsMaybe ActualParamsMaybe) {
        this.ActualParamsMaybe=ActualParamsMaybe;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(ActualParamsMaybe!=null) ActualParamsMaybe.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(ActualParamsMaybe!=null) ActualParamsMaybe.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(ActualParamsMaybe!=null) ActualParamsMaybe.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FuncCallDerived1(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActualParamsMaybe!=null)
            buffer.append(ActualParamsMaybe.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FuncCallDerived1]");
        return buffer.toString();
    }
}
