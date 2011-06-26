package c0.ast;

//for文
public class ForNode extends StatementNode {
	
	private ExpressionNode initializeExpression; //初期化
	private ExpressionNode conditionalExpression; //条件
	private ExpressionNode updateExpression; //更新
	private StatementNode bodyStatement; //文
	
	public ForNode(Location loc, ExpressionNode initializeExpression,
			ExpressionNode conditionalExpression,
			ExpressionNode updateExpression, StatementNode bodyStatement) {
		super(loc);
		this.initializeExpression = initializeExpression;
		this.conditionalExpression = conditionalExpression;
		this.updateExpression = updateExpression;
		this.bodyStatement = bodyStatement;
	}

	@Override
	public void dump(int depth, boolean indentFlag) {
		
		depth++;
		
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("ForNode");
	}
}
