package c0.ast;

import c0.interpreter.Visitor;
import c0.util.DataType;

//データ型
public class DataTypeNode extends ExpressionNode {
	
	private DataType dataType;
	private ExpressionNode elementNumber; //要素数
	
	public DataTypeNode(DataType dataType, ExpressionNode elementNumber) {
		super();
		this.dataType = dataType;
		this.elementNumber = elementNumber;
	}

	@Override
	public Location location() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	public ExpressionNode getElementNumber() {
		return elementNumber;
	}

	public void setElementNumber(ExpressionNode elementNumber) {
		this.elementNumber = elementNumber;
	}

	@Override
	public void dump(int depth, boolean indentFlag) {
		
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("DataTypeNode");
		
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("DataType : " + dataType);
		
		if(elementNumber != null) {
			this.elementNumber.dump(depth, true);
		}
	}

	@Override
	public void accept(Visitor visitor) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}
