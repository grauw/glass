package nl.grauw.asm.parser;

import java.util.ArrayDeque;
import java.util.Queue;

import nl.grauw.asm.expressions.Add;
import nl.grauw.asm.expressions.And;
import nl.grauw.asm.expressions.BitwiseAnd;
import nl.grauw.asm.expressions.BitwiseOr;
import nl.grauw.asm.expressions.BitwiseXor;
import nl.grauw.asm.expressions.Complement;
import nl.grauw.asm.expressions.Divide;
import nl.grauw.asm.expressions.Equals;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.GreaterOrEquals;
import nl.grauw.asm.expressions.GreaterThan;
import nl.grauw.asm.expressions.Group;
import nl.grauw.asm.expressions.LessOrEquals;
import nl.grauw.asm.expressions.LessThan;
import nl.grauw.asm.expressions.Modulo;
import nl.grauw.asm.expressions.Multiply;
import nl.grauw.asm.expressions.Negate;
import nl.grauw.asm.expressions.Not;
import nl.grauw.asm.expressions.NotEquals;
import nl.grauw.asm.expressions.Or;
import nl.grauw.asm.expressions.Positive;
import nl.grauw.asm.expressions.Sequence;
import nl.grauw.asm.expressions.ShiftLeft;
import nl.grauw.asm.expressions.ShiftRight;
import nl.grauw.asm.expressions.Subtract;

public class ExpressionBuilder {
	
	private final Queue<Token> tokens = new ArrayDeque<Token>();
	
	public void addValueToken(Expression value) {
		tokens.add(new ValueToken(value));
	}
	
	public void addOperatorToken(String operator) {
		tokens.add(new OperatorToken(operator));
	}
	
	public boolean hasExpression() {
		return !tokens.isEmpty();
	}
	
	public Expression getExpression() {
		if (tokens.isEmpty())
			throw new RuntimeException("No tokens queued.");
		
		Expression expression = tokens.remove().process(Precedence.NONE);
		while (!tokens.isEmpty() && tokens.peek().isHigherPrecedence(Precedence.NONE))
			expression = tokens.remove().processOperator(expression);
		
		if (!tokens.isEmpty())
			throw new RuntimeException("Not all tokens were processed: " + tokens);
		
		return expression;
	}
	
	private abstract class Token {
		
		public abstract Expression process(Precedence lastPrecedence);
		
		public abstract Expression processOperator(Expression expression);
		
		public abstract boolean isHigherPrecedence(Precedence lastPrecedence);
		
	}
	
	public class ValueToken extends Token {

		private final Expression value;
		
		public ValueToken(Expression value) {
			this.value = value;
		}
		
		@Override
		public Expression process(Precedence lastPrecedence) {
			Expression expression = value;
			while (!tokens.isEmpty() && tokens.peek().isHigherPrecedence(lastPrecedence))
				expression = tokens.remove().processOperator(expression);
			return expression;
		}
		
		@Override
		public Expression processOperator(Expression expression) {
			throw new ExpressionError("Not an operator: " + this);
		}
		
		@Override
		public boolean isHigherPrecedence(Precedence lastPrecedence) {
			throw new ExpressionError("Not an operator: " + this);
		}
		
		public String toString() {
			return value.toString();
		}
		
	}
	
	public class OperatorToken extends Token {
		
		private final String string;
		
		public OperatorToken(String string) {
			this.string = string;
		}
		
		@Override
		public Expression process(Precedence lastPrecedence) {
			if ("+".equals(string))
				return new Positive(tokens.remove().process(Precedence.UNARY));
			if ("-".equals(string))
				return new Negate(tokens.remove().process(Precedence.UNARY));
			if ("~".equals(string))
				return new Complement(tokens.remove().process(Precedence.UNARY));
			if ("!".equals(string))
				return new Not(tokens.remove().process(Precedence.UNARY));
			if ("(".equals(string)) {
				Group group = new Group(tokens.remove().process(Precedence.GROUPING));
				if (tokens.isEmpty())
					throw new ExpressionError("Mismatching parentheses.");
				tokens.remove();
				return group;
			}
			throw new ExpressionError("Not an unary operator, grouping operator or value: " + this);
		}
		
