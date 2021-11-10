package threads.wait;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class BlockQueue<T> {


    @GuardedBy("queue")
    private final Queue<T> queue = new LinkedList<>();

    public void offer(T element) {
        synchronized (queue) {
            while (queue.size() != 0) {
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }

            queue.offer(element);
            queue.notifyAll();
        }
    }

    public T poll() {
        synchronized (queue) {
            while (queue.size() == 0) {
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }

            T element = queue.poll();
            queue.notifyAll();
            return element;
        }
    }

    public Queue<T> getQueue() {
        synchronized (queue) {
            return queue;
        }
    }


    public static void main(String[] args) throws InterruptedException {
        BlockQueue<Integer> queue = new BlockQueue<>();

        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println("WRITE: " + i);
                queue.offer(i);
            }
        });


        Thread consumer = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {

                try {
                    System.out.println(queue.poll());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });


        producer.start();
        consumer.start();
        producer.join();
        consumer.interrupt();

    }
}
