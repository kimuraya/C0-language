package c0.util;

public enum NodeType {
	NODE, //全ての構文木の親クラス
	AST, //グローバル変数の確保など、プログラム全体の初期処理と後処理を行う
	EXPRESSION, //式を表す
	LITERAL, //定数
	IDENTIFIER, //識別子
	ASSIGN, //"="
	EQUIVALENCE, //"=="
	NOTEQUIVALENCE, //"!="
	LESSTHAN, //"<"
	LESSTHANOREQUAL, //"<="
	GREATERTHAN, //">"
	GREATERTHANOREQUAL, //">="
	LOGICALAND, //"&&"
	LOGICALOR, //"||"
	PLUS, //"+"
	MINUS, //"-"
	MUL, //"*"
	DIV, //"/"
	MOD, //"%"
	EXCLAMATION, //"!"
	UNARYMINUS, //"-" 単項マイナス式
	PREINCREMENT, //"++" 前置増分
	PREDECREMENT, //"--" 前置減分
	POSTINCREMENT, //"++" 後置増分
	POSTDECREMENT, //"--" 後置減分
	CALL, //関数呼び出し（ネイティブ関数を含む）
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
