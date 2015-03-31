package com.gushuley.collections2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Collections2 {

	static final Iterator<?> EMPTY_ITERATOR = new Iterator<Object>() {

		@Override
		public boolean hasNext() {
			return false;
		}

		@Override
		public Object next() {
			throw new NoSuchElementException( "emptyIterator" );
		}

		@Override
		public void remove() {
			throw new IllegalStateException( "emptyIterator" );
		}
	};

	@SuppressWarnings( "unchecked" )
	final static <E> Set2<E> emptySet() {
		return (Set2<E>)LinkedSet2.EMPTY;
	}

	@SuppressWarnings( "unchecked" )
	public static <E> Iterator<E> emptyIterator() {
		return (Iterator<E>)EMPTY_ITERATOR;
	}

	final static  Object[] EMPTY_ARRAY = {};
}
