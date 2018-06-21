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

<form:form action="${actionUri}" modelAttribute="chirp" >

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="user" />
	<form:hidden path="taboo" />
	<form:hidden path="moment" />
	<form:hidden path="posted"/>


	<acme:textbox path="title" code="chirp.title"/>
	<acme:textbox path="description" code="chirp.description" />


	<jstl:if test="${chirp.posted eq false}" >
		<acme:submit name="save" code="article.save"/>
	</jstl:if>

	<jstl:if test="${chirp.id!=0}" >
		<jstl:if test="${article.id != 0 or chirp.posted eq false}">
		<acme:submit name="delete" code="chirp.delete"/>
		</jstl:if>
	</jstl:if>

	<acme:cancel code="chirp.cancel" url="chirp/user/list.do"/>

</form:form>





