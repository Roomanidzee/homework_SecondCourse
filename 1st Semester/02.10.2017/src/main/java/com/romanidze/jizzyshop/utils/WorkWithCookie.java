package com.romanidze.jizzyshop.utils;

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

}
