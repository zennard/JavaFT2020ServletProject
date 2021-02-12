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
        <title>Booking request</title>
        <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>"
              crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css"
              integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU"
              crossorigin="anonymous">
        <link rel="stylesheet" href="<c:url value="/css/booking_request.css"/>">
    </head>
    <body>
        <jsp:include page="/JSP/fragments/header.jsp">
            <jsp:param name="module" value="booking_requests"/>
        </jsp:include>

        <c:set var="format" value="${sessionScope.lang == 'ua' ? 'dd-MM-yyyy HH:mm' : 'MM-dd-yyyy HH:mm'}"/>
        <div class="page-container">
            <main class="container-md">
                <div class="container-sm">
                    <div class="row">
                        <p class="col-sm-6"><fmt:message key="booking.request.label.check.in"/></p>
                        <p class="col-sm-6"><tags:localDateTime date="${bookingRequest.getStartsAt()}"
                                                                pattern="${format}"/></p>
                    </div>
                    <div class="row">
                        <p class="col-sm-6"><fmt:message key="booking.request.label.check.out"/></p>
                        <p class="col-sm-6"><tags:localDateTime date="${bookingRequest.getEndsAt()}"
                                                                pattern="${format}"/></p>
                    </div>
                    <div class="row">
                        <p class="col-sm-6"><fmt:message key="booking.request.label.date"/></p>
                        <p class="col-sm-6"><tags:localDateTime date="${bookingRequest.getRequestDate()}"
                                                                pattern="${format}"/></p>
                    </div>
                </div>

                <table class="table">
                    <caption style="caption-side: top;">
                        <fmt:message key="booking.request.caption.request.items"/>
                    </caption>
                    <thead class="thead-dark">
                        <tr>
                            <th scope="col">
                                <fmt:message key="booking.request.label.beds.count"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="booking.request.label.type"/>
                            </th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="item" items="${bookingRequest.getRequestItems()}">
                            <tr>
                                <td>
                                    <span>
                                            ${item.getBedsCount()}
                                    </span>
                                </td>
                                <td>
                                    <span>
                                            ${f:getMessage(item.getType(), sessionScope.lang)}
                                    </span>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <table class="table" style="margin-top: 5px" id="apartmentsTable">
                    <thead class="thead-dark">
                        <tr>
                            <th scope="col"></th>
                            <th scope="col"><fmt:message key="apartments.label.apartment.id"/></th>
                            <th scope="col"><fmt:message key="apartments.label.check.in"/></th>
                            <th scope="col"><fmt:message key="apartments.label.check.out"/></th>
                            <th scope="col"><fmt:message key="apartments.label.type"/></th>
                            <th scope="col"><fmt:message key="apartments.label.beds.count"/></th>
                            <th scope="col"><fmt:message key="apartments.label.price"/></th>
                            <th scope="col"><fmt:message key="apartments.label.status"/></th>
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
                        <c:forEach var="a" items="${apartments}">
                            <c:forEach var="time_slot" items="${a.getSchedule()}">
                                <tr style="vertical-align: middle;">
                                    <td>
                                        <input class="form-check-input" type="checkbox" value=""
                                               name="checkBoxList" onChange="saveChoice()"
                                               data-apartment-id="${a.getId()}">
                                    </td>
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
                                </tr>
                            </c:forEach>
                        </c:forEach>
                    </tbody>
                </table>
            </main>


            <c:set var="page" value="${page}" scope="request"/>
            <div class="container-sm">
                <jsp:include page="/JSP/fragments/pager.jsp"/>
            </div>

            <fmt:message key="booking.request.btn.submit" var="submitBtnLabel"/>
            <fmt:message key="booking.request.btn.cancel" var="cancelBtnLabel"/>
            <div class="container-sm d-flex justify-content-center">
                <form method="POST" action="${'/app/booking-requests/update/' += bookingRequest.getId() += '?startsAt=' += bookingRequest.getStartsAt() +=
             '&endsAt=' += bookingRequest.getEndsAt() += "&bookingStatus=CLOSED" += "&userId=" += bookingRequest.getUserId()}"
                      name="submitForm">
                    <input type="button" class="btn btn-success" onclick="onSubmit()" value="${submitBtnLabel}"/>
                </form>
                <form method="POST"
                      action="${'/app/booking-requests/update/' += bookingRequest.getId() += '?bookingStatus=CANCELED'
                       += "&userId=" += bookingRequest.getUserId()}">
                    <input type="submit" class="btn btn-danger" value="${cancelBtnLabel}"/>
                </form>
            </div>
        </div>

        <script src="<c:url value="/js/jquery-3.5.1.min.js"/>">
        </script>
        <script src="<c:url value="/js/bootstrap.bundle.min.js"/>"
                crossorigin="anonymous"></script>
        <script src="<c:url value="/js/header.js"/>" crossorigin="anonymous">
        </script>
        <script src="<c:url value="/js/booking_request.js"/>" crossorigin="anonymous">
        </script>
    </body>
</html>