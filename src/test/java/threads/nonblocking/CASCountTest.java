package threads.nonblocking;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class CASCountTest {

    @Test
    public void increment() throws InterruptedException {

        CASCount count = new CASCount();

        Thread increment1 = new Thread(count::increment);
        Thread increment2 = new Thread(count::increment);
        Thread increment3 = new Thread(count::increment);

        increment1.start();
        increment2.start();
        increment3.start();

        increment1.join();
        increment2.join();
        increment3.join();

        assertThat(count.get(), is(3));
    }
}