package nl.grauw.asm.instructions;

public class Add extends Instruction {

	@Override
	public String getName() {
		return "add";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
