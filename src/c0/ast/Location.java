package c0.ast;

import c0.parser.Token;

public class Location {
	
	private String fileName;
	private Token token;

	public Location(Token token) {
		super();
		this.token = token;
	}
	
	public Location(String fileName, Token token) {
		super();
		this.fileName = fileName;
		this.token = token;
	}
	
	public Token getToken() {
		return token;
	}
	
	public void setToken(Token token) {
		this.token = token;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
