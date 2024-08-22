// generated with ast extension for cup
// version 0.8
// 22/7/2024 18:13:43


package rs.ac.bg.etf.pp1.ast;

public class FactorNew extends Factor {

    private Type Type;
    private OptionBracketExpr OptionBracketExpr;

    public FactorNew (Type Type, OptionBracketExpr OptionBracketExpr) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.OptionBracketExpr=OptionBracketExpr;
        if(OptionBracketExpr!=null) OptionBracketExpr.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public OptionBracketExpr getOptionBracketExpr() {
        return OptionBracketExpr;
    }

    public void setOptionBracketExpr(OptionBracketExpr OptionBracketExpr) {
        this.OptionBracketExpr=OptionBracketExpr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(OptionBracketExpr!=null) OptionBracketExpr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(OptionBracketExpr!=null) OptionBracketExpr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(OptionBracketExpr!=null) OptionBracketExpr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FactorNew(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OptionBracketExpr!=null)
            buffer.append(OptionBracketExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactorNew]");
        return buffer.toString();
    }
}
