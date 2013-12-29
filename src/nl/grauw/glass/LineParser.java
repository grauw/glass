package nl.grauw.glass;

import nl.grauw.glass.expressions.CharacterLiteral;
import nl.grauw.glass.expressions.Current;
import nl.grauw.glass.expressions.ExpressionBuilder;
import nl.grauw.glass.expressions.Identifier;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.expressions.StringLiteral;
import nl.grauw.glass.expressions.ExpressionBuilder.Operator;

public class LineParser {
	
	private Line line;
	private State state;
	private StringBuilder accumulator = new StringBuilder();
	private ExpressionBuilder expressionBuilder = new ExpressionBuilder();
	
	public Line parse(String text, Line line) {
		this.line = line;
		state = labelStartState;
		
		int column = 0;
		try {
			for (int i = 0, length = text.length(); i < length; i++) {
				column = i;
				state = state.parse(text.charAt(i));
			}
			column = text.length();
			state = state.parse('\0');
			
			if (accumulator.length() > 0)
				throw new AssemblyException("Accumulator not consumed. Value: " + accumulator.toString());
			if (state != endState)
				throw new AssemblyException("Invalid line end state: " + state.getClass().getSimpleName());
		} catch(AssemblyException e) {
			e.setContext(line.getSourceFile(), line.getLineNumber(), text, column);
			throw e;
		}
		
		return line;
	}
	
	private abstract class State {
		public abstract State parse(char character);
		
		public boolean isWhitespace(char character) {
			return character == ' ' || character == '\t';
		}
		
		public boolean isIdentifier(char character) {
			return isIdentifierStart(character) || character >= '0' && character <= '9' ||
					character == '\'';
		}
		
		public boolean isIdentifierStart(char character) {
			return character >= 'a' && character <= 'z' || character >= 'A' && character <= 'Z' ||
					character == '_' || character == '.' || character == '?' || character == '@';
		}
		
	}
	
	private LabelStartState labelStartState = new LabelStartState();
	private class LabelStartState extends State {
		public State parse(char character) {
			if (isIdentifierStart(character)) {
				accumulator.append(character);
				return labelReadState;
			} else if (isWhitespace(character)) {
				return statementStartState;
			} else if (character == ';') {
				return commentReadState;
			} else if (character == '\0') {
				return endState;
			}
			throw new SyntaxError();
		}
	}
	
	private LabelReadState labelReadState = new LabelReadState();
	private class LabelReadState extends State {
		public State parse(char character) {
			if (isIdentifier(character)) {
				accumulator.append(character);
				return labelReadState;
			} else {
				line.setLabel(new Label(accumulator.toString(), line));
				accumulator.setLength(0);
				if (character == ':' || isWhitespace(character)) {
					return statementStartState;
				} else if (character == ';') {
					return commentReadState;
				} else if (character == '\0') {
					return endState;
				}
			}
			throw new SyntaxError();
		}
	}
	
	private StatementStartState statementStartState = new StatementStartState();
	private class StatementStartState extends State {
		public State parse(char character) {
			if (isIdentifierStart(character)) {
				accumulator.append(character);
				return statementReadState;
			} else if (isWhitespace(character)) {
				return statementStartState;
			} else if (character == ';') {
				return commentReadState;
			} else if (character == '\0') {
				return endState;
			}
			throw new SyntaxError();
		}
	}
	
	private StatementReadState statementReadState = new StatementReadState();
	private class StatementReadState extends State {
		public State parse(char character) {
			if (isIdentifier(character)) {
				accumulator.append(character);
				return statementReadState;
			} else {
				line.setMnemonic(accumulator.toString());
				accumulator.setLength(0);
				if (isWhitespace(character)) {
					return argumentStartState;
				} else if (character == ';') {
					return commentReadState;
				} else if (character == '\0') {
					return endState;
				}
			}
			throw new SyntaxError();
		}
	}
	
