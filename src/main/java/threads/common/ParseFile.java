package threads.common;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {
    private File file;

    public synchronized void setFile(File f) {
        file = f;
    }

    public synchronized File getFile() {
        return file;
    }

    public synchronized String getContent() throws IOException {
        return getContent(integer -> true);
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        return getContent(integer -> integer < 0x80);
    }

    public synchronized void saveContent(String content) throws IOException {
        try (OutputStream o = new FileOutputStream(file)) {
            for (int i = 0; i < content.length(); i += 1) {
                o.write(content.charAt(i));
            }
        }
    }

    private String getContent(Predicate<Integer> predicate) throws IOException {
        try (InputStream i = new FileInputStream(file)) {
            StringBuilder output = new StringBuilder();
            int data;
            byte[] dataBuffer = new byte[1024];
            while ((data = i.read(dataBuffer, 0, 1024)) != -1) {
                if (predicate.test(data)) {
                    output.append((char) data);
                }
            }
            return output.toString();
        }
    }

}
