package nl.grauw.asm.parser;

import java.io.File;

import nl.grauw.asm.Comment;
import nl.grauw.asm.Label;
import nl.grauw.asm.Line;
import nl.grauw.asm.Statement;

public class LineParser {
	
	private State state;
	private StringBuilder accumulator = new StringBuilder();
	private Label label;
	private Statement statement;
	private ExpressionBuilder expressionBuilder = new ExpressionBuilder();
	private Comment comment;
	private File sourceFile;
	private int lineNumber;
	private String text;
	private int columnNumber;
	
	public Line parse(String text, File sourceFile, int lineNumber) {
		if (accumulator.length() > 0)
			throw new RuntimeException("Accumulator not consumed. Value: " + accumulator.toString());
		
		state = labelStartState;
		label = null;
		statement = null;
		comment = null;
		this.sourceFile = sourceFile;
		this.lineNumber = lineNumber;
		this.text = text;
		
		try {
			for (int i = 0, length = text.length(); i < length; i++) {
				columnNumber = i;
				state = state.parse(text.charAt(i));
			}
			columnNumber = text.length();
			state = state.parse('\0');
			if (state != endState)
				throw new RuntimeException("Invalid line end state: " + state.getClass().getSimpleName());
		} catch(NumberFormatException e) {
			throw e;
		}
		
		return new Line(label, statement, comment, sourceFile, lineNumber);
	}
	
	private abstract class State {
		public abstract State parse(char character);
		
		public boolean isWhitespace(char character) {
			return character == ' ' || character == '\t';
		}
		
		public boolean isIdentifier(char character) {
			return isIdentifierStart(character) || character >= '0' && character <= '9' ||
					character == '\'';  // for ex af,af'...
		}
		
		public boolean isIdentifierStart(char character) {
			return character >= 'a' && character <= 'z' || character >= 'A' && character <= 'Z' ||
					character == '_' || character == '.' || character == '?' || character == '@';
		}
		
		public boolean isOperator(char character) {
			return character == '!' || character == '%' || character == '&' || character == '*' ||
					character == '+' || character == '-' || character == '/' || character == ':' ||
					character == '<' || character == '=' || character == '>' || character == '?' ||
					character == '^' || character == '|' || character == '~' || character == '#';
				// character == "," (sequence operator)
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
				label = new Label(accumulator.toString());
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
			if (isIdentifierStart(character)) {
				accumulator.append(character);
				return statementReadState;
			} else {
				statement = new Statement(accumulator.toString());
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
			if (isIdentifier(character)) {
				accumulator.append(character);
				return argumentIdentifierState;
			} else if (character >= '0' && character <= '9') {
				accumulator.append(character);
				return argumentNumberState;
			} else {
				return argumentNoIdentifierState.parse(character);
			}
		}
	}
	
	private ArgumentNoIdentifierState argumentNoIdentifierState = new ArgumentNoIdentifierState();
	private class ArgumentNoIdentifierState extends State {
		public State parse(char character) {
			if (character == '(') {
				expressionBuilder.AddToken(new ExpressionBuilder.GroupOpenToken());
				return argumentStartState;
			} else if (character == ')') {
				expressionBuilder.AddToken(new ExpressionBuilder.GroupCloseToken());
				return argumentStartState;
			} else if (character == '$') {
				expressionBuilder.AddToken(new ExpressionBuilder.CurrentToken());
				return argumentStartState;
			} else if (character == '"') {
				return argumentStringState;
			} else if (isOperator(character)) {
				accumulator.append(character);
				return argumentOperatorState;
			} else if (isWhitespace(character)) {
				return argumentStartState;
			} else if (character == ',') {
				statement.AddArgument(expressionBuilder.getExpression());
				return argumentStartState;
			} else if (character == ';') {
				if (expressionBuilder.hasExpression())
					statement.AddArgument(expressionBuilder.getExpression());
				return commentReadState;
			} else if (character == '\0') {
				if (expressionBuilder.hasExpression())
					statement.AddArgument(expressionBuilder.getExpression());
				return endState;
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
				expressionBuilder.AddToken(new ExpressionBuilder.IdentifierToken(accumulator.toString()));
				accumulator.setLength(0);
				return argumentNoIdentifierState.parse(character);
			}
		}
	}
	
