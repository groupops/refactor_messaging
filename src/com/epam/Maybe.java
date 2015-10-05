package com.epam;

import java.util.Iterator;

/**
 * Created by Dmytro_Ulanovych on 10/5/2015.
 */
public class Maybe<T> implements Iterable<T> {
    private T value;

    public Maybe(T value) {
        this.value = value;
    }

    public Maybe() {
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return value != null;
            }

            @Override
            public T next() {
                T returnValue = value;
                value = null;
                return returnValue;
            }
        };
    }
}
