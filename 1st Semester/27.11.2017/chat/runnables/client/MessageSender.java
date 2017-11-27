package chat.runnables.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * 27.11.2017
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public class MessageSender implements Runnable{

    private Socket serverSocket = null;

    private MessageSender(){}

    public MessageSender(Socket socket){
        this.serverSocket = socket;
    }

    @Override
    public void run() {

        try{

            Socket socket = this.serverSocket;

            if(socket.isConnected()){

                System.out.println("Клиент подключился к " + socket.getInetAddress()
                        + ", по порту " + socket.getPort());

                try{

                    PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

                    while(true){

                        System.out.println("Введите сообщение для отправки, или \"Выход\", чтобы выйти: ");

                        String messageToSend = "";
                        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

                        try{

                            messageToSend = br.readLine();
                            pw.println(messageToSend);

                        }catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                }catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
