package threads.sync;


public class User {
    private int id;
    private Integer amount;

    public User(int id, int amount) {
       this.id = id;
       this.amount = amount;
    }

    public synchronized int getId() {
        return id;
    }

    public synchronized void setId(int id) {
        this.id = id;
    }

    public synchronized Integer getAmount() {
        return amount;
    }

    public synchronized void setAmount(Integer amount) {
        this.amount = amount;
    }
}
