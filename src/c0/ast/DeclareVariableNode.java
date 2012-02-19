package c0.ast;

import c0.interpreter.Visitor;
import c0.util.DataType;

/**
 * 変数宣言
 * データ型 単純宣言子 ['=' 式] ';'
 * データ型 配列宣言子 ';'
 */
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
	
	
	public DataTypeNode getDataType() {
		return dataType;
	}


	public void setDataType(DataTypeNode dataType) {
		this.dataType = dataType;
	}


	public IdentifierNode getIdentifier() {
		return identifier;
	}


	public void setIdentifier(IdentifierNode identifier) {
		this.identifier = identifier;
	}


	public ExpressionNode getExpression() {
		return expression;
	}


	public void setExpression(ExpressionNode expression) {
		this.expression = expression;
	}


	@Override
	public void dump(int depth, boolean indentFlag) {
		
		depth++;
		
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("DeclareVariableNode");
		
		this.dataType.dump(depth, true);
		this.identifier.dump(depth, true);
		
		if(this.expression != null) {
			this.expression.dump(depth, true);
		}
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
