package com.gushuley.collections2;

public interface Filter<T> {

	boolean match( T element );
}
