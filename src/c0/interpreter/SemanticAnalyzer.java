package c0.interpreter;

import java.util.LinkedList;
import java.util.List;

import c0.ast.ArraySubscriptExpressionNode;
import c0.ast.AssignNode;
import c0.ast.AstNode;
import c0.ast.BlockNode;
import c0.ast.BreakNode;
import c0.ast.CallNode;
import c0.ast.DeclareVariableNode;
import c0.ast.DivNode;
import c0.ast.EmptyStatementNode;
import c0.ast.EquivalenceNode;
import c0.ast.ExclamationNode;
import c0.ast.ExpressionNode;
import c0.ast.ExpressionStatementNode;
import c0.ast.ForNode;
import c0.ast.GreaterThanNode;
import c0.ast.GreaterThanOrEqualNode;
import c0.ast.IdentifierNode;
import c0.ast.IfNode;
import c0.ast.LessThanNode;
import c0.ast.LessThanOrEqualNode;
import c0.ast.LiteralNode;
import c0.ast.LogicalAndNode;
import c0.ast.LogicalOrNode;
import c0.ast.MinusNode;
import c0.ast.ModNode;
import c0.ast.MulNode;
import c0.ast.NotEquivalenceNode;
import c0.ast.ParameterNode;
import c0.ast.PlusNode;
import c0.ast.PostDecrementNode;
import c0.ast.PostIncrementNode;
import c0.ast.PreDecrementNode;
import c0.ast.PreIncrementNode;
import c0.ast.ReturnNode;
import c0.ast.StatementNode;
import c0.ast.UnaryMinusNode;
import c0.ast.WhileNode;
import c0.util.GlobalScope;
import c0.util.IdentifierType;
import c0.util.LocalScope;
import c0.util.SymbolTable;

/**
 * 意味解析器
 * 式の型付けのチェック、文法エラー、識別子の有効範囲を検査する
 * エラーを発見した場合、文字列のリストにエラーメッセージを追加する
 */
public class SemanticAnalyzer implements Visitor {
	
	LinkedList<String> errorMessages; //エラーメッセージを管理する
	GlobalScope globalScope = null; //シンボルテーブル
	
	public SemanticAnalyzer(GlobalScope globalScope) {
		super();
		this.globalScope = globalScope;
	}
	
	/**
	 * 抽象構文木のルート
	 */
	@Override
	public void visit(AstNode astNode) {
		
		List<DeclareVariableNode> globalVariables = astNode.getGlobalVariables();
		
		for (DeclareVariableNode declareVariableNode : globalVariables) {
			declareVariableNode.accept(this);
		}
		
		List<IdentifierNode> functions = astNode.getFunctions();
		
		for (IdentifierNode function : functions) {
			function.accept(this);
		}
	}

	/**
	 * 式
	 */
	@Override
	public void visit(ExpressionNode expressionNode) {
		expressionNode.accept(this);
	}

	@Override
	public void visit(LiteralNode literalNode) {
		
	}

	/**
	 * 識別子
	 */
	@Override
	public void visit(IdentifierNode identifierNode) {
		
		//TODO 識別子が有効範囲にあるかをチェックする
		
		//関数である場合、複合文を走査する
		
		//変数である場合、生存期間をチェックする
		
	}

	/**
	 * 代入式
	 */
	@Override
	public void visit(AssignNode assignNode) {
		assignNode.getLeftValue().accept(this);
		assignNode.getExpression().accept(this);
	}

	/**
	 * 同等演算子
	 * "=="
	 */
	@Override
	public void visit(EquivalenceNode equivalenceNode) {
		equivalenceNode.getLeft().accept(this);
		equivalenceNode.getRight().accept(this);
	}

	/**
	 * 不等演算子
	 * "!="
	 */
	@Override
	public void visit(NotEquivalenceNode notEquivalenceNode) {
		notEquivalenceNode.getLeft().accept(this);
		notEquivalenceNode.getRight().accept(this);
	}

	/**
	 * 小なり比較演算子
	 * "<"
	 */
	@Override
	public void visit(LessThanNode lessThanNode) {
		lessThanNode.getLeft().accept(this);
		lessThanNode.getRight().accept(this);
	}

	/**
	 * 以下比較演算子
	 * "<="
	 */
	@Override
	public void visit(LessThanOrEqualNode lessThanOrEqualNode) {
		lessThanOrEqualNode.getLeft().accept(this);
		lessThanOrEqualNode.getRight().accept(this);
	}

	/**
	 * 大なり比較演算子
	 * ">"
	 */
	@Override
	public void visit(GreaterThanNode greaterThanNode) {
		greaterThanNode.getLeft().accept(this);
		greaterThanNode.getRight().accept(this);
	}

	/**
	 * 以上比較演算子
	 * ">="
	 */
	@Override
	public void visit(GreaterThanOrEqualNode greaterThanOrEqualNode) {
		greaterThanOrEqualNode.getLeft().accept(this);
		greaterThanOrEqualNode.getRight().accept(this);
	}

	/**
	 * 条件積演算子
	 * "&&"
	 */
	@Override
	public void visit(LogicalAndNode logicalAndNode) {
		logicalAndNode.getLeft().accept(this);
		logicalAndNode.getRight().accept(this);
	}

	/**
	 * 条件和演算子
	 * "||"
	 */
	@Override
	public void visit(LogicalOrNode logicalOrNode) {
		logicalOrNode.getLeft().accept(this);
		logicalOrNode.getRight().accept(this);
	}

	/**
	 * 加算式
	 * "+"
	 */
	@Override
	public void visit(PlusNode plusNode) {
		plusNode.getLeft().accept(this);
		plusNode.getRight().accept(this);
	}

