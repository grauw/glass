package nl.grauw.asm.instructions;

public class Sla extends Instruction {

	@Override
	public String getName() {
		return "sla";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
