package com.romanidze.perpenanto.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 21.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Aspect
@Transactional
public class RepositoryAspect {

    private static final Logger logger = LogManager.getLogger(RepositoryAspect.class);

    @Pointcut("this(org.springframework.data.repository.Repository)")
    public void repositoryPoint() {}

    @Around(value = "com.romanidze.perpenanto.aspects.RepositoryAspect.repositoryPoint()", argNames = "pjp")
    public void showRepositoryInfo(ProceedingJoinPoint pjp) throws Throwable {

        logger.info("Проверяем выполнение методов репозитория");

        Class clazz = pjp.getTarget().getClass();
        Object[] executionArgs = pjp.getArgs();

        Arrays.stream(executionArgs)
              .forEach(arg -> logger.info(new StringBuilder().append("Выполняется класс ")
                                                             .append(clazz.toString())
                                                             .append(" c параметром ")
                                                             .append(arg)
                                                             .toString()));

        Object execResult = pjp.proceed();
        logger.info("Поля класса, полученного в ходе выполнения: ");

        Field[] resultFields = execResult.getClass().getDeclaredFields();

        Arrays.stream(resultFields)
              .forEach(field -> logger.info(field.toGenericString()));

    }

    @AfterThrowing(value = "com.romanidze.perpenanto.aspects.RepositoryAspect.repositoryPoint()", throwing = "ex")
    public void repositoryExceptionHandler(Exception ex) {

        List<StackTraceElement> stackTraceElements = new ArrayList<>(Arrays.asList(ex.getStackTrace()));
        logger.debug("Возможные ошибки(показывается весь отчёт об ошибках)");

        stackTraceElements.forEach(stackTraceElement -> logger.debug(stackTraceElement.toString()));

    }


}
