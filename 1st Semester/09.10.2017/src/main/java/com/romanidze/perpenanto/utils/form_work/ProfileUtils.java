package com.romanidze.perpenanto.utils.form_work;

import java.util.Set;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileUtils {

    private Set<String> countrySet = new HashSet<>();

    public boolean checkCountry(String country) {

        this.countrySet.add("Азербайджан");
        this.countrySet.add("Армения");
        this.countrySet.add("Беларусь");
        this.countrySet.add("Казахстан");
        this.countrySet.add("Молдавия");
        this.countrySet.add("Россия");
        this.countrySet.add("Таджикистан");
        this.countrySet.add("Туркменистан");
        this.countrySet.add("Туркменистан");
        this.countrySet.add("Узбекистан");

        int count = (int) countrySet.stream()
                                    .filter(tempCountrySet -> !tempCountrySet.equals(country))
                                    .count();

        return !(count == this.countrySet.size());
    }

    public boolean checkEmail(String email){

        String emailPattern = "/[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$/";

        Pattern p = Pattern.compile(emailPattern);
        Matcher m = p.matcher(email);

        return m.matches();

    }

}
