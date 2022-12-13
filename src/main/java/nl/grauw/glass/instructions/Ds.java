package nl.grauw.glass.instructions;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.grauw.glass.AssemblyException;
import nl.grauw.glass.Source;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Identifier;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.expressions.Schema;
import nl.grauw.glass.expressions.SectionContext;

public class Ds extends InstructionFactory implements SectionContext {

	public static Schema ARGUMENTS_N = new Schema(Schema.INTEGER);
	public static Schema ARGUMENTS_N_N = new Schema(Schema.INTEGER, Schema.INTEGER);
	public static Schema ARGUMENTS_VIRTUAL = new Schema(new Schema.IsAnnotation(Schema.INTEGER));

	private final List<Source> sectionSources = new ArrayList<>();

	@Override
	public void addSectionSource(Source source) {
		sectionSources.add(source);
	}

	public List<Source> getSectionSources() {
		return sectionSources;
	}

	@Override
	public InstructionObject createObject(Expression address, Expression arguments) {
		if (ARGUMENTS_N.check(arguments))
			return new Ds_N_N(address, null, arguments, IntegerLiteral.ZERO);
		if (ARGUMENTS_N_N.check(arguments))
			return new Ds_N_N(address, null, arguments.getElement(0), arguments.getElement(1));
		if (ARGUMENTS_VIRTUAL.check(arguments))
			return new Ds_N_N(address, arguments.getAnnotation(), arguments.getAnnotee(), IntegerLiteral.ZERO);
		throw new ArgumentException();
	}

	public class Ds_N_N extends InstructionObject {

		private final boolean virtual;
		private final Expression size;
		private final Expression value;

		public Ds_N_N(Expression address, Identifier annotation, Expression size, Expression value) {
			super(address);
			this.virtual = annotation != null && ("virtual".equals(annotation.getName()) || "VIRTUAL".equals(annotation.getName()));
			this.size = size;
			this.value = value;

			if (annotation != null && !virtual)
				throw new ArgumentException("Unsupported annotation: " + annotation.getName());
		}

		@Override
		public Expression resolve() {
			Expression innerAddress = address;
			for (Source source : sectionSources)
				innerAddress = source.resolve(innerAddress);
			return super.resolve();
		}

		@Override
		public Expression getSize() {
			return size;
		}

		@Override
		public byte[] getBytes() {
			ByteArrayOutputStream sourceByteStream = new ByteArrayOutputStream(size.getInteger());
			for (Source source : sectionSources) {
				sourceByteStream.writeBytes(source.getBytes());
			}

			if (sourceByteStream.size() > size.getInteger())
				throw new AssemblyException("Section size exceeds space (required: " +
					sourceByteStream.size() + " bytes, available: " + size.getInteger() + " bytes).");

			if (virtual)
				return b();

			byte[] padding = new byte[size.getInteger() - sourceByteStream.size()];
			Arrays.fill(padding, (byte)value.getInteger());
			sourceByteStream.writeBytes(padding);

			return sourceByteStream.toByteArray();
		}

	}

}
