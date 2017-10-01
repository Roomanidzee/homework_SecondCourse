<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>

    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href = "<c:url value = "/css/materialize.min.css"/>" media="screen,projection"/>
    <link rel="stylesheet" href = "<c:url value = "/css/profile_style.css"/>" media="screen,projection"/>
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Профиль</title>

</head>

<body>

    <nav class = "nav-color">

       <div class = "nav-wrapper">

           <a href = "#" class = "brand-logo left">*здесь должен быть логотип*</a>

           <ul id = "nav-mobile" class = "right hide-on-med-and-down">

              <li><a href = "JizzyShop(Informatics)/index">Главная страница</a></li>
              <li><a href = "#">Каталог товаров</a></li>
              <li><a href = "#">Личный кабинет</a></li>

           </ul>

       </div>

    </nav>

    <div class = "profile">

        <h5 class = "black-text text-lighten-3 center-align">Информация о пользователе: </h5>
        <br/>

        <ul class = "collapsible" data-collapsible="accordion">

            <li>

                <div class = "collapsible-header">

                    <i class="material-icons">email</i>
                    Электронная почта
                    <span class = "badge">
                            <c:out value="${email}"/>
                    </span>
                </div>

            </li>

            <li>

                <div class = "collapsible-header">

                    <i class="material-icons">card_travel</i>
                    Страна
                    <span class = "badge">
                            <c:out value="${country}"/>
                    </span>

                </div>

            </li>

            <li>

                <div class = "collapsible-header">

                    <i class="material-icons">face</i>
                    Пол
                    <span class = "badge">
                            <c:out value="${gender}"/>
                    </span>

                </div>

            </li>

            <li>

                <div class = "collapsible-header">

                    <i class="material-icons">mail</i>
                    Подписка на уведомления
                    <span class = "badge">
                             <c:out value="${subscription}"/>
                    </span>

                </div>

            </li>

        </ul>

    </div>

    <script src = "<c:url value = "/js/jquery-3.2.1.min.js"/>"></script>
    <script src = "<c:url value = "/js/materialize.min.js"/>" ></script>

</body>

</html>
