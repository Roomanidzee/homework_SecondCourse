package chat.runnables.server;

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

    private Socket clientSocket = null;
    private String type;

    public synchronized void setMessageType(String type){
        this.type = type;
    }

    public synchronized String getMessageType(){
        return this.type;
    }

    private MessageReceiver(){}

    public MessageReceiver(Socket socket){
        this.clientSocket = socket;
    }

    @Override
    public void run() {

        try{

            BufferedReader br = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));

            List<String> messages = br.lines()
                                      .collect(Collectors.toList());

            final boolean[] stop = {false};

            while(true){

                 if(stop[0]){
                     break;
                 }

                 System.out.println("Сообщение от клиента: ");

                 messages.forEach(message ->{
                     if(message.equals("Выход")){
                         stop[0] = true;
                     }
                     System.out.println(message);
                     setMessageType(message);
                 });

            }

            this.clientSocket.close();

        }catch(IOException e){
            System.err.println("Ошибка: " + e.getMessage());
        }

    }
}