	private ArgumentStringState argumentStringState = new ArgumentStringState();
	private class ArgumentStringState extends State {
		public State parse(char character) {
			if (character == '"') {
				expressionBuilder.AddToken(new ExpressionBuilder.StringLiteralToken(accumulator.toString()));
				accumulator.setLength(0);
				return argumentStartState;
			} else if (character == '\\') {
				accumulator.append(character);  // TODO
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
			accumulator.append(character);
			if (character == '\0') {
				throw new SyntaxError();
			} else {
				return argumentStringState;
			}
		}
	}
	
	private ArgumentNumberState argumentNumberState = new ArgumentNumberState();
	private class ArgumentNumberState extends State {
		public State parse(char character) {
			if (character >= '0' || character <= '9' || character >= 'A' || character <= 'F' ||
					character >= 'a' || character <= 'f') {
				accumulator.append(character);
				return argumentNumberState;
			} else if (character == 'H' || character == 'h') {
				int value = Integer.parseInt(accumulator.toString(), 16);
				expressionBuilder.AddToken(new ExpressionBuilder.IntegerLiteralToken(value));
				accumulator.setLength(0);
				return argumentStartState;
			} else if (character == 'B' || character == 'b') {
				int value = Integer.parseInt(accumulator.toString(), 2);
				expressionBuilder.AddToken(new ExpressionBuilder.IntegerLiteralToken(value));
				accumulator.setLength(0);
				return argumentStartState;
			} else if (character == 'O' || character == 'o') {
				int value = Integer.parseInt(accumulator.toString(), 8);
				expressionBuilder.AddToken(new ExpressionBuilder.IntegerLiteralToken(value));
				accumulator.setLength(0);
				return argumentStartState;
			} else {
				int value = Integer.parseInt(accumulator.toString());
				expressionBuilder.AddToken(new ExpressionBuilder.IntegerLiteralToken(value));
				accumulator.setLength(0);
				return argumentNoIdentifierState.parse(character);
			}
		}
	}
	
	private ArgumentOperatorState argumentOperatorState = new ArgumentOperatorState();
	private class ArgumentOperatorState extends State {
		public State parse(char character) {
			if (isOperator(character)) {
				accumulator.append(character);
				expressionBuilder.AddToken(new ExpressionBuilder.OperatorToken(accumulator.toString()));
				accumulator.setLength(0);
				return argumentStartState;
			} else {
				expressionBuilder.AddToken(new ExpressionBuilder.OperatorToken(accumulator.toString()));
				accumulator.setLength(0);
				return argumentStartState.parse(character);
			}
		}
	}
	
	private CommentReadState commentReadState = new CommentReadState();
	private class CommentReadState extends State {
		public State parse(char character) {
			accumulator.append(character);
			if (character == '\0') {
				comment = new Comment(accumulator.toString());
				accumulator.setLength(0);
				return endState;
			} else {
				return commentReadState;
			}
		}
	}
	
	private EndState endState = new EndState();
	private class EndState extends State {
		public State parse(char character) {
			throw new RuntimeException("End state reached but not all characters consumed.");
		}
	}
	
	private class SyntaxError extends RuntimeException {
		private static final long serialVersionUID = 1L;
		public SyntaxError() {
			super("Syntax error on line " + lineNumber + ", column " + columnNumber + " of file " + sourceFile +
					"\n" + text + "\n" + (text.substring(0, columnNumber).replaceAll("[^\t]", " ") + "^"));
		}
	}
	
}
