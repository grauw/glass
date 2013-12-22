package nl.grauw.asm.instructions;

public class Djnz extends Instruction {

	@Override
	public String getName() {
		return "djnz";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
