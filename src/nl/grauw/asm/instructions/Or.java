package nl.grauw.asm.instructions;

public class Or extends Instruction {

	@Override
	public String getName() {
		return "or";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