		@Override
		public Expression processOperator(Expression expression) {
			if (")".equals(string))
				return expression;
			if ("*".equals(string))
				return new Multiply(expression, tokens.remove().process(Precedence.MULTIPLICATION));
			if ("/".equals(string))
				return new Divide(expression, tokens.remove().process(Precedence.MULTIPLICATION));
			if ("%".equals(string))
				return new Modulo(expression, tokens.remove().process(Precedence.MULTIPLICATION));
			if ("+".equals(string))
				return new Add(expression, tokens.remove().process(Precedence.ADDITION));
			if ("-".equals(string))
				return new Subtract(expression, tokens.remove().process(Precedence.ADDITION));
			if ("<<".equals(string))
				return new ShiftLeft(expression, tokens.remove().process(Precedence.SHIFT));
			if (">>".equals(string))
				return new ShiftRight(expression, tokens.remove().process(Precedence.SHIFT));
			if ("<".equals(string))
				return new LessThan(expression, tokens.remove().process(Precedence.COMPARISON));
			if ("<=".equals(string))
				return new LessOrEquals(expression, tokens.remove().process(Precedence.COMPARISON));
			if (">".equals(string))
				return new GreaterThan(expression, tokens.remove().process(Precedence.COMPARISON));
			if (">=".equals(string))
				return new GreaterOrEquals(expression, tokens.remove().process(Precedence.COMPARISON));
			if ("=".equals(string))
				return new Equals(expression, tokens.remove().process(Precedence.EQUALITY));
			if ("!=".equals(string))
				return new NotEquals(expression, tokens.remove().process(Precedence.EQUALITY));
			if ("&".equals(string))
				return new BitwiseAnd(expression, tokens.remove().process(Precedence.BITWISE_AND));
			if ("^".equals(string))
				return new BitwiseXor(expression, tokens.remove().process(Precedence.BITWISE_XOR));
			if ("|".equals(string))
				return new BitwiseOr(expression, tokens.remove().process(Precedence.BITWISE_OR));
			if ("&&".equals(string))
				return new And(expression, tokens.remove().process(Precedence.AND));
			if ("||".equals(string))
				return new Or(expression, tokens.remove().process(Precedence.OR));
			if (",".equals(string)) {
				Expression tail = tokens.remove().process(Precedence.SEQUENCE);
				if (tail instanceof Sequence)
					return new Sequence(expression, (Sequence)tail);
				return new Sequence(expression, null);
			}
			throw new ExpressionError("Not a binary operator: " + this);
		}
		
		@Override
		public boolean isHigherPrecedence(Precedence lastPrecedence) {
			return getBinaryPrecedence().compareTo(lastPrecedence) < 0;
		}
		
		private Precedence getBinaryPrecedence() {
			if ("*".equals(string) || "/".equals(string) || "%".equals(string))
				return Precedence.MULTIPLICATION;
			if ("+".equals(string) || "-".equals(string))
				return Precedence.ADDITION;
			if ("<<".equals(string) || ">>".equals(string))
				return Precedence.SHIFT;
			if ("<".equals(string) || "<=".equals(string) || ">".equals(string) || ">=".equals(string))
				return Precedence.COMPARISON;
			if ("==".equals(string) || "!=".equals(string))
				return Precedence.EQUALITY;
			if ("&".equals(string))
				return Precedence.BITWISE_AND;
			if ("^".equals(string))
				return Precedence.BITWISE_XOR;
			if ("|".equals(string))
				return Precedence.BITWISE_OR;
			if ("&&".equals(string))
				return Precedence.AND;
			if ("||".equals(string))
				return Precedence.OR;
			if (",".equals(string))
				return Precedence.SEQUENCE;
			if (")".equals(string))
				return Precedence.GROUPING;
			throw new ExpressionError("Not a binary operator: " + this);
		}
		
		public String toString() {
			return string;
		}
		
	}
	
	private static enum Precedence {
		UNARY,
		MULTIPLICATION,
		ADDITION,
		SHIFT,
		COMPARISON,
		EQUALITY,
		BITWISE_AND,
		BITWISE_XOR,
		BITWISE_OR,
		AND,
		OR,
		ASSIGNMENT,
		SEQUENCE,
		GROUPING,
		NONE
	}
	
	public static class ExpressionError extends RuntimeException {
		private static final long serialVersionUID = 1L;
		public ExpressionError(String message) {
			super("Expression error: " + message);
		}
	}
	
}
