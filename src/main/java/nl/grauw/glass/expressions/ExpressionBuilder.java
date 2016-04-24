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
	private int groupCount = 0;
	
	public ExpressionBuilder() {
		operators.push(SENTINEL);
	}
	
	public void addValueToken(Expression value) {
		operands.push(value);
	}
	
	public void addOperatorToken(Operator operator) {
		evaluateNotYieldingTo(operator);
		
		if (operator == GROUP_OPEN) {
			groupCount++;
			operators.push(operator);
			operators.push(SENTINEL);
		} else if (operator == GROUP_CLOSE) {
			groupCount--;
			if (operators.pop() != SENTINEL)
				throw new AssemblyException("Sentinel expected.");
			if (operators.peek() != GROUP_OPEN)
				throw new ExpressionError("Group open expected.");
		} else {
			operators.push(operator);
		}
	}
	
	public Expression getExpression() {
		if (operands.isEmpty() || operators.isEmpty())
			throw new AssemblyException("Operands / operators is empty: " + this);
		
		// process remainder
		evaluateNotYieldingTo(SENTINEL);
		
		if (operators.size() > 1 && operators.peek() == SENTINEL)
			throw new ExpressionError("Group close expected.");
		if (operands.size() > 1 || operators.size() != 1)
			throw new AssemblyException("Not all operands / operators were processed: " + this);
		
		return operands.pop();
	}
	
	private void evaluateNotYieldingTo(Operator operator) {
		while (!operators.peek().yieldsTo(operator))
			operands.push(operators.pop().evaluate());
	}
	
	public boolean hasOpenGroup()
	{
		return groupCount > 0;
	}
	
	public String toString() {
		return "" + operands + " / " + operators;
	}
	
	private abstract class Operator {
		
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
		
		public abstract Expression evaluate();
	}
	
	public final Operator POSITIVE = new Operator(Precedence.UNARY, false) {
		@Override
		public Expression evaluate() {
			return new Positive(operands.pop());
		};
	};
	
	public final Operator NEGATIVE = new Operator(Precedence.UNARY, false) {
		@Override
		public Expression evaluate() {
			return new Negative(operands.pop());
		};
	};
	
	public final Operator COMPLEMENT = new Operator(Precedence.UNARY, false) {
		@Override
		public Expression evaluate() {
			return new Complement(operands.pop());
		};
	};
	
	public final Operator NOT = new Operator(Precedence.UNARY, false) {
		@Override
		public Expression evaluate() {
			return new Not(operands.pop());
		};
	};
	
	public final Operator MULTIPLY = new Operator(Precedence.MULTIPLICATION, true) {
		@Override
		public Expression evaluate() {
			Expression operandRight = operands.pop();
			return new Multiply(operands.pop(), operandRight);
		};
	};
	
	public final Operator DIVIDE = new Operator(Precedence.MULTIPLICATION, true) {
		@Override
		public Expression evaluate() {
			Expression operandRight = operands.pop();
			return new Divide(operands.pop(), operandRight);
		};
	};
	
	public final Operator MODULO = new Operator(Precedence.MULTIPLICATION, true) {
		@Override
		public Expression evaluate() {
			Expression operandRight = operands.pop();
			return new Modulo(operands.pop(), operandRight);
		};
	};
	
	public final Operator ADD = new Operator(Precedence.ADDITION, true) {
		@Override
		public Expression evaluate() {
			Expression operandRight = operands.pop();
			return new Add(operands.pop(), operandRight);
		};
	};
	
	public final Operator SUBTRACT = new Operator(Precedence.ADDITION, true) {
		@Override
		public Expression evaluate() {
			Expression operandRight = operands.pop();
			return new Subtract(operands.pop(), operandRight);
		};
	};
	
	public final Operator SHIFT_LEFT = new Operator(Precedence.SHIFT, true) {
		@Override
		public Expression evaluate() {
			Expression operandRight = operands.pop();
			return new ShiftLeft(operands.pop(), operandRight);
		};
	};
	
	public final Operator SHIFT_RIGHT = new Operator(Precedence.SHIFT, true) {
		@Override
		public Expression evaluate() {
			Expression operandRight = operands.pop();
			return new ShiftRight(operands.pop(), operandRight);
		};
	};
	
	public final Operator LESS_THAN = new Operator(Precedence.COMPARISON, true) {
		@Override
		public Expression evaluate() {
			Expression operandRight = operands.pop();
			return new LessThan(operands.pop(), operandRight);
		};
	};
	
	public final Operator LESS_OR_EQUALS = new Operator(Precedence.COMPARISON, true) {
		@Override
		public Expression evaluate() {
			Expression operandRight = operands.pop();
			return new LessOrEquals(operands.pop(), operandRight);
		};
	};
	
	public final Operator GREATER_THAN = new Operator(Precedence.COMPARISON, true) {
		@Override
		public Expression evaluate() {
			Expression operandRight = operands.pop();
			return new GreaterThan(operands.pop(), operandRight);
		};
	};
	
	public final Operator GREATER_OR_EQUALS = new Operator(Precedence.COMPARISON, true) {
		@Override
		public Expression evaluate() {
			Expression operandRight = operands.pop();
			return new GreaterOrEquals(operands.pop(), operandRight);
		};
	};
	
	public final Operator EQUALS = new Operator(Precedence.EQUALITY, true) {
		@Override
		public Expression evaluate() {
			Expression operandRight = operands.pop();
			return new Equals(operands.pop(), operandRight);
		};
	};
	
	public final Operator NOT_EQUALS = new Operator(Precedence.EQUALITY, true) {
		@Override
		public Expression evaluate() {
			Expression operandRight = operands.pop();
			return new NotEquals(operands.pop(), operandRight);
		};
	};
	
	public final Operator AND = new Operator(Precedence.AND, true) {
		@Override
		public Expression evaluate() {
			Expression operandRight = operands.pop();
			return new And(operands.pop(), operandRight);
		};
	};
	
	public final Operator XOR = new Operator(Precedence.XOR, true) {
		@Override
		public Expression evaluate() {
			Expression operandRight = operands.pop();
			return new Xor(operands.pop(), operandRight);
		};
	};
	
	public final Operator OR = new Operator(Precedence.OR, true) {
		@Override
		public Expression evaluate() {
			Expression operandRight = operands.pop();
			return new Or(operands.pop(), operandRight);
		};
	};
	
	public final Operator LOGICAL_AND = new Operator(Precedence.LOGICAL_AND, true) {
		@Override
		public Expression evaluate() {
			Expression operandRight = operands.pop();
			return new LogicalAnd(operands.pop(), operandRight);
		};
	};
	
	public final Operator LOGICAL_OR = new Operator(Precedence.LOGICAL_OR, true) {
		@Override
		public Expression evaluate() {
			Expression operandRight = operands.pop();
			return new LogicalOr(operands.pop(), operandRight);
		};
	};
	
	public final Operator ANNOTATION = new Operator(Precedence.ANNOTATION, false) {
		@Override
		public Expression evaluate() {
			Expression operandRight = operands.pop();
			return new Annotation(operands.pop(), operandRight);
		};
	};
	
	public final Operator SEQUENCE = new Operator(Precedence.SEQUENCE, false) {
		@Override
		public Expression evaluate() {
			Expression operandRight = operands.pop();
			return new Sequence(operands.pop(), operandRight);
		};
	};
	
	public final Operator TERNARYIF = new Operator(Precedence.TERNARYIFELSE, false) {
		@Override
		public Expression evaluate() {
			throw new ExpressionError("Ternary if (?) without else (:).");
		};
	};
	
	public final Operator TERNARYELSE = new Operator(Precedence.TERNARYIFELSE, false) {
		@Override
		public Expression evaluate() {
			Expression operandRight = operands.pop();
			while (operators.peek() == TERNARYELSE) {
				operands.push(operators.pop().evaluate());
			}
			if (operators.peek() == TERNARYIF) {
				operators.pop();
				Expression operandMiddle = operands.pop();
				return new IfElse(operands.pop(), operandMiddle, operandRight);
			} else {
				throw new ExpressionError("Ternary else (:) without if (?).");
			}
		};
	};
	
	public final Operator GROUP_OPEN = new Operator(Precedence.GROUPING, true) {
		@Override
		public Expression evaluate() {
			return new Group(operands.pop());
		};
	};
	
	public final Operator GROUP_CLOSE = new Operator(Precedence.NONE, true) {
		@Override
		public Expression evaluate() {
			throw new AssemblyException("Can not evaluate group close.");
		};
	};
	
	public final Operator SENTINEL = new Operator(Precedence.NONE, false) {
		@Override
		public Expression evaluate() {
			throw new AssemblyException("Can not evaluate sentinel.");
		};
	};
	
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
		TERNARYIFELSE,
		ANNOTATION,
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
