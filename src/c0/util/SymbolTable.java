package c0.util;

import java.util.LinkedList;

public class SymbolTable {
	
	private LinkedList<Identifier> symbolTable = new LinkedList<Identifier>();
	
	//識別子がシンボルテーブルに登録済みかをチェックする
	public boolean searchSymbol(Identifier Symbol) {
	}
	
	//識別子をシンボルテーブルに追加する
	public void addSymbol(Identifier Symbol) {
		
	}
	
	//名前でシンボルテーブルを検索し、シンボルを取得する
	public Identifier getSymbol(String name) {
		
	}
	
	//同名の識別子を更新する
	public void updateSymbol(Identifier Symbol) {
		
	}
}
