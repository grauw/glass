package nl.grauw.asm.instructions;

public class Sra extends Instruction {

	@Override
	public String getName() {
		return "sra";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
