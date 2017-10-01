package com.romanidze.jizzyshop.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

public class ProfileUtils {

    private Set<String> countrySet = new HashSet<>();

    public Set<String> getCountrySet() {

        this.countrySet.add("Азербайджан");
        this.countrySet.add("Армения");
        this.countrySet.add("Беларусь");
        this.countrySet.add("Казахстан");
        this.countrySet.add("Молдавия");
        this.countrySet.add("Россия");
        this.countrySet.add("Таджикистан");
        this.countrySet.add("Туркменистан");
        this.countrySet.add("Туркменистан");
        this.countrySet.add("Узбекистан");

        return this.countrySet;
    }

    public void saveData(File csvFile, List<String> data){

        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();

        sb1.append("Почта").append(",")
           .append("Страна").append(",")
           .append("Пол").append(",")
           .append("Подписка");

        int count1 = 3;
        int count2 = 0;

        for (String tempData : data) {
            sb2.append(tempData);
            count2++;

            if (count2 < count1) {

                sb2.append(",");

            }

        }

        try(BufferedReader br = new BufferedReader(new FileReader(csvFile));
            PrintWriter pw = new PrintWriter(csvFile, "UTF-8"))
        {

            String line = br.readLine();

            if(line == null){

                pw.println(sb1.toString());
                pw.println(sb2.toString());

            }else{

                List<String> lineList = new ArrayList<>();

                while(line != null){

                    lineList.add(line);
                    line = br.readLine();

                }

                lineList.forEach(pw::println);

                pw.println(sb2.toString());

            }

        }catch(IOException e){

            e.printStackTrace();

        }

    }

}
