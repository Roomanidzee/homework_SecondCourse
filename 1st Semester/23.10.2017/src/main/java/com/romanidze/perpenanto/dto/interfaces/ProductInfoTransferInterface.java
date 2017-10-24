package com.romanidze.perpenanto.dto.interfaces;

import com.romanidze.perpenanto.models.ProductInfo;
import com.romanidze.perpenanto.models.temp.TempProductInfo;

import java.util.List;

public interface ProductInfoTransferInterface {

    List<TempProductInfo> getTempProductInfos(List<ProductInfo> oldList);

}
