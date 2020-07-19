package nl.grauw.glass.instructions;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.grauw.glass.AssemblyException;
import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Identifier;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.expressions.Schema;
import nl.grauw.glass.expressions.SectionContext;

public class Ds extends InstructionFactory implements SectionContext {

	public static Schema ARGUMENTS_N = new Schema(Schema.INTEGER);
	public static Schema ARGUMENTS_N_N = new Schema(Schema.INTEGER, Schema.INTEGER);
	public static Schema ARGUMENTS_VIRTUAL = new Schema(new Schema.IsAnnotation(Schema.INTEGER));

	private final List<Section> sections = new ArrayList<>();

	@Override
	public void addSection(Section section) {
		sections.add(section);
	}

	public List<Section> getSections() {
		return sections;
	}

	@Override
	public InstructionObject createObject(Scope context, Expression arguments) {
		if (ARGUMENTS_N.check(arguments))
			return new Ds_N_N(context, null, arguments, IntegerLiteral.ZERO);
		if (ARGUMENTS_N_N.check(arguments))
			return new Ds_N_N(context, null, arguments.getElement(0), arguments.getElement(1));
		if (ARGUMENTS_VIRTUAL.check(arguments))
			return new Ds_N_N(context, arguments.getAnnotation(), arguments.getAnnotee(), IntegerLiteral.ZERO);
		throw new ArgumentException();
	}

	public class Ds_N_N extends InstructionObject {

		private final boolean virtual;
		private final Expression size;
		private final Expression value;

		public Ds_N_N(Scope context, Identifier annotation, Expression size, Expression value) {
			super(context);
			this.virtual = annotation != null && ("virtual".equals(annotation.getName()) || "VIRTUAL".equals(annotation.getName()));
			this.size = size;
			this.value = value;

			if (annotation != null && !virtual)
				throw new ArgumentException("Unsupported annotation: " + annotation.getName());
		}

		@Override
		public Expression resolve(Expression address) {
			Expression innerAddress = address;
			for (Section section : sections)
				innerAddress = section.getSource().resolve(innerAddress);
			return super.resolve(address);
		}

		@Override
		public Expression getSize() {
			return size;
		}

		@Override
		public byte[] getBytes() {
			ByteArrayOutputStream sourceByteStream = new ByteArrayOutputStream(size.getInteger());
			for (Section section : sections) {
				byte[] sectionBytes = section.getSource().getBytes();
				sourceByteStream.write(sectionBytes, 0, sectionBytes.length);
			}

			if (sourceByteStream.size() > size.getInteger())
				throw new AssemblyException("Section size exceeds space (required: " +
					sourceByteStream.size() + " bytes, available: " + size.getInteger() + " bytes).");

			if (virtual)
				return new byte[] {};

			byte[] padding = new byte[size.getInteger() - sourceByteStream.size()];
			Arrays.fill(padding, (byte)value.getInteger());
			sourceByteStream.write(padding, 0, padding.length);

			return sourceByteStream.toByteArray();
		}

	}

}
