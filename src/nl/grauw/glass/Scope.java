package nl.grauw.glass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.grauw.glass.expressions.Context;
import nl.grauw.glass.expressions.ContextLiteral;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.instructions.Instruction;
import nl.grauw.glass.instructions.InstructionFactory;

public class Scope implements Context {
	
	private Map<String, List<InstructionFactory>> instructions = new HashMap<>();
	
	Scope parent;
	Map<String, Expression> variables = new HashMap<>();
	private int address = -1;
	
	public Scope() {
		addLabel("$", new ContextLiteral(this));
	}
	
	public Scope(Scope parent) {
		this();
		this.parent = parent;
	}
	
	public Scope getParent() {
		return parent;
	}
	
	@Override
	public int getAddress() {
		if (address == -1)
			throw new AssemblyException("Address not initialized.");
		return address;
	}
	
	public void setAddress(int address) {
		if (address < 0 || address >= 0x10000)
			throw new AssemblyException("Address out of range: " + Integer.toHexString(address) + "H");
		this.address = address;
	}
	
	public void addLabel(String name, Expression value) {
		if (variables.get(name) != null)
			throw new AssemblyException("Can not redefine label.");
		variables.put(name, value);
	}
	
	public boolean hasLabel(String name) {
		return getLocalLabel(name) != null || parent != null && parent.hasLabel(name);
	}
	
	public Expression getLabel(String name) {
		Expression value = getLocalLabel(name);
		if (value != null)
			return value;
		if (parent != null)
			return parent.getLabel(name);
		throw new AssemblyException("Label not found: " + name);
	}
	
	private Expression getLocalLabel(String name) {
		Expression value = variables.get(name);
		if (value != null)
			return value;
		
		int index = name.length();
		while ((index = name.lastIndexOf('.', index - 1)) != -1) {
			Expression result = variables.get(name.substring(0, index));
			if (result instanceof ContextLiteral)
				return ((Scope)((ContextLiteral)result).getContext()).getLocalLabel(name.substring(index + 1));
		}
		return null;
	}
	
	public void addInstruction(String mnemonic, InstructionFactory factory) {
		List<InstructionFactory> factoryList = instructions.get(mnemonic);
		if (factoryList == null) {
			factoryList = new ArrayList<InstructionFactory>();
			instructions.put(mnemonic, factoryList);
		}
		factoryList.add(factory);
	}
	
	public Instruction createInstruction(String mnemonic, Expression arguments) {
		return createInstruction(mnemonic, arguments, this);
	}
	
	public Instruction createInstruction(String mnemonic, Expression arguments, Scope scope) {
		List<InstructionFactory> factoryList = instructions.get(mnemonic);
		if (factoryList != null) {
			for (InstructionFactory factory : factoryList) {
				Instruction instruction = factory.createInstruction(arguments, scope);
				if (instruction != null)
					return instruction;
			}
		}
		if (parent != null)
			return parent.createInstruction(mnemonic, arguments, scope);
		throw new AssemblyException("Unrecognized mnemonic.");
	}
	
}
