package nl.grauw.asm.instructions;

public class Cp extends Instruction {

	@Override
	public String getName() {
		return "cp";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
