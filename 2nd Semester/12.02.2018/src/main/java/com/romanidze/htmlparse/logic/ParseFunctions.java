package com.romanidze.htmlparse.logic;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ParseFunctions {

    public List<String> getData(String link, String xPath) {

        if (link == null || link.isEmpty()) {
            throw new NullPointerException("Введите адрес сайта");
        }

        if (xPath == null || xPath.isEmpty()) {
            throw new NullPointerException("Введите выражение для поиска");
        }

        if(!link.startsWith("http://www")){
            throw new NullPointerException("Введите корректную ссылку");
        }

        if (!xPath.startsWith("/")) {
            throw new IllegalArgumentException("Корректно введите выражение для поиска");
        }

        String htmlFromLink = null;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL(link).openStream()))) {

            htmlFromLink = br.lines()
                             .collect(Collectors.joining());

        } catch (IOException e1) {
            System.err.println(e1.getMessage());
        }

        HtmlCleaner cleaner = new HtmlCleaner();
        TagNode root = cleaner.clean(Objects.requireNonNull(htmlFromLink));
        List<String> resultList = new ArrayList<>();

        try {

            Object[] data = root.evaluateXPath(xPath);

            if (data == null || data.length == 0) {
                throw new IllegalArgumentException("Неверное выражение для поиска. Попробуйте ещё раз");
            }

            resultList = Arrays.stream(data)
                               .map(singleData -> ((TagNode) singleData).getText().toString())
                               .collect(Collectors.toList());

        } catch (XPatherException e) {
            System.err.println(e.getMessage());
        }

        return resultList;

    }

}
