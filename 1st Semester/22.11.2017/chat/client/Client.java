package chat.client;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 25.11.2017
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public class Client {

    private int port = 8080;

    public void launch() {

        System.out.println("Устанавливаем соединение. Пожалуйста, подождите");

        try{

            Socket socket = new Socket(InetAddress.getByName("localhost"), this.port);

            System.out.println("Присоединились к серверу: " + socket);

            String line = "";

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream dataOS = new DataOutputStream(socket.getOutputStream());

            while (!line.equals("exit")) {

                try {

                    line = br.readLine();
                    dataOS.writeUTF(line);
                    dataOS.flush();

                } catch (IOException e) {
                    System.err.println("Ошибка: " + e.getMessage());
                }

            }

            socket.close();
            br.close();
            dataOS.close();

        } catch (IOException e){
            System.err.println("Программа остановлена, причина: " + e.getMessage());
        }

    }

}
