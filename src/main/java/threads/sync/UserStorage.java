package threads.sync;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class UserStorage {

    @GuardedBy("this")
    private final Map<Integer, User> users = new HashMap<>();

    public synchronized boolean add(User user) {

        if (users.get(user.getId()) != null) {
            return false;
        }

        users.put(user.getId(), user);
        return true;
    }

    public synchronized boolean update(User user) {
        User exist = users.get(user.getId());

        if (exist != null) {
            users.put(user.getId(), user);
            return true;
        }

        return false;
    }

    public synchronized boolean delete(User user) {
        User exist = users.get(user.getId());

        if (exist != null) {
            users.remove(user.getId());
            return true;
        }

        return false;
    }

    public synchronized void transfer(int fromId, int toId, int amount) {
        User from = users.get(fromId);
        User target = users.get(toId);
        if (from == null || from.getAmount() < amount || target == null) {
            return;
        }

        from.setAmount(from.getAmount() - amount);
        target.setAmount(target.getAmount() + amount);
    }
}
