package com.gushuley.collections2;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

abstract class LinkedSet2<E> 
extends JRECollections2Adapter<E> 
implements Set2<E> 
{ 
	
	public static class EmptyLinkedSet2Stream<T> implements Stream2<LinkedSet2<T>, T> {
		@Override
		public Iterator<T> iterator() {
			return Collections2.emptyIterator();
		}

		@Override
		public LinkedSet2<T> copyOf() {
			return emptySet();
		}
	}
	
	final static EmptyLinkedSet2Stream<?> EMPTY_STREAM = new EmptyLinkedSet2Stream<>();

	final static Set2<?> EMPTY = new EmptySet2<>();
	
	final static class EmptySet2<E> extends LinkedSet2<E> {
		public int size() {
			return 0;
		}

		public boolean isEmpty() {
			return true;
		}

		public Iterator<E> iterator() {
			return Collections2.emptyIterator();
		}

		public Object[] toArray() {
			return Collections2.EMPTY_ARRAY;
		}

		public <T> T[] toArray( T[] a ) {
			Objects.requireNonNull( a );
			
			return a;
		}

		@Override
		public LinkedSet2<E> plus( E o ) {
			return LinkedSet2.linkedSet( o );
		}

		@Override
		public LinkedSet2<E> minus( Collection2<E> c ) {
			return this;
		}

		@Override
		public LinkedSet2<E> minus( E c ) {
			return this;
		}

		@Override
		public boolean containValue( E o ) {
			return false;
		}

		@Override
		public Stream2<LinkedSet2<E>, E> filter( Filter<E> filter ) {
			return emptyStream();
		}

		@Override
		public <T> Stream2<LinkedSet2<T>, T> map( Transform<E, T> map ) {
			return emptyStream();
		}
	}
	
	private static class SetElement<E> extends LinkedSet2<E> {

		private final E element;
		private final LinkedSet2<E> tail;

		public SetElement( E o, LinkedSet2<E> tail ) {
			Objects.requireNonNull( o, "element" );
			Objects.requireNonNull( tail, "tail" );
			
			this.element = o;
			this.tail = tail;
		}

		@Override
		public LinkedSet2<E> plus( E o ) {
			if ( Objects.equals( o, element ) ) {
				return this;
			} else {
				final LinkedSet2<E> ret = tail.plus( o );
				if ( tail != ret ) {
					return new SetElement<E>( element, ret );
				} else {
					return this;
				}
			}
		}

		@Override
		public LinkedSet2<E> minus( Collection2<E> c ) {
			Objects.requireNonNull( c );
			
			LinkedSet2<E> set = this;
			for ( final E item : c ) {
				set = set.minus( item );
			}
			return set;
		}

		@Override
		public LinkedSet2<E> minus( E c ) {
			if ( Objects.equals( element, c ) ) {
				return tail;
			} else {
				final LinkedSet2<E> ret = tail.minus( c );
				if ( ret != tail ) {
					return new SetElement<E>( element, ret );
				} else {
					return this;
				}
			}
		}

		

		@Override
		public boolean containValue( E item ) {
			Objects.requireNonNull( item, "item" );
			if ( Objects.equals( item, element ) ) {
				return true;
			} else {
				return tail.containValue( item );
			}
		}

		@Override
		public int size() {
			return tail.size() + 1;
		}

		@Override
		public boolean isEmpty() {
			return false;
		}

		@Override
		public Iterator<E> iterator() {
			return null;
		}

		@Override
		public Object[] toArray() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T[] toArray( T[] a ) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Stream2<LinkedSet2<E>, E> filter( Filter<E> filter ) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public <T> Stream2<LinkedSet2<T>, T> map( Transform<E, T> map ) {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	@Override
	public abstract LinkedSet2<E> plus( E o );
	
	@Override
	public abstract LinkedSet2<E> minus( Collection2<E> c );
	
	@Override
	public abstract LinkedSet2<E> minus( E c );

	@Override
	public final boolean containValues( Collection<? extends E> c ) {
		Objects.requireNonNull( c );
		
		for ( final E element : c ) {
			if ( !this.containValue( element ) ) {
				return false;
			}
		}
		
		return true;
	}

	@Override
	public final Collection2<E> reatain( Collection2<E> c ) {
		Objects.requireNonNull( c );
		
		LinkedSet2<E> set = emptySet();
		for ( final E element : c ) {
			if ( this.containValue( element ) ) {
				continue;
			}
			set = set.plus( element );
		}
		return set;
	}
	
	@Override
	public LinkedSet2<E> plus( Collection<? extends E> c ) {
		LinkedSet2<E> set = this;
		for ( E item : c ) {
			set = set.plus( item );
		}
		return set;
	}
	
	@Override
	public Set2<E> copyOf() {
		return this;
	}

	@SuppressWarnings( "unchecked" )
	static <E> LinkedSet2<E> emptySet() {
		return (LinkedSet2<E>)EMPTY;
	}

	@Override
	public Set2<E> empty() {
		return LinkedSet2.emptySet();
	}
	
	static <E> LinkedSet2<E> linkedSet( Collection<? extends E> item ) {
		return LinkedSet2.<E>emptySet().plus( item );
	}

	
	static <E> LinkedSet2<E> linkedSet( E item ) {
		Objects.requireNonNull( item, "item" );
		
		return new SetElement<E>( item, LinkedSet2.<E>emptySet() );
	}

	static <E> LinkedSet2<E> linkedSet( final Iterator<E> iterator ) {
		Objects.requireNonNull( iterator, "iterator" );

		LinkedSet2<E> set = LinkedSet2.<E>emptySet();
		while ( iterator.hasNext() ) {
			set = set.plus( iterator.next() );
		}
		return set;
	}

	@SuppressWarnings( "unchecked" )
	private static <T> EmptyLinkedSet2Stream<T> emptyStream() {
		return (EmptyLinkedSet2Stream<T>)EMPTY_STREAM;
	}
}