	private ArgumentStartState argumentStartState = new ArgumentStartState();
	private class ArgumentStartState extends State {
		public State parse(char character) {
			if (character == ';') {
				return commentReadState;
			} else if (character == '\0') {
				return endState;
			} else if (isWhitespace(character)) {
				return argumentStartState;
			} else {
				return argumentValueState.parse(character);
			}
		}
	}
	
	private ArgumentValueState argumentValueState = new ArgumentValueState();
	private class ArgumentValueState extends State {
		public State parse(char character) {
			if (isIdentifierStart(character)) {
				accumulator.append(character);
				return argumentIdentifierState;
			} else if (character == '$') {
				expressionBuilder.addValueToken(new Current(line));
				return argumentOperatorState;
			} else if (character >= '0' && character <= '9') {
				accumulator.append(character);
				return argumentNumberState;
			} else if (character == '"') {
				return argumentStringState;
			} else if (character == '\'') {
				return argumentCharacterState;
			} else if (character == '+') {
				expressionBuilder.addOperatorToken(Operator.POSITIVE);
				return argumentValueState;
			} else if (character == '-') {
				expressionBuilder.addOperatorToken(Operator.NEGATIVE);
				return argumentValueState;
			} else if (character == '~') {
				expressionBuilder.addOperatorToken(Operator.COMPLEMENT);
				return argumentValueState;
			} else if (character == '!') {
				expressionBuilder.addOperatorToken(Operator.NOT);
				return argumentValueState;
			} else if (character == '(') {
				expressionBuilder.addOperatorToken(Operator.GROUP_OPEN);
				return argumentValueState;
			} else if (isWhitespace(character)) {
				return argumentValueState;
			}
			throw new SyntaxError();
		}
	}
	
	private ArgumentIdentifierState argumentIdentifierState = new ArgumentIdentifierState();
	private class ArgumentIdentifierState extends State {
		public State parse(char character) {
			if (isIdentifier(character)) {
				accumulator.append(character);
				return argumentIdentifierState;
			} else {
				expressionBuilder.addValueToken(new Identifier(accumulator.toString(), line));
				accumulator.setLength(0);
				return argumentOperatorState.parse(character);
			}
		}
	}
	
	private ArgumentStringState argumentStringState = new ArgumentStringState();
	private class ArgumentStringState extends State {
		public State parse(char character) {
			if (character == '"') {
				expressionBuilder.addValueToken(new StringLiteral(accumulator.toString()));
				accumulator.setLength(0);
				return argumentOperatorState;
			} else if (character == '\\') {
				return argumentStringEscapeState;
			} else if (character == '\0') {
				throw new SyntaxError();
			} else {
				accumulator.append(character);
				return argumentStringState;
			}
		}
	}
	
	private ArgumentStringEscapeState argumentStringEscapeState = new ArgumentStringEscapeState();
	private class ArgumentStringEscapeState extends State {
		public State parse(char character) {
			if (character == '0') {
				accumulator.append('\0');
				return argumentStringState;
			} else if (character == 'a') {
				accumulator.append('\7');
				return argumentStringState;
			} else if (character == 't') {
				accumulator.append('\t');
				return argumentStringState;
			} else if (character == 'n') {
				accumulator.append('\n');
				return argumentStringState;
			} else if (character == 'f') {
				accumulator.append('\f');
				return argumentStringState;
			} else if (character == 'r') {
				accumulator.append('\r');
				return argumentStringState;
			} else if (character == 'e') {
				accumulator.append('\33');
				return argumentStringState;
			} else if (character == '"') {
				accumulator.append('"');
				return argumentStringState;
			} else if (character == '\'') {
				accumulator.append('\'');
				return argumentStringState;
			} else if (character == '\\') {
				accumulator.append('\\');
				return argumentStringState;
			} else if (character == '\0') {
				throw new SyntaxError();
			} else {
				throw new SyntaxError();
			}
		}
	}
	
