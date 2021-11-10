package threads.coccurent;

import java.util.List;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {

        var i = 0;
        while (!Thread.currentThread().isInterrupted()) {
            List<String> states = List.of("\\", "|", "/");

            i = i % states.size();

            System.out.printf("\rLoading: %s", states.get(i++));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000); /* симулируем выполнение параллельной задачи в течение 1 секунды. */
        progress.interrupt(); //
    }
}
