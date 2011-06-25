package c0.ast;

import c0.util.DataType;

//変数宣言
//データ型 単純宣言子 ['=' 式] ';'
//|データ型 配列宣言子 ';'
public class DeclareVariableNode extends StatementNode {
	
	private DataTypeNode dataType; //データ型
	private IdentifierNode identifier; //識別子
	private ExpressionNode expression; //初期化式
	
	//データ型 単純宣言子 ['=' 式] ';'
	//|データ型 配列宣言子 ';'
	public DeclareVariableNode(Location loc, DataTypeNode dataType, IdentifierNode identifier,
			ExpressionNode expression) {
		super(loc);
		this.dataType = dataType;
		this.identifier = identifier;
		this.expression = expression;		
	}	
	
	
	@Override
	public void dump(int depth) {
		
		depth++;
		this.printIndent(depth);
		dataType.dump(depth);
		identifier.dump(depth);
		
		if(expression != null) {
			expression.dump(depth);
		}
	}

}
