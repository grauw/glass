package nl.grauw.asm.instructions;

public class Ind extends Instruction {

	@Override
	public String getName() {
		return "ind";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
