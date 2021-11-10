package threads.coccurent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.xml")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);

                if (bytesRead > speed) {
                    System.out.println(bytesRead);
                    int pauseTime = ((bytesRead - speed) / speed) * 1000;
                    System.out.println(pauseTime);
                    Thread.sleep(pauseTime);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws IllegalArgumentException, InterruptedException {
        validateArgs(args);
        Optional<String> url = Optional.of(args[0]);
        Optional<Integer> speed = Optional.of(Integer.parseInt(args[1]));
        Thread wget = new Thread(new Wget(url.get(), speed.get()));
        wget.start();
        wget.join();

    }

    private static void validateArgs(String[] args) throws IllegalArgumentException {
        if (args.length < 2) {
            throw new IllegalArgumentException("Expected two params passed");
        }

        if (args[0] == null || args[0].isEmpty()) {
            throw new IllegalArgumentException("param 0 - url is not passed");
        }

        if (args[1] == null || args[1].isEmpty()) {
            throw new IllegalArgumentException("param 1 - speed is not passed");
        }
    }
}
