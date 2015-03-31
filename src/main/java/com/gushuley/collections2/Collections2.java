package com.gushuley.collections2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Collections2 {
	final static <E> Set2<E> emptySet() {
		return LinkedSet2.emptySet();
	}

	final static  Object[] EMPTY_ARRAY = {};

	public static <T> Set2<T> linkedSet() {
		return LinkedSet2.emptySet();
	}

	static UnsupportedOperationException collectionIsCopyOnWrite( String name, String replacementOperation ) throws UnsupportedOperationException {
		throw new UnsupportedOperationException( name + " is a copy on write structure, use " + replacementOperation + " instead" );
	}
}
