package com.openthid.util;

@FunctionalInterface
public interface ArrayFunction<T, R> {
	@SuppressWarnings("unchecked")
	public R apply(T... t);
}