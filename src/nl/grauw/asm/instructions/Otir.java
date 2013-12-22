package nl.grauw.asm.instructions;

public class Otir extends Instruction {

	@Override
	public String getName() {
		return "otir";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xED, (byte)0xB3 };
	}

}
