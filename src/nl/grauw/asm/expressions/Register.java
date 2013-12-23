package nl.grauw.asm.expressions;

public class Register extends Literal {
	
	public static final int NONE = -1;
	public static final int IX_CODE = 0xDD;
	public static final int IY_CODE = 0xFD;
	
	public static Register B = new Register("b", false, 0, NONE);
	public static Register C = new Register("c", false, 1, NONE);
	public static Register D = new Register("d", false, 2, NONE);
	public static Register E = new Register("e", false, 3, NONE);
	public static Register H = new Register("h", false, 4, NONE);
	public static Register L = new Register("l", false, 5, NONE);
	public static Register A = new Register("a", false, 7, NONE);
	public static Register IXH = new Register("ixh", false, 4, IX_CODE);
	public static Register IXL = new Register("ixl", false, 5, IX_CODE);
	public static Register IYH = new Register("iyh", false, 4, IY_CODE);
	public static Register IYL = new Register("iyl", false, 5, IY_CODE);
	public static Register BC = new Register("bc", true, 0, NONE);
	public static Register DE = new Register("de", true, 1, NONE);
	public static Register HL = new Register("hl", true, 2, NONE);
	public static Register SP = new Register("sp", true, 3, NONE);
	public static Register AF = new Register("af", true, 3, NONE);
	public static Register AF_ = new Register("af'", true, NONE, NONE);
	public static Register IX = new Register("ix", true, 2, IX_CODE);
	public static Register IY = new Register("iy", true, 2, IY_CODE);
	
	private final String name;
	private final boolean pair;
	private final int code;
	private final int indexCode;
	private final int offset;
	
	public Register(String name, boolean pair, int code, int indexCode) {
		this(name, pair, code, indexCode, 0);
	}
	
	public Register(String name, boolean pair, int code, int indexCode, int offset) {
		if (offset != 0 && (!pair || indexCode == NONE))
			throw new RuntimeException("Can only specify offset for 16-bit index registers.");
		
		this.name = name;
		this.pair = pair;
		this.code = code;
		this.indexCode = indexCode;
		this.offset = offset;
	}
	
	public Register(Register register, int offset) {
		this(register.name, register.pair, register.code, register.indexCode, offset);
	}
	
	public boolean isPair() {
		return pair;
	}
	
	public int getCode() {
		if (code == NONE)
			throw new EvaluationException("Register does not have a code.");
		return code;
	}
	
	public boolean isIndex() {
		return indexCode != NONE;
	}
	
	public byte getIndexCode() {
		if (indexCode == NONE)
			throw new EvaluationException("Register does not have an index code.");
		return (byte)indexCode;
	}
	
	public int getOffset() {
		return offset;
	}
	
	@Override
	public boolean isInteger() {
		return false;
	}
	
	@Override
	public int evaluateInteger() {
		throw new EvaluationException("Can not evaluate register to integer.");
	}
	
	@Override
	public boolean isRegister() {
		return true;
	}
	
	@Override
	public Register evaluateRegister() {
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
	
	public static Register getByName(String name) {
		switch (name) {
		case "b":
			return Register.B;
		case "c":
			return Register.C;
		case "d":
			return Register.D;
		case "e":
			return Register.E;
		case "h":
			return Register.H;
		case "l":
			return Register.L;
		case "a":
			return Register.A;
		case "ixh":
			return Register.IXH;
		case "ixl":
			return Register.IXL;
		case "iyh":
			return Register.IYH;
		case "iyl":
			return Register.IYL;
		case "bc":
			return Register.BC;
		case "de":
			return Register.DE;
		case "hl":
			return Register.HL;
		case "sp":
			return Register.SP;
		case "af":
			return Register.AF;
		case "af'":
			return Register.AF_;
		case "ix":
			return Register.IX;
		case "iy":
			return Register.IY;
		}
		return null;
	}
	
}
