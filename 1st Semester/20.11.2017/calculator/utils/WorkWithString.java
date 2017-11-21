package calculator.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 20.11.2017
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public class WorkWithString {

    public List<Long> getParameters(String input){

        int index = input.indexOf("?");
        String data = input.substring(index, input.length());

        int indexOfFirstNumber = data.indexOf("a");
        int indexOfSecondNumber = data.indexOf("b");

        String s1 = IntStream.range(indexOfFirstNumber + 2, indexOfSecondNumber - 1)
                             .mapToObj(i -> String.valueOf(data.charAt(i)))
                             .collect(Collectors.joining());

        String s2 = IntStream.range(indexOfSecondNumber + 2, data.length() - indexOfSecondNumber - 4)
                             .mapToObj(i -> String.valueOf(data.charAt(i)))
                             .collect(Collectors.joining());

        List<Long> resultList = new ArrayList<>();
        resultList.add(Long.valueOf(s1));
        resultList.add(Long.valueOf(s2));

        return resultList;

    }

}
