package com.gushuley.collections2;

import java.util.Iterator;

/**
 * User: andriyg
 * Date: 31/03/2015
 * Time: 15:47
 */
public interface Iterator2<C extends Collection2<E>, E>
	extends
		Iterator<E>
{
	C copyFromCurrent();
}
