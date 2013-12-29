package nl.grauw.glass.expressions;

import java.util.ArrayDeque;
import java.util.Deque;

import nl.grauw.glass.AssemblyException;


/**
 * Constructs an AST from the given expression tokens.
 * 
 * It uses a shunting yard algorithm.
 */
public class ExpressionBuilder {
	
	private Deque<Expression> operands = new ArrayDeque<Expression>();
	private Deque<Operator> operators = new ArrayDeque<Operator>();
	
	public ExpressionBuilder() {
		operators.push(Operator.SENTINEL);
	}
	
	public void addValueToken(Expression value) {
		operands.push(value);
	}
	
	public void addOperatorToken(Operator operator) {
		while (!operators.peek().yieldsTo(operator) && operators.peek() != Operator.SENTINEL)
			operators.pop().evaluate(operands);
		
		if (operator == Operator.GROUP_OPEN) {
			operators.push(operator);
			operators.push(Operator.SENTINEL);
		} else if (operator == Operator.GROUP_CLOSE) {
			if (operators.pop() != Operator.SENTINEL)
				throw new AssemblyException("Sentinel expected.");
			if (operators.peek() != Operator.GROUP_OPEN)
				throw new ExpressionError("Group open expected.");
		} else {
			operators.push(operator);
		}
	}
	
	public Expression getExpression() {
		if (operands.isEmpty() || operators.isEmpty())
			throw new AssemblyException("Operands / operators is empty: " + this);
		
		// process remainder
		while (operators.peek() != Operator.SENTINEL)
			operators.pop().evaluate(operands);
		
		if (operators.size() > 1 && operators.peek() == Operator.SENTINEL)
			throw new ExpressionError("Group close expected.");
		if (operands.size() > 1 || operators.size() != 1)
			throw new AssemblyException("Not all operands / operators were processed: " + this);
		
		return operands.pop();
	}
	
	public String toString() {
		return "" + operands + " / " + operators;
	}
	
	public enum Operator {
		POSITIVE(Precedence.UNARY, true),
		NEGATIVE(Precedence.UNARY, true),
		COMPLEMENT(Precedence.UNARY, true),
		NOT(Precedence.UNARY, true),
		MULTIPLY(Precedence.MULTIPLICATION, true),
		DIVIDE(Precedence.MULTIPLICATION, true),
		MODULO(Precedence.MULTIPLICATION, true),
		ADD(Precedence.ADDITION, true),
		SUBTRACT(Precedence.ADDITION, true),
		SHIFT_LEFT(Precedence.SHIFT, true),
		SHIFT_RIGHT(Precedence.SHIFT, true),
		LESS_THAN(Precedence.COMPARISON, true),
		LESS_OR_EQUALS(Precedence.COMPARISON, true),
		GREATER_THAN(Precedence.COMPARISON, true),
		GREATER_OR_EQUALS(Precedence.COMPARISON, true),
		EQUALS(Precedence.EQUALITY, true),
		NOT_EQUALS(Precedence.EQUALITY, true),
		AND(Precedence.AND, true),
		XOR(Precedence.XOR, true),
		OR(Precedence.OR, true),
		LOGICAL_AND(Precedence.LOGICAL_AND, true),
		LOGICAL_OR(Precedence.LOGICAL_OR, true),
		GROUP_OPEN(Precedence.GROUPING, true),
		GROUP_CLOSE(Precedence.NONE, true),
		SEQUENCE(Precedence.SEQUENCE, false),
		SENTINEL(Precedence.NONE, true);
		
		private Precedence precedence;
		private boolean leftAssociative;
		private Operator(Precedence precedence, boolean leftAssociative) {
			this.precedence = precedence;
			this.leftAssociative = leftAssociative;
		}
		
		public boolean yieldsTo(Operator other) {
			if (leftAssociative)
				return precedence.ordinal() > other.precedence.ordinal();
			else
				return precedence.ordinal() >= other.precedence.ordinal();
		}
		
		public void evaluate(Deque<Expression> operands) {
			Expression operandRight = operands.pop();
			Expression result;
			if (precedence == Precedence.UNARY || precedence == Precedence.GROUPING) {
				result = evaluate(operandRight);
			} else {
				result = evaluate(operands.pop(), operandRight);
			}
			operands.push(result);
		}
		
		private Expression evaluate(Expression operand) {
			switch (this) {
			case POSITIVE:
				return new Positive(operand);
			case NEGATIVE:
				return new Negative(operand);
			case COMPLEMENT:
				return new Complement(operand);
			case NOT:
				return new Not(operand);
			case GROUP_OPEN:
				return new Group(operand);
			default:
				throw new AssemblyException("Not an unary or group operator: " + this);
			}
		}
		
		private Expression evaluate(Expression operand1, Expression operand2) {
			switch (this) {
			case MULTIPLY:
				return new Multiply(operand1, operand2);
			case DIVIDE:
				return new Divide(operand1, operand2);
			case MODULO:
				return new Modulo(operand1, operand2);
			case ADD:
				return new Add(operand1, operand2);
			case SUBTRACT:
				return new Subtract(operand1, operand2);
			case SHIFT_LEFT:
				return new ShiftLeft(operand1, operand2);
			case SHIFT_RIGHT:
				return new ShiftRight(operand1, operand2);
			case LESS_THAN:
				return new LessThan(operand1, operand2);
			case LESS_OR_EQUALS:
				return new LessOrEquals(operand1, operand2);
			case GREATER_THAN:
				return new GreaterThan(operand1, operand2);
			case GREATER_OR_EQUALS:
				return new GreaterOrEquals(operand1, operand2);
			case EQUALS:
				return new Equals(operand1, operand2);
			case NOT_EQUALS:
				return new NotEquals(operand1, operand2);
			case AND:
				return new And(operand1, operand2);
			case XOR:
				return new Xor(operand1, operand2);
			case OR:
				return new Or(operand1, operand2);
			case LOGICAL_AND:
				return new LogicalAnd(operand1, operand2);
			case LOGICAL_OR:
				return new LogicalOr(operand1, operand2);
			case SEQUENCE:
				return new Sequence(operand1, operand2);
			default:
				throw new ExpressionError("Not a binary operator: " + this);
			}
		}
	}
	
	private enum Precedence {
		GROUPING,
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
		NONE
	}
	
	public static class ExpressionError extends AssemblyException {
		private static final long serialVersionUID = 1L;
		public ExpressionError(String message) {
			super("Expression error: " + message);
		}
	}
	
}