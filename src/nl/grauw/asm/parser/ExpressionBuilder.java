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
import nl.grauw.asm.expressions.ShiftLeft;
import nl.grauw.asm.expressions.ShiftRight;
import nl.grauw.asm.expressions.Subtract;

public class ExpressionBuilder {
	
	private Queue<Token> tokens = new ArrayDeque<Token>();
	
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
		
		Expression result = tokens.remove().process();
		
		if (!tokens.isEmpty())
			throw new RuntimeException("Not all tokens were processed: " + tokens);
		
		return result;
	}
	
	private abstract class Token {
		
		// consume()?
		public abstract Expression process();
		
		// process(expression, tokens)?
		public abstract Expression processOperator(Expression expression);
		
	}
	
	public class ValueToken extends Token {

		private Expression value;
		
		public ValueToken(Expression value) {
			this.value = value;
		}
		
		@Override
		public Expression process() {
			Expression expression = value;
			while (!tokens.isEmpty())  // || tokens.peekOperator().isLowerPrecedence()
				expression = tokens.remove().processOperator(expression);
			return expression;
		}
		
		@Override
		public Expression processOperator(Expression expression) {
			throw new ExpressionError("Not an operator: " + this);
		}
		
		public String toString() {
			return value.toString();
		}
		
	}
	
	public class OperatorToken extends Token {
		
		private String string;
		
		public OperatorToken(String string) {
			this.string = string;
		}
		
		public Expression process() {
			if ("+".equals(string))
				return new Positive(tokens.remove().process());
			if ("-".equals(string))
				return new Negate(tokens.remove().process());
			if ("~".equals(string))
				return new Complement(tokens.remove().process());
			if ("!".equals(string))
				return new Not(tokens.remove().process());
			if ("(".equals(string))
				return new Group(tokens.remove().process());
			throw new ExpressionError("Not an unary operator or value: " + this);
		}
		
		public Expression processOperator(Expression expression) {
			if (")".equals(string))
				return expression;
			if ("*".equals(string))
				return new Multiply(expression, tokens.remove().process());
			if ("/".equals(string))
				return new Divide(expression, tokens.remove().process());
			if ("%".equals(string))
				return new Modulo(expression, tokens.remove().process());
			if ("+".equals(string))
				return new Add(expression, tokens.remove().process());
			if ("-".equals(string))
				return new Subtract(expression, tokens.remove().process());
			if ("<<".equals(string))
				return new ShiftLeft(expression, tokens.remove().process());
			if (">>".equals(string))
				return new ShiftRight(expression, tokens.remove().process());
			if ("<".equals(string))
				return new LessThan(expression, tokens.remove().process());
			if ("<=".equals(string))
				return new LessOrEquals(expression, tokens.remove().process());
			if (">".equals(string))
				return new GreaterThan(expression, tokens.remove().process());
			if (">=".equals(string))
				return new GreaterOrEquals(expression, tokens.remove().process());
			if ("=".equals(string))
				return new Equals(expression, tokens.remove().process());
			if ("!=".equals(string))
				return new NotEquals(expression, tokens.remove().process());
			if ("&".equals(string))
				return new BitwiseAnd(expression, tokens.remove().process());
			if ("^".equals(string))
				return new BitwiseXor(expression, tokens.remove().process());
			if ("|".equals(string))
				return new BitwiseOr(expression, tokens.remove().process());
			if ("&&".equals(string))
				return new And(expression, tokens.remove().process());
			if ("||".equals(string))
				return new Or(expression, tokens.remove().process());
			throw new ExpressionError("Not a binary operator: " + this);
		}
		
		public String toString() {
			return string;
		}
		
	}
	
	public static class ExpressionError extends RuntimeException {
		private static final long serialVersionUID = 1L;
		public ExpressionError(String message) {
			super("Expression error: " + message);
		}
	}
	
}
