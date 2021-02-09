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
        <title>Pager</title>
    </head>
    <body>
        <nav aria-label="pager">
            <ul class="pagination">
                <li class="${page.hasPrev()? 'page-item active' : 'page-item disabled'}">
                    <a id="prevPageLink" class="page-link"
                       href="${page.getUrl() += '?page=' += page.getPrevPage() += '&size=' += page.getLimit()}"
                       tabindex="-1">
                        <fmt:message key="pager.prev.page"/>
                    </a>
                </li>
                <li class="page-item active">
                    <a class="page-link" href="#">
                        <c:set var="totalPages" value="${page.getTotalPages()}"/>
                        <c:if test="${totalPages == 0}">
                            <c:set var="totalPages" value="1"/>
                        </c:if>
                        <span>
                            ${page.getCurrentPage() += '/' += totalPages}
                        </span>
                    </a>
                </li>
                <li class="${page.hasNext()? 'page-item active' : 'page-item disabled'}">
                    <a id="nextPageLink" class="page-link"
                       href="${page.getUrl() += '?page=' += page.getNextPage() += '&size=' += page.getLimit()}">
                        <fmt:message key="pager.next.page"/>
                    </a>
                </li>
            </ul>
        </nav>
    </body>
</html>
