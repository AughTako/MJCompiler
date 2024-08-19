// generated with ast extension for cup
// version 0.8
// 19/7/2024 23:37:24


package rs.ac.bg.etf.pp1.ast;

public class DesignatorStatementOpsDerived2 extends DesignatorStatementOps {

    private ActualParamsMaybe ActualParamsMaybe;

    public DesignatorStatementOpsDerived2 (ActualParamsMaybe ActualParamsMaybe) {
        this.ActualParamsMaybe=ActualParamsMaybe;
        if(ActualParamsMaybe!=null) ActualParamsMaybe.setParent(this);
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
        if(ActualParamsMaybe!=null) ActualParamsMaybe.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ActualParamsMaybe!=null) ActualParamsMaybe.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ActualParamsMaybe!=null) ActualParamsMaybe.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorStatementOpsDerived2(\n");

        if(ActualParamsMaybe!=null)
            buffer.append(ActualParamsMaybe.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorStatementOpsDerived2]");
        return buffer.toString();
    }
}
