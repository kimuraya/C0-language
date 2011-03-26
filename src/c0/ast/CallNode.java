package c0.ast;

import java.util.List;

//関数呼び出し（ネイティブ関数を含む）
public class CallNode extends ExpressionNode {
	
	private IdentifierNode function;
	private List<IdentifierNode> parameters; //引数
	
	@Override
	public void dump() {
		// TODO 自動生成されたメソッド・スタブ

	}

}