	private ArgumentCharacterState argumentCharacterState = new ArgumentCharacterState();
	private class ArgumentCharacterState extends State {
		public State parse(char character) {
			if (character == '\\') {
				return argumentCharacterEscapeState;
			} else if (character == '\'' || character == '\0') {
				throw new SyntaxError();
			} else {
				accumulator.append(character);
				return argumentCharacterEndState;
			}
		}
	}
	
	private ArgumentCharacterEscapeState argumentCharacterEscapeState = new ArgumentCharacterEscapeState();
	private class ArgumentCharacterEscapeState extends State {
		public State parse(char character) {
			State state = argumentStringEscapeState.parse(character);
			if (state == argumentStringState)
				return argumentCharacterEndState;
			throw new AssemblyException("Unexpected state.");
		}
	}
	
	private ArgumentCharacterEndState argumentCharacterEndState = new ArgumentCharacterEndState();
	private class ArgumentCharacterEndState extends State {
		public State parse(char character) {
			if (character == '\'') {
				expressionBuilder.addValueToken(new CharacterLiteral(accumulator.charAt(0)));
				accumulator.setLength(0);
				return argumentOperatorState;
			} else {
				throw new SyntaxError();
			}
		}
	}
	
	private ArgumentNumberState argumentNumberState = new ArgumentNumberState();
	private class ArgumentNumberState extends State {
		public State parse(char character) {
			if (character >= '0' && character <= '9' || character >= 'A' && character <= 'F' ||
					character >= 'a' && character <= 'f') {
				accumulator.append(character);
				return argumentNumberState;
			} else {
				String string = accumulator.toString();
				if (character == 'H' || character == 'h') {
					int value = Integer.parseInt(string, 16);
					expressionBuilder.addValueToken(new IntegerLiteral(value));
					accumulator.setLength(0);
					return argumentOperatorState;
				} else if (character == 'O' || character == 'o') {
					int value = Integer.parseInt(string, 8);
					expressionBuilder.addValueToken(new IntegerLiteral(value));
					accumulator.setLength(0);
					return argumentOperatorState;
				} else {
					if (string.endsWith("B") || string.endsWith("b")) {
						int value = Integer.parseInt(string.substring(0, string.length() - 1), 2);
						expressionBuilder.addValueToken(new IntegerLiteral(value));
						accumulator.setLength(0);
					} else {
						int value = Integer.parseInt(string);
						expressionBuilder.addValueToken(new IntegerLiteral(value));
						accumulator.setLength(0);
					}
					return argumentOperatorState.parse(character);
				}
			}
		}
	}
	
	private ArgumentOperatorState argumentOperatorState = new ArgumentOperatorState();
	private class ArgumentOperatorState extends State {
		public State parse(char character) {
			if (character == ')') {
				expressionBuilder.addOperatorToken(Operator.GROUP_CLOSE);
				return argumentOperatorState;
			} else if (character == '*') {
				expressionBuilder.addOperatorToken(Operator.MULTIPLY);
				return argumentValueState;
			} else if (character == '/') {
				expressionBuilder.addOperatorToken(Operator.DIVIDE);
				return argumentValueState;
			} else if (character == '%') {
				expressionBuilder.addOperatorToken(Operator.MODULO);
				return argumentValueState;
			} else if (character == '+') {
				expressionBuilder.addOperatorToken(Operator.ADD);
				return argumentValueState;
			} else if (character == '-') {
				expressionBuilder.addOperatorToken(Operator.SUBTRACT);
				return argumentValueState;
			} else if (character == '<') {
				return argumentLessThanState;
			} else if (character == '>') {
				return argumentGreaterThanState;
			} else if (character == '=') {
				expressionBuilder.addOperatorToken(Operator.EQUALS);
				return argumentValueState;
			} else if (character == '!') {
				return argumentNotEqualsState;
			} else if (character == '&') {
				return argumentAndState;
			} else if (character == '^') {
				expressionBuilder.addOperatorToken(Operator.XOR);
				return argumentValueState;
			} else if (character == '|') {
				return argumentOrState;
			} else if (character == ',') {
				expressionBuilder.addOperatorToken(Operator.SEQUENCE);
				return argumentValueState;
			} else if (isWhitespace(character)) {
				return argumentOperatorState;
			} else if (character == ';') {
				line.setArguments(expressionBuilder.getExpression());
				return commentReadState;
			} else if (character == '\0') {
				line.setArguments(expressionBuilder.getExpression());
				return endState;
			}
			throw new SyntaxError();
		}
	}
	
