// generated with ast extension for cup
// version 0.8
// 19/7/2024 23:37:24


package rs.ac.bg.etf.pp1.ast;

public class DesignatorStatementDerived1 extends DesignatorStatement {

    private Designator Designator;
    private DesignatorStatementOps DesignatorStatementOps;

    public DesignatorStatementDerived1 (Designator Designator, DesignatorStatementOps DesignatorStatementOps) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.DesignatorStatementOps=DesignatorStatementOps;
        if(DesignatorStatementOps!=null) DesignatorStatementOps.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public DesignatorStatementOps getDesignatorStatementOps() {
        return DesignatorStatementOps;
    }

    public void setDesignatorStatementOps(DesignatorStatementOps DesignatorStatementOps) {
        this.DesignatorStatementOps=DesignatorStatementOps;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(DesignatorStatementOps!=null) DesignatorStatementOps.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(DesignatorStatementOps!=null) DesignatorStatementOps.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(DesignatorStatementOps!=null) DesignatorStatementOps.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorStatementDerived1(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorStatementOps!=null)
            buffer.append(DesignatorStatementOps.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorStatementDerived1]");
        return buffer.toString();
    }
}
