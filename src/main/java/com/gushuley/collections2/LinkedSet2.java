package com.gushuley.collections2;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

abstract class LinkedSet2<E> 
extends JRECollections2Adapter<E> 
implements Set2<E> 
{
	private static final Iterator2<? extends LinkedSet2<?>, ?> EMPTY_LINKEDSET_ITERATOR = new Iterator2<LinkedSet2<Object>, Object>() {
		@Override
		public boolean hasNext() {
			return false;
		}

		@Override
		public Object next() {
			throw new NoSuchElementException();
		}

		@Override
		public LinkedSet2<Object> copyFromCurrent() {
			return emptySet();
		}

		@Override
		public void remove() {
			Collections2.collectionIsCopyOnWrite( "LinkedSet2.Iterator", "Set.minus" );
		}
	};

	private static <T> Iterator2<? extends LinkedSet2<T>, T> emptyIterator() {
		return (Iterator2<? extends LinkedSet2<T>, T>) EMPTY_LINKEDSET_ITERATOR;
	}

	private static class LinkedSet2TransformIterator<T, S>
		implements Iterator2<LinkedSet2<T>, T>
	{
		private final Transform<S, T> map;
		private LinkedSet2<S> next;

		public LinkedSet2TransformIterator( LinkedSet2<S> first, Transform<S, T> map ) {
			Objects.requireNonNull( first, "first" );
			Objects.requireNonNull( next, "next" );

			this.map = map;
			this.next = first;
		}

		@Override
		public boolean hasNext() {
			return next instanceof SetElement ;
		}

		@Override
		public T next() {
			if ( ! ( next instanceof SetElement ) ) {
				throw new NoSuchElementException( "No Such Element" );
			}
			final SetElement<S> self = (SetElement<S>) next;
			next = self.tail;
			return map.transform( self.element );
		}

		@Override
		public void remove() {
			throw Collections2.collectionIsCopyOnWrite( "LinkedSet2", "Set2.minus" );
		}

		@Override
		public LinkedSet2<T> copyFromCurrent() {
			return LinkedSet2.linkedSet( new LinkedSet2TransformIterator<>( next, map ) );
		}
	}

	public static class LinkedSet2FilteredIterator<E> implements Iterator2<LinkedSet2<E>, E> {
		private LinkedSet2<E> next;
		private Filter<E> filter;

		public LinkedSet2FilteredIterator( LinkedSet2<E> element, Filter<E> filter ) {
			Objects.requireNonNull( element, "element" );
			Objects.requireNonNull( filter, "filter" );
			this.next = element;
			this.filter = filter;
			
			ensureNextIsMatchToFilter();
		}
		
		private void ensureNextIsMatchToFilter() {
			while ( next instanceof SetElement<?> && !filter.match( ( (SetElement<E>)next ).element ) ) {
				next = ( (SetElement<E>)next ).tail;
			}
		}
		
		@Override
		public boolean hasNext() {
			return ( next instanceof SetElement<?> );
		}

		@Override
		public E next() {
			if ( !( next instanceof SetElement<?> ) ) {
				throw new NoSuchElementException();
			}

			final SetElement<E> self = (SetElement<E>) next;
			next = self.tail;
			ensureNextIsMatchToFilter();
			return self.element;
		}

		@Override
		public void remove() {
			Collections2.collectionIsCopyOnWrite( "LinkedSet2.Iterator", "set.minus" );
		}

		@Override
		public LinkedSet2<E> copyFromCurrent() {
			return LinkedSet2.linkedSet( new LinkedSet2FilteredIterator<E>( next, filter ) );
		}
	}

	public static class EmptyLinkedSet2Stream<T> implements Stream2<LinkedSet2<T>, T> {
		@Override
		public Iterator2<? extends LinkedSet2<T>, T> iterator() {
			return emptyIterator();
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

		public Iterator2<? extends Collection<E>, E> iterator() {
			return emptyIterator();
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

		@Override
		public String toString() {
			return "[empty]";
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
		public Iterator2<? extends LinkedSet2<E>, E> iterator() {
			return new Iterator2<LinkedSet2<E>, E>() {
				LinkedSet2<E> from = SetElement.this;

				@Override
				public boolean hasNext() {
					return ( from instanceof SetElement<?> );
				}

				@Override
				public E next() {
					if ( !( from instanceof SetElement<?> ) ) {
						throw new NoSuchElementException();
					}
					
					final SetElement<E> self = (SetElement<E>)from;
					from = self.tail;
					return self.element;
				}

				@Override
				public LinkedSet2<E> copyFromCurrent() {
					return from;
				}

				@Override
				public void remove() {
					Collections2.collectionIsCopyOnWrite( "LinkSet2.Iterator", "set.minus" );
				}
			};
		}

		@Override
		public Object[] toArray() {
			final Object[] array = new Object[size()];
			int i = 0;
			for ( E e : this ) {
				array[i] = e;
				i ++;
			};
			return array;
		}


		@SuppressWarnings( "unchecked" )
		@Override
		public <T> T[] toArray( T[] array ) {
			Objects.requireNonNull( array, "array" );
			
			final int size = size();
			if ( array.length < size ) {
				array = (T[])java.lang.reflect.Array.newInstance( array.getClass().getComponentType(), size );
			}
			int i = 0;
			
			Object[] result = array;
			for ( E e : this ) {
				result[i] = e;
				i ++;
			};

			return array;
		}

		@Override
		public Stream2<LinkedSet2<E>, E> filter( final Filter<E> filter ) {
			Objects.requireNonNull( filter, "filter" );
			
			return new Stream2<LinkedSet2<E>, E>() {

				@Override
				public Iterator2<? extends LinkedSet2<E>, E> iterator() {
					return new LinkedSet2FilteredIterator<>( SetElement.this, filter );
				}

				@Override
				public LinkedSet2<E> copyOf() {
					return LinkedSet2.linkedSet( iterator() );
				}
			};
		}
		
		@Override
		public <T> Stream2<LinkedSet2<T>, T> map( final Transform<E, T> map ) {
			Objects.requireNonNull( map, "map" );

			return new Stream2<LinkedSet2<T>, T>() {
				@Override
				public Iterator2<? extends LinkedSet2<T>, T> iterator() {
					return new LinkedSet2TransformIterator<>( SetElement.this, map );
				}

				@Override
				public LinkedSet2<T> copyOf() {
					return LinkedSet2.linkedSet( iterator() );
				}
			};
		}

		@Override
		public String toString() {
			final StringBuilder builder = new StringBuilder( "[" );
			for ( E element : this ) {
				builder.append( element ).append( ", " );
			}
			builder.append( "]" );
			return builder.toString();
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
	public final Collection2<E> retain( Collection2<E> c ) {
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
	
	@Override
	protected String getName() {
		return "LinkSet2";
	}

	@Override
	public boolean equals( Object obj ) {
		if ( !( obj instanceof LinkedSet2 ) ) {
			return false;
		}

		final LinkedSet2<E> other = ( LinkedSet2<E>) obj;
		if ( !other.minus( this ).isEmpty() ) {
			return false;
		}
		if ( !this.minus( other ).isEmpty() ) {
			return false;
		}
		return true;
	}
}
