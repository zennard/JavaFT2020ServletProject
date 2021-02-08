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
        <title>Profile</title>
        <link rel="stylesheet" href="<c:url value = "/css/bootstrap.min.css"/>"
              crossorigin="anonymous" id="bootstrap-css">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css"
              integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU"
              crossorigin="anonymous">
        <link rel="stylesheet" href="<c:url value = "/css/user.css"/>">
    </head>
    <body>
        <jsp:include page="/JSP/fragments/header.jsp">
            <jsp:param name="module" value="user"/>
        </jsp:include>

        <div class="container emp-profile">
            <div class="row">
                <div class="col-md-4">
                </div>
                <div class="col-md-6">
                    <div class="profile-head">
                        <h5>
                            ${userProfile.firstName += ' '} ${userProfile.lastName}
                        </h5>
                        <h6>
                            ${f:getMessage(userProfile.getRole(), sessionScope.lang)}
                        </h6>
                        <ul class="nav nav-tabs" id="myTab" role="tablist">
                            <li class="nav-item">
                                <a class="nav-link active" id="home-tab" data-toggle="tab" href="#" role="tab"
                                   aria-controls="home" aria-selected="true">
                                    <fmt:message key="profile.label.orders"/>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="row justify-content-center">
                <div class="col-md-auto">
                    <div class="tab-content profile-tab" id="myTabContent">
                        <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
                            <table class="table">
                                <thead class="thead-dark">
                                    <tr>
                                        <th scope="col">
                                            <fmt:message key="user.label.order.id"/>
                                        </th>
                                        <th scope="col">
                                            <fmt:message key="user.label.order.date"/>
                                        </th>
                                        <th scope="col">
                                            <fmt:message key="user.label.order.check.in"/>
                                        </th>
                                        <th scope="col">
                                            <fmt:message key="user.label.order.check.out"/>
                                        </th>
                                        <th scope="col">
                                            <fmt:message key="user.label.order.status"/>
                                        </th>
                                        <th scope="col">
                                            <fmt:message key="user.label.order.apartment.id"/>
                                        </th>
                                        <th scope="col">
                                            <fmt:message key="user.label.order.price"/>
                                        </th>
                                    </tr>
                                </thead>
                                <c:set var="format"
                                       value="${sessionScope.lang == 'ua' ? 'dd-MM-yyyy HH:mm' : 'MM-dd-yyyy HH:mm'}"/>
                                <tbody>
                                    <c:if test="${empty orders}">
                                        <tr>
                                            <td colspan="7"><fmt:message key="user.label.orders.empty"/></td>
                                        </tr>
                                    </c:if>
                                    <c:forEach var="o" items="${orders}">
                                        <tr>
                                            <th scope="row">
                                                    <span>
                                                            ${o.id}
                                                    </span>
                                            </th>
                                            <td>
                                                    <span>
                                                        <tags:localDateTime date="${o.getOrderDate()}"
                                                                            pattern="${format}"/>
                                                    </span>
                                            </td>
                                            <td>
                                                    <span>
                                                        <tags:localDateTime date="${o.getStartsAt()}"
                                                                            pattern="${format}"/>
                                                    </span>
                                            </td>
                                            <td>
                                                    <span>
                                                        <tags:localDateTime date="${o.getEndsAt()}"
                                                                            pattern="${format}"/>
                                                    </span>
                                            </td>
                                            <td>
                                                    <span>
                                                            ${f:getMessage(o.getStatus(), sessionScope.lang)}
                                                    </span>
                                            </td>
                                            <c:set var="items" value="${o.getOrderItems()}"/>
                                            <c:set var="itemsSize" value="${items.size()}"/>
                                            <td>
                                                <c:set var="counter" value="1"/>
                                                <span>
                                                        <c:forEach var="item" items="${items}">
                                                            ${item.apartmentId}
                                                            <c:if test="${counter != itemsSize}">
                                                                ,
                                                            </c:if>
                                                            <c:set var="counter" value="${counter + 1}"/>
                                                        </c:forEach>
                                                    </span>
                                            </td>
                                            <td>
                                                <c:set var="totalOrderPrice" value="0"/>
                                                <c:forEach var="item" items="${o.getOrderItems()}">
                                                    <c:set var="totalOrderPrice"
                                                           value="${totalOrderPrice + item.price}"/>
                                                </c:forEach>
                                                <span>
                                                        ${totalOrderPrice}
                                                </span>
                                            </td>
                                            <c:if test="${o.status.toString() == 'APPROVED'}">
                                                <td>
                                                    <form method="POST"
                                                          action="${'/app/orders/update/' += o.id += '?orderStatus=CONFIRMED'}">
                                                        <fmt:message var="confirmBtnValue"
                                                                     key="user.buttons.confirm"/>
                                                        <input type="submit" class="btn btn-success"
                                                               value="${confirmBtnValue}"/>
                                                    </form>
                                                </td>
                                            </c:if>
                                            <c:if test="${o.status.toString() == 'CONFIRMED'}">
                                                <td>
                                                    <form method="POST"
                                                          action="${'/app/orders/update/' += o.id += '?orderStatus=PAID'}">
                                                        <fmt:message var="payBtnValue" key="user.buttons.pay"/>
                                                        <input type="submit" class="btn btn-success"
                                                               value="${payBtnValue}"/>
                                                    </form>
                                                </td>
                                            </c:if>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>

                            <c:set var="page" value="${page}" scope="request"/>
                            <div class="container-sm justify-content-center">
                                <jsp:include page="/JSP/fragments/pager.jsp"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="<c:url value = "/js/jquery-3.5.1.min.js"/>"></script>
        <script src="<c:url value = "/js/bootstrap.bundle.min.js"/>"
                crossorigin="anonymous"></script>
        <script src="<c:url value = "/js/header.js"/>" crossorigin="anonymous">
        </script>
    </body>
</html>