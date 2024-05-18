import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main
 {

    private static final String logFilePath = "file.txt";

    public static void main(String[] args) {
        try {
            FileWriter fw = new FileWriter(logFilePath, true);
            System.out.println("Keylogging started. Press Ctrl+C to stop.");
            while (true) {
                int key = System.in.read();
                fw.write(key);
                fw.flush();
            }
        } catch (IOException ex) {
            Logger.getLogger(Keylogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
