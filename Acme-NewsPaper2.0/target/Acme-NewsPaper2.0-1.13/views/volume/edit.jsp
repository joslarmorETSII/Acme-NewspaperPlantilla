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

<form:form action="${actionUri}" modelAttribute="volume" >

    <form:hidden path="id"/>
    <form:hidden path="version"/>

    <acme:textbox path="title" code="volume.title"/>
    <acme:textarea path="description" code="volume.description"/>
    <acme:textbox path="anyo" code="volume.anyo" placeHolder="2018"/>

    <acme:submit name="save" code="volume.save"/>

    <acme:cancel code="volume.cancel" url="${cancelUriSession}"/>


</form:form>

