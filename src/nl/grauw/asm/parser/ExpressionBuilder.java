package nl.grauw.asm.parser;

import java.util.ArrayDeque;
import java.util.Deque;
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
		if (tokens.isEmpty())
			throw new RuntimeException("No tokens queued.");
		
		Deque<Expression> stack = new ArrayDeque<Expression>();
		
		tokens.remove().process(tokens, stack);
		
//		if (!tokens.isEmpty())
//			throw new RuntimeException("Not all tokens were processed.");
		tokens.clear();
		
		if (stack.size() > 1)
			throw new RuntimeException("Not all expressions were processed.");
		if (!stack.isEmpty())
			return stack.pop();
		return new Identifier("XXX");
	}
	
	private abstract static class Token {
		
		public abstract void process(Queue<Token> tokens, Deque<Expression> stack);
		
	}
	
	public static class IdentifierToken extends Token {
		
		private String string;
		
		public IdentifierToken(String string) {
			this.string = string;
		}
		
		public void process(Queue<Token> tokens, Deque<Expression> stack) {
		}
		
		public String toString() {
			return this.string;
		}
		
	}
	
	public static class CurrentToken extends Token {
		
		public void process(Queue<Token> tokens, Deque<Expression> stack) {
		}
		
		public String toString() {
			return "$";
		}
		
	}
	
	public static class OperatorToken extends Token {
		
		private String string;
		
		public OperatorToken(String string) {
			this.string = string;
		}
		
		public void process(Queue<Token> tokens, Deque<Expression> stack) {
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
		
		public void process(Queue<Token> tokens, Deque<Expression> stack) {
			stack.push(new StringLiteral(string));
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
		
		public void process(Queue<Token> tokens, Deque<Expression> stack) {
			stack.push(new IntegerLiteral(value));
		}
		
		public String toString() {
			return "" + value;
		}
		
	}
	
	public static class GroupOpenToken extends Token {
		
		public void process(Queue<Token> tokens, Deque<Expression> stack) {
		}
		
		public String toString() {
			return "(";
		}
		
	}
	
	public static class GroupCloseToken extends Token {
		
		public void process(Queue<Token> tokens, Deque<Expression> stack) {
		}
		
		public String toString() {
			return ")";
		}
		
	}
	
}
