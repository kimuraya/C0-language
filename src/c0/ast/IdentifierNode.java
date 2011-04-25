package c0.ast;

import c0.ast.Location;
import c0.util.Identifier;

//識別子
public class IdentifierNode extends ExpressionNode {
	
	private Identifier identifier;
	
	public IdentifierNode(Location location, String identifierName) {
		super(location);
		identifier = new Identifier();
		identifier.setName(identifierName);
	}

	@Override
	public void dump() {
		// TODO 自動生成されたメソッド・スタブ

	}
}
