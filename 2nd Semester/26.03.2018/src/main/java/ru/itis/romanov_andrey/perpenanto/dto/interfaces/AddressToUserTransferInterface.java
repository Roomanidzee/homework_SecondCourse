package ru.itis.romanov_andrey.perpenanto.dto.interfaces;

import ru.itis.romanov_andrey.perpenanto.models.AddressToUser;
import ru.itis.romanov_andrey.perpenanto.models.temp.TempAddressToUser;

import java.util.List;

public interface AddressToUserTransferInterface {

    List<TempAddressToUser> getTempAddressToUsers(List<AddressToUser> oldList);

}
