package threads.sync;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class DynamicList<E> implements Iterable<E>  {

    private Object[] container;
    private int position = 0;

    public DynamicList() {
        this.container = new Object[1];
    }

    public void add(E value) {
        if (this.container.length == this.position + 1) {
            this.container = Arrays.copyOf(this.container, this.container.length + 1);
        }
        this.container[position] = value;
        position++;
    }

    public E get(int index) throws ArrayIndexOutOfBoundsException {
        if (index < 0 || index > this.container.length) {
            throw new ArrayIndexOutOfBoundsException("Index out of range array!");
        }
        return (E) this.container[index];
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            int modCount = position;

            int itPosition = 0;

            @Override
            public boolean hasNext() throws ConcurrentModificationException {
                boolean result = false;
                if (itPosition != container.length - 1) {
                    result = true;
                }
                if (modCount != position) {
                    throw new ConcurrentModificationException("Collection has been modified!");
                }
                return result;
            }

            @Override
            public E next() throws NoSuchElementException {
                if (hasNext()) {
                    return (E) container[itPosition++];
                }
                throw new NoSuchElementException("No more left element in collection!");
            }
        };
    }
}
