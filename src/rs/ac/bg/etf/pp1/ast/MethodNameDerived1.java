// generated with ast extension for cup
// version 0.8
// 19/7/2024 23:37:24


package rs.ac.bg.etf.pp1.ast;

public class MethodNameDerived1 extends MethodName {

    private TypeList TypeList;
    private String I2;

    public MethodNameDerived1 (TypeList TypeList, String I2) {
        this.TypeList=TypeList;
        if(TypeList!=null) TypeList.setParent(this);
        this.I2=I2;
    }

    public TypeList getTypeList() {
        return TypeList;
    }

    public void setTypeList(TypeList TypeList) {
        this.TypeList=TypeList;
    }

    public String getI2() {
        return I2;
    }

    public void setI2(String I2) {
        this.I2=I2;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(TypeList!=null) TypeList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(TypeList!=null) TypeList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(TypeList!=null) TypeList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodNameDerived1(\n");

        if(TypeList!=null)
            buffer.append(TypeList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+I2);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodNameDerived1]");
        return buffer.toString();
    }
}
