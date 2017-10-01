<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>

    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel = "stylesheet" href = "<c:url value = "/css/materialize.min.css"/>" media="screen,projection"/>
    <link rel = "stylesheet" href = "<c:url value = "/css/index_style.css"/>" media="screen,projection"/>
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Главная страница</title>

</head>

<body>


   <div class = "intro blue-grey darken-1 z-depth-1">

      <h1 class = "grey-text text-lighten-3">Jizzyshop</h1>
      <h5 class = "grey-text text-lighten-3">Идеальный магазин для ваших потребностей</h5>

   </div>

   <div class = "about blue-grey darken-3">

       <h5 class = "grey-text text-lighten-3 center-align">Примеры товаров из нашего магазина: </h5>

       <div class = "row">

           <div class = "col s12 m4 l4">

               <h6 class = "grey-text text-lighten-3 center-align">Товар: Книга</h6>
               <img class="materialboxed responsive-img" src="<c:url value = "/img/book.jpg"/>" data-caption="Полезная книга"/>
               <br/>
               <h6 class = "grey-text text-lighten-3 center-align">Цена: 1500<span>&#8381;</span></h6>

           </div>

           <div class = "col s12 m4 l4">

               <h6 class = "grey-text text-lighten-3 center-align">Товар: Смартфон</h6>
               <img class="materialboxed responsive-img" src="<c:url value = "/img/phone.jpg"/>" data-caption="Современный телефон"/>
               <br/>
               <h6 class = "grey-text text-lighten-3 center-align">Цена: 4000<span>&#8381;</span></h6>

           </div>

           <div class = "col s12 m4 l4">

               <h6 class = "grey-text text-lighten-3 center-align">Товар: Очки</h6>
               <img class="materialboxed responsive-img" src="<c:url value="/img/glasses.jpg"/>" data-caption="Прекрасные очки"/>
               <br/>
               <h6 class = "grey-text text-lighten-3 center-align">Цена: 300<span>&#8381;</span></h6>

           </div>

         </div>

         <div class = "centerized">

           <a class = "waves-effect waves-light btn red" href = "#">
               <i class = "material-icons right">chevron_right</i>
               Перейти к каталогу товаров
           </a>

         </div>

         <br/>

         <h5 class = "grey-text text-lighten-3 center-align">Войдите в личный кабинет, чтобы совершить покупки!</h5>

         <div class = "centerized">

           <a class = "waves-effect waves-light btn red" href = "/JizzyShop(Informatics)/login">
               <i class = "material-icons right">chevron_right</i>
               Войти
           </a>

         </div>


   </div>

   <script src = "<c:url value = "/js/jquery-3.2.1.min.js"/>"></script>
   <script src = "<c:url value = "/js/materialize.min.js"/>" ></script>

</body>

</html>