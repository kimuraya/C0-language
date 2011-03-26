package c0.ast;

//for文
public class ForNode extends StatementNode {
	
	private ExpressionNode initializeExpression; //初期化
	private ExpressionNode conditionalExpression; //条件
	private ExpressionNode updateExpression; //更新
	private StatementNode statement; //文
	
	@Override
	public void dump() {
		// TODO 自動生成されたメソッド・スタブ

	}


}
