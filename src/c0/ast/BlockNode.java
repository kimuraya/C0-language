package c0.ast;

import java.util.List;
import c0.util.NodeType;

//複合文
public class BlockNode extends StatementNode {
	
	private List<DeclareVariableNode> localVariables; //ローカル変数
	private List<StatementNode> statements; //ブロック文本体
	
	public BlockNode(Location loc, List<DeclareVariableNode> localVariables,
			List<StatementNode> statements) {
		super(loc);
		this.localVariables = localVariables;
		this.statements = statements;
	}

	@Override
	public void dump(int depth) {
		// TODO 自動生成されたメソッド・スタブ

	}


}
