package nl.grauw.asm.instructions;

public class Rrd extends Instruction {

	@Override
	public String getName() {
		return "rrd";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
