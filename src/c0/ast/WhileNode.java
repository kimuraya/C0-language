package c0.ast;

//while文
public class WhileNode extends StatementNode {
	
	private ExpressionNode expression; //条件式
	private StatementNode statement; //文
	
	public WhileNode(Location loc, ExpressionNode expression,
			StatementNode statement) {
		super(loc);
		this.expression = expression;
		this.statement = statement;
	}

	@Override
	public void dump() {
		// TODO 自動生成されたメソッド・スタブ

	}

}
