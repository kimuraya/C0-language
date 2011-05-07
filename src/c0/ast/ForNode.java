package c0.ast;

//for文
public class ForNode extends StatementNode {
	
	private ExpressionNode initializeExpression; //初期化
	private ExpressionNode conditionalExpression; //条件
	private ExpressionNode updateExpression; //更新
	private StatementNode statement; //文
	
	public ForNode(Location loc, ExpressionNode initializeExpression,
			ExpressionNode conditionalExpression,
			ExpressionNode updateExpression, StatementNode statement) {
		super(loc);
		this.initializeExpression = initializeExpression;
		this.conditionalExpression = conditionalExpression;
		this.updateExpression = updateExpression;
		this.statement = statement;
	}

	@Override
	public void dump() {
		// TODO 自動生成されたメソッド・スタブ

	}


}
