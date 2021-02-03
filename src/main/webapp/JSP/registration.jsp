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
        <title>Registration form</title>
        <link rel="stylesheet" href="<c:url value = "/css/bootstrap.min.css"/>"
              crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css"
              integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU"
              crossorigin="anonymous">
        <link rel="stylesheet" href="<c:url value = "/css/registration.css"/>"
              crossorigin="anonymous">
    </head>
    <body>
        <jsp:include page="/JSP/fragments/header.jsp">
            <jsp:param name="module" value="register"/>
        </jsp:include>

        <div class="container">
            <div class="d-flex justify-content-center h-100">
                <div class="card">
                    <div class="card-header">
                        <h3>Registration form</h3>
                    </div>
                    <div class="card-body">
                        <c:if test="${error == 'validation'}">
                            <div class="error">
                                <fmt:message key="validation.error.input.credentials"/>
                            </div>
                        </c:if>
                        <c:if test="${error == 'email'}">
                            <div class="error">
                                <fmt:message key="validation.error.input.email.duplicate"/>
                            </div>
                        </c:if>

                        <form autocomplete="off" novalidate method="POST"
                              action="/app/register">
                            <div class="input-group form-group align-items-center">
                                <label for="inputFirstName" class="card-input">
                                    <fmt:message key="input.firstname"/>
                                </label>
                                <div class="input-group-prepend icon-container">
                                    <span class="input-group-text"><i class="fas fa-id-card"></i></span>
                                </div>
                                <fmt:message key="input.placeholder.first.name" var="fName"/>
                                <input type="text"
                                       class="form-control"
                                       name="firstName"
                                       id="inputFirstName"
                                       placeholder="${fName}"
                                       value="${prevFormData.getFirstName()}"
                                       required
                                >
                                <c:if test="${firstNameError == true}">
                                    <div class="error" role="alert">
                                        <fmt:message key="validation.error.name.empty"/>
                                        <fmt:message key="validation.error.name.size"/>
                                    </div>
                                </c:if>
                            </div>

                            <div class="input-group form-group align-items-center">
                                <label for="inputLastName" class="card-input">
                                    <fmt:message key="input.lastname"/>
                                </label>
                                <div class="input-group-prepend icon-container">
                                    <span class="input-group-text"><i class="fas fa-id-card"></i></span>
                                </div>
                                <fmt:message key="input.placeholder.last.name" var="lName"/>
                                <input type="text"
                                       class="form-control"
                                       name="lastName"
                                       id="inputLastName"
                                       placeholder="${lName}"
                                       value="${prevFormData.getLastName()}"
                                       required
                                >
                                <c:if test="${lastNameError == true}">
                                    <div class="error" role="alert">
                                        <fmt:message key="validation.error.surname.empty"/>
                                        <fmt:message key="validation.error.surname.size"/>
                                    </div>
                                </c:if>
                            </div>

                            <div class="input-group form-group align-items-center">
                                <label for="inputEmail" class="card-input">
                                    <fmt:message key="input.email"/>
                                </label>
                                <div class="input-group-prepend icon-container">
                                    <span class="input-group-text"><i class="fas fa-user"></i></span>
                                </div>
                                <fmt:message key="input.placeholder.email" var="emailP"/>
                                <input type="email"
                                       class="form-control"
                                       name="email"
                                       id="inputEmail"
                                       placeholder="${emailP}"
                                       required
                                       value="${prevFormData.getEmail()}"
                                >
                                <c:if test="${emailError == true}">
                                    <div class="error" role="alert">
                                        <fmt:message key="validation.error.email.empty"/>
                                        <fmt:message key="validation.error.email.invalid"/>
                                    </div>
                                </c:if>
                            </div>

                            <div class="input-group form-group align-items-center">
                                <label for="inputPassword" class="card-input">
                                    <fmt:message key="input.password"/>
                                </label>
                                <div class="input-group-prepend icon-container">
                                    <span class="input-group-text"><i class="fas fa-key"></i></span>
                                </div>
                                <fmt:message key="input.placeholder.password" var="pass"/>
                                <input type="password"
                                       class="form-control"
                                       name="password"
                                       id="inputPassword"
                                       placeholder="${pass}"
                                       required
                                >
                                <c:if test="${passwordError == true}">
                                    <div class="error" role="alert">
                                        <fmt:message key="validation.error.password.size"/>
                                    </div>
                                </c:if>
                            </div>

                            <div class="input-group form-group align-items-center">
                                <label for="confirmPassword" class="card-input">
                                    <fmt:message key="input.confirm.password"/>
                                </label>
                                <div class="input-group-prepend icon-container">
                                    <span class="input-group-text"><i class="fas fa-key"></i></span>
                                </div>
                                <fmt:message key="input.placeholder.confirm.password" var="confirmPass"/>
                                <input type="password"
                                       class="form-control"
                                       name="confirmPassword"
                                       id="confirmPassword"
                                       placeholder="${confirmPass}"
                                       required
                                       onkeyup='check();'/>
                                <div class="error"
                                     id="message"
                                     role="alert">
                                </div>
                            </div>

                            <div class="form-group">
                                <fmt:message key="button.register" var="regBtn"/>
                                <input id="submit" type="submit" class="btn float-right register_btn"
                                       style="margin-top:30px"
                                       value="${regBtn}"/>
                            </div>
                        </form>
                    </div>
                </div>

            </div>
        </div>

        <script src="<c:url value = "/js/jquery-3.5.1.min.js"/>"
                crossorigin="anonymous">
        </script>
        <script src="<c:url value = "/js/bootstrap.bundle.min.js"/>"
                crossorigin="anonymous"></script>
        <script src="<c:url value = "/js/header.js"/>"
                crossorigin="anonymous">
        </script>
        <script src="<c:url value = "/js/registration.js"/>" crossorigin="anonymous">
        </script>
    </body>
</html>