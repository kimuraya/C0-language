package c0.interpreter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
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
import c0.util.DataType;
import c0.util.GlobalScope;
import c0.util.Identifier;
import c0.util.IdentifierType;
import c0.util.NodeType;
import c0.util.SymbolTable;

/**
 * 意味解析器
 * 式の型付けのチェック、文法エラー、識別子の有効範囲を検査する
 * エラーを発見した場合、文字列のリストにエラーメッセージを追加する
 */
public class SemanticAnalyzer implements Visitor {
	
	private Map<Integer, Map<String, StatementNode>> errorMessages = null; //エラーメッセージを管理する
	private GlobalScope globalScope = null; //シンボルテーブル
	private String beingProcessedFunctionName = null; //現在処理中の関数名
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
			this.beingProcessedFunctionName = identifierNode.getIdentifier().getName();
			
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
		this.binaryExpressionCheck(plusNode); //TODO 被演算子のデータ型のチェック
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
	 * 二項演算子をチェックする
	 * @param expression
	 */
	private void binaryExpressionCheck(ExpressionNode expression) {
		
		switch(expression.getNodeType()) {
			
			case PLUS: //"+"
				PlusNode plusNode = (PlusNode) expression;
				
				//左右の子のチェック
				ExpressionNode left = plusNode.getLeft();
				ExpressionNode right = plusNode.getRight();
				
				//else ifを追加する。被演算子が別の式である場合の処理を追加
				if (left.getNodeType() != NodeType.INT_LITERAL || left.getNodeType() == NodeType.IDENTIFIER) {
					
					//識別子の処理
					//TODO 識別子が関数化変数のどちらかをチェックするメソッドを追加する
					if (left.getNodeType() == NodeType.IDENTIFIER) {
						
						IdentifierNode identifierNode = (IdentifierNode) left;
						
						//識別子の有効範囲のチェック
						if(!this.identifierScopeCheck(identifierNode)) {
							
							errorCount++;
							String errorMessage = this.properties.getProperty("error.IdentifierIsValidOutsideTheRange");
							Map<String, StatementNode> errorMap = new LinkedHashMap<String, StatementNode>();
							errorMap.put(errorMessage, this.beingProcessedStatement);
							this.errorMessages.put(errorCount, errorMap);
							
						} else {
							
							//識別子のデータ型チェック
							//識別子のデータ型のチェックは、識別子が有効範囲に存在する場合のみ、実施する
							if(!this.identifierDataTypeCheck(identifierNode, DataType.INT)) {
								
								errorCount++;
								String errorMessage = this.properties.getProperty("error.IncorrectDataTypeOfIdentifierUsedInTheFormula");
								Map<String, StatementNode> errorMap = new LinkedHashMap<String, StatementNode>();
								errorMap.put(errorMessage, this.beingProcessedStatement);
								this.errorMessages.put(errorCount, errorMap);
								
							}
							
							//識別子が初期化されているかチェックする
							if(!this.identifierInitializationCheck(identifierNode)) {
								errorCount++;
								String errorMessage = this.properties.getProperty("error.VariableHasNotBeenInitialized");
								Map<String, StatementNode> errorMap = new LinkedHashMap<String, StatementNode>();
								errorMap.put(errorMessage, this.beingProcessedStatement);
								this.errorMessages.put(errorCount, errorMap);
							}
						}
						
					} else {
						//識別子でもなければ、リテラルの整数でもない
						errorCount++;
						String errorMessage = this.properties.getProperty("error.TheDataTypeOfTheOperandIsIncorrect");
						Map<String, StatementNode> errorMap = new LinkedHashMap<String, StatementNode>();
						errorMap.put(errorMessage, this.beingProcessedStatement);
						this.errorMessages.put(errorCount, errorMap);
					}
				}
		}
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
		boolean foundFlag = false; //見つかっているならtrue
		Identifier searchSymbol = null; //シンボルテーブルから検索された識別子
		
		searchSymbol = this.searchIdentifier(checkIdentifierNode);
		
		//取り出した識別子が変数なのか、関数なのかをチェック
		IdentifierType identifierType = searchSymbol.getIdentifierType();
		
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
}
