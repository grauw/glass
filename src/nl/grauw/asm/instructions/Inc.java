package nl.grauw.asm.instructions;

public class Inc extends Instruction {

	@Override
	public String getName() {
		return "inc";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
