package c0.ast;

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
}
