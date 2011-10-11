package c0.interpreter;

import java.util.ArrayList;
import java.util.List;

import c0.ast.ExpressionNode;
import c0.util.GlobalScope;
import c0.util.Identifier;
import c0.util.IdentifierType;

/**
 * 標準関数を管理するクラス
 * 
 */
public class StandardFunction {
	
	/**
	 * グローバル領域に標準関数を追加する
	 * @param globalScope
	 */
	public void addSymbolTable(GlobalScope globalScope) {
		
		//グローバル領域に追加する標準関数のリストを作成する
		List<Identifier> functionList = this.createFunctionList();
		
		//すべての標準関数をシンボルテーブルに登録する
		for (Identifier function : functionList) {
			globalScope.getGlobalSymbolTable().addSymbol(function);
		}
		
		return;
	}
	
	/**
	 * System.out.printlnのラッパー
	 * 識別子からラップしているクラス名を取り出し、引数から値を取り出して、処理を実行させる
	 */
	public void printFunction(ExpressionNode function, List<ExpressionNode> parameters) {
		
	}
	
	/**
	 * グローバル領域に追加する標準関数のリストを作成する
	 * @return
	 */
	private List<Identifier> createFunctionList() {
		
		List<Identifier> functionList = new ArrayList<Identifier>();
		
		//識別子の作成
		Identifier printFunc = new Identifier("print"); //インタプリタ内で使用する関数名
		printFunc.setIdentifierType(IdentifierType.FUNCTION);
		printFunc.setStandardFunctionFlag(true); //標準関数である
		printFunc.setStandardFunctionName("printFunction"); //ラッパーの名前を保存する
		
		functionList.add(printFunc);
		
		return functionList;
	}
}
