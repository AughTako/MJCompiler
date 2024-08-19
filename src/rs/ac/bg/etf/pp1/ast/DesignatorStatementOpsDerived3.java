// generated with ast extension for cup
// version 0.8
// 19/7/2024 23:37:24


package rs.ac.bg.etf.pp1.ast;

public class DesignatorStatementOpsDerived3 extends DesignatorStatementOps {

    public DesignatorStatementOpsDerived3 () {
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
        buffer.append("DesignatorStatementOpsDerived3(\n");

        buffer.append(tab);
        buffer.append(") [DesignatorStatementOpsDerived3]");
        return buffer.toString();
    }
}
