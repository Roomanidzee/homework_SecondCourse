package com.romanidze.perpenanto.utils.data_work;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WorkWithCookie {

    private String type = "status";

    public String getCookieWithType(HttpServletRequest req, HttpServletResponse resp){

        List<Cookie> cookies = new ArrayList<>(Arrays.asList(req.getCookies()));

        String result = "";

        int i = 0;

        while (i < cookies.size()) {

            if(cookies.get(i).getName().equals(this.type)){

                result = cookies.get(i).getValue();
                return result;

            }

            i++;
        }

        if(result.isEmpty()){

            result = "0";
            resp.addCookie(new Cookie(this.type, result));
            return result;

        }

        return result;

    }

    public void createRememberCookie(HttpServletResponse resp, boolean memberCase){

        Cookie result;

        if(memberCase){

            result = new Cookie("remember_user", "yes");
            result.setMaxAge(60 * 60 * 24 * 365);

        }else{

            result = new Cookie("remember_user","no");

        }

        resp.addCookie(result);
    }

    public void createCookie(HttpServletResponse resp, String name, String value){

        Cookie cookie = new Cookie(name, value);
        resp.addCookie(cookie);

    }

    public Cookie getCookieByName(HttpServletRequest req, String name){

        List<Cookie> cookies = new ArrayList<>(Arrays.asList(req.getCookies()));

        int index = 0;

        int streamCount = (int) cookies.stream()
                                       .filter(tempCookieList -> !tempCookieList.getName().equals(name))
                                       .count();

        if(streamCount == cookies.size()){

            throw new NullPointerException("No such cookie!");

        }else{

            int i = 0;

            while (i < cookies.size()) {

                String tempName = cookies.get(i).getName();

                if(tempName.equals(name)){

                    index = i;

                }
                i++;
            }

            return cookies.get(index);

        }

    }

}
