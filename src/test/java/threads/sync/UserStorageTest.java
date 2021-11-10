package threads.sync;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserStorageTest {

    @Test
    public void whenSuccess() throws Exception {

        UserStorage storage = new UserStorage();

        User user1 = new User(1, 200);
        User user2 = new User(2, 200);
        storage.add(user1);
        storage.add(user2);

        storage.transfer(1, 2, 100);

        Integer expectedUser1 = 100;
        Integer expectedUser2 = 300;
        assertEquals(expectedUser1, user1.getAmount());
        assertEquals(expectedUser2, user2.getAmount());

    }
}