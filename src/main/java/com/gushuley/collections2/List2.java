package com.gushuley.collections2;

import java.util.Collection;
import java.util.List;


public interface List2<E> extends List<E> {
	@Deprecated
	public boolean contains( Object o );

	@Deprecated
	public boolean containsAll( Collection<?> c );

	@Deprecated
	public int lastIndexOf( Object o );
	
	@Deprecated
	public int indexOf( Object o );
	
	@Deprecated
	public boolean remove( Object o );	
}
