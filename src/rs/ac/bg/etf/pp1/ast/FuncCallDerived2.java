// generated with ast extension for cup
// version 0.8
// 19/7/2024 23:37:24


package rs.ac.bg.etf.pp1.ast;

public class FuncCallDerived2 extends FuncCall {

    private FuncCallMaybe FuncCallMaybe;

    public FuncCallDerived2 (FuncCallMaybe FuncCallMaybe) {
        this.FuncCallMaybe=FuncCallMaybe;
        if(FuncCallMaybe!=null) FuncCallMaybe.setParent(this);
    }

    public FuncCallMaybe getFuncCallMaybe() {
        return FuncCallMaybe;
    }

    public void setFuncCallMaybe(FuncCallMaybe FuncCallMaybe) {
        this.FuncCallMaybe=FuncCallMaybe;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FuncCallMaybe!=null) FuncCallMaybe.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FuncCallMaybe!=null) FuncCallMaybe.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FuncCallMaybe!=null) FuncCallMaybe.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FuncCallDerived2(\n");

        if(FuncCallMaybe!=null)
            buffer.append(FuncCallMaybe.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FuncCallDerived2]");
        return buffer.toString();
    }
}
