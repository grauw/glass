package nl.grauw.asm.expressions;

public class Current extends Expression {
	
	private Context context;
	
	public Current() {
		this.context = new Context() {
			@Override
			public Expression getLabel(String label) {
				throw new EvaluationException("Label not found: " + label);
			}
			@Override
			public int getAddress() {
				return 0;
			}
		};
	}
	
	@Override
	public boolean isInteger() {
		return true;
	}
	
	@Override
	public int getInteger() {
		return context.getAddress();
	}
	
	public String toString() {
		return "$";
	}
	
	public String toDebugString() {
		return toString();
	}
	
}
