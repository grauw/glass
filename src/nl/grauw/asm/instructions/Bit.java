package nl.grauw.asm.instructions;

public class Bit extends Instruction {

	@Override
	public String getName() {
		return "bit";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
