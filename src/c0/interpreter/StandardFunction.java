package c0.interpreter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import c0.ast.DataTypeNode;
import c0.ast.IdentifierNode;
import c0.util.DataType;
import c0.util.GlobalScope;
import c0.util.Identifier;
import c0.util.IdentifierType;
import c0.util.NodeType;
import c0.util.Value;

/**
 * 標準関数を管理するクラス
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
	 * System.out.printf(String format, Object... args)のラッパー
	 * 引数から値を取り出して、処理を実行させる
	 */
	public void printFunction(LinkedList<Value> valueList) {
		
		//TODO
		//データ型のチェックを入れる
		
		//インタプリタの引数から値を取り出す。先頭の要素は削除する
		String format = null;
		if (valueList.peekFirst().getDataType() == DataType.STRING) {
			format = valueList.poll().getStringLiteral();
		} else if (valueList.peekFirst().getDataType() == DataType.INT)  {
			format = String.valueOf(valueList.poll().getInteger());
		} else if (valueList.peekFirst().getDataType() == DataType.INT_ARRAY)  {
			format = String.valueOf(valueList.poll().getIntegerArray());
		} else if (valueList.peekFirst().getDataType() == DataType.BOOLEAN)  {
			format = String.valueOf(valueList.poll().isBool());
		} else if (valueList.peekFirst().getDataType() == DataType.BOOLEAN_ARRAY)  {
			format = String.valueOf(valueList.poll().getBooleanArray());
		}
		
		ArrayList<Object> argList = new ArrayList<Object>();
		for (Value value : valueList) {
			
			//TODO
			switch (value.getDataType()) {
				case INT:
					argList.add(value.getInteger());
					break;
				case INT_ARRAY:
					argList.add(value.getIntegerArray());
					break;
				case STRING:
					argList.add(value.getStringLiteral());
					break;
				case BOOLEAN:
					argList.add(value.isBool());
					break;
				case BOOLEAN_ARRAY:
					argList.add(value.getBooleanArray());
					break;
			}
		}
		
		Object[] args = argList.toArray();
		
		//ラップしているメソッドの実行
		System.out.printf(format, args);
		
		return;
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
		printFunc.setVariableArgumentFlag(true); //可変長引数を持つ関数である為、trueにする
		
		//識別子のノードの作製
		IdentifierNode identifierNode = new IdentifierNode();
		identifierNode.setIdentifier(printFunc);
		identifierNode.setNodeType(NodeType.CALL);
		identifierNode.setReturnDataType(new DataTypeNode(DataType.VOID, null));
		printFunc.setFunctionNode(identifierNode);
		
		functionList.add(printFunc);
		
		return functionList;
	}
}
