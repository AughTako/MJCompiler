// generated with ast extension for cup
// version 0.8
// 22/7/2024 18:13:43


package rs.ac.bg.etf.pp1.ast;

public class RelOpG extends RelOp {

    public RelOpG () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("RelOpG(\n");

        buffer.append(tab);
        buffer.append(") [RelOpG]");
        return buffer.toString();
    }
}
