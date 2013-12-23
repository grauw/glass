package nl.grauw.asm.expressions;

public class Not extends UnaryOperator {
	
	public Not(Expression term) {
		super(term);
	}
	
	@Override
	public int evaluateInteger() {
		return term.evaluateInteger() == 0 ? -1 : 0;
	}
	
	public String toString() {
		return "!" + term;
	}
	
	public String toDebugString() {
		return "!" + term.toDebugString();
	}
	
}
