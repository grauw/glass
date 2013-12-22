package nl.grauw.asm.instructions;

public class Ldi extends Instruction {

	@Override
	public String getName() {
		return "ldi";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xED, (byte)0xA0 };
	}

}
