package nl.grauw.asm.instructions;

public class Adc extends Instruction {

	@Override
	public String getName() {
		return "adc";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
