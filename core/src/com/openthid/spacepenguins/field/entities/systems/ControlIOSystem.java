package com.openthid.spacepenguins.field.entities.systems;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.openthid.spacepenguins.field.entities.components.ControlIOComponent;
import com.openthid.spacepenguins.field.entities.ship.control.ControlInput;
import com.openthid.spacepenguins.field.entities.ship.control.ControlOutput;

public class ControlIOSystem extends EntitySystem {

	private ImmutableArray<Entity> entities;
	private ScriptEngine scriptEngine;

	public ControlIOSystem() {
		ScriptEngineManager manager = new ScriptEngineManager();
		scriptEngine = manager.getEngineByName("nashorn");
		
		scriptEngine.put("io", new IOScriptInterface());
		try {
			scriptEngine.eval("var IOable = Java.type(\"com.openthid.spacepenguins.field.entities.ship.control.IOable\")");
		} catch (ScriptException e) {
			throw new Error(e);
		}
	}

	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(ControlIOComponent.class).get());
	}

	@Override
	public void update(float deltaTime) {
		try {
			scriptEngine.eval("var x = io.getO(\"GYRO\", \"torque\").set(IOable.fromFloat(-5)); //print(x);");
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}

	public class IOScriptInterface {
		private IOScriptInterface() {
		}

		public ControlInput getI(String nameSpace, String key) {
			for (int i = 0; i < entities.size(); i++) {
				ControlIOComponent ioComponent = entities.get(i).getComponent(ControlIOComponent.class);
				if (ioComponent.getNameSpace().equals(nameSpace)) {
					return ioComponent.getInput(key);
				}
			}
			return null;
		}

		public ControlOutput getO(String nameSpace, String key) {
			for (int i = 0; i < entities.size(); i++) {
				ControlIOComponent ioComponent = entities.get(i).getComponent(ControlIOComponent.class);
				if (ioComponent.getNameSpace().equals(nameSpace)) {
					return ioComponent.getOutput(key);
				}
			}
			return null;
		}
	}
}