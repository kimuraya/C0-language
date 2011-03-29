package c0.ast;

import java.util.List;
import c0.util.NodeType;

//複合文
public class BlockNode extends StatementNode {
	
	private List<IdentifierNode> localVariables; //ローカル変数
	private List<StatementNode> statements; //ブロック文本体
	
	@Override
	public void dump() {
		// TODO 自動生成されたメソッド・スタブ

	}


}
