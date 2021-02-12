<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE HTML>
<html lang="${sessionScope.lang}">
    <head>
        <title>Header</title>
    </head>
    <body>
        <nav class="navbar navbar navbar-expand-lg navbar-inverse navbar-fixed-top navbar-dark bg-dark">
            <div class="container-fluid">
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar-collapse"
                        aria-controls="navbar-collapse" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div id="navbar-collapse" class="navbar-collapse collapse nav-pills">
                    <a class="navbar-brand" href="#">Sorson Hotel</a>
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <c:if test="${empty user}">
                                <a class="${param.module == 'home' ? 'nav-link active' : 'nav-link'}"
                                   href="/app/">
                                    <fmt:message key="navigation.section.home"/>
                                </a>
                            </c:if>
                        </li>
                        <li class="nav-item">
                            <a class="${param.module == 'apartments' ? 'nav-link active' : 'nav-link'}"
                               href="/app/apartments?page=0&size=2">
                                <fmt:message key="navigation.section.apartments"/>
                            </a>
                        </li>
                        <c:if test="${user.getRole().toString() == 'ROLE_MANAGER'}">
                            <li class="nav-item">
                                <a class="${param.module == 'users' ? 'nav-link active' : 'nav-link'}"
                                   href="/app/users?page=0&size=2">
                                    <fmt:message key="navigation.section.users"/>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="${param.module == 'orders' ? 'nav-link active' : 'nav-link'}"
                                   href="/app/orders?page=0&size=2">
                                    <fmt:message key="navigation.section.orders"/>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="${param.module == 'booking_requests' ? 'nav-link active' : 'nav-link'}"
                                   href="/app/booking-requests?page=0&size=2">
                                    <fmt:message key="navigation.section.booking.requests"/>
                                </a>
                            </li>
                        </c:if>
                        <c:if test="${user.getRole().toString() == 'ROLE_USER'}">
                            <li class="nav-item">
                                <a class="${param.module == 'booking_request_creation' ? 'nav-link active' : 'nav-link'}"
                                   href="/app/booking-requests/create">
                                    <fmt:message key="navigation.section.booking.requests.creation"/>
                                </a>
                            </li>
                        </c:if>
                        <c:if test="${not empty user}">
                            <li class="nav-item">
                                <a class="${module == 'user' ? 'nav-link active' : 'nav-link'}"
                                   href="${'/app/users/' += user.getId()}">
                                    <fmt:message key="navigation.section.user"/>
                                </a>
                            </li>
                        </c:if>
                    </ul>
                </div>

                <div class="navbar-collapse collapse nav-pills justify-content-end" style="padding-right:30px;">
                    <ul class="navbar-nav">
                        <c:if test="${empty user}">
                            <li class="nav-item">
                                <a class="${param.module == 'login' ? 'nav-link active' : 'nav-link'}"
                                   href="/app/login">
                                    <fmt:message key="navigation.section.login"/>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="${param.module == 'register' ? 'nav-link active' : 'nav-link'}"
                                   href="/app/register">
                                    <fmt:message key="navigation.section.register"/>
                                </a>
                            </li>
                        </c:if>
                        <c:if test="${not empty user}">
                            <li class="nav-item">
                                <a class="nav-link"
                                   href="/app/logout" onclick="$('#form').submit();">
                                    <button id="logOutButton" type="button" class="btn btn-info navbar-btn">
                                        <i class="glyphicon glyphicon-log-out"></i>
                                        <fmt:message key="navigation.section.logout"/>
                                    </button>
                                </a>
                                <form style="visibility: hidden" id="form" method="post"
                                      action="/app/logout"></form>
                            </li>
                        </c:if>
                        <li style="align-self: center; padding-left: 5px;">
                            <div class="dropdown">
                                <button class="btn btn-outline-light btn dropdown-toggle" type="button"
                                        id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">
                                    <fmt:message key="lang.change"/>
                                </button>
                                <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                    <li>
                                        <a class="dropdown-item" href="#" onclick="addLangAttribute('en')">
                                            <fmt:message key="lang.en"/>
                                        </a>
                                    </li>
                                    <li>
                                        <a class="dropdown-item" href="#" onclick="addLangAttribute('ua')">
                                            <fmt:message key="lang.ua"/>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>

    </body>
</html>
