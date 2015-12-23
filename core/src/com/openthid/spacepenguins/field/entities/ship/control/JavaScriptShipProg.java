package com.openthid.spacepenguins.field.entities.ship.control;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.openthid.spacepenguins.field.entities.ship.Ship.ShipProgInterface;

public class JavaScriptShipProg extends ShipProg {

	private static ScriptEngineManager manager = new ScriptEngineManager();

	private String initCode =
			  "var IOable = Java.type(\"com.openthid.spacepenguins.field.entities.ship.control.IOable\");"
			+ "var mkio = IOable.fromFloat;"
			+ "var zero = IOable.ZERO;";
	private String updateCode =
			  "var t = io.getI(\"Clock\", \"time\").get();"
			+ "var kQ = io.getI(\"KeyPanel\", \"q\").get();"
			+ "var kE = io.getI(\"KeyPanel\", \"e\").get();"
			+ "var kW = io.getI(\"KeyPanel\", \"w\").get();"
			+ "var kS = io.getI(\"KeyPanel\", \"s\").get();"
			+ "var gyro = mkio(70).mul(kQ).add(mkio(-70).mul(kE));"
			+ "var thrust = mkio(10).mul(kW).add(mkio(-10).mul(kS));"
			+ "var oGyro = io.getO(\"Gyro\", \"torque\").set(gyro);"
			+ "var oThrust = io.getO(\"Engine\", \"thrust\").set(thrust);"
			+ "io.status(gyro.getValue() + \" : \" + oGyro + \" | \" + thrust.getValue() + \" : \" + oThrust);";
	private ScriptEngine scriptEngine;

	public JavaScriptShipProg(String updateCode, ShipProgInterface shipProgInterface) {
		super(shipProgInterface);
		
		scriptEngine = manager.getEngineByName("nashorn");
		
		scriptEngine.put("io", shipProgInterface);
		try {
			scriptEngine.eval(initCode);
		} catch (ScriptException e) {
			throw new Error(e);
		}
	}

	@Override
	public void update(float deltaTime) {
		try {
			scriptEngine.eval(updateCode);
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}
}