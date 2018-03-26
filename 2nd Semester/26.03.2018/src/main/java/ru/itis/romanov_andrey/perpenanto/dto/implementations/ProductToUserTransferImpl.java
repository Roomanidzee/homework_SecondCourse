package ru.itis.romanov_andrey.perpenanto.dto.implementations;

import ru.itis.romanov_andrey.perpenanto.dto.interfaces.ProductToUserTransferInterface;
import ru.itis.romanov_andrey.perpenanto.models.ProductToUser;
import ru.itis.romanov_andrey.perpenanto.models.temp.TempProductToUser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ProductToUserTransferImpl implements ProductToUserTransferInterface {
    @Override
    public List<TempProductToUser> getTempProductToUsers(List<ProductToUser> oldList) {

        int productsCount = oldList.size();

        List<Long> ids = new ArrayList<>(productsCount);
        List<Long> userIds = new ArrayList<>(productsCount);
        List<Long> productIds = new ArrayList<>(productsCount);

        int count = 1;
        int i1 = 0;

        while(i1 < productsCount){
            ids.add((long) count);
            count++;
            i1++;
        }

        IntStream.range(0, productsCount).forEachOrdered(i -> {
            userIds.add(oldList.get(i).getId());
            productIds.add(oldList.get(i).getProducts().get(0).getId());
        });

        return IntStream.range(0, productsCount)
                         .mapToObj(i -> new TempProductToUser(
                                                            ids.get(i),
                                                            userIds.get(i),
                                                            productIds.get(i)
                                                           )
                                  )
                         .collect(Collectors.toList());
    }
}
