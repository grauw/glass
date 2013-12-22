package nl.grauw.asm.instructions;

public class Ccf extends Instruction {

	@Override
	public String getName() {
		return "ccf";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x3F };
	}

}
