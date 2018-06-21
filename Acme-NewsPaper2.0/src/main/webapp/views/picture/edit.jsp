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

<form:form action="${actionUri}" modelAttribute="picture" >

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="followUp" />
	<form:hidden path="article" />

	<acme:textbox path="url" code="picture.url"/>

	<acme:submit name="save" code="picture.save"/>

	<jstl:if test="${picture.id != 0}" >
		<acme:submit name="delete" code="picture.delete"/>
	</jstl:if>

	<input type="button" name="cancel" value="<spring:message code="general.cancel" />"
		   onclick="javascript: relativeRedir('${cancelUriSession}');" />
</form:form>





