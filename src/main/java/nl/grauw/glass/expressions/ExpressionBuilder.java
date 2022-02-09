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
		operator.tryEvaluate();
	}

	public Expression getExpression() {
		if (operands.isEmpty() || operators.isEmpty())
			throw new AssemblyException("Operands / operators is empty: " + this);

		// process remainder
		SENTINEL.tryEvaluate();

		if (operators.pop() != SENTINEL)
			throw new AssemblyException("Sentinel expected.");
		if (operators.size() > 1 && operators.peek() == SENTINEL)
			throw new ExpressionError("Group close expected.");
		if (operands.size() > 1 || operators.size() != 1)
			throw new AssemblyException("Not all operands / operators were processed: " + this);

		return operands.pop();
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
		private Associativity associativity;
		private String token;

		private Operator(Precedence precedence, Associativity associativity, String token) {
			this.precedence = precedence;
			this.associativity = associativity;
			this.token = token;
		}

		public void tryEvaluate() {
			while (!operators.peek().yieldsTo(this))
				operators.pop().evaluate();
			operators.push(this);
		}

		public boolean yieldsTo(Operator other) {
			if (associativity == Associativity.LEFT_TO_RIGHT)
				return precedence.ordinal() > other.precedence.ordinal();
			else
				return precedence.ordinal() >= other.precedence.ordinal();
		}

		public abstract void evaluate();

		@Override
		public String toString() {
			return token;
		}
	}

	public final Operator POSITIVE = new Operator(Precedence.UNARY, Associativity.RIGHT_TO_LEFT, "+") {
		@Override
		public void evaluate() {
			operands.push(new Positive(operands.pop()));
		};
	};

	public final Operator NEGATIVE = new Operator(Precedence.UNARY, Associativity.RIGHT_TO_LEFT, "-") {
		@Override
		public void evaluate() {
			operands.push(new Negative(operands.pop()));
		};
	};

	public final Operator COMPLEMENT = new Operator(Precedence.UNARY, Associativity.RIGHT_TO_LEFT, "~") {
		@Override
		public void evaluate() {
			operands.push(new Complement(operands.pop()));
		};
	};

	public final Operator NOT = new Operator(Precedence.UNARY, Associativity.RIGHT_TO_LEFT, "!") {
		@Override
		public void evaluate() {
			operands.push(new Not(operands.pop()));
		};
	};

	public final Operator MEMBER = new Operator(Precedence.MEMBER, Associativity.LEFT_TO_RIGHT, ".") {
		@Override
		public void evaluate() {
			Expression operandRight = operands.pop();
			if (!(operandRight instanceof Identifier))
				throw new ExpressionError("Member operator right hand side must be an identifier.");
			operands.push(new Member(operands.pop(), (Identifier)operandRight));
		};
	};

	public final Operator MULTIPLY = new Operator(Precedence.MULTIPLICATION, Associativity.LEFT_TO_RIGHT, "*") {
		@Override
		public void evaluate() {
			Expression operandRight = operands.pop();
			operands.push(new Multiply(operands.pop(), operandRight));
		};
	};

	public final Operator DIVIDE = new Operator(Precedence.MULTIPLICATION, Associativity.LEFT_TO_RIGHT, "/") {
		@Override
		public void evaluate() {
			Expression operandRight = operands.pop();
			operands.push(new Divide(operands.pop(), operandRight));
		};
	};

	public final Operator MODULO = new Operator(Precedence.MULTIPLICATION, Associativity.LEFT_TO_RIGHT, "%") {
		@Override
		public void evaluate() {
			Expression operandRight = operands.pop();
			operands.push(new Modulo(operands.pop(), operandRight));
		};
	};

	public final Operator ADD = new Operator(Precedence.ADDITION, Associativity.LEFT_TO_RIGHT, "+") {
		@Override
		public void evaluate() {
			Expression operandRight = operands.pop();
			operands.push(new Add(operands.pop(), operandRight));
		};
	};

	public final Operator SUBTRACT = new Operator(Precedence.ADDITION, Associativity.LEFT_TO_RIGHT, "-") {
		@Override
		public void evaluate() {
			Expression operandRight = operands.pop();
			operands.push(new Subtract(operands.pop(), operandRight));
		};
	};

	public final Operator SHIFT_LEFT = new Operator(Precedence.SHIFT, Associativity.LEFT_TO_RIGHT, "<<") {
		@Override
		public void evaluate() {
			Expression operandRight = operands.pop();
			operands.push(new ShiftLeft(operands.pop(), operandRight));
		};
	};

	public final Operator SHIFT_RIGHT = new Operator(Precedence.SHIFT, Associativity.LEFT_TO_RIGHT, ">>") {
		@Override
		public void evaluate() {
			Expression operandRight = operands.pop();
			operands.push(new ShiftRight(operands.pop(), operandRight));
		};
	};

	public final Operator SHIFT_RIGHT_UNSIGNED = new Operator(Precedence.SHIFT, Associativity.LEFT_TO_RIGHT, ">>>") {
		@Override
		public void evaluate() {
			Expression operandRight = operands.pop();
			operands.push(new ShiftRightUnsigned(operands.pop(), operandRight));
		};
	};

	public final Operator LESS_THAN = new Operator(Precedence.COMPARISON, Associativity.LEFT_TO_RIGHT, "<") {
		@Override
		public void evaluate() {
			Expression operandRight = operands.pop();
			operands.push(new LessThan(operands.pop(), operandRight));
		};
	};

	public final Operator LESS_OR_EQUALS = new Operator(Precedence.COMPARISON, Associativity.LEFT_TO_RIGHT, "<=") {
		@Override
		public void evaluate() {
			Expression operandRight = operands.pop();
			operands.push(new LessOrEquals(operands.pop(), operandRight));
		};
	};

	public final Operator GREATER_THAN = new Operator(Precedence.COMPARISON, Associativity.LEFT_TO_RIGHT, ">") {
		@Override
		public void evaluate() {
			Expression operandRight = operands.pop();
			operands.push(new GreaterThan(operands.pop(), operandRight));
		};
	};

	public final Operator GREATER_OR_EQUALS = new Operator(Precedence.COMPARISON, Associativity.LEFT_TO_RIGHT, ">=") {
		@Override
		public void evaluate() {
			Expression operandRight = operands.pop();
			operands.push(new GreaterOrEquals(operands.pop(), operandRight));
		};
	};

	public final Operator EQUALS = new Operator(Precedence.EQUALITY, Associativity.LEFT_TO_RIGHT, "=") {
		@Override
		public void evaluate() {
			Expression operandRight = operands.pop();
			operands.push(new Equals(operands.pop(), operandRight));
		};
	};

	public final Operator NOT_EQUALS = new Operator(Precedence.EQUALITY, Associativity.LEFT_TO_RIGHT, "!=") {
		@Override
		public void evaluate() {
			Expression operandRight = operands.pop();
			operands.push(new NotEquals(operands.pop(), operandRight));
		};
	};

	public final Operator AND = new Operator(Precedence.AND, Associativity.LEFT_TO_RIGHT, "&") {
		@Override
		public void evaluate() {
			Expression operandRight = operands.pop();
			operands.push(new And(operands.pop(), operandRight));
		};
	};

	public final Operator XOR = new Operator(Precedence.XOR, Associativity.LEFT_TO_RIGHT, "^") {
		@Override
		public void evaluate() {
			Expression operandRight = operands.pop();
			operands.push(new Xor(operands.pop(), operandRight));
		};
	};

	public final Operator OR = new Operator(Precedence.OR, Associativity.LEFT_TO_RIGHT, "|") {
		@Override
		public void evaluate() {
			Expression operandRight = operands.pop();
			operands.push(new Or(operands.pop(), operandRight));
		};
	};

	public final Operator LOGICAL_AND = new Operator(Precedence.LOGICAL_AND, Associativity.LEFT_TO_RIGHT, "&&") {
		@Override
		public void evaluate() {
			Expression operandRight = operands.pop();
			operands.push(new LogicalAnd(operands.pop(), operandRight));
		};
	};

	public final Operator LOGICAL_OR = new Operator(Precedence.LOGICAL_OR, Associativity.LEFT_TO_RIGHT, "||") {
		@Override
		public void evaluate() {
			Expression operandRight = operands.pop();
			operands.push(new LogicalOr(operands.pop(), operandRight));
		};
	};

	public final Operator ANNOTATION = new Operator(Precedence.ANNOTATION, Associativity.RIGHT_TO_LEFT, " ") {
		@Override
		public void evaluate() {
			Expression operandRight = operands.pop();
			Expression operandLeft = operands.pop();
			if (!(operandLeft instanceof Identifier))
				throw new ExpressionError("Annotation left hand side must be an identifier.");
			operands.push(new Annotation((Identifier)operandLeft, operandRight));
		};
	};

	public final Operator SEQUENCE = new Operator(Precedence.SEQUENCE, Associativity.RIGHT_TO_LEFT, ",") {
		@Override
		public void evaluate() {
			Expression operandRight = operands.pop();
			operands.push(new Sequence(operands.pop(), operandRight));
		};
	};

	public final Operator TERNARYIF = new Operator(Precedence.TERNARYIFELSE, Associativity.RIGHT_TO_LEFT, "?") {
		@Override
		public void evaluate() {
			throw new ExpressionError("Ternary if (?) without else (:).");
		};
	};

	public final Operator TERNARYELSE = new Operator(Precedence.TERNARYIFELSE, Associativity.RIGHT_TO_LEFT, ":") {
		@Override
		public void evaluate() {
			Expression operandRight = operands.pop();
			while (operators.peek() == TERNARYELSE) {
				operators.pop().evaluate();
			}
			if (operators.peek() == TERNARYIF) {
				operators.pop();
				Expression operandMiddle = operands.pop();
				operands.push(new IfElse(operands.pop(), operandMiddle, operandRight));
			} else {
				throw new ExpressionError("Ternary else (:) without if (?).");
			}
		};
	};

	public final Operator GROUP_OPEN = new Operator(Precedence.GROUPING, Associativity.RIGHT_TO_LEFT, "(") {
		@Override
		public void evaluate() {
			operands.push(new Group(operands.pop()));
		};
		@Override
		public void tryEvaluate() {
			super.tryEvaluate();
			operators.push(SENTINEL);
			groupCount++;
		}
	};

	public final Operator GROUP_CLOSE = new Operator(Precedence.NONE, Associativity.LEFT_TO_RIGHT, ")") {
		@Override
		public void evaluate() {
			throw new AssemblyException("Can not evaluate group close.");
		};
		@Override
		public void tryEvaluate() {
			super.tryEvaluate();
			if (operators.pop() != GROUP_CLOSE)
				throw new AssemblyException("Group close expected.");
			if (operators.pop() != SENTINEL)
				throw new AssemblyException("Sentinel expected.");
			if (operators.peek() != GROUP_OPEN)
				throw new ExpressionError("Group open expected.");
			groupCount--;
		}
	};

	public final Operator INDEX_OPEN = new Operator(Precedence.MEMBER, Associativity.LEFT_TO_RIGHT, "[") {
		@Override
		public void evaluate() {
			Expression operandRight = operands.pop();
			operands.push(new Index(operands.pop(), operandRight));
		};
		@Override
		public void tryEvaluate() {
			super.tryEvaluate();
			operators.push(SENTINEL);
			groupCount++;
		}
	};

	public final Operator INDEX_CLOSE = new Operator(Precedence.NONE, Associativity.LEFT_TO_RIGHT, "]") {
		@Override
		public void evaluate() {
			throw new AssemblyException("Can not evaluate index close.");
		};
		@Override
		public void tryEvaluate() {
			super.tryEvaluate();
			if (operators.pop() != INDEX_CLOSE)
				throw new AssemblyException("Index close expected.");
			if (operators.pop() != SENTINEL)
				throw new AssemblyException("Sentinel expected.");
			if (operators.peek() != INDEX_OPEN)
				throw new ExpressionError("Index open expected.");
			groupCount--;
		}
	};

	public final Operator SENTINEL = new Operator(Precedence.NONE, Associativity.RIGHT_TO_LEFT, "#") {
		@Override
		public void evaluate() {
			throw new AssemblyException("Can not evaluate sentinel.");
		};
	};

	private enum Precedence {
		GROUPING,
		MEMBER,
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

	private enum Associativity {
		LEFT_TO_RIGHT,
		RIGHT_TO_LEFT
	}

	public static class ExpressionError extends AssemblyException {
		private static final long serialVersionUID = 1L;
		public ExpressionError(String message) {
			super("Expression error: " + message);
		}
	}

}
