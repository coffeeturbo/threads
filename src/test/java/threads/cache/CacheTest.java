package threads.cache;

import org.junit.Assert;
import org.junit.Test;

public class CacheTest {

    @Test
    public void failUpdate() {
        Cache cache = new Cache();
        Base base = Base.builder().id(1).version(0).build();
        cache.add(base);

        Base user1 = Base.builder().id(1).version(-1).build();
        user1.setName("User 2");
        Assert.assertThrows("разные версии не обновляем", OptimisticException.class,  () -> cache.update(user1));
    }

    @Test
    public void successUpdate() {
        Cache cache = new Cache();
        Base base =  Base.builder().id(1).version(0).build();
        cache.add(base);

        Base user1 =  Base.builder().id(1).version(0).build();
        user1.setName("User 1");
        Assert.assertTrue(cache.update(user1));
        Base user2 =  Base.builder().id(1).version(1).build();
        user2.setName("User 1");
        Assert.assertTrue(cache.update(user2));
    }

    @Test
    public void successDelete() {
        Cache cache = new Cache();
        Base base = Base.builder().id(1).version(0).build();
        cache.add(base);
        cache.delete(base);
        Base user2 = Base.builder().id(1).version(0).build();
        user2.setName("User 1");
        Assert.assertTrue(cache.add(user2));
    }

}