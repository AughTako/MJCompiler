// generated with ast extension for cup
// version 0.8
// 19/7/2024 23:37:24


package rs.ac.bg.etf.pp1.ast;

public class StatementDerived8 extends Statement {

    private ForEnter ForEnter;
    private ForDesignator ForDesignator;
    private Statement Statement;

    public StatementDerived8 (ForEnter ForEnter, ForDesignator ForDesignator, Statement Statement) {
        this.ForEnter=ForEnter;
        if(ForEnter!=null) ForEnter.setParent(this);
        this.ForDesignator=ForDesignator;
        if(ForDesignator!=null) ForDesignator.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public ForEnter getForEnter() {
        return ForEnter;
    }

    public void setForEnter(ForEnter ForEnter) {
        this.ForEnter=ForEnter;
    }

    public ForDesignator getForDesignator() {
        return ForDesignator;
    }

    public void setForDesignator(ForDesignator ForDesignator) {
        this.ForDesignator=ForDesignator;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ForEnter!=null) ForEnter.accept(visitor);
        if(ForDesignator!=null) ForDesignator.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ForEnter!=null) ForEnter.traverseTopDown(visitor);
        if(ForDesignator!=null) ForDesignator.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ForEnter!=null) ForEnter.traverseBottomUp(visitor);
        if(ForDesignator!=null) ForDesignator.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementDerived8(\n");

        if(ForEnter!=null)
            buffer.append(ForEnter.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForDesignator!=null)
            buffer.append(ForDesignator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementDerived8]");
        return buffer.toString();
    }
}
