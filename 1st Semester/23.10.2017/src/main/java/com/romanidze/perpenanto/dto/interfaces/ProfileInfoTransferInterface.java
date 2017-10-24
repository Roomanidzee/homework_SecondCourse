package com.romanidze.perpenanto.dto.interfaces;

import com.romanidze.perpenanto.models.ProfileInfo;
import com.romanidze.perpenanto.models.temp.TempProfileInfo;

import java.util.List;

public interface ProfileInfoTransferInterface {

    List<TempProfileInfo> getTempProfileInfos(List<ProfileInfo> oldList);

}
