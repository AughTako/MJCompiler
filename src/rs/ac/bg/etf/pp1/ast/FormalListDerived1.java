// generated with ast extension for cup
// version 0.8
// 19/7/2024 23:37:24


package rs.ac.bg.etf.pp1.ast;

public class FormalListDerived1 extends FormalList {

    private FormalList FormalList;
    private FormalListNN FormalListNN;

    public FormalListDerived1 (FormalList FormalList, FormalListNN FormalListNN) {
        this.FormalList=FormalList;
        if(FormalList!=null) FormalList.setParent(this);
        this.FormalListNN=FormalListNN;
        if(FormalListNN!=null) FormalListNN.setParent(this);
    }

    public FormalList getFormalList() {
        return FormalList;
    }

    public void setFormalList(FormalList FormalList) {
        this.FormalList=FormalList;
    }

    public FormalListNN getFormalListNN() {
        return FormalListNN;
    }

    public void setFormalListNN(FormalListNN FormalListNN) {
        this.FormalListNN=FormalListNN;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FormalList!=null) FormalList.accept(visitor);
        if(FormalListNN!=null) FormalListNN.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormalList!=null) FormalList.traverseTopDown(visitor);
        if(FormalListNN!=null) FormalListNN.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormalList!=null) FormalList.traverseBottomUp(visitor);
        if(FormalListNN!=null) FormalListNN.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormalListDerived1(\n");

        if(FormalList!=null)
            buffer.append(FormalList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormalListNN!=null)
            buffer.append(FormalListNN.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormalListDerived1]");
        return buffer.toString();
    }
}
