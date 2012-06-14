package c0.interpreter;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import c0.ast.ArraySubscriptExpressionNode;
import c0.ast.AssignNode;
import c0.ast.AstNode;
import c0.ast.BlockNode;
import c0.ast.BreakNode;
import c0.ast.CallNode;
import c0.ast.DataTypeNode;
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
import c0.util.DataType;
import c0.util.GlobalScope;
import c0.util.Identifier;
import c0.util.IdentifierType;
import c0.util.NodeType;
import c0.util.SymbolTable;
import c0.util.Value;

/**
 * 意味解析器
 * 式の型付けのチェック、文法エラー、識別子の有効範囲を検査する
 * エラーを発見した場合、文字列のリストにエラーメッセージを追加する
 */
public class SemanticAnalyzer implements Visitor {
	
	private Map<Integer, Map<String, StatementNode>> errorMessages = null; //エラーメッセージを管理する
	private GlobalScope globalScope = null; //シンボルテーブル
	private IdentifierNode beingProcessedFunction = null; //現在処理中の関数名
	private BlockNode beingProcessedBlock = null; //現在処理中の複合文
	private StatementNode  beingProcessedStatement = null; //現在処理中の文
	private Properties properties = null; //エラーメッセージ
	private Integer errorCount = 1; //エラーの数
	
	public SemanticAnalyzer(GlobalScope globalScope) {
		super();
		this.globalScope = globalScope;
		this.errorMessages = new LinkedHashMap<Integer, Map<String, StatementNode>>();
	}
	
	public Map<Integer, Map<String, StatementNode>> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(Map<Integer, Map<String, StatementNode>> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
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
		List<DeclareVariableNode> glovalVariables = astNode.getGlobalVariables();
		
		//変数名の重複をチェックする
		if (this.getDuplicateCheckingVariable(glovalVariables)) {
			errorCount++;
			String errorMessage = this.properties.getProperty("error.DuplicateCheckingVariable");
			Map<String, StatementNode> errorMap = new LinkedHashMap<String, StatementNode>();
			errorMap.put(errorMessage, this.beingProcessedStatement);
			this.errorMessages.put(errorCount, errorMap);
		}
		
		//main関数が宣言されているかチェック
		if (!this.searchMainFunction(functions)) {
			errorCount++;
			String errorMessage = this.properties.getProperty("error.NotFoundMainFunction");
			Map<String, StatementNode> errorMap = new LinkedHashMap<String, StatementNode>();
			errorMap.put(errorMessage, null);
			this.errorMessages.put(errorCount, errorMap);
		}
		
		//関数名の重複をチェックする
		if (this.getDuplicateCheckingFunction(functions)) {
			errorCount++;
			String errorMessage = this.properties.getProperty("error.DuplicateCheckingFunction");
			Map<String, StatementNode> errorMap = new LinkedHashMap<String, StatementNode>();
			errorMap.put(errorMessage, null);
			this.errorMessages.put(errorCount, errorMap);
		}
		
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
		if ((identifierNode.getIdentifier().getIdentifierType() == IdentifierType.FUNCTION) && (identifierNode.getBlock() != null)) {
			
			//現在走査中の関数名を取得
			this.beingProcessedFunction = identifierNode;
			
			//複合文がある場合
			if(identifierNode.getBlock() != null) {
				identifierNode.getBlock().accept(this);
			}
		}
		
		//変数である場合、生存期間をチェックする
		
	}

	/**
	 * 代入式
	 */
	@Override
	public void visit(AssignNode assignNode) {
		
		//TODO 添字式だった場合の処理を追加する
		
		IdentifierNode identifierNode = (IdentifierNode) assignNode.getLeftValue();
		
		//識別子の有効範囲のチェック
		if(!this.identifierScopeCheck(identifierNode)) {
			
			errorCount++;
			String errorMessage = this.properties.getProperty("error.IdentifierIsValidOutsideTheRange");
			Map<String, StatementNode> errorMap = new LinkedHashMap<String, StatementNode>();
			errorMap.put(errorMessage, this.beingProcessedStatement);
			this.errorMessages.put(errorCount, errorMap);
			
		} else {
			//ここで初期化のフラグを立てる
			//識別子をシンボルテーブルから探す。式文の節にあるノードには、識別子の名前しか情報が無い為の処置
			Identifier identifier = this.searchIdentifier(identifierNode);
			identifier.setAssignFlag(true);
		}
		
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
		
		//現在処理中の文を更新
		this.beingProcessedStatement = statementNode;
		
		statementNode.accept(this);
	}

