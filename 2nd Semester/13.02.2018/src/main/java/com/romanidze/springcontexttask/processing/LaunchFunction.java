package com.romanidze.springcontexttask.processing;

import com.romanidze.springcontexttask.config.AppConfig;
import com.romanidze.springcontexttask.logic.interfaces.BYNCurrency;
import com.romanidze.springcontexttask.logic.interfaces.GELCurrency;
import com.romanidze.springcontexttask.logic.interfaces.USDCurrency;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class LaunchFunction {

    public static void launch(){

        Scanner sc = new Scanner(System.in);
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        System.out.println("Введите сумму для конвертации: ");
        Double money = sc.nextDouble();
        sc.nextLine();
        System.out.println();

        System.out.println("Введите валюту: ");
        String currency = sc.nextLine();
        System.out.println();

        switch(currency){

            case "BYN":
                BYNCurrency bynCurrency = context.getAutowireCapableBeanFactory().getBean(BYNCurrency.class);
                System.out.println(bynCurrency.convert(money));
                break;

            case "GEL":
                GELCurrency gelCurrency = context.getAutowireCapableBeanFactory().getBean(GELCurrency.class);
                System.out.println(gelCurrency.convert(money));
                break;

            case "USD":
                USDCurrency usdCurrency = context.getAutowireCapableBeanFactory().getBean(USDCurrency.class);
                System.out.println(usdCurrency.convert(money));
                break;

            default:
                System.err.println("Введите корректную валюту");

        }


    }

}
