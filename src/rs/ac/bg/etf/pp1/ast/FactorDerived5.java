// generated with ast extension for cup
// version 0.8
// 19/7/2024 23:37:24


package rs.ac.bg.etf.pp1.ast;

public class FactorDerived5 extends Factor {

    private Type Type;
    private ListOrMatrix ListOrMatrix;

    public FactorDerived5 (Type Type, ListOrMatrix ListOrMatrix) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.ListOrMatrix=ListOrMatrix;
        if(ListOrMatrix!=null) ListOrMatrix.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public ListOrMatrix getListOrMatrix() {
        return ListOrMatrix;
    }

    public void setListOrMatrix(ListOrMatrix ListOrMatrix) {
        this.ListOrMatrix=ListOrMatrix;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(ListOrMatrix!=null) ListOrMatrix.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(ListOrMatrix!=null) ListOrMatrix.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(ListOrMatrix!=null) ListOrMatrix.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FactorDerived5(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ListOrMatrix!=null)
            buffer.append(ListOrMatrix.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactorDerived5]");
        return buffer.toString();
    }
}
