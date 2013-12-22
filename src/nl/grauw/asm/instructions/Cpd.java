package nl.grauw.asm.instructions;

public class Cpd extends Instruction {

	@Override
	public String getName() {
		return "cpd";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
