package c0.ast;

import c0.util.DataType;
import c0.util.Value;

//定数
public class LiteralNode extends ExpressionNode {
	
	private Value literal;
	
	public LiteralNode(Location location, DataType dataType, String image) {
		super(location);
		literal = new Value();
		literal.setDataType(dataType);
		literal.setInteger(Integer.parseInt(image, 10));
	}

	@Override
	public void dump() {
		// TODO 自動生成されたメソッド・スタブ

	}
}