	/**
	 * 複合文
	 */
	@Override
	public void visit(BlockNode blockNode) {
		
		//現在処理中の文を更新
		this.beingProcessedStatement = blockNode;
		
		//現在処理中の複合文を更新する
		this.beingProcessedBlock = blockNode;
		
		//局所変数が宣言されていた場合
		if (blockNode.getLocalVariables() != null) {
			
			List<DeclareVariableNode> localVariables = blockNode.getLocalVariables();
			
			//変数名の重複をチェックする
			if (this.getDuplicateCheckingVariable(localVariables)) {
				errorCount++;
				String errorMessage = this.properties.getProperty("error.DuplicateCheckingVariable");
				Map<String, StatementNode> errorMap = new LinkedHashMap<String, StatementNode>();
				errorMap.put(errorMessage, this.beingProcessedStatement);
				this.errorMessages.put(errorCount, errorMap);
			}
			
			for (DeclareVariableNode localVariable : localVariables) {
				localVariable.accept(this);
			}
		}
		
		//複合文の中に文がある場合
		if (blockNode.getStatements() != null) {
			List<StatementNode> statements = blockNode.getStatements();
			
			for (StatementNode statement : statements) {
				statement.accept(this);
				
				//複合分の場合、現在処理中の複合文が更新されている
				if (statement.getNodeType() == NodeType.BLOCK_STATEMENT) {
					//現在処理中の複合文を元に戻す
					this.beingProcessedBlock = blockNode;
				}
			}
		}
		
	}

	/**
	 * if-else文
	 */
	@Override
	public void visit(IfNode ifNode) {
		
		//現在処理中の文を更新
		this.beingProcessedStatement = ifNode;
		
		//条件式のチェック
		ExpressionNode conditionalExpression = ifNode.getConditionalExpression();
		if (!this.conditionalExpressionCheck(conditionalExpression)) {
			errorCount++;
			String errorMessage = this.properties.getProperty("error.ConditionalExpressionError");
			Map<String, StatementNode> errorMap = new LinkedHashMap<String, StatementNode>();
			errorMap.put(errorMessage, this.beingProcessedStatement);
			this.errorMessages.put(errorCount, errorMap);
		}
		
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
		
		//現在処理中の文を更新
		this.beingProcessedStatement = whileNode;
		
		//条件式のチェック
		ExpressionNode conditionalExpression = whileNode.getConditionalExpression();
		if (!this.conditionalExpressionCheck(conditionalExpression)) {
			errorCount++;
			String errorMessage = this.properties.getProperty("error.ConditionalExpressionError");
			Map<String, StatementNode> errorMap = new LinkedHashMap<String, StatementNode>();
			errorMap.put(errorMessage, this.beingProcessedStatement);
			this.errorMessages.put(errorCount, errorMap);
		}
		
		whileNode.getConditionalExpression().accept(this);
		whileNode.getBodyStatement().accept(this);
	}

	/**
	 * for文
	 */
	@Override
	public void visit(ForNode forNode) {
		
		//現在処理中の文を更新
		this.beingProcessedStatement = forNode;
		
		//条件式のチェック
		ExpressionNode conditionalExpression = forNode.getConditionalExpression();
		if (!this.conditionalExpressionCheck(conditionalExpression)) {
			errorCount++;
			String errorMessage = this.properties.getProperty("error.ConditionalExpressionError");
			Map<String, StatementNode> errorMap = new LinkedHashMap<String, StatementNode>();
			errorMap.put(errorMessage, this.beingProcessedStatement);
			this.errorMessages.put(errorCount, errorMap);
		}
		
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
		
		//現在処理中の文を更新
		this.beingProcessedStatement = breakNode;
		
		// TODO break文
		// TODO StatementNodeのloopFlagの活用を検討
		// TODO breakの位置から外側にあるループを検索し、無かった場合はエラーにする
	}

