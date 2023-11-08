package it.unibo.inner.impl;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T> {

    private final List<T> elements;
    private Predicate<T> filter;

    public IterableWithPolicyImpl(T[] elements) {
        this(
            elements,
            new Predicate<>() {
                @Override
                public boolean test(T elem) {
                    return true;
                }
            }
        );
    }

    public IterableWithPolicyImpl(T[] elements, Predicate<T> filter) {
        this.elements = List.of(elements);
        this.filter = filter;
    }

    @Override
    public Iterator<T> iterator() {
        return new FilteredIterator();
    }

    @Override
    public void setIterationPolicy(Predicate<T> filter) {
        this.filter = filter;
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        sb.append("[");
        for (var elem : this) {
            sb.append(elem + ", ");
        }
        sb.append("]");
        return sb.toString();
    }

    private class FilteredIterator implements Iterator<T> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            while (currentIndex < elements.size()) {
                T elem = elements.get(currentIndex);
                if (filter.test(elem)) {
                    return true;
                }
                currentIndex++;
            }
            return false;
        }

        @Override
        public T next() {
            if (hasNext()) {
                return elements.get(currentIndex++);
            }
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }
    
}
