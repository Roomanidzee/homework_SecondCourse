package ru.itis.romanov_andrey.perpenanto.dto.interfaces;

import ru.itis.romanov_andrey.perpenanto.models.ProductToUser;
import ru.itis.romanov_andrey.perpenanto.models.temp.TempProductToUser;

import java.util.List;

public interface ProductToUserTransferInterface {

    List<TempProductToUser> getTempProductToUsers(List<ProductToUser> oldList);

}
