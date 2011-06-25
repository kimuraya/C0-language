package c0.ast;

import c0.util.DataType;
import c0.util.Value;

//定数
public class LiteralNode extends ExpressionNode {
	
	private Value literal;
	private Location location;
	
	public LiteralNode(Location location, DataType dataType, String image) {
		this.location = location;
		literal = new Value();
		literal.setDataType(dataType);
		
		switch (dataType) {
			case INT:
				literal.setDataType(dataType);
				literal.setInteger(Integer.parseInt(image, 10));
			case INT_ARRAY:
			case STRING:
				literal.setDataType(dataType);
				literal.setStringLiteral(image);
		}
	}

	@Override
	public Location location() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void dump(int depth) {
		// TODO 自動生成されたメソッド・スタブ
		DataType dataType = this.literal.getDataType();
		
		switch (dataType) {
			case INT:
				System.out.println(this.literal.getInteger());
			case INT_ARRAY:
				System.out.println(this.literal.getArray());
			case STRING:
				System.out.println(this.literal.getStringLiteral());
		}
	}
}
