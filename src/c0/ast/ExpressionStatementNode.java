package c0.ast;

//式文
public class ExpressionStatementNode extends StatementNode {
	
	private ExpressionNode expression; //式
	
	public ExpressionStatementNode(Location loc, ExpressionNode expression) {
		super(loc);
		this.expression = expression;
	}

	@Override
	public void dump(int depth) {
		// TODO 自動生成されたメソッド・スタブ

	}


}
