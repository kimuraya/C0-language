package c0.ast;

import java.util.LinkedList;
import java.util.List;
import c0.ast.Location;
import c0.util.Identifier;

//識別子
public class IdentifierNode extends ExpressionNode {
	
	private Identifier identifier;
	private List<ParameterNode> parameters = new LinkedList<ParameterNode>(); //引数のリスト
	private StatementNode block; //関数本体（複合文）
	private Location location;
	
	public IdentifierNode(Location location, String name) {
		super();
		this.location = location;
		this.identifier = new Identifier();
		this.identifier.setName(name);
	}

	public Identifier getIdentifier() {
		return identifier;
	}
	
	public void setIdentifier(Identifier identifier) {
		this.identifier = identifier;
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
	public void dump(int depth, boolean indentFlag) {
		
		depth++;
		
		//変数か関数かで出力処理を分ける
		switch(this.identifier.getIdentifierType()) {
		
			case VARIABLE:
				
				depth--;
				
				if (indentFlag) {
					this.printIndent(depth);
				}
				
				System.out.println("name : " + this.identifier.getName());
				
				if (indentFlag) {
					this.printIndent(depth);
				}
				
				System.out.println("IdentifierType : " + this.identifier.getIdentifierType());
				
				break;
				
			case FUNCTION:
				
				if (indentFlag) {
					this.printIndent(depth);
				}
				
				System.out.println("name : " + this.identifier.getName());
				
				if (indentFlag) {
					this.printIndent(depth);
				}
				
				System.out.println("IdentifierType : " + this.identifier.getIdentifierType());
				
				block.dump(depth, true);
				
				break;
		}
	}
}
