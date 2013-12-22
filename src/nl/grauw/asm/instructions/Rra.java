package nl.grauw.asm.instructions;

public class Rra extends Instruction {

	@Override
	public String getName() {
		return "rra";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
