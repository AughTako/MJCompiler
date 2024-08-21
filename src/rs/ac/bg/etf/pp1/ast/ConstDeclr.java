// generated with ast extension for cup
// version 0.8
// 21/7/2024 19:7:15


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclr implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private Type Type;
    private String constName;
    private ConstVals ConstVals;
    private MoreConstVals MoreConstVals;

    public ConstDeclr (Type Type, String constName, ConstVals ConstVals, MoreConstVals MoreConstVals) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.constName=constName;
        this.ConstVals=ConstVals;
        if(ConstVals!=null) ConstVals.setParent(this);
        this.MoreConstVals=MoreConstVals;
        if(MoreConstVals!=null) MoreConstVals.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getConstName() {
        return constName;
    }

    public void setConstName(String constName) {
        this.constName=constName;
    }

    public ConstVals getConstVals() {
        return ConstVals;
    }

    public void setConstVals(ConstVals ConstVals) {
        this.ConstVals=ConstVals;
    }

    public MoreConstVals getMoreConstVals() {
        return MoreConstVals;
    }

    public void setMoreConstVals(MoreConstVals MoreConstVals) {
        this.MoreConstVals=MoreConstVals;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(ConstVals!=null) ConstVals.accept(visitor);
        if(MoreConstVals!=null) MoreConstVals.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(ConstVals!=null) ConstVals.traverseTopDown(visitor);
        if(MoreConstVals!=null) MoreConstVals.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(ConstVals!=null) ConstVals.traverseBottomUp(visitor);
        if(MoreConstVals!=null) MoreConstVals.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDeclr(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+constName);
        buffer.append("\n");

        if(ConstVals!=null)
            buffer.append(ConstVals.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MoreConstVals!=null)
            buffer.append(MoreConstVals.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclr]");
        return buffer.toString();
    }
}