	private ArgumentLessThanState argumentLessThanState = new ArgumentLessThanState();
	private class ArgumentLessThanState extends State {
		public State parse(char character) {
			if (character == '<') {
				expressionBuilder.addOperatorToken(Operator.SHIFT_LEFT);
				return argumentValueState;
			} else if (character == '=') {
				expressionBuilder.addOperatorToken(Operator.LESS_OR_EQUALS);
				return argumentValueState;
			} else {
				expressionBuilder.addOperatorToken(Operator.LESS_THAN);
				return argumentValueState.parse(character);
			}
		}
	}
	
	private ArgumentGreaterThanState argumentGreaterThanState = new ArgumentGreaterThanState();
	private class ArgumentGreaterThanState extends State {
		public State parse(char character) {
			if (character == '>') {
				expressionBuilder.addOperatorToken(Operator.SHIFT_RIGHT);
				return argumentValueState;
			} else if (character == '=') {
				expressionBuilder.addOperatorToken(Operator.GREATER_OR_EQUALS);
				return argumentValueState;
			} else {
				expressionBuilder.addOperatorToken(Operator.GREATER_THAN);
				return argumentValueState.parse(character);
			}
		}
	}
	
	private ArgumentNotEqualsState argumentNotEqualsState = new ArgumentNotEqualsState();
	private class ArgumentNotEqualsState extends State {
		public State parse(char character) {
			if (character == '=') {
				expressionBuilder.addOperatorToken(Operator.NOT_EQUALS);
				return argumentValueState;
			}
			throw new SyntaxError();
		}
	}
	
	private ArgumentAndState argumentAndState = new ArgumentAndState();
	private class ArgumentAndState extends State {
		public State parse(char character) {
			if (character == '&') {
				expressionBuilder.addOperatorToken(Operator.LOGICAL_AND);
				return argumentValueState;
			} else {
				expressionBuilder.addOperatorToken(Operator.AND);
				return argumentValueState.parse(character);
			}
		}
	}
	
	private ArgumentOrState argumentOrState = new ArgumentOrState();
	private class ArgumentOrState extends State {
		public State parse(char character) {
			if (character == '|') {
				expressionBuilder.addOperatorToken(Operator.LOGICAL_OR);
				return argumentValueState;
			} else {
				expressionBuilder.addOperatorToken(Operator.OR);
				return argumentValueState.parse(character);
			}
		}
	}
	
	private CommentReadState commentReadState = new CommentReadState();
	private class CommentReadState extends State {
		public State parse(char character) {
			if (character == '\0') {
				line.setComment(new Comment(accumulator.toString()));
				accumulator.setLength(0);
				return endState;
			} else {
				accumulator.append(character);
				return commentReadState;
			}
		}
	}
	
	private EndState endState = new EndState();
	private class EndState extends State {
		public State parse(char character) {
			throw new AssemblyException("End state reached but not all characters consumed.");
		}
	}
	
	public static class SyntaxError extends AssemblyException {
		private static final long serialVersionUID = 1L;
		
		public SyntaxError() {
			this(null);
		}
		
		public SyntaxError(Throwable cause) {
			super("Syntax error.", cause);
		}
		
	}
	
}
