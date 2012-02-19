package c0.ast;

import c0.interpreter.Visitor;
import c0.util.NodeType;

/**
 * break文
 */
public class BreakNode extends StatementNode {
	
	public BreakNode(Location loc) {
		super(loc);
		nodeType = NodeType.BREAK_STATEMENT;
	}

	@Override
	public void dump(int depth, boolean indentFlag) {
		
		depth++;
		
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("BreakNode");
		
		this.printFileNameAndLine(depth, indentFlag);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
