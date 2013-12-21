package nl.grauw.asm.expressions;

import java.util.ArrayList;
import java.util.List;

public class Sequence extends Expression {
	
	private final List<Expression> elements = new ArrayList<Expression>();
	
	public void AddElement(Expression element) {
		elements.add(element);
	}
	
}
