package com.romanidze.htmlparse.processing;

import com.romanidze.htmlparse.logic.ParseFunctions;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Scanner;

public class LaunchFunction {

    public static void launch(){

        System.out.println("XPathParserLauncher, version 1.0");
        System.out.println("Команды: \"search\" - начать поиск, \"exit\" - выйти из программы");

        Scanner sc = new Scanner(System.in);
        ParseFunctions parseFunc = new ParseFunctions();
        boolean exit = false;

        while(!exit){

            System.out.println();
            System.out.println("Ваше действие: ");
            String command = sc.nextLine();

            switch(command){

                case "exit":

                    System.out.println();
                    System.out.println("Спасибо вам за использование данной программы!");
                    exit = true;
                    break;

                case "search":

                    System.out.println();
                    System.out.println("Введите сайт для поиска: ");
                    String link = sc.nextLine();
                    System.out.println();
                    System.out.println("Введите выражение для поиска: ");
                    String xPath = sc.nextLine();

                    System.out.println();
                    List<String> resultList = parseFunc.getData(link, xPath);

                    System.out.println();
                    System.out.println("Результат: ");

                    resultList.forEach(item ->{
                        try {
                            System.out.println(new String(item.getBytes(), "UTF-8"));
                        } catch (UnsupportedEncodingException e) {
                            System.out.println("Произошла ошибка с кодировкой: " + e.getMessage());
                        }
                    });

                    break;

                default:
                    System.out.println("Введите корректную команду");

            }

        }

    }

}
