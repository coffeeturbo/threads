package threads.wait;

import org.junit.Assert;
import org.junit.Test;

public class BlockQueueTest {

    @Test
    public void offer() throws InterruptedException {
        BlockQueue<Integer> queue = new BlockQueue<>();

        Thread producer = new Thread(() -> queue.offer(1));
        Thread consumer = new Thread(queue::poll);


        producer.start();
        consumer.start();
        producer.join();
        consumer.join();

        Assert.assertEquals(0, queue.getQueue().size());

    }

    @Test
    public void poll() throws InterruptedException {
        BlockQueue<Integer> queue = new BlockQueue<>();

        Thread producer = new Thread(() -> queue.offer(1));

        producer.start();
        producer.join();

        Assert.assertEquals(1, queue.getQueue().size());
    }
}