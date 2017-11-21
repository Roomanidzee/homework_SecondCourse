package calculator.launcher;

import calculator.functions.Server;

/**
 * 20.11.2017
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public class ServerLauncher {

    public static void main(String[] args) {

        Server server = new Server();
        server.launch();

    }

}
