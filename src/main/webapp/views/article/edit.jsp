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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<jstl:if test="${not empty newsPapers}">
<form:form action="${actionUri}" modelAttribute="article" >

	<form:hidden path="id"/>
	<form:hidden path="version"/>

	<form:hidden path="followUps" />
	<form:hidden path="taboo" />
	<form:hidden path="moment" />
	<form:hidden path="pictures" />


	<form:label path="newsPaper"><spring:message code="article.newsPaper"/></form:label>
	<form:select path="newsPaper">
		<form:option label="----" value="0"/>
		<form:options items="${newsPapers}" itemLabel="title" itemValue="id"/>
	</form:select>
	<form:errors path="newsPaper" cssClass="error"/>
	<br/>

	<acme:textbox path="title" code="article.title"/>
	<acme:textbox path="summary" code="article.summary" />
	<acme:textarea path="body" code="article.body" />
	<acme:checkbox path="finalMode" code="article.finalMode"/>

	<jstl:if test="${article.finalMode eq false or article.id eq 0}" >
		<acme:submit name="save" code="article.save"/>
	</jstl:if>

	<jstl:if test="${article.id != 0}" >
		<acme:submit name="delete" code="article.delete"/>
	</jstl:if>

	<acme:cancel code="article.cancel" url="${cancelUri}"/>

</form:form>
</jstl:if>
<jstl:if test="${ empty newsPapers}">
	<spring:message code="general.create.newspaper"/>
</jstl:if>




