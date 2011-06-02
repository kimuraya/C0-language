package c0.ast;

import java.util.List;
import c0.util.NodeType;

//抽象構文木のルート
//グローバル変数の確保とmain関数の呼び出しを実行する
public class AstNode extends Node {
	private List<IdentifierNode> globalVariables; //グローバル変数のリスト
	private IdentifierNode mainFunction; //main関数の参照
	
	public AstNode(List<IdentifierNode> globalVariables,
			IdentifierNode mainFunction) {
		super();
		this.globalVariables = globalVariables;
		this.mainFunction = mainFunction;
	}	
}
