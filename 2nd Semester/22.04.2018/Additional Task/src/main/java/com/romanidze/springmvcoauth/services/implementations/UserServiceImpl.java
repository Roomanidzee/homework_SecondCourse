package com.romanidze.springmvcoauth.services.implementations;

import com.romanidze.springmvcoauth.security.utils.OAuthAuthentication;
import com.romanidze.springmvcoauth.services.interfaces.UserService;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 28.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Service
public class UserServiceImpl implements UserService{

    @Override
    public UserXtrCounters getUserInfo(OAuthAuthentication oauthAuthentication) {

        VkApiClient vk = new VkApiClient(new HttpTransportClient());

        UserActor userActor = new UserActor(Integer.parseInt(oauthAuthentication.getUser()),
                                                             oauthAuthentication.getToken());
        List<UserXtrCounters> getUsersResponse = null;

        try {

            getUsersResponse = vk.users()
                                 .get(userActor)
                                 .userIds(oauthAuthentication.getUser())
                                 .execute();

        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }

        return getUsersResponse.get(0);

    }
}
