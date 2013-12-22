package nl.grauw.asm.instructions;

public class Set extends Instruction {

	@Override
	public String getName() {
		return "set";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
