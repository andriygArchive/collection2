package com.gushuley.collections2;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;


public interface List2<E> extends List<E>, Collection2<E> {
	@Deprecated @Override
	E set( int index, E element );

	@Deprecated @Override
	void add( int index, E element );

	@Deprecated @Override
	boolean addAll( int index, Collection<? extends E> c );

	List<E> insertAt( int index, Collection<? extends E> c );

	@Deprecated @Override
	E remove( int index );

	@Deprecated @Override
	int indexOf( Object o );

	@Deprecated @Override
	int lastIndexOf( Object o );

	int indexOfValue( E o );

	int lastIndexOfValue( E o );

	@Deprecated @Override
	List2Iterator<E> listIterator();

	@Override
	List2Iterator<E> listIterator( int index );

	@Override
	List2<E> subList( int fromIndex, int toIndex );

	List2<E> insertAt( int index, E element );

	List2<E> plus( E o );
}
