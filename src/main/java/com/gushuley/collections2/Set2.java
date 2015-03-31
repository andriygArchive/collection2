package com.gushuley.collections2;

import java.util.Collection;
import java.util.Set;

public interface Set2<E> extends Collection2<E>, Set<E> {
	@Override
	public Set2<E> plus( Collection<? extends E> c );
	
	@Override
	public Set2<E> plus( E o );

	@Override
	public Set2<E> copyOf();
	
	@Override
	public Set2<E> empty();
	
	@Override
	public Set2<E> minus( Collection2<E> c );
	
	@Override
	public Set2<E> minus( E c );
	
	@Override
	public Stream2<? extends Set2<E>, E> filter( Filter<E> filter );
	
	@Override
	public <T> Stream2<? extends Set2<T>, T> map( Transform<E, T> map );
}
