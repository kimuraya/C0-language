package c0.util;

import java.util.LinkedList;

//局所変数のスコープを管理する
public class LocalScope {
	
	//スコープを持つ関数名
	String functionName = null;
	
	//局所変数のシンボルテーブル
	private LinkedList<SymbolTable> localSymbolTableList = new LinkedList<SymbolTable>();

	public LinkedList<SymbolTable> getLocalSymbolTableList() {
		return localSymbolTableList;
	}

	public void setLocalSymbolTableList(LinkedList<SymbolTable> localSymbolTableList) {
		this.localSymbolTableList = localSymbolTableList;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
}
