package chat.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.BufferedInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 25.11.2017
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public class Server {

    private int port = 8080;

    public void launch(){

        System.out.println("Подключаемся к порту: " + this.port);

        try{

            ServerSocket server = new ServerSocket(this.port);

            System.out.println("Запущен сервер: " + server);
            Socket socket = server.accept();
            System.out.println("Подключен клиент: " + socket);

            boolean exit = false;

            DataInputStream dataIS = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

            while(!exit){

                try{

                    String line = dataIS.readUTF();
                    System.out.println(line);
                    exit = line.equals("exit");

                }catch(IOException e){
                    System.out.println("Сервер завершил свою работу");
                    exit = true;
                }

            }

            socket.close();
            dataIS.close();

        }catch(IOException e){
            System.err.println("Программа остановлена, причина: " + e.getMessage());
        }

    }

}
