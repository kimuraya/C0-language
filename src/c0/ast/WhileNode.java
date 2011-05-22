package c0.ast;

//while文
public class WhileNode extends StatementNode {
	
	private ExpressionNode conditionalExpression; //条件式
	private StatementNode bodyStatement; //文
	
	public WhileNode(Location loc, ExpressionNode conditionalExpression,
			StatementNode bodyStatement) {
		super(loc);
		this.conditionalExpression = conditionalExpression;
		this.bodyStatement = bodyStatement;
	}

	@Override
	public void dump() {
		// TODO 自動生成されたメソッド・スタブ

	}

}
