package c0.ast;

import java.util.List;

//関数呼び出し（ネイティブ関数を含む）
public class CallNode extends ExpressionNode {
	
	private IdentifierNode function;
	private List<IdentifierNode> parameters; //引数
	
	public CallNode(Location location, IdentifierNode function,
			List<IdentifierNode> parameters) {
		super(location);
		this.function = function;
		this.parameters = parameters;
	}

	@Override
	public void dump() {
		// TODO 自動生成されたメソッド・スタブ

	}

}
