package chat.runnables.server;

import chat.functions.TimeCommand;

import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.net.Socket;

/**
 * 27.11.2017
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public class MessageSender implements Runnable {

    private Socket clientSocket = null;
    private String messageType;

    private MessageSender() {
    }

    public MessageSender(Socket socket, String messageType) {
        this.clientSocket = socket;
        this.messageType = messageType;
    }

    @Override
    public void run() {

        try {

            PrintWriter pw = new PrintWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));

            while (true) {

                TimeCommand time = new TimeCommand();
                pw.println(time.getExistingTime());
                pw.println();
                pw.println("Ваше сообщение: " + this.messageType);
                pw.flush();

            }

        } catch (IOException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }

    }

}
