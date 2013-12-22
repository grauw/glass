package nl.grauw.asm.instructions;

public class Ldd extends Instruction {

	@Override
	public String getName() {
		return "ldd";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
