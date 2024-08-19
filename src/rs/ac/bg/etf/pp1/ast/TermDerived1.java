// generated with ast extension for cup
// version 0.8
// 19/7/2024 23:37:24


package rs.ac.bg.etf.pp1.ast;

public class TermDerived1 extends Term {

    private MulOpFactorOptions MulOpFactorOptions;

    public TermDerived1 (MulOpFactorOptions MulOpFactorOptions) {
        this.MulOpFactorOptions=MulOpFactorOptions;
        if(MulOpFactorOptions!=null) MulOpFactorOptions.setParent(this);
    }

    public MulOpFactorOptions getMulOpFactorOptions() {
        return MulOpFactorOptions;
    }

    public void setMulOpFactorOptions(MulOpFactorOptions MulOpFactorOptions) {
        this.MulOpFactorOptions=MulOpFactorOptions;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MulOpFactorOptions!=null) MulOpFactorOptions.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MulOpFactorOptions!=null) MulOpFactorOptions.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MulOpFactorOptions!=null) MulOpFactorOptions.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("TermDerived1(\n");

        if(MulOpFactorOptions!=null)
            buffer.append(MulOpFactorOptions.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [TermDerived1]");
        return buffer.toString();
    }
}
