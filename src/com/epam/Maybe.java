package com.epam;

import java.util.*;
import java.util.function.Consumer;

/**
 * Created by Dmytro_Ulanovych on 10/5/2015.
 */
public class Maybe<T> implements Iterable<T> {
    private List<T> value;

    public Maybe(T value) {
        if (value == null) {
            this.value = Collections.EMPTY_LIST;
        } else {
            this.value = new ArrayList<>();
            this.value.add(value);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return value.iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        value.forEach(action);
    }

    @Override
    public Spliterator<T> spliterator() {
        return value.spliterator();
    }
}
