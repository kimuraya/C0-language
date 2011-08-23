package c0.ast;

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
		
		this.printFileNameAndLine(depth, indentFlag);
	}
}
