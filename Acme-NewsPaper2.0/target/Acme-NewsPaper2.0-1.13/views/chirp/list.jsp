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


<fieldset>
		<security:authorize access="hasRole('ADMINISTRATOR')" >
			<b><spring:message code="chirps.allTaboo"/></b>
		</security:authorize>
	<display:table name="chirps" id="row" pagesize="5" class="displaytag" requestURI="${requestURI}">

		<security:authorize access="hasRole('USER')" >
			<display:column>
			<jstl:if test="${row.posted eq false}" >
				<acme:button url="chirp/user/edit.do?chirpId=${row.id}" code="chirp.edit" />
			</jstl:if>
			</display:column>
		</security:authorize>

		<security:authorize access="hasRole('USER')">
			<display:column >
				<jstl:if test="${ row.posted ne true}">
					<acme:button url="chirp/user/post.do?chirpId=${row.id}" code="chirp.post"/>
				</jstl:if>
				<jstl:if test="${row.posted eq true}">
					<spring:message code="chirp.publicado" var="publicado"/> <jstl:out value="${publicado}" />
				</jstl:if>
			</display:column>
		</security:authorize>

		<acme:column code="chirp.title" value="${row.title}" />

		<spring:message var="moment" code="chirp.moment"/>
		<spring:message var="formatDate" code="event.format.date"/>
		<display:column property="moment" title="${moment}" format="${formatDate}" sortable="true" />

		<acme:column code="chirp.description" value="${row.description}" sortable="true"/>


		<security:authorize access="hasRole('ADMINISTRATOR')" >
			<display:column>
				<acme:button code="general.delete" url="chirp/administrator/edit.do?chirpId=${row.id}"/>
			</display:column>
		</security:authorize>

		<security:authorize access="isAuthenticated()" >
			<display:column>
				<acme:button url="chirp/user/display.do?chirpId=${row.id}" code="chirp.display" />
			</display:column>
		</security:authorize>

	</display:table>

	<security:authorize access="hasRole('USER')">
		<acme:button code="chirp.create" url="chirp/user/create.do"/>
	</security:authorize>
</fieldset>
<br/>
<security:authorize access="!hasRole('ADMINISTRATOR')">
<fieldset>
	<h1><b><spring:message code="chirp.following.user"/>
	</b></h1>
	<display:table name="chirpsFollowing" id="chirp" pagesize="5" class="displaytag" requestURI="${requestURI}">
		<acme:column code="chirp.user" value="${chirp.user.name}" />
		<acme:column code="chirp.title" value="${chirp.title}" />
		<spring:message var="moment" code="chirp.moment"/>
		<spring:message var="formatDate" code="event.format.date"/>
		<display:column property="moment" title="${moment}" format="${formatDate}" sortable="true" />
		<acme:column code="chirp.description" value="${chirp.description}" />
		<display:column>
			<acme:button url="chirp/user/display.do?chirpId=${chirp.id}" code="chirp.display" />
		</display:column>
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<acme:button code="general.delete" url="chirp/administrator/delete.do?chirpId?=${chirp.id}"/>
		</security:authorize>

	</display:table>
</fieldset>
</security:authorize>

<security:authorize access="hasRole('ADMINISTRATOR')">
	<fieldset>
		<b><spring:message code="chirp.all"/>
		</b>
		<display:table name="allChirps" id="chirp2" pagesize="5" class="displaytag" requestURI="${requestURI}">

			<acme:column code="chirp.user" value="${chirp2.user.name}" />
			<acme:column code="chirp.title" value="${chirp2.title}" />

			<spring:message var="moment" code="chirp.moment"/>
			<spring:message var="formatDate" code="event.format.date"/>
			<display:column property="moment" title="${moment}" format="${formatDate}" sortable="true" />

			<acme:column code="chirp.description" value="${chirp2.description}" />

			<display:column>
				<acme:button code="general.delete" url="chirp/administrator/edit.do?chirpId=${chirp2.id}"/>
			</display:column>

			<display:column>
				<acme:button url="chirp/user/display.do?chirpId=${chirp2.id}" code="chirp.display" />
			</display:column>

		</display:table>
	</fieldset>
</security:authorize>

<acme:button code="chirp.cancel" url="${cancelUri}"/>




