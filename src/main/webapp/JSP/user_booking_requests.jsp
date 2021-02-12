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
        <title>Booking requests</title>
        <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>"
              crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css"
              integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU"
              crossorigin="anonymous">
        <link rel="stylesheet" href="<c:url value="/css/booking_requests.css"/>">
    </head>
    <body>
        <jsp:include page="/JSP/fragments/header.jsp">
            <jsp:param name="module" value="user"/>
        </jsp:include>

        <div class="page-container">
            <main class="container-md">
                <table class="table">
                    <thead class="thead-dark">
                        <tr>
                            <th scope="col">
                                <fmt:message key="booking.requests.label.id"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="booking.requests.label.date"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="booking.requests.label.check.in"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="booking.requests.label.check.out"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="booking.requests.label.status"/>
                            </th>
                        </tr>
                    </thead>

                    <c:set var="format" value="${sessionScope.lang == 'ua' ? 'dd-MM-yyyy HH:mm' : 'MM-dd-yyyy HH:mm'}"/>
                    <tbody>
                        <c:if test="${empty requests}">
                            <tr>
                                <td colspan="6">
                                    <fmt:message key="booking.requests.label.empty"/>
                                </td>
                            </tr>
                        </c:if>
                        <c:forEach var="req" items="${requests}">
                            <tr>
                                <th scope="row">
                                    <span>
                                            ${req.getId()}
                                    </span>
                                </th>
                                <td>
                                    <span>
                                        <tags:localDateTime date="${req.getRequestDate()}" pattern="${format}"/>
                                    </span>
                                </td>
                                <td>
                                    <span>
                                        <tags:localDateTime date="${req.getStartsAt()}" pattern="${format}"/>
                                    </span>
                                </td>
                                <td>
                                    <span>
                                        <tags:localDateTime date="${req.getEndsAt()}" pattern="${format}"/>
                                    </span>
                                </td>
                                <td>
                                    <span>
                                            ${f:getMessage(req.getRequestStatus(), sessionScope.lang)}
                                    </span>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </main>

            <c:set var="page" value="${page}" scope="request"/>
            <div class="container-sm">
                <jsp:include page="/JSP/fragments/pager.jsp"/>
            </div>
        </div>

        <script src="<c:url value="/js/jquery-3.5.1.min.js"/>"></script>
        <script src="<c:url value="/js/bootstrap.bundle.min.js"/>"
                crossorigin="anonymous"></script>
        <script src="<c:url value="/js/header.js"/>" crossorigin="anonymous">
        </script>
    </body>
</html>