package c0.util;

public enum NodeType {
	NODE, //全ての構文木の親クラス
	AST, //グローバル変数の確保など、プログラム全体の初期処理と後処理を行う
	EXPRESSION, //式を表す
	LITERAL, //定数
	IDENTIFIER, //識別子
	ASSIGN, //"="
	EQUIVALENCE, //"=="
	NOT_EQUIVALENCE, //"!="
	LESS_THAN, //"<"
	LESS_THAN_OR_EQUAL, //"<="
	GREATER_THAN, //">"
	GREATER_THAN_OR_EQUAL, //">="
	LOGICAL_AND, //"&&"
	LOGICAL_OR, //"||"
	PLUS, //"+"
	MINUS, //"-"
	MUL, //"*"
	DIV, //"/"
	MOD, //"%"
	EXCLAMATION, //"!"
	UNARY_MINUS, //"-" 単項マイナス式
	PRE_INCREMENT, //"++" 前置増分
	PRE_DECREMENT, //"--" 前置減分
	POST_INCREMENT, //"++" 後置増分
	POST_DECREMENT, //"--" 後置減分
	CALL, //関数呼び出し（ネイティブ関数を含む）
	ARRAY_SUBSCRIPT, //添字式
	STATEMENT, //文を表す
	BLOCK_STATEMENT, //複合文
	IF_STATEMENT, //if-else文
	WHILE_STATEMENT, //while文
	FOR_STATEMENT, //for文
	BREAK_STATEMENT, //break文
	RETURN_STATEMENT, //return文
	EXPRESSION_STATEMENT, //式文
	EMPTY_STATEMENT //空文
}
