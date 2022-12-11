package nl.grauw.glass.expressions;

public class Flag extends Expression {

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

	@Override
	public Flag copy(Context context) {
		return this;
	}

	public int getCode() {
		return code;
	}

	@Override
	public boolean is(Expression type) {
		return type.is(Type.FLAG);
	}

	@Override
	public Expression get(Expression type) {
		if (type.is(Type.FLAG))
			return this;
		return super.get(type);
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
		return switch (name) {
			case "nz", "NZ" -> Flag.NZ;
			case "z",  "Z"  -> Flag.Z;
			case "nc", "NC" -> Flag.NC;
			case "c",  "C"  -> Flag.C;
			case "po", "PO" -> Flag.PO;
			case "pe", "PE" -> Flag.PE;
			case "p",  "P"  -> Flag.P;
			case "m",  "M"  -> Flag.M;
			default -> null;
		};
	}

}
