package nl.grauw.asm.instructions;

public class Neg extends Instruction {

	@Override
	public String getName() {
		return "neg";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xED, (byte)0x44 };
	}

}
