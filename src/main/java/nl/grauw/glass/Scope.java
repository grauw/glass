package nl.grauw.glass;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import nl.grauw.glass.expressions.Context;
import nl.grauw.glass.expressions.ContextLiteral;
import nl.grauw.glass.expressions.EvaluationException;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.instructions.Instruction;

public class Scope implements Context {
	
	private final Scope parent;
	private final Map<String, Expression> symbols = new HashMap<>();
	private final Map<String, Instruction> instructions = new HashMap<>();
	private int address = -1;
	
	public Scope() {
		this(null);
	}
	
	public Scope(Scope parent) {
		this.parent = parent;
		addSymbol("$", this);
	}
	
	public Scope getParent() {
		return parent;
	}
	
	@Override
	public int getAddress() {
		if (address == -1)
			throw new EvaluationException("Address not initialized.");
		return address;
	}
	
	public void setAddress(int address) {
		if (this.address != -1)
			throw new AssemblyException("Address was already set.");
		if (address < 0 || address >= 0x10000)
			throw new AssemblyException("Address out of range: " + Integer.toHexString(address) + "H");
		this.address = address;
	}
	
	public void addSymbol(String name, Expression value) {
		if (name == null || value == null)
			throw new AssemblyException("Symbol name and value must not be null.");
		if (symbols.containsKey(name))
			throw new AssemblyException("Can not redefine symbol: " + name);
		symbols.put(name, value);
	}
	
	public void addSymbol(String name, Scope context) {
		addSymbol(name, new ContextLiteral(context));
	}
	
	public boolean hasSymbol(String name) {
		return getLocalSymbol(name) != null || parent != null && parent.hasSymbol(name);
	}
	
	public Expression getSymbol(String name) {
		Expression value = getLocalSymbol(name);
		if (value != null)
			return value;
		if (parent != null)
			return parent.getSymbol(name);
		throw new SymbolNotFoundException(name);
	}
	
	private Expression getLocalSymbol(String name) {
		Expression value = symbols.get(name);
		if (value != null)
			return value;
		
		int index = name.length();
		while ((index = name.lastIndexOf('.', index - 1)) != -1) {
			Expression result = symbols.get(name.substring(0, index));
			if (result != null && result.isContext())
				return ((Scope)result.getContext()).getLocalSymbol(name.substring(index + 1));
		}
		return null;
	}
	
	public Instruction addInstruction(String mnemonic, Instruction factory) {
		if (mnemonic == null || factory == null)
			throw new AssemblyException("Instruction mnemonic and factory must not be null.");
		if (instructions.containsKey(mnemonic))
			throw new AssemblyException("Instruction already registered.");
		instructions.put(mnemonic, factory);
		return factory;
	}
	
	public Instruction getInstruction(String mnemonic) {
		Instruction factory = instructions.get(mnemonic);
		if (factory != null)
			return factory;
		if (parent != null)
			return parent.getInstruction(mnemonic);
		throw new AssemblyException("Unrecognized mnemonic.");
	}
	
	public static class SymbolNotFoundException extends AssemblyException {
		private static final long serialVersionUID = 1L;
		
		public SymbolNotFoundException(String name) {
			super("Symbol not found: " + name);
		}
	}
	
	public String serializeSymbols() {
		return serializeSymbols("");
	}
	
	public String serializeSymbols(String namePrefix) {
		StringBuilder builder = new StringBuilder();
		TreeMap<String, Expression> sortedMap = new TreeMap<>(symbols);
		for (Map.Entry<String, Expression> entry : sortedMap.entrySet()) {
			if (entry.getValue() instanceof ContextLiteral && !"$".equals(entry.getKey())) {
				String name = namePrefix + entry.getKey();
				try {
					builder.append(name + ": equ " + entry.getValue().getHexValue() + "\n");
				} catch (EvaluationException e) {
					// ignore
				}
				Scope context = (Scope)((ContextLiteral)entry.getValue()).getContext();
				builder.append(context.serializeSymbols(name + "."));
			}
		}
		return builder.toString();
	}
	
	public String toString() {
		return serializeSymbols();
	}
	
}
