package com.openthid.util;

import java.util.Map.Entry;

public class ImmutableEntry<K, V> implements Entry<K, V> {

	private K key;
	private V value;

	public ImmutableEntry(K key, V value) {
		super();
		this.key = key;
		this.value = value;
	}

	@Override
	public K getKey() {
		return key;
	}

	@Override
	public V getValue() {
		return value;
	}

	@Override
	public V setValue(V value) {
		throw new UnsupportedOperationException("Object is Immutable");
	}
}
