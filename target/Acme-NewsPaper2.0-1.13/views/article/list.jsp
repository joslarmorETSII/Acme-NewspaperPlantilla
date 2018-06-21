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
	<b><spring:message code="article.allTaboo"/></b>
	</security:authorize>
<display:table name="articles" id="row" pagesize="5" class="displaytag" requestURI="${requestURI}">

	<security:authorize access="hasRole('USER')" >

		<display:column>
		<jstl:if test="${row.finalMode eq false and allArticlesView eq true}" >
			<acme:button url="article/user/edit.do?articleId=${row.id}" code="article.edit" />
		</jstl:if>
		</display:column>

	</security:authorize>

	<acme:column code="article.title" value="${row.title}" />

	<spring:message var="moment" code="article.moment"/>
	<spring:message var="formatDate" code="event.format.date"/>
	<display:column property="moment" title="${moment}" format="${formatDate}" sortable="true" />

	<acme:column code="article.summary" value="${row.summary}" sortable="true"/>
	<acme:column code="article.finalMode" value="${row.finalMode}"/>
	<security:authorize access="hasRole('USER')" >
		<display:column>
			<jstl:if test="${allArticlesView}">
			<acme:button url="picture/user/create.do?articleId=${row.id}" code="article.picture.add" />
			</jstl:if>
		</display:column>
	</security:authorize>
	<security:authorize access="hasAnyRole('CUSTOMER','AGENT')">
		<display:column>
			<jstl:if test="${row.newsPaper.modePrivate eq false}">
				<acme:button url="article/display.do?articleId=${row.id}" code="article.display" />
			</jstl:if>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('USER')">
		<display:column>
				<acme:button url="article/user/display.do?articleId=${row.id}" code="article.display" />
		</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('ADMINISTRATOR')">
		<display:column>
			<acme:button url="article/display.do?articleId=${row.id}" code="article.display" />
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('ADMINISTRATOR')" >
		<display:column>
			<acme:button url="article/administrator/edit.do?articleId=${row.id}" code="article.delete" />
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('USER')" >
		<display:column>
		<jstl:if test="${row.finalMode eq false and allArticlesView eq true}" >
			<acme:button url="picture/user/create.do?articleId=${row.id}" code="article.picture.add" />
		</jstl:if>
		</display:column>
	</security:authorize>

</display:table>
</fieldset>

<security:authorize access="hasRole('ADMINISTRATOR')" >

	<fieldset>
		<b><spring:message code="article.all"/></b>


<display:table name="allArticles" id="row2" pagesize="5" class="displaytag" requestURI="${requestURI}">

	<acme:column code="article.title" value="${row2.title}" />

	<spring:message var="moment" code="article.moment"/>
	<spring:message var="formatDate" code="event.format.date"/>
	<display:column property="moment" title="${moment}" format="${formatDate}" sortable="true" />

	<acme:column code="article.summary" value="${row2.summary}" sortable="true"/>
	<acme:column code="article.finalMode" value="${row2.finalMode}"/>

	<display:column>
		<acme:button url="article/display.do?articleId=${row2.id}" code="article.display" />
	</display:column>

	<display:column>
		<acme:button url="article/administrator/edit.do?articleId=${row2.id}" code="article.delete" />
	</display:column>

</display:table>
	</fieldset>
</security:authorize>

<security:authorize access="hasRole('USER')">
	<acme:button code="article.create" url="article/user/create.do"/>
</security:authorize>

<acme:button code="article.cancel" url="${cancelUri}"/>






