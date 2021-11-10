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

    @Test
    public void whenManyTimes() {
        BlockQueue<Integer> queue = new BlockQueue<>();

        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                queue.offer(i);
            }
        });


        Thread consumer = new Thread(() -> {
            while (true) {
                System.out.println(queue.poll());
            }
        });

        consumer.start();
        producer.start();

//        producer.join();
//        consumer.join();

//        Assert.assertEquals(0, queue.getQueue().size());
    }
}