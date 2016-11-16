import com.shuyun.crypt.EncryptServer;

import java.io.IOException;

/**
 * @author hewei
 * @version 5.0
 * @date 16/11/16  15:02
 * @desc
 */
public class Launcher {

    public static void main(String[] args) throws IOException, InterruptedException {
        final EncryptServer server = new EncryptServer();
        server.start();
        server.blockUntilShutdown();
    }
}
