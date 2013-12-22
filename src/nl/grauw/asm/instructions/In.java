package nl.grauw.asm.instructions;

public class In extends Instruction {

	@Override
	public String getName() {
		return "in";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
