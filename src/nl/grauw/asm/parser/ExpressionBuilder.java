package nl.grauw.asm.parser;

import java.util.ArrayDeque;
import java.util.Queue;

import nl.grauw.asm.expressions.Add;
import nl.grauw.asm.expressions.And;
import nl.grauw.asm.expressions.Complement;
import nl.grauw.asm.expressions.Divide;
import nl.grauw.asm.expressions.Equals;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.GreaterOrEquals;
import nl.grauw.asm.expressions.GreaterThan;
import nl.grauw.asm.expressions.Group;
import nl.grauw.asm.expressions.LessOrEquals;
import nl.grauw.asm.expressions.LessThan;
import nl.grauw.asm.expressions.LogicalAnd;
import nl.grauw.asm.expressions.LogicalOr;
import nl.grauw.asm.expressions.Modulo;
import nl.grauw.asm.expressions.Multiply;
import nl.grauw.asm.expressions.Negative;
import nl.grauw.asm.expressions.Not;
import nl.grauw.asm.expressions.NotEquals;
import nl.grauw.asm.expressions.Or;
import nl.grauw.asm.expressions.Positive;
import nl.grauw.asm.expressions.Sequence;
import nl.grauw.asm.expressions.ShiftLeft;
import nl.grauw.asm.expressions.ShiftRight;
import nl.grauw.asm.expressions.Subtract;
import nl.grauw.asm.expressions.Xor;

public class ExpressionBuilder {
	
	private final Queue<Token> tokens = new ArrayDeque<Token>();
	
	public void addValueToken(Expression value) {
		tokens.add(new ValueToken(value));
	}
	
	public void addOperatorToken(Operator operator) {
		tokens.add(new OperatorToken(operator));
	}
	
	public boolean hasExpression() {
		return !tokens.isEmpty();
	}
	
	public Expression getExpression() {
		if (tokens.isEmpty())
			throw new RuntimeException("No tokens queued.");
		
		Expression expression = tokens.remove().processValue(Precedence.NONE);
		while (!tokens.isEmpty() && tokens.peek().isHigherPrecedence(Precedence.NONE))
			expression = tokens.remove().processOperator(expression);
		
		if (!tokens.isEmpty())
			throw new RuntimeException("Not all tokens were processed: " + tokens);
		
		return expression;
	}
	
	private abstract class Token {
		
		public abstract Expression processValue(Precedence lastPrecedence);
		
		public abstract Expression processOperator(Expression expression);
		
		public abstract boolean isHigherPrecedence(Precedence lastPrecedence);
		
	}
	
	public class ValueToken extends Token {

		private final Expression value;
		
		public ValueToken(Expression value) {
			this.value = value;
		}
		
		@Override
		public Expression processValue(Precedence lastPrecedence) {
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
		
		private final Operator operator;
		
		public OperatorToken(Operator operator) {
			this.operator = operator;
		}
		
		@Override
		public Expression processValue(Precedence lastPrecedence) {
			switch (operator) {
			case POSITIVE:
				return new Positive(processNext());
			case NEGATIVE:
				return new Negative(processNext());
			case COMPLEMENT:
				return new Complement(processNext());
			case NOT:
				return new Not(processNext());
			case GROUP_OPEN:
				Expression expression = new Group(processNext());
				if (tokens.isEmpty())
					throw new ExpressionError("Mismatching parenthesis.");
				tokens.remove();
				while (!tokens.isEmpty() && tokens.peek().isHigherPrecedence(lastPrecedence))
					expression = tokens.remove().processOperator(expression);
				return expression;
			default:
				throw new ExpressionError("Not an unary operator, grouping operator or value: " + this);
			}
		}
		
		@Override
		public Expression processOperator(Expression expression) {
			switch (operator) {
			case GROUP_CLOSE:
				return expression;
			case MULTIPLY:
				return new Multiply(expression, processNext());
			case DIVIDE:
				return new Divide(expression, processNext());
			case MODULO:
				return new Modulo(expression, processNext());
			case ADD:
				return new Add(expression, processNext());
			case SUBTRACT:
				return new Subtract(expression, processNext());
			case SHIFT_LEFT:
				return new ShiftLeft(expression, processNext());
			case SHIFT_RIGHT:
				return new ShiftRight(expression, processNext());
			case LESS_THAN:
				return new LessThan(expression, processNext());
			case LESS_OR_EQUALS:
				return new LessOrEquals(expression, processNext());
			case GREATER_THAN:
				return new GreaterThan(expression, processNext());
			case GREATER_OR_EQUALS:
				return new GreaterOrEquals(expression, processNext());
			case EQUALS:
				return new Equals(expression, processNext());
			case NOT_EQUALS:
				return new NotEquals(expression, processNext());
			case BITWISE_AND:
				return new And(expression, processNext());
			case BITWISE_XOR:
				return new Xor(expression, processNext());
			case BITWISE_OR:
				return new Or(expression, processNext());
			case AND:
				return new LogicalAnd(expression, processNext());
			case OR:
				return new LogicalOr(expression, processNext());
			case SEQUENCE:
				Expression tail = processNext();
				if (tail instanceof Sequence)
					return new Sequence(expression, (Sequence)tail);
				return new Sequence(expression, new Sequence(tail, null));
			default:
				throw new ExpressionError("Not a binary operator: " + this);
			}
		}
		
		public Expression processNext() {
			return tokens.remove().processValue(operator.precedence);
		}
		
		@Override
		public boolean isHigherPrecedence(Precedence lastPrecedence) {
			return operator.precedence.compareTo(lastPrecedence) < 0;
		}
		
		public String toString() {
			return operator.toString();
		}
		
	}
	
	public static enum Operator {
		POSITIVE(Precedence.UNARY),
		NEGATIVE(Precedence.UNARY),
		COMPLEMENT(Precedence.UNARY),
		NOT(Precedence.UNARY),
		MULTIPLY(Precedence.MULTIPLICATION),
		DIVIDE(Precedence.MULTIPLICATION),
		MODULO(Precedence.MULTIPLICATION),
		ADD(Precedence.ADDITION),
		SUBTRACT(Precedence.ADDITION),
		SHIFT_LEFT(Precedence.SHIFT),
		SHIFT_RIGHT(Precedence.SHIFT),
		LESS_THAN(Precedence.COMPARISON),
		LESS_OR_EQUALS(Precedence.COMPARISON),
		GREATER_THAN(Precedence.COMPARISON),
		GREATER_OR_EQUALS(Precedence.COMPARISON),
		EQUALS(Precedence.EQUALITY),
		NOT_EQUALS(Precedence.EQUALITY),
		BITWISE_AND(Precedence.AND),
		BITWISE_XOR(Precedence.XOR),
		BITWISE_OR(Precedence.OR),
		AND(Precedence.LOGICAL_AND),
		OR(Precedence.LOGICAL_OR),
		GROUP_OPEN(Precedence.GROUPING),
		GROUP_CLOSE(Precedence.GROUPING),
		SEQUENCE(Precedence.SEQUENCE);
		
		private Precedence precedence;
		private Operator(Precedence precedence) {
			this.precedence = precedence;
		}
	}
	
	private static enum Precedence {
		UNARY,
		MULTIPLICATION,
		ADDITION,
		SHIFT,
		COMPARISON,
		EQUALITY,
		AND,
		XOR,
		OR,
		LOGICAL_AND,
		LOGICAL_OR,
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
