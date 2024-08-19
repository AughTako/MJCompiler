// generated with ast extension for cup
// version 0.8
// 19/7/2024 21:39:14


package rs.ac.bg.etf.pp1.ast;

public class VarDeclListDerived1 extends VarDeclList {

    private VarDeclList VarDeclList;
    private VarDeclListNN VarDeclListNN;

    public VarDeclListDerived1 (VarDeclList VarDeclList, VarDeclListNN VarDeclListNN) {
        this.VarDeclList=VarDeclList;
        if(VarDeclList!=null) VarDeclList.setParent(this);
        this.VarDeclListNN=VarDeclListNN;
        if(VarDeclListNN!=null) VarDeclListNN.setParent(this);
    }

    public VarDeclList getVarDeclList() {
        return VarDeclList;
    }

    public void setVarDeclList(VarDeclList VarDeclList) {
        this.VarDeclList=VarDeclList;
    }

    public VarDeclListNN getVarDeclListNN() {
        return VarDeclListNN;
    }

    public void setVarDeclListNN(VarDeclListNN VarDeclListNN) {
        this.VarDeclListNN=VarDeclListNN;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDeclList!=null) VarDeclList.accept(visitor);
        if(VarDeclListNN!=null) VarDeclListNN.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclList!=null) VarDeclList.traverseTopDown(visitor);
        if(VarDeclListNN!=null) VarDeclListNN.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclList!=null) VarDeclList.traverseBottomUp(visitor);
        if(VarDeclListNN!=null) VarDeclListNN.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclListDerived1(\n");

        if(VarDeclList!=null)
            buffer.append(VarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclListNN!=null)
            buffer.append(VarDeclListNN.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclListDerived1]");
        return buffer.toString();
    }
}
