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
        <title>Apartments</title>
        <link rel="stylesheet" href="<c:url value = "/css/bootstrap.min.css"/>"
              crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css"
              integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU"
              crossorigin="anonymous">
        <link rel="stylesheet" href="<c:url value = "/css/apartments.css"/>">
        <link rel="stylesheet" href="<c:url value = "/css/datepicker.css"/>">
    </head>
    <body>
        <jsp:include page="/JSP/fragments/header.jsp">
            <jsp:param name="module" value="apartments"/>
        </jsp:include>

        <div class="page-container">
            <c:set var="date" value="${pageContextVar.getDate()}" scope="request"/>
            <div class="container-sm">
                <jsp:include page="/JSP/fragments/datepicker.jsp"/>
            </div>

            <form name="sortForm" method="GET">
                <input type="hidden" id="sortTypeValue" name="sort">
                <input type="hidden" id="sortStartsAt" name="startsAt">
                <input type="hidden" id="sortEndsAt" name="endsAt">
            </form>

            <c:set var="apartments" value="${pageContextVar.getApartments()}"/>
            <main class="container-md">
                <table class="table">
                    <thead class="thead-dark">
                        <tr>
                            <th scope="col"><fmt:message key="apartments.label.apartment.id"/></th>
                            <th scope="col"><fmt:message key="apartments.label.check.in"/></th>
                            <th scope="col"><fmt:message key="apartments.label.check.out"/></th>
                            <th scope="col" class="active-sort-tab" style="background-color: black;">
                                <a class="sort-link" onclick="onSortButtonClick()" data-type="type">
                                    <fmt:message key="apartments.label.type"/>
                                </a>
                            </th>
                            <th scope="col" class="active-sort-tab" style="background-color: black;">
                                <a class="sort-link" onclick="onSortButtonClick()" data-type="beds_count">
                                    <fmt:message key="apartments.label.beds.count"/>
                                </a>
                            </th>
                            <th scope="col" class="active-sort-tab" style="background-color: black;">
                                <a class="sort-link" onclick="onSortButtonClick()" data-type="price">
                                    <fmt:message key="apartments.label.price"/>
                                </a>
                            </th>
                            <th scope="col" class="active-sort-tab" style="background-color: black;">
                                <a class="sort-link" onclick="onSortButtonClick()" data-type="status">
                                    <fmt:message key="apartments.label.status"/>
                                </a>
                            </th>
                            <th scope="col"><fmt:message key="apartments.label.book"/></th>
                        </tr>
                    </thead>
                    <c:if test="${empty apartments}">
                        <tbody>
                            <tr>
                                <td colspan="8"><fmt:message key="apartments.label.empty"/></td>
                            </tr>
                        </tbody>
                    </c:if>
                    <tbody>
                        <c:set var="format"
                               value="${sessionScope.lang == 'ua' ? 'dd-MM-yyyy HH:mm' : 'MM-dd-yyyy HH:mm'}"/>
                        <%--                        <jsp:useBean id="i18n" class="ua.training.servlet_project.model.util.Internationalization"/>--%>
                        <c:forEach var="a" items="${apartments}">
                            <c:forEach var="time_slot" items="${a.getSchedule()}">
                                <tr>
                                    <th scope="row">
                                        <span>
                                                ${a.id}
                                        </span>
                                    </th>
                                    <td>
                                        <span>
                                            <tags:localDateTime date="${time_slot.getStartsAt()}" pattern="${format}"/>
                                        </span>
                                    </td>
                                    <td><span>
                                        <tags:localDateTime date="${time_slot.getEndsAt()}" pattern="${format}"/>
                                    </span>
                                    </td>
                                    <td>
                                        <span>
                                                ${f:getMessage(a.getType(), sessionScope.lang)}
                                        </span>
                                    </td>
                                    <td>
                                        <span>
                                                ${a.getBedsCount()}
                                        </span>
                                    </td>
                                    <td>
                                        <span>
                                                ${a.getPrice()}
                                        </span>
                                    </td>
                                    <td>
                                        <span>
                                                ${f:getMessage(time_slot.getStatus(), sessionScope.lang)}
                                        </span>
                                    </td>
                                    <td>
                                        <button type="button" class="btn btn-success"
                                                action="${'/app/apartments/' += a.id += '?slotId=' += time_slot.id}"
                                                onclick="onApartmentClick()">
                                            <fmt:message key="apartments.buttons.book"/>
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:forEach>
                    </tbody>
                </table>
            </main>

            <c:set var="page" value="${pageContextVar.getPage()}" scope="request"/>
            <div class="container-sm">
                <jsp:include page="/JSP/fragments/pager.jsp"/>
            </div>
        </div>

        <script src="<c:url value = "/js/jquery-3.5.1.min.js"/>"></script>
        <script src="<c:url value = "/js/bootstrap.bundle.min.js"/>"
                crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.3/moment.min.js" crossorigin="anonymous">
        </script>
        <script src="<c:url value = "/js/datepicker.js"/>">
        </script>
        <script src="<c:url value = "/js/apartments.js"/>">
        </script>
        <script src="<c:url value = "/js/header.js"/>" crossorigin="anonymous">
        </script>
    </body>
</html>
