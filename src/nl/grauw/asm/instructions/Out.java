package nl.grauw.asm.instructions;

public class Out extends Instruction {

	@Override
	public String getName() {
		return "out";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
