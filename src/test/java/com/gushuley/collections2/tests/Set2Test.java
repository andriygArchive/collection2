/*
 * Idea from apache pivot collections unit tests
 */

package com.gushuley.collections2.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;

import org.junit.Test;

import com.gushuley.collections2.Collection2;
import com.gushuley.collections2.Collections2;
import com.gushuley.collections2.Set2;

public class Set2Test {

	@Test
	public void basicTest() {
		Set2<String> set = Collections2.linkedSet();

		set = set.plus( "A" );
		assertFalse( set.containValue( "0" ) );
		assertTrue( set.containValue( "A" ) );
		assertFalse( set.containValue( "B" ) );

		set = set.plus( "B" );
		assertFalse( set.containValue( "0" ) );
		assertTrue( set.containValue( "A" ) );
		assertTrue( set.containValue( "B" ) );
		assertFalse( set.containValue( "C" ) );

		System.out.println( set );

		assertEquals( set.size(), 2 );

		int i = 0;
		for ( String element : set ) {
			assertTrue( element.equals( "A" ) || element.equals( "B" ) );
			i++;
		}

		assertEquals( i, 2 );

		set = set.minus( "B" );
		assertFalse( set.contains( "B" ) );

		set = set.minus( "A" );
		assertFalse( set.contains( "A" ) );

		assertTrue( set.isEmpty() );

		set = set.plus( "A" );
		set = set.plus( "B" );
		set = set.plus( "C" );

		Iterator<String> iter = set.iterator();
		int count = 0;
		while ( iter.hasNext() ) {
			String s = iter.next();
			if ( !set.contains( s ) ) {
				fail( "Unknown element in set " + s );
			}
			count++;
		}
		assertEquals( 3, count );

		iter = set.iterator();
		while ( iter.hasNext() ) {
			set = set.minus( iter.next() );
		}
		assertEquals( 0, set.size() );
	}

	@Test
	public void equalsTest() {
		Set2<String> set1 = Collections2.<String>linkedSet()
			.plus( "one" ).plus( "two" ).plus( "three" );

		Set2<String> set2 = Collections2.<String>linkedSet()
			.plus( "one" ).plus( "two" ).plus( "three" );;

		// Same
		assertEquals( set1, set2 );

		// Different values
		set2 = set1.minus( "three" ).plus( "four" );
		assertNotEquals( set1, set2 );

		// Different lengths
		set2 = set2.plus( "three" );
		assertNotEquals( set1, set2 );
	}
}
