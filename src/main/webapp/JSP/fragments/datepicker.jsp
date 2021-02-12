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
        <title>Datepicker</title>
    </head>
    <body>
        <form name="dateForm" method="GET">
            <label for="checkIn"><fmt:message key="datepicker.check.in"/></label>
            <input class="date-input" type="date" id="checkIn" name="startsAt"
                   min="${date.prevYear} += '-01-01'" max="${date.nextYear} += '-12-31'"
                   data-date="${date.checkIn}"
                   data-date-format="${sessionScope.lang == 'ua' ?'DD-MM-YYYY':'MM-DD-YYYY'}"
                   value="${date.checkIn}"
            />
            <br/>
            <label for="checkOut"><fmt:message key="datepicker.check.out"/></label>
            <input class="date-input" type="date" id="checkOut" name="endsAt"
                   min="${date.prevYear} += '-01-01'" max="${date.nextYear} += '-12-31'"
                   data-date="${date.checkOut}"
                   data-date-format="${sessionScope.lang == 'ua' ?'DD-MM-YYYY':'MM-DD-YYYY'}"
                   value="${date.checkOut}"/>
            <br/>
            <fmt:message key="datepicker.button.search" var="btnLabel"/>
            <input id="searchBtn" type="button" class="btn btn-info" value="${btnLabel}"/>
        </form>
    </body>
</html>
