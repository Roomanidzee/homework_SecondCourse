package chat.client;

import chat.runnables.client.MessageReceiver;
import chat.runnables.client.MessageSender;

import java.io.IOException;
import java.net.Socket;

/**
 * 25.11.2017
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public class ClientLauncher {

    public static void main(String[] args) {

           try{

               Socket socket = new Socket("127.0.0.1", 8080);

               MessageSender messageSender = new MessageSender(socket);
               Thread thread1 = new Thread(messageSender);
               thread1.start();

               MessageReceiver messageReceiver = new MessageReceiver(socket);
               Thread thread2 = new Thread(messageReceiver);
               thread2.start();

           }catch(IOException e){
               System.err.println("Ошибка: " + e.getMessage());
           }

    }

}
