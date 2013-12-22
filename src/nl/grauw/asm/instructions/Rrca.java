package nl.grauw.asm.instructions;

public class Rrca extends Instruction {

	@Override
	public String getName() {
		return "rrca";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
