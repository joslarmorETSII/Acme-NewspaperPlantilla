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

<jstl:if test="${pageContext.response.locale.language == 'es' }">
	<jstl:set value="{0,date,dd/MM/yyyy HH:mm}" var="formatDate"/>
</jstl:if>

<jstl:if test="${pageContext.response.locale.language == 'en' }">
	<jstl:set value="{0,date,yyyy/MM/dd HH:mm}" var="formatDate"/>
</jstl:if>

<display:table name="followUps" id="row" pagesize="5" class="displaytag" requestURI="${requestURI}">

	<acme:column code="followUp.title" value="${row.title}" />

	<spring:message var="moment" code="followUp.moment"/>
	<spring:message var="formatDate" code="event.format.date"/>
	<display:column property="moment" title="${moment}" format="${formatDate}" sortable="true" />

	<acme:column code="followUp.summary" value="${row.summary}" sortable="true"/>

	<security:authorize access="isAuthenticated()" >
		<display:column>
			<acme:button url="followUp/display.do?followUpId=${row.id}" code="general.display" />
		</display:column>
	</security:authorize>


</display:table>

<security:authorize access="hasRole('USER')">
	<acme:button code="followUp.create" url="followUp/user/create.do"/>
</security:authorize>

<input type="button" name="cancel" value="<spring:message code="newsPaper.cancel" />" onclick="javascript: relativeRedir('welcome/index.do');" />