	/**
	 * return文
	 */
	@Override
	public void visit(ReturnNode returnNode) {
		
		//現在処理中の文を更新
		this.beingProcessedStatement = returnNode;
		
		//現在処理中の関数から戻り値のデータ型を入手し、戻り値のデータ型と比較
		DataTypeNode dataTypeNode = this.beingProcessedFunction.getReturnDataType();
		DataType returnDataType = dataTypeNode.getDataType();
		
		//戻り値がvoidである場合
		if ((returnDataType == DataType.VOID) && (returnNode.getExpression() != null)) {
			errorCount++;
			String errorMessage = this.properties.getProperty("error.TryingToReturnAVoidReturnValueYet");
			Map<String, StatementNode> errorMap = new LinkedHashMap<String, StatementNode>();
			errorMap.put(errorMessage, this.beingProcessedStatement);
			this.errorMessages.put(errorCount, errorMap);
		}
		
		//戻り値がvoidでない場合、戻り値が書かれているかチェックする
		if ((returnDataType != DataType.VOID) && (returnNode.getExpression() == null)) {
			errorCount++;
			String errorMessage = this.properties.getProperty("error.ThisFunctionShouldReturnAValue");
			Map<String, StatementNode> errorMap = new LinkedHashMap<String, StatementNode>();
			errorMap.put(errorMessage, this.beingProcessedStatement);
			this.errorMessages.put(errorCount, errorMap);
		}
		
		if (returnNode.getExpression() != null) {
			returnNode.getExpression().accept(this);
		}
	}

	/**
	 * 式文
	 */
	@Override
	public void visit(ExpressionStatementNode expressionStatementNode) {
		
		//現在処理中の文を更新
		this.beingProcessedStatement = expressionStatementNode;
		
		//TODO ここを起点に配下にある式のノードすべてを走査する
		//TODO 式文の下にある式は演算子単位でチェックを行う
		//TODO 式の型付けの規則を走査する
		ExpressionNode expression = expressionStatementNode.getExpression();
		this.expressionCheck(expression);
		
		expressionStatementNode.getExpression().accept(this);
	}

	/**
	 * 空文
	 */
	@Override
	public void visit(EmptyStatementNode emptyStatementNode) {
		
		//現在処理中の文を更新
		this.beingProcessedStatement = emptyStatementNode;
		
	}

	/**
	 * 変数宣言
	 * データ型 単純宣言子 ['=' 式] ';'
	 * データ型 配列宣言子 ';'
	 */
	@Override
	public void visit(DeclareVariableNode declareVariableNode) {
		
		//現在処理中の文を更新
		this.beingProcessedStatement = declareVariableNode;
		
		declareVariableNode.getIdentifier().accept(this);
		
		//TODO 代入文と同じデータ型のチェックを行う
		if (declareVariableNode.getExpression() != null) {
			declareVariableNode.getExpression().accept(this);
			
			//代入が行われた為、フラグを更新する
			declareVariableNode.getIdentifier().getIdentifier().setAssignFlag(true);
		}
	}

	/**
	 * 引数
	 * データ型 識別子
	 */
	@Override
	public void visit(ParameterNode parameterNode) {
		
		//現在処理中の文を更新
		this.beingProcessedStatement = parameterNode;
		
		parameterNode.getIdentifier().accept(this);
	}
	
	/**
	 * 識別子とデータ型の情報を受け取り、
	 * 識別子が変数か関数か、変数だった場合、
	 * 指定されたデータ型の変数かをチェックする
	 * データ型が正しい場合、trueを返す
	 * @return
	 */
	private boolean identifierDataTypeCheck(IdentifierNode checkIdentifierNode, DataType checkDataType) {
		
		boolean ret = false;
		Identifier searchSymbol = null; //シンボルテーブルから検索された識別子
		
		searchSymbol = this.searchIdentifier(checkIdentifierNode);
		
		//取り出した識別子が変数なのか、関数なのかをチェック
		IdentifierType identifierType = null;
		if (searchSymbol != null) {
			identifierType = searchSymbol.getIdentifierType();
		}
		
		//変数だった場合、変数のデータ型をチェックする。受け取ったデータ型と一致しなければ、エラー
		//TODO 取り出した識別子が関数だった場合の処理を追加する
		if (identifierType == IdentifierType.VARIABLE) {
			
			//識別子のデータ型が正しいか比較する
			if (searchSymbol.getDataType() == checkDataType) {
				ret = true;
			}
			
		}
		
		return ret;
	}
	
	/**
	 * 識別子が初期化されているかチェックする
	 * 初期化されている場合、trueを返す
	 * @param checkIdentifierNode
	 * @return
	 */
	private boolean identifierInitializationCheck(IdentifierNode checkIdentifierNode) {
		
		boolean ret = false;
		boolean foundFlag = false; //見つかっているならtrue
		Identifier searchSymbol = null; //シンボルテーブルから検索された識別子
		
		searchSymbol = this.searchIdentifier(checkIdentifierNode);
		
		//取り出した識別子が変数なのか、関数なのかをチェック
		IdentifierType identifierType = searchSymbol.getIdentifierType();
		
		//変数だった場合、変数のデータ型をチェックする。受け取ったデータ型と一致しなければ、エラー
		//TODO 取り出した識別子が関数だった場合の処理を追加する
		if (identifierType == IdentifierType.VARIABLE) {
			
			//代入が行われているかチェックする
			if(searchSymbol.isAssignFlag()) {
				ret = true;
			}
			
		}
		
		return ret;
	}
	
