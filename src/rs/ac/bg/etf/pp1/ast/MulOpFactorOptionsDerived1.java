// generated with ast extension for cup
// version 0.8
// 19/7/2024 23:37:24


package rs.ac.bg.etf.pp1.ast;

public class MulOpFactorOptionsDerived1 extends MulOpFactorOptions {

    private MulOpFactorOptions MulOpFactorOptions;
    private MulOp MulOp;
    private Factor Factor;

    public MulOpFactorOptionsDerived1 (MulOpFactorOptions MulOpFactorOptions, MulOp MulOp, Factor Factor) {
        this.MulOpFactorOptions=MulOpFactorOptions;
        if(MulOpFactorOptions!=null) MulOpFactorOptions.setParent(this);
        this.MulOp=MulOp;
        if(MulOp!=null) MulOp.setParent(this);
        this.Factor=Factor;
        if(Factor!=null) Factor.setParent(this);
    }

    public MulOpFactorOptions getMulOpFactorOptions() {
        return MulOpFactorOptions;
    }

    public void setMulOpFactorOptions(MulOpFactorOptions MulOpFactorOptions) {
        this.MulOpFactorOptions=MulOpFactorOptions;
    }

    public MulOp getMulOp() {
        return MulOp;
    }

    public void setMulOp(MulOp MulOp) {
        this.MulOp=MulOp;
    }

    public Factor getFactor() {
        return Factor;
    }

    public void setFactor(Factor Factor) {
        this.Factor=Factor;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MulOpFactorOptions!=null) MulOpFactorOptions.accept(visitor);
        if(MulOp!=null) MulOp.accept(visitor);
        if(Factor!=null) Factor.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MulOpFactorOptions!=null) MulOpFactorOptions.traverseTopDown(visitor);
        if(MulOp!=null) MulOp.traverseTopDown(visitor);
        if(Factor!=null) Factor.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MulOpFactorOptions!=null) MulOpFactorOptions.traverseBottomUp(visitor);
        if(MulOp!=null) MulOp.traverseBottomUp(visitor);
        if(Factor!=null) Factor.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MulOpFactorOptionsDerived1(\n");

        if(MulOpFactorOptions!=null)
            buffer.append(MulOpFactorOptions.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MulOp!=null)
            buffer.append(MulOp.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Factor!=null)
            buffer.append(Factor.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MulOpFactorOptionsDerived1]");
        return buffer.toString();
    }
}
