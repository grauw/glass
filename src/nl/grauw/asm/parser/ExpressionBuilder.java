package nl.grauw.asm.parser;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

import nl.grauw.asm.expressions.Add;
import nl.grauw.asm.expressions.Complement;
import nl.grauw.asm.expressions.Current;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Group;
import nl.grauw.asm.expressions.Identifier;
import nl.grauw.asm.expressions.IntegerLiteral;
import nl.grauw.asm.expressions.Negate;
import nl.grauw.asm.expressions.Not;
import nl.grauw.asm.expressions.Positive;
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
		
		Expression result = tokens.remove().process(tokens);
		
//		if (!tokens.isEmpty())
//			throw new RuntimeException("Not all tokens were processed.");
		tokens.clear();
		
		return result;
	}
	
	private abstract static class Token {
		
		// consume()?
		public abstract Expression process(Queue<Token> tokens);
		
		// process(expression, tokens)?
		public abstract Expression processOperator(Expression expression, Queue<Token> tokens);
		
	}
	
	public static class IdentifierToken extends Token {
		
		private String string;
		
		public IdentifierToken(String string) {
			this.string = string;
		}
		
		@Override
		public Expression process(Queue<Token> tokens) {
			Expression expression = new Identifier(string);
//			while not(tokens.isEmpty() || tokens.peekOperator().isGroupClose() || tokens.peekOperator().isLowerPrecedence())
//				expression = tokens.remove().processOperator(expression, tokens);
			return expression;
		}
		
		@Override
		public Expression processOperator(Expression expression, Queue<Token> tokens) {
			throw new ExpressionError("Not an operator.");
		}
		
		public String toString() {
			return this.string;
		}
		
	}
	
	public static class CurrentToken extends Token {
		
		@Override
		public Expression process(Queue<Token> tokens) {
			return new Current();
		}
		
		@Override
		public Expression processOperator(Expression expression, Queue<Token> tokens) {
			throw new ExpressionError("Not an operator.");
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
		
		public Expression process(Queue<Token> tokens) {
			if ("+".equals(string))
				return new Positive(tokens.remove().process(tokens));
			if ("-".equals(string))
				return new Negate(tokens.remove().process(tokens));
			if ("~".equals(string))
				return new Complement(tokens.remove().process(tokens));
			if ("!".equals(string))
				return new Not(tokens.remove().process(tokens));
			if ("(".equals(string))
				return new Group(tokens.remove().process(tokens));
			throw new ExpressionError("Not an unary operator.");
		}
		
		public Expression processOperator(Expression expression, Queue<Token> tokens) {
			if ("+".equals(string))
				return new Add(expression, process(tokens));
			throw new ExpressionError("Not a binary operator.");
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
		
		@Override
		public Expression process(Queue<Token> tokens) {
			return new StringLiteral(string);
		}
		
		@Override
		public Expression processOperator(Expression expression, Queue<Token> tokens) {
			throw new ExpressionError("Not an operator.");
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
		
		@Override
		public Expression process(Queue<Token> tokens) {
			return new IntegerLiteral(value);
		}
		
		@Override
		public Expression processOperator(Expression expression, Queue<Token> tokens) {
			throw new ExpressionError("Not an operator.");
		}
		
		public String toString() {
			return "" + value;
		}
		
	}
	
	public static class ExpressionError extends RuntimeException {
		private static final long serialVersionUID = 1L;
		public ExpressionError(String message) {
			super("Expression error: " + message);
		}
	}
	
}
