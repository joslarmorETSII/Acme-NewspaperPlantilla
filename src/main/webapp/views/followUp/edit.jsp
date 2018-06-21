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

<jstl:if test="${not empty articles}">

<form:form action="${actionUri}" modelAttribute="followUp" >

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="moment" />
	<form:hidden path="pictures" />


	<form:label path="article"><spring:message code="followUp.article"/></form:label>
	<form:select path="article">
		<form:option label="----" value="0"/>
		<form:options items="${articles}" itemLabel="title" itemValue="id"/>
	</form:select>
	<form:errors path="article" cssClass="error"/>
	<br/>

	<acme:textbox path="title" code="followUp.title"/>
	<acme:textbox path="summary" code="followUp.summary" />
	<acme:textarea path="text" code="followUp.text" />

	<acme:submit name="save" code="followUp.save"/>

	<acme:cancel code="followUp.cancel" url="${cancelUriSession}"/>
</form:form>
</jstl:if>
<jstl:if test="${ empty articles}">
	<spring:message code="general.create.article"/>
</jstl:if>





