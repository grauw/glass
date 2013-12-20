package nl.grauw.asm.parser;

import java.util.ArrayDeque;
import java.util.Queue;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.IntegerLiteral;
import nl.grauw.asm.expressions.StringLiteral;

public class ExpressionBuilder {
	
	private Queue<Token> tokens = new ArrayDeque<Token>();
	
	public void AddToken(Token token) {
		tokens.add(token);
	}
	
	public boolean hasExpression() {
		return tokens.size() > 0;
	}
	
	public Expression getExpression() {
		Expression expression;
		if (tokens.peek() instanceof StringLiteralToken)
			expression = new StringLiteral(((StringLiteralToken)tokens.remove()).string);
		else if (tokens.peek() instanceof IntegerLiteralToken)
			expression = new IntegerLiteral(((IntegerLiteralToken)tokens.remove()).value);
		else
			expression = new StringLiteral("XXX");
		
		tokens.clear();
		
		return expression;
	}
	
	private abstract static class Token {
	}
	
	public static class IdentifierToken extends Token {
		
		private String string;
		
		public IdentifierToken(String string) {
			this.string = string;
		}
		
		public String toString() {
			return this.string;
		}
		
	}
	
	public static class CurrentToken extends Token {
		
		public String toString() {
			return "$";
		}
		
	}
	
	public static class OperatorToken extends Token {
		
		private String string;
		
		public OperatorToken(String string) {
			this.string = string;
		}
		
		public String toString() {
			return this.string;
		}
		
	}
	
	public static class StringLiteralToken extends Token {
		
		private String string;
		
		public StringLiteralToken(String string) {
			this.string = string;
		}
		
		public String toString() {
			return "\"" + this.string + "\"";
		}
		
	}
	
	public static class IntegerLiteralToken extends Token {
		
		private int value;
		
		public IntegerLiteralToken(int value) {
			this.value = value;
		}
		
		public String toString() {
			return "" + value;
		}
		
	}
	
	public static class GroupOpenToken extends Token {
		
		public String toString() {
			return "(";
		}
		
	}
	
	public static class GroupCloseToken extends Token {
		
		public String toString() {
			return ")";
		}
		
	}
	
}
