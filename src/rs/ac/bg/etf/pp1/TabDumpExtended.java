package rs.ac.bg.etf.pp1;

import rs.etf.pp1.symboltable.concepts.*;
import rs.etf.pp1.symboltable.visitors.DumpSymbolTableVisitor;

public class TabDumpExtended extends DumpSymbolTableVisitor{
	@Override
	public void visitStructNode(Struct structToVisit) {
		if(structToVisit.getKind() == Struct.Array && structToVisit.getElemType().getKind() == Struct.Array) {
			output.append("Matrix of ");
			switch (structToVisit.getElemType().getElemType().getKind()) {
			case Struct.Int:
				output.append("int");
				break;
			case Struct.Char:
				output.append("char");
				break;
			case Struct.Bool:
				output.append("bool");
				break;
			}
		}
		if (structToVisit.getKind() == Struct.Array && structToVisit.getElemType().getKind() == Struct.Bool) {
			output.append("Arr of bool");
			return;
		}
		

		switch (structToVisit.getKind()) {
		case Struct.Bool:
			output.append("bool");
			break;
		default:
			if(structToVisit.getKind() == Struct.Array && structToVisit.getElemType().getKind() == Struct.Array)
				break;
			super.visitStructNode(structToVisit);
		}
	}
}
