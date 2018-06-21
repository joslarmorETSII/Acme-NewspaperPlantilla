<%--
  Created by IntelliJ IDEA.
  User: khawla
  Date: 04/04/2018
  Time: 11:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="${actionUri}" modelAttribute="newsPaper">

    <form:hidden path="id"/>
    <form:hidden path="version"/>

    <acme:textbox path="title" code="newsPaper.name"/>
    <acme:textarea path="description" code="newsPaper.description" />
    <acme:textbox path="picture" code="newsPaper.picture"/>
    <acme:checkbox path="modePrivate" code="newsPaper.modePrivate"/>

    <security:authorize access="hasRole('USER')">
        <acme:submit name="save" code="newsPaper.save"/>
    </security:authorize>

    <security:authorize access="hasRole('ADMINISTRATOR')">
        <jstl:if test="${ row.taboo ne false }">
            <acme:submit name="delete" code="newsPaper.delete"/>
        </jstl:if>
    </security:authorize>

    <security:authorize access="hasRole('USER')">
        <jstl:if test="${empty newsPaper.subscriptions && newsPaper.id !=0}">
            <acme:submit name="delete" code="newsPaper.delete"/>
        </jstl:if>
    </security:authorize>

    <input type="button" name="cancel" value="<spring:message code="newsPaper.cancel" />" onclick="javascript: relativeRedir('${cancelUriSession}');" />

</form:form>


