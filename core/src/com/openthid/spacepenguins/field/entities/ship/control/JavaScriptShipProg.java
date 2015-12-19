package com.openthid.spacepenguins.field.entities.ship.control;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.openthid.spacepenguins.field.entities.ship.Ship.ShipProgInterface;

public class JavaScriptShipProg extends ShipProg {

	private static ScriptEngineManager manager = new ScriptEngineManager();

	private String initCode =
			  "var IOable = Java.type(\"com.openthid.spacepenguins.field.entities.ship.control.IOable\");"
			+ "var mkio = IOable.fromFloat";
	private String updateCode =
			  "var t = io.getI(\"Clock\", \"time\").get();" 
			+ "var tt = t.add(mkio(9)).mul(mkio(10)).sinDeg().mul(mkio(5));"
			+ "var x = io.getO(\"Gyro\", \"torque\").set(tt);"
			+ "io.status(x + \"  \" + tt.getValue());";
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