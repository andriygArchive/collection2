package com.gushuley.collections2;

import java.util.Iterator;


public interface Stream2<C extends Collection2<? extends E>, E> extends Iterable<E> {
	@Override
	public Iterator2<? extends C, E> iterator();

	/***
	 * @return subset of selection
	 */
	C copyOf();
}
