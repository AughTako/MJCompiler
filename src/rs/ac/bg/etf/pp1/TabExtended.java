package rs.ac.bg.etf.pp1;

import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.*;

public class TabExtended extends Tab{
	public static final Struct boolType = new Struct(Struct.Bool);

	public static void init() {
		Tab.init();
		Tab.currentScope.addToLocals(new Obj(Obj.Type, "bool", boolType));
	}

	public static void dump() {
		Tab.dump(new TabDumpExtended());
	}
}
