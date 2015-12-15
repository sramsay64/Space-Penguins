package com.openthid.spacepenguins.field.entities.ship.control;

import java.util.TreeMap;
import java.util.function.Function;

import com.badlogic.ashley.core.Component;
import com.openthid.util.FunctionalUtils;

/**
 * A {@link Component} to store all the Control IOs
 */
public class ControlIOComponent implements Component {
	private TreeMap<String, ControlInput<? extends IOable>> inputs;
	private TreeMap<String, ControlOutput<? extends IOable>> outputs;
	private TreeMap<String, ControlReadOutput<? extends IOable>> readOutputs;

	public ControlIOComponent(
			TreeMap<String, ControlInput<? extends IOable>> inputs,
			TreeMap<String, ControlOutput<? extends IOable>> outputs,
			TreeMap<String, ControlReadOutput<? extends IOable>> readOutputs)
	{
		this.inputs = inputs;
		this.outputs = outputs;
		this.readOutputs = readOutputs;
	}

	/**
	 * Creates a {@link ControlIOComponent} from lists of Objects and their respective String labels/keys
	 * @param controlInputs
	 * @param controlInputKeys labels for controlInputs
	 * @param controlOutputs
	 * @param controlOutputKeys labels for controlOutputs
	 * @param controlReadOutputs
	 * @param controlReadOutputKeys labels for controlReadOutputs
	 */
	public <T extends IOable> ControlIOComponent(
			ControlInput<T>[] controlInputs, String[] controlInputKeys,
			ControlOutput<T>[] controlOutputs, String[] controlOutputKeys,
			ControlReadOutput<T>[] controlReadOutputs, String[] controlReadOutputKeys) {
		this(
			FunctionalUtils.applyManyZip(new TreeMap<>(), controlInputKeys, controlInputs, TreeMap::put),
			FunctionalUtils.applyManyZip(new TreeMap<>(), controlOutputKeys, controlOutputs, TreeMap::put),
			FunctionalUtils.applyManyZip(new TreeMap<>(), controlReadOutputKeys, controlReadOutputs, TreeMap::put)
		);
	}

	public ControlInput<? extends IOable> getInputs(String key) {
		return inputs.get(key);
	}

	public ControlOutput<? extends IOable> getOutput(String key) {
		return outputs.get(key);
	}

	public ControlReadOutput<? extends IOable> getReadOutputs(String key) {
		return readOutputs.get(key);
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
	 * Where the ellipses are replaced with a list of {@link String}s, {@link ControlInput}s, {@link String}s, {@link ControlOutput}s, {@link String}s and {@link ControlReadOutput} respectively
	 */
	//A mess of code for exposing a clean API. I wish I could write this in Haskell, it would be much shorter.
	@SuppressWarnings("unchecked")
	@SafeVarargs
	public static <T extends IOable> Function<String[], Function<ControlOutput<T>[], Function<String[], Function<ControlReadOutput<T>[], Function<String[], ControlIOComponent>>>>> build(ControlInput<T>... controlInputs) {
		return (String... controlInputKeys) ->
		(ControlOutput<T>... controlOutputs) ->
		(String... controlOutputKeys) ->
		(ControlReadOutput<T>... controlReadOutputs) ->
		(String... controlReadOutputKeys) -> {
			return new ControlIOComponent(controlInputs, controlInputKeys, controlOutputs, controlOutputKeys, controlReadOutputs, controlReadOutputKeys);
		};
	}
}