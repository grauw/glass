package nl.grauw.asm.expressions;

public abstract class Expression {
	
	public abstract String toDebugString();
	
	public abstract boolean isInteger(Context context);
	
	public abstract int getInteger(Context context);
	
	public boolean isRegister(Context context) {
		return false;
	}
	
	public Register getRegister(Context context) {
		throw new EvaluationException("Not a register.");
	}
	
	public boolean isFlag(Context context) {
		return false;
	}
	
	public Flag getFlag(Context context) {
		throw new EvaluationException("Not a flag.");
	}
	
	public Expression getElement(int index) {
		return index == 0 ? this : null;
	}
	
	// temporary
	Context fakeContext = new Context() {
		public Expression getLabel(String label) {
			throw new EvaluationException("Label not found: " + label);
		}
		public int getAddress() {
			return 0;
		}
	};
	
	public boolean isInteger() {
		return isInteger(fakeContext);
	}
	
	public int getInteger() {
		return getInteger(fakeContext);
	}
	
	public boolean isRegister() {
		return isRegister(fakeContext);
	}
	
	public Register getRegister() {
		return getRegister(fakeContext);
	}
	
	public boolean isFlag() {
		return isFlag(fakeContext);
	}
	
	public Flag getFlag() {
		return getFlag(fakeContext);
	}
	
}
