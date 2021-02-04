<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@taglib uri="/WEB-INF/functions.tld" prefix="f" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE HTML>
<html lang="${sessionScope.lang}">
    <head>
        <title>Apartment</title>
        <link rel="stylesheet" href="<c:url value = "/css/bootstrap.min.css"/>"
              crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css"
              integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU"
              crossorigin="anonymous">
        <link rel="stylesheet" href="<c:url value = "/css/apartment.css"/>">
    </head>
    <body>
        <jsp:include page="/JSP/fragments/header.jsp">
            <jsp:param name="module" value="apartments"/>
        </jsp:include>

        <main class="container d-flex align-items-center">
            <div class="row justify-content-around align-self-center">
                <article class="col-5 text-left align-self-center">
                    ${apartment.getDescription()}
                </article>

                <c:set var="format"
                       value="${sessionScope.lang == 'ua' ? 'dd-MM-yyyy HH:mm' : 'MM-dd-yyyy HH:mm'}"/>
                <div class="col-3 justify-content-end align-self-center">
                    <div class="card" style="width: 18rem;">
                        <div class="card-body">
                            <h5 class="card-title">
                                ${f:getMessage(apartment.getType(), sessionScope.lang)}
                            </h5>
                            <h6 class="card-subtitle mb-2 text-muted">
                                <fmt:message key="apartment.label.price"/>: ${apartment.getPrice()}
                            </h6>
                            <p class="card-text">
                                <fmt:message key="apartment.label.beds.count"/>: ${apartment.getBedsCount()}
                            </p>
                            <p class="card-text">
                                <fmt:message
                                        key="apartment.label.status"/>: ${f:getMessage(schedule.getStatus(), sessionScope.lang)}
                            </p>
                            <c:choose>
                                <c:when test="${schedule.getStartsAt() != null}">
                                    <p class="card-text">
                                        <fmt:message key="apartment.label.date.start"/>: <tags:localDateTime
                                            date="${schedule.getStartsAt()}" pattern="${format}"/>
                                    </p>
                                </c:when>
                                <c:when test="${schedule.getStartsAt() == null}">
                                    <p class="card-text">
                                        <fmt:message key="apartment.label.date.start"/>: <tags:localDateTime
                                            date="${userStartsAt}" pattern="${format}"/>
                                    </p>
                                </c:when>
                            </c:choose>
                            <c:choose>
                                <c:when test="${schedule.getEndsAt() != null}">
                                    <p class="card-text">
                                        <fmt:message key="apartment.label.date.end"/>: <tags:localDateTime
                                            date="${schedule.getEndsAt()}" pattern="${format}"/>
                                    </p>
                                </c:when>
                                <c:when test="${schedule.getEndsAt() == null}">
                                    <p class="card-text">
                                        <fmt:message key="apartment.label.date.end"/>: <tags:localDateTime
                                            date="${userEndsAt}" pattern="${format}"/>
                                    </p>
                                </c:when>
                            </c:choose>
                            <fmt:message var="submitBtnLabel" key="apartment.label.reservation"/>
                            <c:if test="${not empty user and schedule.getStatus().toString() == 'FREE'}">
                                <div>
                                    <form class="reservation-form" method="POST"
                                          action="${'/app/orders?apartmentId=' += apartment.getId()
                                          += '&startsAt=' += userStartsAt += '&endsAt=' += userEndsAt}">
                                        <c:forEach var="id" items="${apartmentIds}">
                                            <input type="hidden" name="apartmentIds" value="${id}"/>
                                        </c:forEach>
                                        <input type="submit" class="btn btn-dark" value="${submitBtnLabel}">
                                    </form>
                                </div>
                            </c:if>
                            <c:if test="${empty user and schedule.getStatus().toString() == 'FREE'}">
                                <div>
                                    <a class="card-link" href="/app/login">
                                        <fmt:message key="apartment.label.reservation.forbidden"/>
                                    </a>
                                </div>
                            </c:if>
                            <c:if test="${schedule.getStatus().toString() != 'FREE'}">
                                <div>
                                    <p class="form-warning-text">
                                        <fmt:message key="apartment.label.reservation.locked"/>
                                    </p>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </main>

        <script src="<c:url value = "/js/jquery-3.5.1.min.js"/>"></script>
        <script src="<c:url value = "/js/bootstrap.bundle.min.js"/>"
                crossorigin="anonymous"></script>
        <script src="<c:url value = "/js/header.js"/>" crossorigin="anonymous">
        </script>
    </body>
</html>
