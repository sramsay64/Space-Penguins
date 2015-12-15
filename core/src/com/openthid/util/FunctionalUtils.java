package com.openthid.util;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

/**
 * A class with utility functions to assist functional programming
 */
public class FunctionalUtils {
	/**
	 * Creates a {@link ChangeListener} from a function
	 */
	public static ChangeListener makeChangeListener(BiConsumer<ChangeEvent, Actor> biConsumer) {
		return new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				biConsumer.accept(event, actor);
			}
		};
	}

	/**
	 * Takes two arrays and creates a third by applying each element from the two arrays to the given function
	 * Same as Haskell's zipWith function.
	 */
	public static <A, B> B[] map(A[] as, Function<A, B> function, IntFunction<B[]> newArrayFunction) {
		B[] cs = newArrayFunction.apply(as.length);
		for (int i = 0; i < as.length; i++) {
			cs[i] = function.apply(as[i]);
		}
		return cs;
	}

	/**
	 * Takes two arrays and creates a third by applying each element from the two arrays to the given function
	 * Same as Haskell's zipWith function.
	 * throws {@link ArrayIndexOutOfBoundsException} If bs.length < as.length
	 */
	public static <A, B, C> C[] zipWith(A[] as, B[] bs, BiFunction<A, B, C> function, IntFunction<C[]> newArrayFunction) {
		C[] cs = newArrayFunction.apply(as.length);
		for (int i = 0; i < as.length; i++) {
			cs[i] = function.apply(as[i], bs[i]);
		}
		return cs;
	}

	public static <A, B> A applyMany(A a, B[] bs, BiConsumer<A, B> biConsumer) {
		for (int i = 0; i < bs.length; i++) {
			biConsumer.accept(a, bs[i]);
		}
		return a;
	}

	/**
	 * 
	 * @param a
	 * @param bs
	 * @param cs
	 * @param biConsumer
	 * @return a
	 * throws {@link ArrayIndexOutOfBoundsException} If cs.length < bs.length
	 */
	public static <A, B, C> A applyManyZip(A a, B[] bs, C[] cs, TriConsumer<A, B, C> triConsumer) {
		for (int i = 0; i < bs.length; i++) {
			triConsumer.accept(a, bs[i], cs[i]);
		}
		return a;
	}
}