package c0.util;

import java.util.LinkedList;

/**
 * C0言語インタプリタのシンボルテーブル
 */
public class SymbolTable {
	
	//シンボルテーブル
	private LinkedList<Identifier> symbolTable = new LinkedList<Identifier>();
	
	//上位のシンボルテーブルへのリンク
	private SymbolTable prev = null;
	
	/**
	 * 識別子がシンボルテーブルに登録済みかをチェックする
	 * @param Symbol
	 * @return
	 */
	public boolean searchSymbol(Identifier Symbol) {
		
		//指定された要素が存在する場合
		if (this.symbolTable.indexOf(Symbol) != -1) {
			return true;
		} else {
			//指定された要素が存在しない
			return false;
		}
	}
	
	/**
	 * 識別子がシンボルテーブルに登録済みかをチェックする
	 * @param name
	 * @return
	 */
	public boolean searchSymbol(String name) {
		
		//名前の検索
		for (Identifier symbol : this.symbolTable) {
			
			//指定された要素が存在する場合
			if (symbol.getName().equals(name)) {
				return true;
			}
		}
		
		//指定された要素が存在しない
		return false;
	}
	
	/**
	 * 識別子をシンボルテーブルに追加する
	 * @param symbol
	 */
	public void addSymbol(Identifier symbol) {
		this.symbolTable.add(symbol);
	}
	
	/**
	 * 名前でシンボルテーブルを検索し、シンボルを取得する
	 * @param name
	 * @return
	 */
	public Identifier getSymbol(String name) {
		
		//名前の検索
		for (Identifier symbol : this.symbolTable) {
			
			//名前が一致した場合
			if (symbol.getName().equals(name)) {
				return symbol;
			}
		}
		
		//一致する要素が無かった場合
		return null;
	}
	
	/**
	 * 同名の識別子を更新する
	 * @param symbol
	 * @return
	 */
	public boolean updateSymbol(Identifier symbol) {
		
		int cnt = 0;
		
		//名前の検索
		for (Identifier updateSymbol : this.symbolTable) {
			
			//名前が一致した場合、更新を行う
			if (updateSymbol.getName().equals(symbol.getName())) {
				this.symbolTable.set(cnt, symbol);
				return true;
			}
			
			cnt++;
		}
		
		//更新をしなかった場合
		return false;
	}

	public LinkedList<Identifier> getSymbolTable() {
		return symbolTable;
	}

	public void setSymbolTable(LinkedList<Identifier> symbolTable) {
		this.symbolTable = symbolTable;
	}

	public SymbolTable getPrev() {
		return prev;
	}

	public void setPrev(SymbolTable prev) {
		this.prev = prev;
	}
}
