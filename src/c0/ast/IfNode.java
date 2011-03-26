package c0.ast;

//if-else文
public class IfNode extends StatementNode {
	
	private ExpressionNode expression; //条件式
	private StatementNode thenStatement; //then
	private StatementNode elseStatement; //else
	
	@Override
	public void dump() {
		// TODO 自動生成されたメソッド・スタブ
		
	}


}
