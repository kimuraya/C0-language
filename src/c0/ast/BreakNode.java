package c0.ast;

import c0.util.NodeType;

//break文
public class BreakNode extends StatementNode {
	
	public BreakNode(Location loc) {
		super(loc);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void dump(int depth, boolean indentFlag) {
		
		depth++;
		
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("BreakNode");
	}
}
