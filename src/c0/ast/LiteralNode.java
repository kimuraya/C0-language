package c0.ast;

import c0.interpreter.Visitor;
import c0.util.DataType;
import c0.util.NodeType;
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
				this.literal.setDataType(dataType);
				this.literal.setInteger(Integer.parseInt(image, 10));
				this.nodeType = NodeType.INT_LITERAL;
				break;
			case STRING:
				this.literal.setDataType(dataType);
				this.literal.setStringLiteral(image);
				this.nodeType = NodeType.STRING_LITERAL;
				break;
			case BOOLEAN:
				//trueの場合
				if (image.equals("true")) {
					this.literal.setDataType(dataType);
					this.literal.setBool(true);
				//falseの場合
				} else if (image.equals("false")) {
					this.literal.setDataType(dataType);
					this.literal.setBool(false);
				}
				this.nodeType = NodeType.BOOLEAN_LITERAL;
				break;
		}
	}

	public Value getLiteral() {
		return literal;
	}

	public void setLiteral(Value literal) {
		this.literal = literal;
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
				
				System.out.println("value : " + this.literal.getIntegerArray());
				break;
				
			case STRING:
				
				System.out.println("DataType : " + dataType);
				
				if (indentFlag) {
					this.printIndent(depth);
				}
				
				System.out.println("value : " + this.literal.getStringLiteral());
				break;
			
			case BOOLEAN:
				
				System.out.println("DataType : " + dataType);
				
				if (indentFlag) {
					this.printIndent(depth);
				}
				
				System.out.println("value : " + this.literal.isBool());
				break;
				
			case BOOLEAN_ARRAY:
				
				System.out.println("DataType : " + dataType);
				
				if (indentFlag) {
					this.printIndent(depth);
				}
				
				System.out.println("value : " + this.literal.getBooleanArray());
				break;
		}
	}

	@Override
	public void accept(Visitor visitor) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}
