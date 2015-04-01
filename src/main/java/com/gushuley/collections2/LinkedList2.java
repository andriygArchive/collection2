package com.gushuley.collections2;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * User: andriyg
 * Date: 01/04/2015
 * Time: 11:08
 */
public abstract class LinkedList2<E>
	extends
		JRECollections2Adapter<E>
	implements
		List2<E>
{
	private static final List2Iterator<?> EMPTY_LIST_ITERATOR = new List2Iterator<Object>() {
		@Override
		public boolean hasNext() {
			return false;
		}

		@Override
		public Object next() {
			throw new NoSuchElementException();
		}

		@Override
		public void remove() {
			throw Collections2.collectionIsCopyOnWrite( "Lis2Iterator.empty", "List2.minus,List2.removeAt" );
		}

		@Override
		public List2<Object> copyFromCurrent() {
			return emptyList();
		}

		@Override
		public void add( Object o ) {
			throw Collections2.collectionIsCopyOnWrite( "Lis2Iterator.empty", "List2.plus,List2.addAfter,Iterator.addItem" );
		}

		@Override
		public List2<Object> addItem( Object o ) {
			return listOf( o );
		}

		@Override
		public boolean hasPrevious() {
			return false;
		}

		@Override
		public Object previous() {
			throw new NoSuchElementException();
		}

		@Override
		public int nextIndex() {
			return -1;
		}

		@Override
		public int previousIndex() {
			return -1;
		}

		@Override
		public void set( Object o ) {
			throw Collections2.collectionIsCopyOnWrite( "Lis2Iterator.empty", "List2.setAt,Iterator.setItem" );
		}

		@Override
		public List2<Object> replaceItem( Object o ) {
			throw new IndexOutOfBoundsException( "empty collectoin" );
		}
	};

	public static <E> List2Iterator<E> emptyListIterator() {
		return (List2Iterator<E>) EMPTY_LIST_ITERATOR;
	}

	@Override
	protected String getName() {
		return "LinkedList2";
	}

	protected IndexOutOfBoundsException indexOfBounds( int index ) throws IndexOutOfBoundsException {
		throw new IndexOutOfBoundsException( index + " of " + size() );
	}

	@Override @Deprecated
	public E set( int index, E element ) {
		throw collectionIsCopyOnWrite( "setItem" );
	}

	@Override @Deprecated
	public void add( int index, E element ) {
		throw collectionIsCopyOnWrite( "addAfter" );
	}

	@Override @Deprecated
	public E remove( int index ) {
		throw collectionIsCopyOnWrite( "removeAt" );
	}

	@Override @Deprecated
	public int indexOf( Object o ) {
		return indexOfValue( (E) o );
	}

	@Override @Deprecated
	public int lastIndexOf( Object o ) {
		return lastIndexOfValue( (E) o );
	}

	public abstract int indexOfValue( E o );

	public abstract int lastIndexOfValue( E o );

	public abstract LinkedList2<E> plus( E o );

	public abstract LinkedList2<E> plus( Collection<? extends E> c );

	public boolean addAll( int index, Collection<? extends E> c ) {
		throw collectionIsCopyOnWrite( "addAfter" );
	}


	@Override
	public abstract LinkedList2<E> insertAt( int index, E element );

	private static final class EmptyElement<E> extends LinkedList2<E> {
		@Override
		public LinkedList2<E> insertAt( int index, E element ) {
			if ( index < 0 || index > 0 ) {
				throw indexOfBounds( index );
			}
			return new ListItem( element, LinkedList2.emptyList() );
		}

		public LinkedList2<E> removeAt( int index ) {
			throw new IndexOutOfBoundsException( "Collection is empty" );
		}

		@Override
		public int indexOfValue( E o ) {
			return -1;
		}

		@Override
		public int lastIndexOfValue( E o ) {
			return -1;
		}

		@Override
		public List2Iterator<E> listIterator() {
			return emptyListIterator();
		}

		@Override
		public List2Iterator<E> listIterator( int index ) {
			if ( index < 0 || index > 0 ) {
				throw indexOfBounds( index );
			}

			return emptyListIterator();
		}

		@Override
		public List2<E> subList( int fromIndex, int toIndex ) {
			return this;
		}

		@Override
		public boolean containValue( E o ) {
			return false;
		}

		@Override
		public boolean containValues( Collection<? extends E> c ) {
			return false;
		}

		@Override
		public LinkedList2<E> plus( E o ) {
			return LinkedList2.listOf( o );
		}

		@Override
		public LinkedList2<E> plus( Collection<? extends E> c ) {
			return null;
		}

		@Override
		public Collection2<E> minus( Collection2<E> c ) {
			return this;
		}

		@Override
		public Collection2<E> minus( E c ) {
			return this;
		}

		@Override
		public Collection2<E> retain( Collection2<E> c ) {
			return null;
		}

		@Override
		public Collection2<E> empty() {
			return this;
		}

		@Override
		public Collection2<E> copyOf() {
			return this;
		}

		@Override
		public Stream2<? extends Collection2<E>, E> filter( Filter<E> filter ) {
			return null;
		}

		@Override
		public <T> Stream2<? extends Collection2<T>, T> map( Transform<E, T> map ) {
			return null;
		}

		@Override
		public int size() {
			return 0;
		}

		@Override
		public boolean isEmpty() {
			return true;
		}

		@Override
		public Iterator2<? extends Collection<E>, E> iterator() {
			return emptyListIterator();
		}

		@Override
		public Object[] toArray() {
			return Collections2.EMPTY_ARRAY;
		}

		@Override
		public <T> T[] toArray( T[] array ) {
			Objects.requireNonNull( array, "array" );
			return array;
		}

		@Override
		public E get( int index ) {
			throw new IndexOutOfBoundsException( "Empty collection" );
		}

		@Override
		public List2<E> insertAt( int index, Collection<? extends E> c ) {
			if ( index < 0 || index > 0 ) {
				throw indexOfBounds( index );
			}
			return LinkedList2.listOf( c );
		}

		@Override
		public String toString() {
			return "[empty]";
		}
	}

	private static class ListItem<E>
		extends
			LinkedList2<E>
	{
		private final E element;
		private final LinkedList2<E> head;
		private final int index;

		public ListItem( E element, LinkedList2<E> head ) {
			Objects.requireNonNull( head );

			this.element = element;
			this.head = head;
			if ( head instanceof ListItem ) {
				this.index = ( (ListItem) head ).index + 1;
			} else {
				this.index = 0;
			}
		}

		@Override
		public List<E> insertAt( int index, Collection<? extends E> c ) {
			return null;
		}

		@Override
		public int indexOfValue( E o ) {
			return 0;
		}

		@Override
		public int lastIndexOfValue( E o ) {
			return 0;
		}

		@Override
		public List2Iterator<E> listIterator() {
			return null;
		}

		@Override
		public List2Iterator<E> listIterator( int index ) {
			return null;
		}

		@Override
		public List2<E> subList( int fromIndex, int toIndex ) {
			return null;
		}

		@Override
		public boolean containValue( E o ) {
			return false;
		}

		@Override
		public boolean containValues( Collection<? extends E> c ) {
			return false;
		}

		@Override
		public LinkedList2<E> plus( E o ) {
			return null;
		}

		@Override
		public LinkedList2<E> plus( Collection<? extends E> c ) {
			return null;
		}

		@Override
		public Collection2<E> minus( Collection2<E> c ) {
			return null;
		}

		@Override
		public Collection2<E> minus( E c ) {
			return null;
		}

		@Override
		public Collection2<E> retain( Collection2<E> c ) {
			return null;
		}

		@Override
		public Collection2<E> empty() {
			return null;
		}

		@Override
		public Collection2<E> copyOf() {
			return null;
		}

		@Override
		public Stream2<? extends Collection2<E>, E> filter( Filter<E> filter ) {
			return null;
		}

		@Override
		public <T> Stream2<? extends Collection2<T>, T> map( Transform<E, T> map ) {
			return null;
		}

		@Override
		public int size() {
			return 0;
		}

		@Override
		public boolean isEmpty() {
			return false;
		}

		@Override
		public Iterator2<? extends Collection<E>, E> iterator() {
			return null;
		}

		@Override
		public Object[] toArray() {
			return new Object[0];
		}

		@Override
		public <T> T[] toArray( T[] a ) {
			return null;
		}

		@Override
		public E get( int index ) {
			return null;
		}

		@Override
		public LinkedList2<E> insertAt( int index, E element ) {
			return null;
		}

	}

	private static LinkedList2<?> EMPTY_LIST = new EmptyElement<>();

	static <E> LinkedList2<E> emptyList() {
		return (LinkedList2<E>) EMPTY_LIST;
	}

	public static <E> LinkedList2<E> listOf( E o ) {
		return new ListItem( o, emptyList() );
	}

	public static <E> LinkedList2<E> listOf( Collection<? extends E> o ) {
		Objects.requireNonNull( o, "o" );

		if ( o instanceof LinkedList2 ) {
			return (LinkedList2<E>) o;
		}

		LinkedList2<E> list = emptyList();
		for ( final E value : o ) {
			list = list.plus( o );
		}
		return list;
	}


}
