// generated with ast extension for cup
// version 0.8
// 19/7/2024 23:37:24


package rs.ac.bg.etf.pp1.ast;

public class ForEnterDerived1 extends ForEnter {

    private ForDesignator ForDesignator;
    private ConditionOptions ConditionOptions;

    public ForEnterDerived1 (ForDesignator ForDesignator, ConditionOptions ConditionOptions) {
        this.ForDesignator=ForDesignator;
        if(ForDesignator!=null) ForDesignator.setParent(this);
        this.ConditionOptions=ConditionOptions;
        if(ConditionOptions!=null) ConditionOptions.setParent(this);
    }

    public ForDesignator getForDesignator() {
        return ForDesignator;
    }

    public void setForDesignator(ForDesignator ForDesignator) {
        this.ForDesignator=ForDesignator;
    }

    public ConditionOptions getConditionOptions() {
        return ConditionOptions;
    }

    public void setConditionOptions(ConditionOptions ConditionOptions) {
        this.ConditionOptions=ConditionOptions;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ForDesignator!=null) ForDesignator.accept(visitor);
        if(ConditionOptions!=null) ConditionOptions.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ForDesignator!=null) ForDesignator.traverseTopDown(visitor);
        if(ConditionOptions!=null) ConditionOptions.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ForDesignator!=null) ForDesignator.traverseBottomUp(visitor);
        if(ConditionOptions!=null) ConditionOptions.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ForEnterDerived1(\n");

        if(ForDesignator!=null)
            buffer.append(ForDesignator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConditionOptions!=null)
            buffer.append(ConditionOptions.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ForEnterDerived1]");
        return buffer.toString();
    }
}