	/**
	 * 識別子の有効範囲をチェックする
	 * 識別子が有効範囲にある場合、trueを返す
	 * @return
	 */
	private boolean identifierScopeCheck(IdentifierNode identifierNode) {
		
		boolean ret = false;
		
		//入れ子の内側から外側の複合文を調べる
		SymbolTable beingProcessedSymbolTable = this.beingProcessedBlock.getSymbolTable();
		
		//現在処理中の複合文にある識別子を検索する
		if (beingProcessedSymbolTable.searchSymbol(identifierNode.getIdentifier().getName())) {
			ret = true;
		}
		
		if (!ret) {
			//存在しない場合、外側にある複合文への検索を試みる
			//関数本体の複合文にもない場合、関数内に識別子が存在しないと判断し、処理を打ち切る
			//関数本体のouterNestBlockはnull
			for (BlockNode blockNode = this.beingProcessedBlock.getOuterNestBlock(); blockNode != null; blockNode = blockNode.getOuterNestBlock()) {
				
				SymbolTable symbolTable = blockNode.getSymbolTable();
				
				//現在処理中の複合文にある識別子を検索する
				if (symbolTable.searchSymbol(identifierNode.getIdentifier().getName())) {
					ret = true;
					break;
				}
			}
		}
		
		//関数のスコープにない場合、大域のシンボルテーブルをチェックする
		if (!ret) {
			
			//大域領域の識別子を検索する
			if (this.globalScope.getGlobalSymbolTable().searchSymbol(identifierNode.getIdentifier().getName())) {
				ret = true;
			}
		}
		
		return ret;
	}
	
	/**
	 * 識別子の名前を使い、シンボルテーブルから識別子を検索する
	 * @param identifierNode
	 * @return
	 */
	private Identifier searchIdentifier(IdentifierNode identifierNode) {
		
		Identifier ret = null; //シンボルテーブルから検索された識別子
		boolean foundFlag = false; //見つかっているならtrue
		
		//処理中の関数のLocalScopeから識別子を検索
		SymbolTable beingProcessedSymbolTable = this.beingProcessedBlock.getSymbolTable();
		
		//複合文にある識別子を検索する
		if (beingProcessedSymbolTable.searchSymbol(identifierNode.getIdentifier().getName())) {
			ret = beingProcessedSymbolTable.getSymbol(identifierNode.getIdentifier().getName());
			foundFlag = true;
		}
		
		//存在しない場合、外側にある複合文への検索を試みる
		//関数本体の複合文にもない場合、関数内に識別子が存在しないと判断し、処理を打ち切る
		//関数本体のouterNestBlockはnull
		if (!foundFlag) {
			for (BlockNode blockNode = this.beingProcessedBlock.getOuterNestBlock(); blockNode != null; blockNode = blockNode.getOuterNestBlock()) {
				
				//外側の複合文から識別子を検索
				SymbolTable symbolTable = blockNode.getSymbolTable();
				
				//複合文にある識別子を検索する
				if (symbolTable.searchSymbol(identifierNode.getIdentifier().getName())) {
					ret = symbolTable.getSymbol(identifierNode.getIdentifier().getName());
					foundFlag = true;
					break;
				}
			
			}
		}
		
		//グローバル領域からグローバル変数を検索
		if (!foundFlag) {
			//大域領域の識別子を検索する
			if (this.globalScope.getGlobalSymbolTable().searchSymbol(identifierNode.getIdentifier().getName())) {
				ret = this.globalScope.getGlobalSymbolTable().getSymbol(identifierNode.getIdentifier().getName());
			}
		}
		
		return ret;
	}
	
