package nl.grauw.asm.instructions;

public class Ld extends Instruction {

	@Override
	public String getName() {
		return "ld";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
