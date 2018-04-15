package com.romanidze.perpenanto.utils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 07.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface CompareAttributes<M> {
    List<M> sortList(List<M> oldList, Map<String, Function<M, String>> functionMap, String sortType);
}