	/**
	 * 条件式が真偽値定数、真偽値の変数、真偽値の添字式、関係式、同等式、不等式、論理否定、論理AND、論理ORである場合、true
	 * @return
	 */
	private boolean conditionalExpressionCheck(ExpressionNode conditionalExpression) {
		
		boolean ret = false;
		
		switch(conditionalExpression.getNodeType()) {
			
			//結果が真偽値になる式であった場合
			case EQUIVALENCE: //"=="
			case NOT_EQUIVALENCE: //"!="
			case LESS_THAN: //"<"
			case LESS_THAN_OR_EQUAL: //"<="
			case GREATER_THAN: //">"
			case GREATER_THAN_OR_EQUAL: //">="
			case LOGICAL_AND: //"&&"
			case LOGICAL_OR: //"||"
			case EXCLAMATION: //"!"
			case BOOLEAN_LITERAL: //true, false
				ret = true;
				break;
			
			//シンボルテーブルから識別子を検索し、識別子が変数であり、かつ、データ型がbooleanであるかを調べる
			case IDENTIFIER:
				IdentifierNode identifierNode = (IdentifierNode) conditionalExpression;
				Identifier identifier = this.searchIdentifier(identifierNode);
				
				if ((identifier.getDataType() == DataType.BOOLEAN)) {
					ret = true;
				}
				
				break;
				
			//添字式である場合、添字式の識別子のデータ型を調べる
			case ARRAY_SUBSCRIPT:
				ArraySubscriptExpressionNode arraySubscriptExpressionNode = (ArraySubscriptExpressionNode) conditionalExpression;
				IdentifierNode arrayIdentifierNode = arraySubscriptExpressionNode.getArray();
				Identifier arrayIdentifier = this.searchIdentifier(arrayIdentifierNode);
				
				if ((arrayIdentifier.getDataType() == DataType.BOOLEAN_ARRAY)) {
					ret = true;
				}
				
			//結果が真偽値にならない式の場合
			default:
				break;
		}
		
		return ret;
	}
	
	/**
	 * 関数のリストから、main関数を探す。main関数が見つかった場合、trueを返す
	 * @param functions
	 * @return
	 */
	private boolean searchMainFunction(List<IdentifierNode> functions) {
	
		boolean ret = false;
		
		for (IdentifierNode function : functions) {
			
			Identifier identifier = function.getIdentifier();
			String functionName = identifier.getName();
			
			//関数名をチェック
			if (functionName.equals("main")) {
				ret = true;
			}
		}
		
		return ret;
	}
	
	/**
	 * 指定された名前の関数を探し、識別子のノードを返す
	 * @param functions
	 * @param functionName
	 * @return
	 */
	private IdentifierNode getFunction(List<IdentifierNode> functions, String functionName) {
		
		IdentifierNode ret = null;
		
		for (IdentifierNode function : functions) {
			
			Identifier identifier = function.getIdentifier();
			
			//関数名をチェック
			if (functionName.equals(identifier.getName())) {
				ret = function;
			}
		}
		
		return ret;
	}
	
	/**
	 * 関数名の重複をチェックする。関数の宣言が重複していた場合、trueを返す
	 * @param functions
	 * @return
	 */
	private boolean getDuplicateCheckingFunction(List<IdentifierNode> functions) {
		
		boolean ret = false;
		
		Set<String> set = new HashSet<String>();
		
		for (IdentifierNode function : functions) {
			
			Identifier identifier = function.getIdentifier();
			String functionName = identifier.getName();
			
			if (set.contains(functionName)) {
				
				//関数名の重複を見つけた
				ret = true;
				
			} else {
				set.add(functionName);
			}
		}
		
		return ret;
	}
	
	/**
	 * 変数名の重複をチェックする。重複していた場合、trueを返す
	 * @param variables
	 * @return
	 */
	private boolean getDuplicateCheckingVariable(List<DeclareVariableNode> variables) {
		
		boolean ret = false;
		
		Set<String> set = new HashSet<String>();
		
		for (DeclareVariableNode variable : variables) {
			
			Identifier identifier = variable.getIdentifier().getIdentifier();
			String variableName = identifier.getName();
			
			if (set.contains(variableName)) {
				
				//変数名の重複を見つけた
				ret = true;
				
			} else {
				set.add(variableName);
			}
		}
		
		return ret;
	}
	
