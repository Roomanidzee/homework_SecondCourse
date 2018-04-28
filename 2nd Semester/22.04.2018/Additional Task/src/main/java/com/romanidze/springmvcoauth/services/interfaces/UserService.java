package com.romanidze.springmvcoauth.services.interfaces;

import com.romanidze.springmvcoauth.security.utils.OAuthAuthentication;
import com.vk.api.sdk.objects.users.UserXtrCounters;

/**
 * 28.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface UserService {

    UserXtrCounters getUserInfo(OAuthAuthentication oauthAuthentication);

}
