package com.gushuley.collections2;

import java.util.Collection;
import java.util.Iterator;


public interface Collection2<E> extends Collection<E> {
	@Deprecated
	@Override
	public boolean contains( Object o );
	
	@Deprecated
	@Override
	public boolean containsAll( Collection<?> c );
	
	public boolean containValue( E o );

	public boolean containValues( Collection<? extends E> c );

	@Deprecated
	@Override
	public boolean add( E e );
	
	@Deprecated
	@Override
	public boolean addAll( Collection<? extends E> c );
	
	public Collection2<E> plus( E o );
	
	public Collection2<E> plus( Collection<? extends E> c );
	
	/***
	 * See {@link Collection2#minus(Object)}
	 */
	@Deprecated
	@Override
	public boolean remove( Object o );
	
	/***
	 * See {@link Collection2#minus(Collection2)}
	 */
	@Deprecated
	@Override
	public boolean removeAll( Collection<?> c );
	
	/***
	 * Returns current collection without elements, which are in asked collection. Or return current collection.
	 * {@link Collection#removeAll(Collection)} 
	 * @param c
	 * @return
	 */
	public Collection2<E> minus( Collection2<E> c );
	
	/***
	 * Returns current collection without instances of element. Or return current collection.
	 * {@link Collection#remove(Object)} 
	 * @param c
	 * @return
	 */
	public Collection2<E> minus( E c );
	
	/***
	 * See {@link Collection2#retain}
	 */
	@Deprecated
	@Override
	public boolean retainAll( Collection<?> c );
	
	/**
	 * Return subset of current collection, which are exist in asked collection. Return empty collection if they are not intersected.
	 * See {@link Collection#retainAll(Collection)}
	 * @param c
	 * @return new Collection
	 */
	public Collection2<E> retain( Collection2<E> c );

	/***
	 * See {@link Collection2#empty()}
	 */
	@Deprecated()
	@Override
	public void clear();
	
	/***
	 * Returns empty version of collection
	 */
	public Collection2<E> empty();
	
	/***
	 * Ensure that collection is final. To get a copy of mapped, filtered collection.
	 * @return
	 */
	public Collection2<E> copyOf();
	
	/**
	 * Subset of elements, which are mets filter
	 * @return
	 */
	public Stream2<? extends Collection2<E>, E> filter( Filter<E> filter );
	
	public <T> Stream2<? extends Collection2<T>, T> map( Transform<E, T> map);

	@Override
	Iterator2<? extends Collection<E>, E> iterator();
}
