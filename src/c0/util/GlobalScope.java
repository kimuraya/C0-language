package c0.util;

import java.util.LinkedList;

//大域変数と関数名のスコープを管理する
public class GlobalScope {
	
	//大域変数のシンボルテーブル
	private SymbolTable globalSymbolTable = new SymbolTable();
	
	//各関数のスコープ
	private LinkedList<LocalScope> functionScopeList = new LinkedList<LocalScope>();

	public SymbolTable getGlobalSymbolTable() {
		return globalSymbolTable;
	}

	public void setGlobalSymbolTable(SymbolTable globalSymbolTable) {
		this.globalSymbolTable = globalSymbolTable;
	}

	public LinkedList<LocalScope> getFunctionScopeList() {
		return functionScopeList;
	}

	public void setFunctionScopeList(LinkedList<LocalScope> functionScopeList) {
		this.functionScopeList = functionScopeList;
	}
}
