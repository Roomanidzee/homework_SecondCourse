package chat.runnables.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 27.11.2017
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public class MessageReceiver implements Runnable{

    private Socket serverSocket = null;

    private MessageReceiver(){}

    public MessageReceiver(Socket socket){
        this.serverSocket = socket;
    }

    @Override
    public void run() {

        try{

            BufferedReader br = new BufferedReader(new InputStreamReader(this.serverSocket.getInputStream()));

            while(br.readLine() != null){

                List<String> messages = br.lines()
                                          .collect(Collectors.toList());

                System.out.println("Сообщение от сервера: ");
                messages.forEach(System.out::println);
                System.out.println("Напечатайте следующую команду: ");

            }

        }catch(IOException e){
            System.err.println("Ошибка: " + e.getMessage());
        }

    }
}
