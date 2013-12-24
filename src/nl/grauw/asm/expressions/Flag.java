package nl.grauw.asm.expressions;

public class Flag extends Literal {
	
	public static Flag NZ = new Flag("nz", 0);
	public static Flag Z = new Flag("z", 1);
	public static Flag NC = new Flag("nc", 2);
	public static Flag C = new Flag("c", 3);
	public static Flag PO = new Flag("po", 4);
	public static Flag PE = new Flag("pe", 5);
	public static Flag P = new Flag("p", 6);
	public static Flag M = new Flag("m", 7);
	
	private final String name;
	private final int code;
	
	public Flag(String name, int code) {
		this.name = name;
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	@Override
	public boolean isInteger(Context context) {
		return false;
	}
	
	@Override
	public int getInteger(Context context) {
		throw new EvaluationException("Can not evaluate flag to integer.");
	}
	
	@Override
	public boolean isFlag(Context context) {
		return true;
	}
	
	@Override
	public Flag getFlag(Context context) {
		return this;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public String toDebugString() {
		return toString();
	}
	
	public static Flag getByName(String name) {
		switch (name) {
		case "nz":
			return Flag.NZ;
		case "z":
			return Flag.Z;
		case "nc":
			return Flag.NC;
		case "c":
			return Flag.C;
		case "po":
			return Flag.PO;
		case "pe":
			return Flag.PE;
		case "p":
			return Flag.P;
		case "m":
			return Flag.M;
		}
		return null;
	}
	
}
