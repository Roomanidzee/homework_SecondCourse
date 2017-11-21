package calculator.functions;

import calculator.utils.WorkWithString;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * 20.11.2017
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public class Server {

    private MathOperations mathOperations = new MathOperations();
    private WorkWithString stringWork = new WorkWithString();

    public void launch() {

        try (ServerSocket serverSocket = new ServerSocket(8080)) {

            List<String> requestLines = new ArrayList<>();
            Socket socket = serverSocket.accept();

            while(socket != null){

                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String tempString;

                while(true){

                    tempString = br.readLine();
                    requestLines.add(tempString);

                    if(tempString == null || tempString.isEmpty()){
                        break;
                    }

                }

                requestLines.forEach(System.out::println);

                PrintWriter pw = new PrintWriter(socket.getOutputStream());

                StringBuilder sb = new StringBuilder();
                List<Long> numbers = this.stringWork.getParameters(requestLines.get(0));

                List<String> resultList = new ArrayList<>();

                resultList.add((String.valueOf(this.mathOperations.add(numbers.get(0), numbers.get(1)))));
                sb.setLength(0);

                resultList.add(String.valueOf(this.mathOperations.subtract(numbers.get(0), numbers.get(1))));
                sb.setLength(0);

                resultList.add(String.valueOf(this.mathOperations.multiply(numbers.get(0), numbers.get(1))));
                sb.setLength(0);

                resultList.add(String.valueOf(this.mathOperations.divide(numbers.get(0), numbers.get(1))));
                sb.setLength(0);

                int count = resultList.stream()
                                      .mapToInt(String::length)
                                      .sum();

                pw.println("HTTP/1.1 200 OK");
                pw.println("Content-type:text/plain;Charset:UTF-8");
                pw.println("Content-length:" + count);
                pw.println();
                resultList.forEach(pw::println);
                pw.flush();
                socket.close();

            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

}
