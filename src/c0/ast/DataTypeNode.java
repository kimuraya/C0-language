package c0.ast;

import c0.util.DataType;

//データ型
public class DataTypeNode extends ExpressionNode {
	
	DataType dataType;
	private ExpressionNode elementNumber; //要素数

	public DataTypeNode(Location location, DataType dataType,
			ExpressionNode elementNumber) {
		super(location);
		this.dataType = dataType;
		this.elementNumber = elementNumber;
	}
	
	@Override
	public void dump() {
		// TODO 自動生成されたメソッド・スタブ

	}

}