	/**
	 * 減算式
	 * "-"
	 */
	@Override
	public void visit(MinusNode minusNode) {
		minusNode.getLeft().accept(this);
		minusNode.getRight().accept(this);
	}

	/**
	 * 乗算
	 * "*"
	 */
	@Override
	public void visit(MulNode mulNode) {
		mulNode.getLeft().accept(this);
		mulNode.getRight().accept(this);
	}

	/**
	 * 除算式
	 * "/"
	 */
	@Override
	public void visit(DivNode divNode) {
		divNode.getLeft().accept(this);
		divNode.getRight().accept(this);
	}

	/**
	 * 剰余式
	 * "%"
	 */
	@Override
	public void visit(ModNode modNode) {
		modNode.getLeft().accept(this);
		modNode.getRight().accept(this);
	}

	/**
	 * 論理否定演算子
	 * "!"
	 */
	@Override
	public void visit(ExclamationNode exclamationNode) {
		exclamationNode.getLeftValue().accept(this);
	}

	/**
	 *　単項マイナス式
	 * "-"
	 */
	@Override
	public void visit(UnaryMinusNode unaryMinusNode) {
		unaryMinusNode.getLeftValue().accept(this);
	}

	/**
	 * 前置増分
	 * "++"
	 */
	@Override
	public void visit(PreIncrementNode preIncrementNode) {
		preIncrementNode.getLeftValue().accept(this);
	}

	/**
	 * 前置減分
	 * "--"
	 */
	@Override
	public void visit(PreDecrementNode preDecrementNode) {
		preDecrementNode.getLeftValue().accept(this);
	}

	/**
	 * 後置増分
	 * "++"
	 */
	@Override
	public void visit(PostIncrementNode postIncrementNode) {
		postIncrementNode.getLeftValue().accept(this);
	}

	/**
	 * 後置減分
	 * "--"
	 */
	@Override
	public void visit(PostDecrementNode postDecrementNode) {
		postDecrementNode.getLeftValue().accept(this);
	}

	/**
	 * 関数呼び出し
	 */
	@Override
	public void visit(CallNode callNode) {
		
		//TODO 関数宣言の引数と関数呼び出しの引数のデータ型、個数が一致するかをチェック
		
		callNode.getFunction().accept(this);
		List<ExpressionNode> parameters = callNode.getArguments();
		
		if (parameters != null) {
			for (ExpressionNode parameter : parameters) {
				parameter.accept(this);
			}
		}
	}

	/**
	 * 添字式
	 */
	@Override
	public void visit(ArraySubscriptExpressionNode arraySubscriptExpressionNode) {
		arraySubscriptExpressionNode.getArray().accept(this);
		arraySubscriptExpressionNode.getIndex().accept(this);
	}	
	
	/**
	 * 文
	 */
	@Override
	public void visit(StatementNode statementNode) {
		statementNode.accept(this);
	}

	/**
	 * 複合文
	 */
	@Override
	public void visit(BlockNode blockNode) {
		
		//局所変数が宣言されていた場合
		if (blockNode.getLocalVariables() != null) {
			List<DeclareVariableNode> localVariables = blockNode.getLocalVariables();
			
			for (DeclareVariableNode localVariable : localVariables) {
				localVariable.accept(this);
			}
		}
		
		//複合文の中に文がある場合
		if (blockNode.getStatements() != null) {
			List<StatementNode> statements = blockNode.getStatements();
			
			for (StatementNode statement : statements) {
				statement.accept(this);
			}
		}
		
	}

	/**
	 * if-else文
	 */
	@Override
	public void visit(IfNode ifNode) {
		ifNode.getConditionalExpression().accept(this);
		ifNode.getThenStatement().accept(this);
		if (ifNode.getElseStatement() != null) {
			ifNode.getElseStatement().accept(this);
		}
	}

	/**
	 * while文
	 */
	@Override
	public void visit(WhileNode whileNode) {
		whileNode.getConditionalExpression().accept(this);
		whileNode.getBodyStatement().accept(this);
	}

	/**
	 * for文
	 */
	@Override
	public void visit(ForNode forNode) {
		forNode.getInitializeExpression().accept(this);
		forNode.getConditionalExpression().accept(this);
		forNode.getUpdateExpression().accept(this);
		forNode.getBodyStatement().accept(this);
	}

	/**
	 * break文
	 */
	@Override
	public void visit(BreakNode breakNode) {
		// TODO break文
		// TODO StatementNodeのloopFlagの活用を検討
	}

	/**
	 * return文
	 */
	@Override
	public void visit(ReturnNode returnNode) {
		if (returnNode.getExpression() != null) {
			returnNode.getExpression().accept(this);
		}
	}

	/**
	 * 式文
	 */
	@Override
	public void visit(ExpressionStatementNode expressionStatementNode) {
		
		//TODO ここを起点に配下にある式のノードすべてを走査する
		//TODO 式文の下にある式は演算子単位でチェックを行う
		//TODO 式の型付けの規則を走査する
		
		expressionStatementNode.getExpression().accept(this);
	}

	/**
	 * 空文
	 */
	@Override
	public void visit(EmptyStatementNode emptyStatementNode) {
		
	}

	/**
	 * 変数宣言
	 * データ型 単純宣言子 ['=' 式] ';'
	 * データ型 配列宣言子 ';'
	 */
	@Override
	public void visit(DeclareVariableNode declareVariableNode) {
		
		declareVariableNode.getIdentifier().accept(this);
		
		//TODO 代入文と同じデータ型のチェックを行う
		if (declareVariableNode.getExpression() != null) {
			declareVariableNode.getExpression().accept(this);
		}
	}

	/**
	 * 引数
	 * データ型 識別子
	 */
	@Override
	public void visit(ParameterNode parameterNode) {
		parameterNode.getIdentifier().accept(this);
	}

}
