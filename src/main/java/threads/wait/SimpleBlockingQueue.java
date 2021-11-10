package threads.wait;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;


@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    private final Integer capacity;


    public SimpleBlockingQueue(final Integer capacity) {
        this.capacity = capacity;
    }

    public void offer(T value) {
        synchronized (this) {
            while (queue.size() == capacity) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.offer(value);
            this.notifyAll();
        }
    }

    public T poll() throws InterruptedException {
        synchronized (this) {
            if (queue.size() == 0) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    throw new InterruptedException();
                }
            }

            T val = queue.poll();
            this.notifyAll();
            return val;
        }
    }

    public synchronized Integer size() {
        return this.queue.size();
    }


}
