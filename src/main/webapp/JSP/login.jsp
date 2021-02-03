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
        <link rel="stylesheet" type="text/css" href="<c:url value = "/css/login_form.css"/>">

    </head>
    <body>
        <jsp:include page="/JSP/fragments/header.jsp">
            <jsp:param name="module" value="login"/>
        </jsp:include>

        <div class="container">
            <div class="d-flex justify-content-center h-100">
                <div class="card">
                    <div class="card-header">
                        <h3>Login</h3>
                    </div>
                    <div class="card-body">
                        <c:if test="${error == 'credentials'}">
                            <div class="alert alert-danger" role="alert">
                                <p>
                                    <fmt:message key="validation.error.input.credentials"/>
                                </p>
                            </div>
                        </c:if>
                        <c:if test="${param.logout != null}">
                            <div class="alert alert-info" role="alert">
                                <p>
                                    <fmt:message key="information.logout.successful"/>
                                </p>
                            </div>
                        </c:if>

                        <form autocomplete="off" novalidate method="POST"
                              action="${pageContext.request.contextPath}/app/login">
                            <div class="input-group form-group align-items-center">
                                <label for="inputEmail" class="card-input">
                                    <fmt:message key="input.email"/>
                                </label>
                                <div class="input-group-prepend icon-container">
                                    <span class="input-group-text"><i class="fas fa-user"></i></span>
                                </div>
                                <fmt:message var="emailPlaceholderValue" key="input.placeholder.email"/>
                                <input type="email"
                                       class="form-control"
                                       id="inputEmail"
                                       name="email"
                                       placeholder="${emailPlaceholderValue}"
                                       required
                                >
                            </div>

                            <div class="input-group form-group align-items-center">
                                <label for="inputPassword" class="card-input">
                                    <fmt:message key="input.password"/>
                                </label>
                                <div class="input-group-prepend icon-container">
                                    <span class="input-group-text"><i class="fas fa-key"></i></span>
                                </div>
                                <fmt:message var="passwordPlaceholderValue" key="input.placeholder.password"/>
                                <input type="password"
                                       class="form-control"
                                       id="inputPassword"
                                       name="pass"
                                       placeholder="${passwordPlaceholderValue}"
                                       required
                                >
                            </div>
                            <div class="form-group">
                                <fmt:message var="loginBtnValue" key="button.login"/>
                                <input type="submit" class="btn float-right login_btn" style="margin-top:30px"
                                       value="${loginBtnValue}"/>
                            </div>
                        </form>
                    </div>
                    <div class="card-footer">
                        <div class="d-flex justify-self-center links">
                            <fmt:message key="login.label.registration"/>
                            <a href="/app/register" class="text-light">
                                <fmt:message key="button.register"/>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="<c:url value = "/js/jquery-3.5.1.min.js"/>"
                crossorigin="anonymous"></script>
        <script src="<c:url value = "/js/bootstrap.bundle.min.js"/>"
                crossorigin="anonymous"></script>
        <script src="<c:url value = "/js/header.js"/>"
                crossorigin="anonymous">
        </script>
    </body>
</html>