package nl.grauw.asm.expressions;

public class Register extends Literal {
	
	public static final int NONE = -1;
	public static final int IX_CODE = 0xDD;
	public static final int IY_CODE = 0xFD;
	
	public static Register B = new Register("b", false, 0, NONE, NONE);
	public static Register C = new Register("c", false, 1, NONE, NONE);
	public static Register D = new Register("d", false, 2, NONE, NONE);
	public static Register E = new Register("e", false, 3, NONE, NONE);
	public static Register H = new Register("h", false, 4, NONE, NONE);
	public static Register L = new Register("l", false, 5, NONE, NONE);
	public static Register A = new Register("a", false, 7, NONE, NONE);
	public static Register IXH = new Register("ixh", false, 4, NONE, IX_CODE);
	public static Register IXL = new Register("ixl", false, 5, NONE, IX_CODE);
	public static Register IYH = new Register("iyh", false, 4, NONE, IY_CODE);
	public static Register IYL = new Register("iyl", false, 5, NONE, IY_CODE);
	public static Register BC = new Register("bc", true, NONE, 0, NONE);
	public static Register DE = new Register("de", true, NONE, 1, NONE);
	public static Register HL = new Register("hl", true, 6, 2, NONE);
	public static Register SP = new Register("sp", true, NONE, 3, NONE);
	public static Register AF = new Register("af", true, NONE, 3, NONE);
	public static Register AF_ = new Register("af'", true, NONE, NONE, NONE);
	public static Register IX = new Register("ix", true, 6, 2, IX_CODE);
	public static Register IY = new Register("iy", true, 6, 2, IY_CODE);
	public static Register I = new Register("i", false, NONE, NONE, NONE);
	public static Register R = new Register("r", false, NONE, NONE, NONE);
	
	private final String name;
	private final boolean pair;
	private final int code8;
	private final int code16;
	private final int indexCode;
	private final int indexOffset;
	
	public Register(String name, boolean pair, int code8, int code16, int indexCode) {
		this(name, pair, code8, code16, indexCode, 0);
	}
	
	public Register(String name, boolean pair, int code8, int code16, int indexCode, int offset) {
		if (offset != 0 && (!pair || indexCode == NONE))
			throw new RuntimeException("Can only specify offset for 16-bit index registers.");
		
		this.name = name;
		this.pair = pair;
		this.code8 = code8;
		this.code16 = code16;
		this.indexCode = indexCode;
		this.indexOffset = offset;
	}
	
	public Register(Register register, int offset) {
		this(register.name, register.pair, register.code8, register.code16, register.indexCode, offset);
	}
	
	public boolean isPair() {
		return pair;
	}
	
	public int get8BitCode() {
		if (code8 == NONE)
			throw new EvaluationException("Register does not have an 8-bit code.");
		return code8;
	}
	
	public int get16BitCode() {
		if (code16 == NONE)
			throw new EvaluationException("Register does not have a 16-bit code.");
		return code16;
	}
	
	public boolean isIndex() {
		return indexCode != NONE;
	}
	
	public byte getIndexCode() {
		if (indexCode == NONE)
			throw new EvaluationException("Not an index register.");
		return (byte)indexCode;
	}
	
	public byte getIndexOffset() {
		if (indexCode == NONE || !pair)
			throw new EvaluationException("Not an index register pair.");
		return (byte)indexOffset;
	}
	
	@Override
	public boolean isInteger(Context context) {
		return false;
	}
	
	@Override
	public int getInteger(Context context) {
		throw new EvaluationException("Can not evaluate register to integer.");
	}
	
	@Override
	public boolean isRegister(Context context) {
		return true;
	}
	
	@Override
	public Register getRegister(Context context) {
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
		case "i":
			return Register.I;
		case "r":
			return Register.R;
		}
		return null;
	}
	
}
