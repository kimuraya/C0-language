package c0.ast;

import java.util.List;

//複合文
public class BlockNode extends StatementNode {
	
	private List<DeclareVariableNode> localVariables; //局所変数
	private List<StatementNode> statements; //ブロック文本体
	
	public BlockNode(Location loc, List<DeclareVariableNode> localVariables,
			List<StatementNode> statements) {
		super(loc);
		this.localVariables = localVariables;
		this.statements = statements;
	}

	@Override
	public void dump(int depth, boolean indentFlag) {
		
		depth++;
		
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("BlockNode");
		
		this.printFileNameAndLine(depth, indentFlag);
		
		//局所変数の出力
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("localVariables");
		
		for(DeclareVariableNode var : localVariables) {
			var.dump(depth, true);
		}
		
		//ブロック文本体の出力
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("statements");
		
		for(StatementNode statement : statements) {
			statement.dump(depth, true);
		}
	}
}
