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
        <title>Home</title>
        <link rel="stylesheet" href="<c:url value = "/css/bootstrap.min.css"/>"
              crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css"
              integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU"
              crossorigin="anonymous">
        <link rel="stylesheet" href="<c:url value = "/css/index.css"/>" crossorigin="anonymous">
    </head>
    <body>
        <jsp:include page="/JSP/fragments/header.jsp">
            <jsp:param name="module" value="home"/>
        </jsp:include>

        <main class="bg-image">
            <div class="welcome-block">
                <h2 class="welcome-text">
                    <fmt:message key="greeting"/>
                </h2>
                <div class="d-grid login-btn col-12">
                    <button class="btn btn-primary btn-lg" onclick="location.href = '/login';">
                        <fmt:message key="button.login"/>
                    </button>
                </div>
            </div>
        </main>

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
