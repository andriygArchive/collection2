package com.gushuley.collections2;

import java.util.Collection;

abstract class JRECollections2Adapter<E> 
implements Collection2<E>{
	@SuppressWarnings( "unchecked" )
	@Override @Deprecated
	public final boolean contains( Object o ) {
		return this.containValue( (E) o );
	}

	@SuppressWarnings( "unchecked" )
	@Override @Deprecated
	public final boolean containsAll( Collection<?> c ) {
		return containValues( (Collection<E>)c );
	}
	

	@Override @Deprecated
	public final boolean add( E e ) {
		throw collectionIsCopyOnWrite( "plus" );
	}

	protected UnsupportedOperationException collectionIsCopyOnWrite( String replacementOperation) throws UnsupportedOperationException {
		 throw new UnsupportedOperationException( getClass().getSimpleName() + " is a copy on write structure, use " + replacementOperation + " instead" );
	}

	@Override  @Deprecated
	public final boolean addAll( Collection<? extends E> c ) {
		throw collectionIsCopyOnWrite( "plus" );
	}

	@Override @Deprecated
	public final  boolean remove( Object o ) {
		throw collectionIsCopyOnWrite( "minus" );
	}

	@Override @Deprecated
	public final  boolean removeAll( Collection<?> c ) {
		throw collectionIsCopyOnWrite( "minus" );
	}

	@Override @Deprecated
	public final boolean retainAll( Collection<?> c ) {
		throw collectionIsCopyOnWrite( "retain" );
	}

	@Override @Deprecated
	public final void clear() {
		throw collectionIsCopyOnWrite( "Collection2.emptySet()|emptyList()|emptyArrayList()" );
	}
}
