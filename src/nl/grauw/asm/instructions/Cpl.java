package nl.grauw.asm.instructions;

public class Cpl extends Instruction {

	@Override
	public String getName() {
		return "cpl";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x2F };
	}

}
