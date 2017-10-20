package com.romanidze.perpenanto.services.interfaces;

import com.romanidze.perpenanto.models.Profile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ProfileServiceInterface {

    void addProfile(Profile model);
    void showProfile(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine, WebContext context);

}
