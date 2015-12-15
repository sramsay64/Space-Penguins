package com.openthid.spacepenguins.field.entities.ship.control;

import java.util.Map.Entry;
import java.util.function.Function;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Array;
import com.openthid.util.FunctionalUtils;
import com.openthid.util.ImmutableEntry;

public class ControlIOComponent implements Component {//A mess of code for exposing a clean API. I wish I could write this in Haskell, it would be much shorter.
	private ImmutableArray<Entry<String, ControlInput<? extends Controlable>>> inputs;
	private ImmutableArray<Entry<String, ControlOutput<? extends Controlable>>> outputs;
	private ImmutableArray<Entry<String, ControlReadOutput<? extends Controlable>>> readOutputs;

	public ControlIOComponent(
			ImmutableArray<Entry<String, ControlInput<? extends Controlable>>> inputs,
			ImmutableArray<Entry<String, ControlOutput<? extends Controlable>>> outputs,
			ImmutableArray<Entry<String, ControlReadOutput<? extends Controlable>>> readOutputs)
	{
		this.inputs = inputs;
		this.outputs = outputs;
		this.readOutputs = readOutputs;
	}

	@SuppressWarnings("unchecked")
	public <T extends Controlable> ControlIOComponent(
			ControlInput<T>[] controlInputs,
			String[] controlInputKeys,
			ControlOutput<T>[] controlOutputs,
			String[] controlOutputKeys,
			ControlReadOutput<T>[] controlReadOutputs,
			String[] controlReadOutputKeys)
	{
		this(
			new ImmutableArray<>(new Array<>(FunctionalUtils.zipWith(
					controlInputKeys,
					controlInputs, (key, inp) -> new ImmutableEntry<String, ControlInput<? extends Controlable>>(key, inp),
					i -> new ImmutableEntry[i]
				))),
			new ImmutableArray<>(new Array<>(FunctionalUtils.zipWith(
					controlOutputKeys,
					controlOutputs, (key, inp) -> new ImmutableEntry<String, ControlOutput<? extends Controlable>>(key, inp),
					i -> new ImmutableEntry[i]
				))),
			new ImmutableArray<>(new Array<>(FunctionalUtils.zipWith(
					controlReadOutputKeys,
					controlReadOutputs, (key, inp) -> new ImmutableEntry<String, ControlReadOutput<? extends Controlable>>(key, inp),
					i -> new ImmutableEntry[i]
				)))
		);
	}

	public ImmutableArray<Entry<String, ControlInput<? extends Controlable>>> getInputs() {
		return inputs;
	}

	public ImmutableArray<Entry<String, ControlOutput<? extends Controlable>>> getOutputs() {
		return outputs;
	}

	public ImmutableArray<Entry<String, ControlReadOutput<? extends Controlable>>> getReadOutputs() {
		return readOutputs;
	}

	/**
	 * A very curried function to build a {@link ControlIOComponent} from six lists
	 * Use as:
	 * <pre>
	 * ControlIOComponent c = ControlIOComponent
	 *     .build(...)
	 *     .apply(...)
	 *     .apply(...)
	 *     .apply(...)
	 *     .apply(...)
	 *     .apply(...)
	 * </pre>
	 * Where the ellipses are replaced with a list of {@link ControlInput}s and {@link ControlOutput}s respectively
	 */
	@SafeVarargs
	public static <T extends Controlable> Function<String[], Function<ControlOutput<T>[], Function<String[], Function<ControlReadOutput<T>[], Function<String[], ControlIOComponent>>>>> build(ControlInput<T>... controlInputs) {
		return (String[] controlInputKeys) ->
		(ControlOutput<T>[] controlOutputs) ->
		(String[] controlOutputKeys) ->
		(ControlReadOutput<T>[] controlReadOutputs) ->
		(String[] controlReadOutputKeys) -> {
			return new ControlIOComponent(controlInputs, controlInputKeys, controlOutputs, controlOutputKeys, controlReadOutputs, controlReadOutputKeys);
		};
	}
}