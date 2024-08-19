// generated with ast extension for cup
// version 0.8
// 19/7/2024 23:37:24


package rs.ac.bg.etf.pp1.ast;

public class ExprDerived1 extends Expr {

    private MinOpOptions MinOpOptions;
    private AddOpOptions AddOpOptions;

    public ExprDerived1 (MinOpOptions MinOpOptions, AddOpOptions AddOpOptions) {
        this.MinOpOptions=MinOpOptions;
        if(MinOpOptions!=null) MinOpOptions.setParent(this);
        this.AddOpOptions=AddOpOptions;
        if(AddOpOptions!=null) AddOpOptions.setParent(this);
    }

    public MinOpOptions getMinOpOptions() {
        return MinOpOptions;
    }

    public void setMinOpOptions(MinOpOptions MinOpOptions) {
        this.MinOpOptions=MinOpOptions;
    }

    public AddOpOptions getAddOpOptions() {
        return AddOpOptions;
    }

    public void setAddOpOptions(AddOpOptions AddOpOptions) {
        this.AddOpOptions=AddOpOptions;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MinOpOptions!=null) MinOpOptions.accept(visitor);
        if(AddOpOptions!=null) AddOpOptions.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MinOpOptions!=null) MinOpOptions.traverseTopDown(visitor);
        if(AddOpOptions!=null) AddOpOptions.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MinOpOptions!=null) MinOpOptions.traverseBottomUp(visitor);
        if(AddOpOptions!=null) AddOpOptions.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ExprDerived1(\n");

        if(MinOpOptions!=null)
            buffer.append(MinOpOptions.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AddOpOptions!=null)
            buffer.append(AddOpOptions.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ExprDerived1]");
        return buffer.toString();
    }
}
