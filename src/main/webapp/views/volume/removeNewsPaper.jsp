<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<jstl:if test="${not empty newsPapers}">
<form:form action="${actionUri}" modelAttribute="volume" >

    <form:hidden path="id"/>
    <form:hidden path="version"/>

    <form:select path="newsPapersVolume" items="${newsPapers}" itemLabel="title"/>
    <form:errors path="newsPapersVolume" cssClass="error" />
    <br />

    <acme:submit name="delete" code="volume.delete"/>

    <acme:cancel code="volume.cancel" url="volume/user/list.do"/>


</form:form>
</jstl:if>
<jstl:if test="${empty newsPapers}">
    <spring:message code="volume.NoNewsPapers"/>
</jstl:if>

