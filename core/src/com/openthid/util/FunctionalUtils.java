package com.openthid.util;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.IntFunction;

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

	public static <A, B, C> C[] zipWith(A[] as, B[] bs, BiFunction<A, B, C> function, IntFunction<C[]> newArrayFunction) {
		C[] cs = newArrayFunction.apply(as.length);
		for (int i = 0; i < bs.length; i++) {
			cs[i] = function.apply(as[i], bs[i]);
		}
		return cs;
	}
}