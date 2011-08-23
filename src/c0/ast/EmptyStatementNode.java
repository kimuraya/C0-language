package c0.ast;

//空文
public class EmptyStatementNode extends StatementNode {
	
	public EmptyStatementNode(Location loc) {
		super(loc);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void dump(int depth, boolean indentFlag) {
		
		depth++;
		
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("EmptyStatementNode");
		
		this.printFileNameAndLine(depth, indentFlag);
	}
}
