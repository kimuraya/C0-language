package c0.ast;

import c0.util.DataType;

//変数宣言
//データ型 単純宣言子 ['=' 式] ';'
//|データ型 配列宣言子 ';'
public class DeclareVariableNode extends StatementNode {
	
	private DataType dataType; //Nodeにするかどうか検討する
	private IdentifierNode identifier;
	private ExpressionNode expression; //初期化式。識別子の種類が配列だった場合、要素数を表す	
	
	//データ型 単純宣言子 ['=' 式] ';'
	//|データ型 配列宣言子 ';'
	public DeclareVariableNode(Location loc, DataType dataType, IdentifierNode identifier,
			ExpressionNode expression) {
		super(loc);
		this.dataType = dataType;
		this.identifier = identifier;
		this.expression = expression;		
	}	
	
	@Override
	public void dump() {
		// TODO 自動生成されたメソッド・スタブ

	}

}
