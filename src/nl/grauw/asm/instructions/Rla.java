package nl.grauw.asm.instructions;

public class Rla extends Instruction {

	@Override
	public String getName() {
		return "rla";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
