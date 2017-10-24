package com.romanidze.perpenanto.utils.comparators;

import java.util.List;

public interface CompareAttributes<M> {

    List<M> sortByAttr(List<M> oldList, String sortType);

}
