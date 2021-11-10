package threads.coccurent;

import org.junit.Test;

public class WgetTest {

    @Test(expected = IllegalArgumentException.class)
    public void whenFalse() throws InterruptedException {
         Wget.main(new String[]{});
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenArgTwoEmptyFalse() throws InterruptedException {
         Wget.main(new String[]{"url"});
    }

}