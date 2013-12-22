package nl.grauw.asm.instructions;

public class Halt extends Instruction {

	@Override
	public String getName() {
		return "halt";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
