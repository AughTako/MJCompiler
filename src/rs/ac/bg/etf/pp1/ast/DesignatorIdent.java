// generated with ast extension for cup
// version 0.8
// 23/7/2024 15:7:46


package rs.ac.bg.etf.pp1.ast;

public class DesignatorIdent extends Designator {

    private String desName;
    private OptionBracketExpr OptionBracketExpr;

    public DesignatorIdent (String desName, OptionBracketExpr OptionBracketExpr) {
        this.desName=desName;
        this.OptionBracketExpr=OptionBracketExpr;
        if(OptionBracketExpr!=null) OptionBracketExpr.setParent(this);
    }

    public String getDesName() {
        return desName;
    }

    public void setDesName(String desName) {
        this.desName=desName;
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
        if(OptionBracketExpr!=null) OptionBracketExpr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OptionBracketExpr!=null) OptionBracketExpr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OptionBracketExpr!=null) OptionBracketExpr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorIdent(\n");

        buffer.append(" "+tab+desName);
        buffer.append("\n");

        if(OptionBracketExpr!=null)
            buffer.append(OptionBracketExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorIdent]");
        return buffer.toString();
    }
}
