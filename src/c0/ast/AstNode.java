package c0.ast;

import java.util.List;
import c0.util.NodeType;

//抽象構文木のルート
public class AstNode extends Node {
	
	private List<DeclareVariableNode> globalVariables; //外部変数のリスト
	private List<IdentifierNode> functions; //関数のリスト
	
	public AstNode(List<DeclareVariableNode> globalVariables,
			List<IdentifierNode> functions) {
		super();
		this.globalVariables = globalVariables;
		this.functions = functions;
	}

	//ルートを出力し、外部変数と関数を出力する処理を呼び出す
	@Override
	public void dump(int depth) {
		
		System.out.println("AST");
		
		//外部変数の出力
		for(DeclareVariableNode var : globalVariables) {
			var.dump(depth);
		}
		
		//関数の出力
		for(IdentifierNode function : functions) {
			function.dump(depth);
		}
	}	
}
