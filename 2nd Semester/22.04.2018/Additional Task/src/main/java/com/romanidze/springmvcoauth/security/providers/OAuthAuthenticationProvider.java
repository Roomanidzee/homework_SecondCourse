package com.romanidze.springmvcoauth.security.providers;

import com.romanidze.springmvcoauth.security.utils.OAuthAuthentication;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 28.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Component
public class OAuthAuthenticationProvider implements AuthenticationProvider{

    private final Environment environment;

    @Autowired
    public OAuthAuthenticationProvider(Environment environment) {
        this.environment = environment;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        OAuthAuthentication oauthAuthentication = (OAuthAuthentication) authentication;

        Integer clientID = Integer.parseInt(this.environment.getProperty("vk.application.client_id"));
        String clientSecret = this.environment.getProperty("vk.application.client_secret");
        String redirectURI = this.environment.getProperty("vk.application.redirect_uri");

        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest req = requestAttributes.getRequest();
        String code = req.getParameter("code");

        VkApiClient vk = new VkApiClient(new HttpTransportClient());
        UserAuthResponse userAuthResponse;

        try{

            userAuthResponse = vk.oauth()
                                 .userAuthorizationCodeFlow(clientID, clientSecret, redirectURI, code)
                                 .execute();

        } catch (ApiException | ClientException e) {
            throw new BadCredentialsException("Неверно введены логин / пароль");
        }

        oauthAuthentication.setUser(String.valueOf(userAuthResponse.getUserId()));
        oauthAuthentication.setToken(userAuthResponse.getAccessToken());
        oauthAuthentication.setAuthenticated(true);

        return oauthAuthentication;

    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.getName().equals(OAuthAuthentication.class.getName());
    }
}
