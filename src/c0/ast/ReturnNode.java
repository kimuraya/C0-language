package c0.ast;

//return文
public class ReturnNode extends StatementNode {
	
	private ExpressionNode expression; //式
	
	public ReturnNode(Location loc, ExpressionNode expression) {
		super(loc);
		this.expression = expression;
	}

	@Override
	public void dump(int depth) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
