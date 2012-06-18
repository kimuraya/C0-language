package c0.ast;

import java.util.LinkedList;
import java.util.List;
import c0.ast.Location;
import c0.interpreter.Visitor;
import c0.util.Identifier;
import c0.util.NodeType;

//識別子
public class IdentifierNode extends ExpressionNode {
	
	private Identifier identifier;
	private DataTypeNode returnDataType; //関数の戻り値のデータ型
	private List<ParameterNode> parameters; //引数のリスト
	private StatementNode block; //関数本体（複合文）
	
	public IdentifierNode(Location location) {
		super();
		this.location = location;
		this.nodeType = NodeType.IDENTIFIER;
	}
	
	public IdentifierNode() {
		super();
	}

	public Identifier getIdentifier() {
		return identifier;
	}
	
	public void setIdentifier(Identifier identifier) {
		this.identifier = identifier;
	}
	
	public DataTypeNode getReturnDataType() {
		return returnDataType;
	}

	public void setReturnDataType(DataTypeNode returnDataType) {
		this.returnDataType = returnDataType;
	}

	public List<ParameterNode> getParameters() {
		return parameters;
	}
	
	public void setParameters(List<ParameterNode> parameters) {
		this.parameters = parameters;
	}
	
	public StatementNode getBlock() {
		return block;
	}
	
	public void setBlock(StatementNode block) {
		this.block = block;
	}
	
	@Override
	public Location location() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	public void dump(int depth, boolean indentFlag) {
		
		depth++;
		
		if (this.identifier.getIdentifierType() != null) {
		
			//変数か関数かで出力処理を分ける
			switch(this.identifier.getIdentifierType()) {
			
				case VARIABLE:
					
					depth--;
					
					if (indentFlag) {
						this.printIndent(depth);
					}
					
					System.out.println("IdentifierNode");
					
					if (indentFlag) {
						this.printIndent(depth);
					}
					
					System.out.println("name : " + this.identifier.getName());
					
					if (indentFlag) {
						this.printIndent(depth);
					}
					
					System.out.println("IdentifierType : " + this.identifier.getIdentifierType());
					
					this.printFileNameAndLine(depth, indentFlag);
					
					break;
					
				case FUNCTION:
					
					if (indentFlag) {
						this.printIndent(depth);
					}
					
					System.out.println("IdentifierNode");
					
					if (indentFlag) {
						this.printIndent(depth);
					}
					
					System.out.println("name : " + this.identifier.getName());
					
					if (indentFlag) {
						this.printIndent(depth);
					}
					
					System.out.println("IdentifierType : " + this.identifier.getIdentifierType());
					
					this.printFileNameAndLine(depth, indentFlag);
					
					this.returnDataType.dump(depth, true);
					
					if (parameters != null) {
						//depth--;
						
						for(ParameterNode parameter : parameters) {
							parameter.dump(depth, true);
						}
					}
					
					this.block.dump(depth, true);
					
					break;
			}
		} else {
			
			if (indentFlag) {
				this.printIndent(depth);
			}
			
			System.out.println("IdentifierNode");
			
			if (indentFlag) {
				this.printIndent(depth);
			}
			
			System.out.println("name : " + this.identifier.getName());
		}
	}
}
