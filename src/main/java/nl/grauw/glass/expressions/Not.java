package nl.grauw.glass.expressions;

import nl.grauw.glass.AssemblyException;

public class Not extends UnaryOperator {

	public Not(Expression term) {
		super(term);
	}

	@Override
	public Not copy(Context context) {
		return new Not(term.copy(context));
	}

	@Override
	public boolean is(Expression type) {
		return type.is(Type.FLAG) && term.is(Type.FLAG) || super.is(type);
	}

	@Override
	public Expression get(Expression type) {
		if (type.is(Type.INTEGER))
			return IntegerLiteral.of(term.getInteger() == 0);
		if (type.is(Type.FLAG)) {
			Flag flag = term.getFlag();
			if (flag == Flag.NZ)
				return Flag.Z;
			if (flag == Flag.Z)
				return Flag.NZ;
			if (flag == Flag.NC)
				return Flag.C;
			if (flag == Flag.C)
				return Flag.NC;
			if (flag == Flag.PO)
				return Flag.PE;
			if (flag == Flag.PE)
				return Flag.PO;
			if (flag == Flag.P)
				return Flag.M;
			if (flag == Flag.M)
				return Flag.P;
			throw new AssemblyException("Unrecognised flag.");
		}
		return super.get(type);
	}

	@Override
	public String getLexeme() {
		return "!";
	}

}
