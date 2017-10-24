package com.romanidze.perpenanto.dto.interfaces;

import com.romanidze.perpenanto.models.UserInfo;
import com.romanidze.perpenanto.models.temp.TempUserInfo;

import java.util.List;

public interface UserInfoTransferInterface {

    List<TempUserInfo> getTempUserInfos(List<UserInfo> oldList);

}
