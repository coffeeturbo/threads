package threads.sync;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {

    @GuardedBy("this")
    private final DynamicList<T> list = new DynamicList<>();

    public void add(T elem) {
        synchronized (this) {
            list.add(elem);
        }
    }

    @Override
    public  Iterator<T> iterator() {
        synchronized (this) {
            return copy(list).iterator();
        }
    }

    private synchronized DynamicList<T> copy(DynamicList<T> iterator) {
        DynamicList<T> newList = new DynamicList<>();
        iterator.forEach(newList::add);
        return newList;
    }

}
