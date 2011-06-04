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
	
	public IdentifierNode(Location location, String identifierName) {
		super(location);
		identifier = new Identifier();
		identifier.setName(identifierName);
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
	public void dump() {
		// TODO 自動生成されたメソッド・スタブ

	}
}
