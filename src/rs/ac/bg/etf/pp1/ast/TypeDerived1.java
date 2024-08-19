// generated with ast extension for cup
// version 0.8
// 19/7/2024 23:37:24


package rs.ac.bg.etf.pp1.ast;

public class TypeDerived1 extends Type {

    private String I1;
    private IdentList IdentList;

    public TypeDerived1 (String I1, IdentList IdentList) {
        this.I1=I1;
        this.IdentList=IdentList;
        if(IdentList!=null) IdentList.setParent(this);
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
    }

    public IdentList getIdentList() {
        return IdentList;
    }

    public void setIdentList(IdentList IdentList) {
        this.IdentList=IdentList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(IdentList!=null) IdentList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(IdentList!=null) IdentList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(IdentList!=null) IdentList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("TypeDerived1(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(IdentList!=null)
            buffer.append(IdentList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [TypeDerived1]");
        return buffer.toString();
    }
}
