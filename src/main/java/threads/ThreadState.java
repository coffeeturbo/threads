package threads;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
            () -> System.out.println(Thread.currentThread().getName())
        );
        first.start();

        Thread second = new Thread(
            () -> System.out.println(Thread.currentThread().getName())
        );
        second.start();

        while ((first.getState() != Thread.State.TERMINATED)
            || (second.getState() != Thread.State.TERMINATED)) {
            print(first);
            print(second);
        }
        print(first);
        print(second);
    }

    private static void print(Thread thread) {
        System.out.printf("%s: %s \n", thread.getName(), thread.getState());
    }
}