	/**
	 * 式のチェックを行う
	 * @param expression
	 */
	public DataType expressionCheck(ExpressionNode expression) {
		
		DataType ret = null;
		
		switch(expression.getNodeType()) {
		
			case INT_LITERAL: //10進定数
				LiteralNode integerLiteral = (LiteralNode) expression;
				Value integer = integerLiteral.getLiteral();
				ret = integer.getDataType();
				break;
				
			case STRING_LITERAL: //文字列定数
				LiteralNode stringLiteral = (LiteralNode) expression;
				Value string = stringLiteral.getLiteral();
				ret = string.getDataType();
				break;
				
			case BOOLEAN_LITERAL: //真偽値定数
				LiteralNode booleanLiteral = (LiteralNode) expression;
				Value bool = booleanLiteral.getLiteral();
				ret = bool.getDataType();
				break;
				
			case IDENTIFIER: //識別子
				
				IdentifierNode identifierNode = (IdentifierNode) expression;
				
				//識別子の処理
				//TODO 識別子が関数化変数のどちらかをチェックするメソッドを追加する
				
				//識別子の有効範囲のチェック
				if(!this.identifierScopeCheck(identifierNode)) {
					
					errorCount++;
					String errorMessage = this.properties.getProperty("error.IdentifierIsValidOutsideTheRange");
					Map<String, StatementNode> errorMap = new LinkedHashMap<String, StatementNode>();
					errorMap.put(errorMessage, this.beingProcessedStatement);
					this.errorMessages.put(errorCount, errorMap);
					
				} else {
					
					//識別子が初期化されているかチェックする
					if(!this.identifierInitializationCheck(identifierNode)) {
						errorCount++;
						String errorMessage = this.properties.getProperty("error.VariableHasNotBeenInitialized");
						Map<String, StatementNode> errorMap = new LinkedHashMap<String, StatementNode>();
						errorMap.put(errorMessage, this.beingProcessedStatement);
						this.errorMessages.put(errorCount, errorMap);
					}
				}
				
				//TODO 書き直す
				//識別子（変数）が持つデータ型をチェックする
				if(this.identifierDataTypeCheck(identifierNode, DataType.INT)) {
					ret = DataType.INT;
				} else if (this.identifierDataTypeCheck(identifierNode, DataType.BOOLEAN)) {
					ret = DataType.BOOLEAN;
				} else {
					//TODO 宣言されていない変数など、データ型が分からない変数の処理
				}
				
				//TODO あとで追記する。配列の処理など
				
				break;
				
			case ARRAY_SUBSCRIPT: //添字式
				
				ArraySubscriptExpressionNode arraySubscriptExpressionNode = (ArraySubscriptExpressionNode) expression;
				
				IdentifierNode array = arraySubscriptExpressionNode.getArray();
				
				if(this.identifierDataTypeCheck(array, DataType.INT_ARRAY)) {
					ret = DataType.INT; //欲しいのは式の実行結果のデータ型である為、この書き方で良い
				} else if (this.identifierDataTypeCheck(array, DataType.BOOLEAN_ARRAY)) {
					ret = DataType.BOOLEAN; //欲しいのは式の実行結果のデータ型である為、この書き方で良い
				}
				
				//TODO あとで追記する
				
				break;
				
			case ASSIGN: //"="
				//不要？
				break;
			case LOGICAL_AND: //"&&"
			case LOGICAL_OR: //"||"
				this.logicalOperatorExpressionCheck(expression);
				break;
			case EQUIVALENCE: //"=="
			case NOT_EQUIVALENCE: //"!="
			case LESS_THAN: //"<"
			case LESS_THAN_OR_EQUAL: //"<="
			case GREATER_THAN: //">"
			case GREATER_THAN_OR_EQUAL: //">="
			case PLUS: //"+"
			case MINUS: //"-"
			case MUL: //"*"
			case DIV: //"/"
			case MOD: //"%"
				ret = this.binaryOperatorExpressionCheck(expression);
				break;
			case EXCLAMATION: //"!"
			case UNARY_MINUS: //"-" 単項マイナス式
			case PRE_INCREMENT: //"++" 前置増分
			case PRE_DECREMENT: //"--" 前置減分
			case POST_INCREMENT: //"++" 後置増分
			case POST_DECREMENT: //"--" 後置減分
				ret = this.unaryOperatorExpressionCheck(expression);
				break;
			case CALL: //関数呼び出し
				ret = this.functionCallExpressionCheck(expression);
				break;
		}
		
		return ret;
	}
	
	/**
	 * 論理AND, 論理ORをチェックする
	 * @param expression
	 * @return
	 */
	private DataType logicalOperatorExpressionCheck(ExpressionNode expression) {
		
		DataType ret = null;
		DataType leftDataType = null;
		DataType rightDataType = null;
		ExpressionNode left = null;
		ExpressionNode right = null;
		
		switch(expression.getNodeType()) {
			
			case LOGICAL_AND: //"&&"
				LogicalAndNode logicalAndNode = (LogicalAndNode) expression;
				left = logicalAndNode.getLeft();
				right = logicalAndNode.getRight();
				break;
				
			case LOGICAL_OR: //"||"
				LogicalOrNode logicalOrNode = (LogicalOrNode) expression;
				left = logicalOrNode.getLeft();
				right = logicalOrNode.getRight();
				break;
		}
		
		//左右のデータ型を得る
		leftDataType = this.expressionCheck(left);
		rightDataType = this.expressionCheck(right);
		
		//左右のデータ型をチェックする
		if ((leftDataType == DataType.BOOLEAN) && (rightDataType == DataType.BOOLEAN)) {
			
			ret = DataType.BOOLEAN;
			
		//被演算子のデータ型が正しくない場合
		}  else {
			errorCount++;
			String errorMessage = this.properties.getProperty("error.ExpressionIsNotProperly");
			Map<String, StatementNode> errorMap = new LinkedHashMap<String, StatementNode>();
			errorMap.put(errorMessage, this.beingProcessedStatement);
			this.errorMessages.put(errorCount, errorMap);
		}
		
		return ret;
	}
	
