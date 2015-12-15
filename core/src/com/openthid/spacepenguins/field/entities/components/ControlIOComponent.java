package com.openthid.spacepenguins.field.entities.components;

import java.util.TreeMap;

import com.badlogic.ashley.core.Component;
import com.openthid.spacepenguins.field.entities.ship.control.ControlInput;
import com.openthid.spacepenguins.field.entities.ship.control.ControlOutput;
import com.openthid.spacepenguins.field.entities.ship.control.ControlReadOutput;
import com.openthid.util.ArrayFunction;
import com.openthid.util.FunctionalUtils;

/**
 * A {@link Component} to store all the Control IOs
 */
public class ControlIOComponent implements Component {
	private TreeMap<String, ControlInput> inputs;
	private TreeMap<String, ControlOutput> outputs;
	private TreeMap<String, ControlReadOutput> readOutputs;

	public ControlIOComponent(
			TreeMap<String, ControlInput> inputs,
			TreeMap<String, ControlOutput> outputs,
			TreeMap<String, ControlReadOutput> readOutputs)
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
	public ControlIOComponent(
			ControlInput[] controlInputs, String[] controlInputKeys,
			ControlOutput[] controlOutputs, String[] controlOutputKeys,
			ControlReadOutput[] controlReadOutputs, String[] controlReadOutputKeys) {
		this(
			FunctionalUtils.applyManyZip(new TreeMap<>(), controlInputKeys, controlInputs, TreeMap::put),
			FunctionalUtils.applyManyZip(new TreeMap<>(), controlOutputKeys, controlOutputs, TreeMap::put),
			FunctionalUtils.applyManyZip(new TreeMap<>(), controlReadOutputKeys, controlReadOutputs, TreeMap::put)
		);
	}

	public ControlInput getInputs(String key) {
		return inputs.get(key);
	}

	public ControlOutput getOutput(String key) {
		return outputs.get(key);
	}

	public ControlReadOutput getReadOutputs(String key) {
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
	public static ArrayFunction<String, ArrayFunction<ControlOutput, ArrayFunction<String, ArrayFunction<ControlReadOutput, ArrayFunction<String, ControlIOComponent>>>>> build(ControlInput... controlInputs) {
		return (String... controlInputKeys) ->
		(ControlOutput... controlOutputs) ->
		(String... controlOutputKeys) ->
		(ControlReadOutput... controlReadOutputs) ->
		(String... controlReadOutputKeys) -> {
			return new ControlIOComponent(controlInputs, controlInputKeys, controlOutputs, controlOutputKeys, controlReadOutputs, controlReadOutputKeys);
		};
	}
}