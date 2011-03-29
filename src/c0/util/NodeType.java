package c0.util;

//構文木の種類
public enum NodeType {
	NODE,                //全ての構文木の親クラス
	AST,                 //グローバル変数の確保など、プログラム全体の初期処理と後処理を行う
	EXPRESSION,          //式を表す
	LITERAL,             //定数
	IDENTIFIER,          //識別子
	ASSIGN,              //"="
	EQUIVALENCE,         //"=="
	NOTEQUIVALENCE,      //"!="
	LESSTHAN,            //"<"
	LESSTHANOREQUAL,     //"<="
	GREATERTHAN,         //">"
	GREATERTHANOREQUAL,  //">="
	LOGICALAND,          //"&&"
	LOGICALOR,           //"||"
	PLUS,                //"+"
	MINUS,               //"-"
	MUL,                 //"*"
	DIV,                 //"/"
	MOD,                 //"%"
	EXCLAMATION,         //"!"
	UNARYMINUS,          //"-" 単項マイナス式
	PREINCREMENT,        //"++" 前置増分
	PREDECREMENT,        //"--" 前置減分
	POSTINCREMENT,       //"++" 後置増分
	POSTDECREMENT,       //"--" 後置減分
	CALL,                //関数呼び出し（ネイティブ関数を含む）
	STATEMENT,           //文を表す
	BLOCK,               //複合文
	IF,                  //if-else文
	WHILE,               //while文
	FOR,                 //for文
	BREAK,               //break文
	RETURN,              //return文
	EXPRESSIONSTATEMENT, //式文
	EMPTYSTATEMENT       //空文
}