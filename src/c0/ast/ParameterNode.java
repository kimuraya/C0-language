package c0.ast;

//引数 :=
//データ型 識別子
public class ParameterNode extends StatementNode {
	
	private DataTypeNode dataType; //データ型
	private IdentifierNode identifier; //識別子
	
	public ParameterNode(Location loc, DataTypeNode dataType,
			IdentifierNode identifier) {
		super(loc);
		this.dataType = dataType;
		this.identifier = identifier;
	}

	@Override
	public void dump(int depth, boolean indentFlag) {
		// TODO 自動生成されたメソッド・スタブ
		
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("ParameterNode");
		
		this.dataType.dump(depth, true);
		this.identifier.dump(depth, true);
	}

}
