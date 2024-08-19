// generated with ast extension for cup
// version 0.8
// 19/7/2024 23:37:24


package rs.ac.bg.etf.pp1.ast;

public class DesignatorRepetitionDerived1 extends DesignatorRepetition {

    private DesignatorRepetition DesignatorRepetition;
    private String I2;

    public DesignatorRepetitionDerived1 (DesignatorRepetition DesignatorRepetition, String I2) {
        this.DesignatorRepetition=DesignatorRepetition;
        if(DesignatorRepetition!=null) DesignatorRepetition.setParent(this);
        this.I2=I2;
    }

    public DesignatorRepetition getDesignatorRepetition() {
        return DesignatorRepetition;
    }

    public void setDesignatorRepetition(DesignatorRepetition DesignatorRepetition) {
        this.DesignatorRepetition=DesignatorRepetition;
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
        if(DesignatorRepetition!=null) DesignatorRepetition.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorRepetition!=null) DesignatorRepetition.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorRepetition!=null) DesignatorRepetition.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorRepetitionDerived1(\n");

        if(DesignatorRepetition!=null)
            buffer.append(DesignatorRepetition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+I2);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorRepetitionDerived1]");
        return buffer.toString();
    }
}
