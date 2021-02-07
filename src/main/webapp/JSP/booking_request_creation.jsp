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
        <link rel="stylesheet" href="<c:url value = "/css/datepicker.css"/>">
    </head>
    <body>
        <jsp:include page="/JSP/fragments/header.jsp">
            <jsp:param name="module" value="booking_request_creation"/>
        </jsp:include>

        <div class="page-container">
            <c:set var="date" value="${pageContextVar.getDate()}" scope="request"/>
            <fmt:message var="standard" key="roomtype.standard"/>
            <fmt:message var="suite" key="roomtype.suite"/>
            <fmt:message var="deluxe" key="roomtype.deluxe"/>
            <fmt:message var="president" key="roomtype.president"/>
            <main class="container-md">
                <form name="requestForm" method="POST" action="/app/booking-requests" onsubmit="return false;">
                    <input type="hidden" name="type" value="${standard}" data-type="STANDARD">
                    <input type="hidden" name="type" value="${suite}" data-type="SUITE">
                    <input type="hidden" name="type" value="${deluxe}" data-type="DELUXE">
                    <input type="hidden" name="type" value="${president}" data-type="PRESIDENT">
                    <label for="checkIn"><fmt:message key="datepicker.check.in"/></label>
                    <input class="date-input" type="date" id="checkIn" name="startsAt"
                           min="${date.prevYear} += '-01-01'" max="${date.nextYear} += '-12-31'"
                           data-date="${date.checkIn}"
                           data-date-format="${sessionScope.lang == 'ua' ?'DD-MM-YYYY':'MM-DD-YYYY'}"
                           value="${date.checkIn}" required/>
                    <br/>
                    <label for="checkOut"><fmt:message key="datepicker.check.out"/></label>
                    <input class="date-input" type="date" id="checkOut" name="endsAt"
                           min="${date.prevYear} += '-01-01'" max="${date.nextYear} += '-12-31'"
                           data-date="${date.checkOut}"
                           data-date-format="${sessionScope.lang == 'ua' ?'DD-MM-YYYY':'MM-DD-YYYY'}"
                           value="${date.checkOut}" required/>
                    <br/>
                    <fmt:message var="submitBtnLabel" key="booking.request.creation.btn.submit"/>
                    <input id="submitBtn" type="submit" value="${submitBtnLabel}"/>
                </form>
                <button onclick="addInputRow();"></button>
                <div class="error" id="error-panel">
                </div>
            </main>
        </div>

        <script src="<c:url value = "/js/jquery-3.5.1.min.js"/>"></script>
        <script src="<c:url value = "/js/bootstrap.bundle.min.js"/>"
                crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.3/moment.min.js" crossorigin="anonymous">
        </script>
        <script src="<c:url value = "/js/datepicker.js"/>">
        </script>
        <script src="<c:url value = "/js/header.js"/>" crossorigin="anonymous">
        </script>
        <script src="<c:url value="/js/booking_request_creation.js"/>">
        </script>
    </body>
</html>
