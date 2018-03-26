package ru.itis.romanov_andrey.perpenanto.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.romanov_andrey.perpenanto.dao.interfaces.ProductToUserDAOInterface;
import ru.itis.romanov_andrey.perpenanto.dto.implementations.ProductToUserTransferImpl;
import ru.itis.romanov_andrey.perpenanto.dto.interfaces.ProductToUserTransferInterface;
import ru.itis.romanov_andrey.perpenanto.models.ProductToUser;
import ru.itis.romanov_andrey.perpenanto.models.temp.TempProductToUser;
import ru.itis.romanov_andrey.perpenanto.services.interfaces.ProductInfoServiceInterface;
import ru.itis.romanov_andrey.perpenanto.utils.CompareAttributes;
import ru.itis.romanov_andrey.perpenanto.utils.StreamCompareAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.IntStream;

@Service
public class ProductInfoServiceImpl implements ProductInfoServiceInterface{

    @Autowired
    private ProductToUserDAOInterface productInfoDAO;

    @Override
    public List<TempProductToUser> getProductInfos() {

        List<TempProductToUser> resultList = new ArrayList<>();
        ProductToUserTransferInterface productInfoDTO = new ProductToUserTransferImpl();

        resultList.addAll(productInfoDTO.getTempProductToUsers(this.productInfoDAO.findAll()));
        return resultList;

    }

    @Override
    public List<TempProductToUser> getProductInfosByCookie(String cookieValue) {

        List<ProductToUser> currentProductInfos = this.productInfoDAO.findAll();
        List<TempProductToUser> tempList = new ArrayList<>();
        List<TempProductToUser> sortedList = new ArrayList<>();

        ProductToUserTransferInterface productInfoDTO = new ProductToUserTransferImpl();
        tempList.addAll(productInfoDTO.getTempProductToUsers(currentProductInfos));

        int size = 3;

        Function<TempProductToUser, String> zero = (TempProductToUser tpi) -> String.valueOf(tpi.getId());
        Function<TempProductToUser, String> first = (TempProductToUser tpi) -> String.valueOf(tpi.getUserId());
        Function<TempProductToUser, String> second = (TempProductToUser tpi) -> String.valueOf(tpi.getProductId());

        List<Function<TempProductToUser, String>> functions = Arrays.asList(zero, first, second);
        List<String> indexes = Arrays.asList("0", "1", "2");

        Map<String, Function<TempProductToUser, String>> functionMap = new HashMap<>();

        IntStream.range(0, size).forEachOrdered(i -> functionMap.put(indexes.get(i), functions.get(i)));

        CompareAttributes<TempProductToUser> compareAttr = new StreamCompareAttributes<>();
        sortedList.addAll(compareAttr.sortList(tempList, functionMap, cookieValue));

        return sortedList;

    }

    @Override
    public void addProductInfo(ProductToUser model) {
          this.productInfoDAO.save(model);
    }

    @Override
    public void updateProductInfo(ProductToUser model) {
          this.productInfoDAO.update(model);
    }

    @Override
    public void deleteProductInfo(Long id) {
          this.productInfoDAO.delete(id);
    }
}
