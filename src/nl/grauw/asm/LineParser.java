package nl.grauw.asm;

import java.io.File;

public class LineParser {
	
	private State state;
	private StringBuilder accumulator = new StringBuilder();
	private Label label;
	private Statement statement;
	private Comment comment;
	private File sourceFile;
	private int lineNumber;
	private String text;
	
	public Line parse(String text, File sourceFile, int lineNumber) {
		state = labelStartState;
		label = null;
		statement = null;
		comment = null;
		this.sourceFile = sourceFile;
		this.lineNumber = lineNumber;
		this.text = text;
		
		for (int i = 0, length = text.length(); i < length; i++) {
			state = state.parse(text.charAt(i));
		}
		state.parse('\0');
		
		return new Line(label, statement, comment, sourceFile, lineNumber);
	}
	
	private abstract class State {
		public abstract State parse(char character);
		
		public boolean isWhitespace(char character) {
			return character == ' ' || character == '\t';
		}
		
		public boolean isIdentifier(char character) {
			return isIdentifierStart(character) || character >= '0' && character <= '9';
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
				accumulator.setLength(0);
				accumulator.append(character);
				return labelReadState;
			} else if (isWhitespace(character)) {
				return statementStartState;
			} else if (character == ';') {
				return commentStartState;
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
				return this;
			} else {
				label = new Label(accumulator.toString());
				if (character == ':' || isWhitespace(character)) {
					return statementStartState;
				} else if (character == ';') {
					return commentStartState;
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
				accumulator.setLength(0);
				accumulator.append(character);
				return statementReadState;
			} else if (isWhitespace(character)) {
				return this;
			} else if (character == ';') {
				return commentStartState;
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
				return this;
			} else {
				statement = new Statement(accumulator.toString());
				if (isWhitespace(character)) {
					return argumentStartState;
				} else if (character == ';') {
					return commentStartState;
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
//			if (character == '"') {
//				return stringReadState;
//			} else if (character == ',' || character == ';' || character == '\0') {
//				
//			}
//			if (isIdentifierStart(character)) {
//				accumulator.setLength(0);
//				accumulator.append(character);
//				return statementReadState;
//			} else if (isWhitespace(character)) {
//				return this;
//			} else if (character == ';') {
//				return commentStartState;
//			} else if (character == '\0') {
//				return endState;
//			}
			throw new SyntaxError();
		}
	}
	
	private ArgumentReadState argumentReadState = new ArgumentReadState();
	private class ArgumentReadState extends State {
		public State parse(char character) {
//			if (isIdentifierStart(character)) {
//				accumulator.setLength(0);
//				accumulator.append(character);
//				return statementReadState;
//			} else if (isWhitespace(character)) {
//				return this;
//			} else if (character == ';') {
//				return commentStartState;
//			} else if (character == '\0') {
//				return endState;
//			}
			throw new SyntaxError();
		}
	}
	
	private CommentStartState commentStartState = new CommentStartState();
	private class CommentStartState extends State {
		public State parse(char character) {
			if (character == '\0') {
				comment = new Comment("");
				return endState;
			} else {
				accumulator.setLength(0);
				accumulator.append(character);
				return commentReadState;
			}
		}
	}
	
	private CommentReadState commentReadState = new CommentReadState();
	private class CommentReadState extends State {
		public State parse(char character) {
			accumulator.append(character);
			if (character == '\0') {
				comment = new Comment(accumulator.toString());
				return endState;
			}
			return this;
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
			super("Syntax error on line " + lineNumber + " of file " + sourceFile + "\n" + text);
		}
	}
	
}
