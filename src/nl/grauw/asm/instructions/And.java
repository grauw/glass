package nl.grauw.asm.instructions;

public class And extends Instruction {

	@Override
	public String getName() {
		return "and";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
