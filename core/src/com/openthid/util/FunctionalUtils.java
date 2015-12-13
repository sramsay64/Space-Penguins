package com.openthid.util;

import java.util.function.BiConsumer;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

public class FunctionalUtils {
	public static ChangeListener makeChangeListener(BiConsumer<ChangeEvent, Actor> biConsumer) {
		return new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				biConsumer.accept(event, actor);
			}
		};
	}
}
