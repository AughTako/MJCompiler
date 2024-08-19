// generated with ast extension for cup
// version 0.8
// 19/7/2024 23:37:24


package rs.ac.bg.etf.pp1.ast;

public class ActualParamsMaybeDerived1 extends ActualParamsMaybe {

    private ActualParams ActualParams;

    public ActualParamsMaybeDerived1 (ActualParams ActualParams) {
        this.ActualParams=ActualParams;
        if(ActualParams!=null) ActualParams.setParent(this);
    }

    public ActualParams getActualParams() {
        return ActualParams;
    }

    public void setActualParams(ActualParams ActualParams) {
        this.ActualParams=ActualParams;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ActualParams!=null) ActualParams.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ActualParams!=null) ActualParams.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ActualParams!=null) ActualParams.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ActualParamsMaybeDerived1(\n");

        if(ActualParams!=null)
            buffer.append(ActualParams.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ActualParamsMaybeDerived1]");
        return buffer.toString();
    }
}
