package chat.server;

import chat.runnables.server.MessageReceiver;
import chat.runnables.server.MessageSender;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 25.11.2017
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public class ServerLauncher {

    public static void main(String[] args) {

        final int port = 8080;
        System.out.println("Сервер ожидает соединения на порте: " + port);

        try{

            ServerSocket serverSocket = new ServerSocket(port);
            Socket clientSocket = serverSocket.accept();

            System.out.println("Получено соединение от " + clientSocket.getInetAddress()
                    + ", порт: " + clientSocket.getPort());

            MessageReceiver messageReceiver = new MessageReceiver(clientSocket);

            Thread thread1 = new Thread(messageReceiver);
            thread1.start();

            MessageSender messageSender = new MessageSender(clientSocket, messageReceiver.getMessageType());

            Thread thread2 = new Thread(messageSender);
            thread2.start();

        }catch(IOException e){
            System.err.println("Ошибка: " + e.getMessage());
        }


    }

}
