package c0.ast;

import java.util.List;

//関数呼び出し（ネイティブ関数を含む）
public class CallNode extends ExpressionNode {
	
	private ExpressionNode function;
	private List<ExpressionNode> parameters; //引数
	
	public CallNode(Location location, ExpressionNode function,
			List<ExpressionNode> parameters) {
		super(location);
		this.function = function;
		this.parameters = parameters;
	}

	@Override
	public void dump() {
		// TODO 自動生成されたメソッド・スタブ

	}

}
