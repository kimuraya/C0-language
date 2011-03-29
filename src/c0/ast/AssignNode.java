package c0.ast;

import c0.util.NodeType;

//"="
public class AssignNode extends ExpressionNode {

	private IdentifierNode leftValue; //左辺値
	private ExpressionStatementNode expression; //式
	
	public AssignNode(IdentifierNode leftValue, ExpressionStatementNode expression) {
		super();
		this.nodeType = NodeType.ASSIGN;
		this.leftValue = leftValue;
		this.expression = expression;
	}
	
	@Override
	public void dump() {
		// TODO 自動生成されたメソッド・スタブ

	}
}
