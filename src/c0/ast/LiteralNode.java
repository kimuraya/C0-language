package c0.ast;

import c0.interpreter.Visitor;
import c0.util.DataType;
import c0.util.Value;

//定数
public class LiteralNode extends ExpressionNode {
	
	private Value literal;
	
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
	public void dump(int depth, boolean indentFlag) {
		// TODO 自動生成されたメソッド・スタブ
		depth++;
		
		DataType dataType = this.literal.getDataType();
		
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("LiteralNode");
		
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		switch (dataType) {
			
			case INT:
				
				System.out.println("DataType : " + dataType);
				
				if (indentFlag) {
					this.printIndent(depth);
				}
				
				System.out.println("value : " + this.literal.getInteger());
				break;
				
			case INT_ARRAY:
				
				System.out.println("DataType : " + dataType);
				
				if (indentFlag) {
					this.printIndent(depth);
				}
				
				System.out.println("value : " + this.literal.getArray());
				break;
				
			case STRING:
				
				System.out.println("DataType : " + dataType);
				
				if (indentFlag) {
					this.printIndent(depth);
				}
				
				System.out.println("value : " + this.literal.getStringLiteral());
				break;
		}
	}

	@Override
	public void accept(Visitor visitor) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}
