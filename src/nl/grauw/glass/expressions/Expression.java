package nl.grauw.glass.expressions;

import java.util.ArrayList;
import java.util.List;

public abstract class Expression {
	
	public abstract String toDebugString();
	
	public abstract Expression copy(Context context);
	
	public abstract boolean isInteger();
	
	public abstract int getInteger();
	
	public boolean isRegister() {
		return false;
	}
	
	public Register getRegister() {
		throw new EvaluationException("Not a register.");
	}
	
	public boolean isFlag() {
		return false;
	}
	
	public Flag getFlag() {
		throw new EvaluationException("Not a flag.");
	}
	
	public int getAddress() {
		int address = getInteger();
		if (address < 0 || address >= 0x10000)
			throw new EvaluationException("Address out of range: " + Integer.toHexString(address) + "H");
		return address;
	}
	
	public List<Expression> getList() {
		List<Expression> list = new ArrayList<>();
		addToList(list);
		return list;
	}

	protected void addToList(List<Expression> list) {
		list.add(this);
	}
	
	public Expression getElement(int index) {
		return index == 0 ? this : null;
	}
	
}