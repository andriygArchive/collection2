package com.gushuley.collections2;

import java.util.List;
import java.util.ListIterator;

/**
 * User: andriyg
 * Date: 01/04/2015
 * Time: 11:05
 */
public interface List2Iterator<E> extends Iterator2<List2<E>, E>, ListIterator<E> {
	@Deprecated @Override
	void add( E e );

	List2<E> addItem( E e );

	@Deprecated @Override
	void set( E e );

	List2<E> replaceItem( E e );
}
