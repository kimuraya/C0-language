package c0.ast;

//変数宣言
//データ型 単純宣言子 ['=' 式] ';'
//|データ型 配列宣言子 ';'
public class DeclareVariableNode extends StatementNode {
	
	IdentifierNode identifier;
	ExpressionNode expression;
	
	public DeclareVariableNode(Location loc, IdentifierNode identifier,
			ExpressionNode expression) {
		super(loc);
		this.identifier = identifier;
		this.expression = expression;
	}

	@Override
	public void dump() {
		// TODO 自動生成されたメソッド・スタブ

	}

}
