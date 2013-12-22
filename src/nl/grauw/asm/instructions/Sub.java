package nl.grauw.asm.instructions;

public class Sub extends Instruction {

	@Override
	public String getName() {
		return "sub";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
