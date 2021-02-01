<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE HTML>
<html lang="${sessionScope.lang}">
    <head>
        <meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1">
        <title>Login Page</title>
        <link rel="stylesheet" href="<c:url value = "/css/bootstrap.min.css"/>"
              crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css"
              integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU"
              crossorigin="anonymous">
    </head>
    <body>
        <jsp:include page="/JSP/fragments/header.jsp">
            <jsp:param name="module" value="login"/>
        </jsp:include>
        <c:if test="${error == 'credentials'}">
            <div>
                <p>
                    Wrong credentials
                </p>
            </div>
        </c:if>
        <p><fmt:message key="greeting"/></p>
        <h1>Вход в систему</h1><br/>
        <form method="POST" action="${pageContext.request.contextPath}/app/login">

            <input type="text" name="email"><br/>
            <input type="password" name="pass"><br/><br/>
            <input class="button" type="submit" value="Войти">

        </form>
        <br/>
        <a href="${pageContext.request.contextPath}/app/logout">На головну</a>

        <script src="<c:url value = "/js/jquery-3.5.1.min.js"/>"
                crossorigin="anonymous">
        </script>
        <script src="<c:url value = "/js/bootstrap.bundle.min.js"/>"
                crossorigin="anonymous"></script>
        <script src="<c:url value = "/js/header.js"/>"
                crossorigin="anonymous">
        </script>
    </body>
</html>