	/**
	 * 二項演算子をチェックする
	 * @param expression
	 */
	private DataType binaryOperatorExpressionCheck(ExpressionNode expression) {
		
		DataType ret = null;
		DataType leftDataType = null;
		DataType rightDataType = null;
		ExpressionNode left = null;
		ExpressionNode right = null;
		
		switch(expression.getNodeType()) {
			
			case EQUIVALENCE: //"=="
				EquivalenceNode equivalenceNode = (EquivalenceNode) expression;
				left = equivalenceNode.getLeft();
				right = equivalenceNode.getRight();
				break;
			case NOT_EQUIVALENCE: //"!="
				NotEquivalenceNode notEquivalenceNode = (NotEquivalenceNode) expression;
				left = notEquivalenceNode.getLeft();
				right = notEquivalenceNode.getRight();
				break;
			case LESS_THAN: //"<"
				LessThanNode lessThanNode = (LessThanNode) expression;
				left = lessThanNode.getLeft();
				right = lessThanNode.getRight();
				break;
			case LESS_THAN_OR_EQUAL: //"<="
				LessThanOrEqualNode lessThanOrEqualNode = (LessThanOrEqualNode) expression;
				left = lessThanOrEqualNode.getLeft();
				right = lessThanOrEqualNode.getRight();
				break;
			case GREATER_THAN: //">"
				GreaterThanNode greaterThanNode = (GreaterThanNode) expression;
				left = greaterThanNode.getLeft();
				right = greaterThanNode.getRight();
				break;
			case GREATER_THAN_OR_EQUAL: //">="
				GreaterThanOrEqualNode greaterThanOrEqualNode = (GreaterThanOrEqualNode) expression;
				left = greaterThanOrEqualNode.getLeft();
				right = greaterThanOrEqualNode.getRight();
				break;
			case PLUS: //"+"
				PlusNode plusNode = (PlusNode) expression;
				left = plusNode.getLeft();
				right = plusNode.getRight();
				break;
			case MINUS: //"-"
				MinusNode minusNode = (MinusNode) expression;
				left = minusNode.getLeft();
				right = minusNode.getRight();
				break;
			case MUL: //"*"
				MulNode mulNode = (MulNode) expression;
				left = mulNode.getLeft();
				right = mulNode.getRight();
				break;
			case DIV: //"/"
				DivNode divNode = (DivNode) expression;
				left = divNode.getLeft();
				right = divNode.getRight();
				break;
			case MOD: //"%"
				ModNode modNode = (ModNode) expression;
				left = modNode.getLeft();
				right = modNode.getRight();
				break;
		}
		
		//左右のデータ型を得る
		leftDataType = this.expressionCheck(left);
		rightDataType = this.expressionCheck(right);
		
		//左右のデータ型をチェックする
		//整数の式
		if ((leftDataType == DataType.INT) && (rightDataType == DataType.INT)) {
			
			ret = DataType.INT;
			
		//真偽値の式
		} else if ((leftDataType == DataType.BOOLEAN) && (rightDataType == DataType.BOOLEAN)) {
			
			ret = DataType.BOOLEAN;
			
		//被演算子のデータ型が正しくない場合
		}  else {
			errorCount++;
			String errorMessage = this.properties.getProperty("error.ExpressionIsNotProperly");
			Map<String, StatementNode> errorMap = new LinkedHashMap<String, StatementNode>();
			errorMap.put(errorMessage, this.beingProcessedStatement);
			this.errorMessages.put(errorCount, errorMap);
		}
		
		return ret;
	}
	
