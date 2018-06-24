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

<form:form action="${actionUri}" modelAttribute="audit">

    <form:hidden path="id"/>
    <form:hidden path="version"/>
    <form:hidden path="administrator"/>
    <form:hidden path="newsPaper"/>
    <form:hidden path="ticker"/>


    <acme:textbox path="title" code="audit.title"/>

    <acme:textarea path="description" code="audit.description" />

    <form:label path="ticker"><spring:message code="audit.ticker"/>:&nbsp;</form:label>
    <input value="${audit.ticker}" readonly="readonly"/>
    <form:errors path="ticker" cssClass="error"/>
    <br/>

    <acme:textbox path="gauge" code="audit.gauge" placeHolder="Range 1-3 points"/>

    <form:label path="moment"><spring:message code="audit.moment"/></form:label>
    <form:input path="moment" placeholder="dd/mm/yyyy hh:mm"/>
    <form:errors path="moment" cssClass="error"/>
    <br/>

    <acme:checkbox path="finalMode" code="audit.finalMode"/>


        <acme:submit name="save" code="audit.save"/>







    <input type="button" name="cancel" value="<spring:message code="audit.cancel" />" onclick="javascript: relativeRedir('audit/administrator/list.do');" />

</form:form>


