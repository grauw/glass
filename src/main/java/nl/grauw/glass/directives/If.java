package nl.grauw.glass.directives;

import java.util.HashSet;
import java.util.Set;

import nl.grauw.glass.Line;
import nl.grauw.glass.Scope;
import nl.grauw.glass.Source;
import nl.grauw.glass.expressions.ContextLiteral;
import nl.grauw.glass.expressions.Identifier;
import nl.grauw.glass.expressions.IfElse;
import nl.grauw.glass.expressions.Member;

public class If extends Directive {

	private final Source thenSource;
	private final Source elseSource;

	public If(Source thenSource, Source elseSource) {
		this.thenSource = thenSource;
		this.elseSource = elseSource;
	}

	@Override
	public Directive copy(Scope scope) {
		return new If(thenSource.copy(new Scope(scope.getParent())), elseSource.copy(new Scope(scope.getParent())));
	}

	@Override
	public void register(Scope scope, Line line) {
		line.setInstruction(new nl.grauw.glass.instructions.If(thenSource, elseSource));
		super.register(scope, line);
		thenSource.register();
		elseSource.register();

		Set<String> symbols = new HashSet<>();
		symbols.addAll(thenSource.getScope().getSymbols());
		symbols.addAll(elseSource.getScope().getSymbols());
		for (String symbol : symbols)
		{
			scope.addSymbol(symbol, new IfElse(line.getArguments(),
				new Member(new ContextLiteral(thenSource.getScope()), new Identifier(symbol, null)),
				new Member(new ContextLiteral(elseSource.getScope()), new Identifier(symbol, null))
			));
		}
	}

}