	/**
	 * 単項演算子をチェックする
	 * @param expression
	 * @return
	 */
	private DataType unaryOperatorExpressionCheck(ExpressionNode expression) {
		
		DataType ret = null;
		DataType leftDataType = null;
		ExpressionNode left = null;
		
		//ノードの種類によって、処理を分ける
		switch(expression.getNodeType()) {
			
			case EXCLAMATION: //"!"
				ExclamationNode exclamationNode = (ExclamationNode) expression;
				left = exclamationNode.getLeftValue();
				break;
			case UNARY_MINUS: //"-" 単項マイナス式
				UnaryMinusNode unaryMinusNode = (UnaryMinusNode) expression;
				left = unaryMinusNode.getLeftValue();
				break;
			case PRE_INCREMENT: //"++" 前置増分
				PreIncrementNode preIncrementNode = (PreIncrementNode) expression;
				left = preIncrementNode.getLeftValue();
				break;
			case PRE_DECREMENT: //"--" 前置減分
				PreDecrementNode preDecrementNode = (PreDecrementNode) expression;
				left = preDecrementNode.getLeftValue();
				break;
			case POST_INCREMENT: //"++" 後置増分
				PostIncrementNode postIncrementNode = (PostIncrementNode) expression;
				left = postIncrementNode.getLeftValue();
				break;
			case POST_DECREMENT: //"--" 後置減分
				PostDecrementNode postDecrementNode = (PostDecrementNode) expression;
				left = postDecrementNode.getLeftValue();
				break;
		}
		
		//左のデータ型を得る
		leftDataType = this.expressionCheck(left);
		
		//データ型のチェック
		//整数の式
		switch(expression.getNodeType()) {
		
		case EXCLAMATION: //"!"
			
			if (leftDataType == DataType.BOOLEAN) {
				
				ret = DataType.BOOLEAN;
				
			} else {
				errorCount++;
				String errorMessage = this.properties.getProperty("error.ExpressionIsNotProperly");
				Map<String, StatementNode> errorMap = new LinkedHashMap<String, StatementNode>();
				errorMap.put(errorMessage, this.beingProcessedStatement);
				this.errorMessages.put(errorCount, errorMap);
			}
			
			break;
			
		case UNARY_MINUS: //"-" 単項マイナス式
			
			if (leftDataType == DataType.INT) {
				
				ret = DataType.INT;
				
			} else {
				errorCount++;
				String errorMessage = this.properties.getProperty("error.ExpressionIsNotProperly");
				Map<String, StatementNode> errorMap = new LinkedHashMap<String, StatementNode>();
				errorMap.put(errorMessage, this.beingProcessedStatement);
				this.errorMessages.put(errorCount, errorMap);
			}
			
			break;
			
		case PRE_INCREMENT: //"++" 前置増分
		case PRE_DECREMENT: //"--" 前置減分
		case POST_INCREMENT: //"++" 後置増分
		case POST_DECREMENT: //"--" 後置減分
			
			if ((left instanceof IdentifierNode) && (leftDataType == DataType.INT)) {
				
				ret = DataType.INT;
				
			} else {
				errorCount++;
				String errorMessage = this.properties.getProperty("error.ExpressionIsNotProperly");
				Map<String, StatementNode> errorMap = new LinkedHashMap<String, StatementNode>();
				errorMap.put(errorMessage, this.beingProcessedStatement);
				this.errorMessages.put(errorCount, errorMap);
			}
			
			break;
	}
		
		return ret;
	}
	
	/**
	 * 関数呼び出しの戻り値のデータ型を返す
	 * @param expression
	 * @return
	 */
	private DataType functionCallExpressionCheck(ExpressionNode expression) {
		
		DataType ret = null;
		
		CallNode callNode = (CallNode) expression;
		IdentifierNode identifierNode = (IdentifierNode) callNode.getFunction(); //関数呼び出しから識別子を取り出す。この識別子は関数名だけを持つ
		Identifier identifier = identifierNode.getIdentifier();
		Identifier function = this.globalScope.getGlobalSymbolTable().getSymbol(identifier.getName()); //関数名でシンボルテーブルを検索
		
		//シンボルテーブルに存在しない識別子を使った呼び出し、関数以外の識別子を使った関数呼び出しを禁止する
		if (function != null && function.getIdentifierType() == IdentifierType.FUNCTION) {
			IdentifierNode functionNode = function.getFunctionNode(); //識別子から構文木上の関数のノードを取り出す
			DataTypeNode returnDataType = functionNode.getReturnDataType(); //関数のノードから戻り値のデータ型を得る
			ret = returnDataType.getDataType();
		} else {
			errorCount++;
			String errorMessage = this.properties.getProperty("error.ExpressionIsNotProperly");
			Map<String, StatementNode> errorMap = new LinkedHashMap<String, StatementNode>();
			errorMap.put(errorMessage, this.beingProcessedStatement);
			this.errorMessages.put(errorCount, errorMap);
		}
		
		return ret;
	}
}