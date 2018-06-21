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



<form action="user/search.do" method="get">
	<input type="text" name="keyword" value="${keyword}" />
	<input type="submit" value="<spring:message code="master.page.search"/>"/>
</form>
<br/>
<br/>
<fieldset>
	<legend><spring:message code="newsPaper.listArticles"/> </legend>
	<display:table name="articles" id="row" pagesize="5" class="displaytag" requestURI="${requestURI}">

	<acme:column code="article.title" value="${row.title}" />
	<spring:message var="moment" code="article.moment"/>
	<spring:message var="formatDate" code="event.format.date"/>
	<display:column property="moment" title="${moment}" format="${formatDate}" sortable="true" />

	<acme:column code="article.summary" value="${row.summary}" sortable="true"/>
	<acme:column code="article.finalMode" value="${row.finalMode}"/>

	<security:authorize access="hasRole('ADMINISTRATOR')" >
		<display:column>
			<acme:button url="article/display.do?articleId=${row.id}" code="article.display" />
		</display:column>
	</security:authorize>

	<security:authorize access="!hasRole('ADMINISTRATOR')" >
	<display:column>
		<jstl:if test="${row.newsPaper.modePrivate eq false}">
			<acme:button url="article/display.do?articleId=${row.id}" code="article.display" />
		</jstl:if>
	</display:column>
	</security:authorize>

	</display:table>
</fieldset>
<br/>
<br/>
<fieldset>
	<legend><spring:message code="article.newsPaper"/> </legend>
<display:table name="newsPapers" id="newspaper" pagesize="5" class="displaytag" requestURI="${requestURI}">

	<acme:column code="newsPaper.publisher" value="${newspaper.publisher.name} " />
	<acme:column code="newsPaper.title" value="${newspaper.title}"/>
	<acme:column code="newsPaper.description" value="${newspaper.description}"/>

	<spring:message code="newsPaper.picture" var="pic"/>
	<display:column title="${pic}"><img src="${newspaper.picture}" alt="no image" width="130" height="100"></display:column>
	<spring:message var="publicationDate" code="newsPaper.publicationDate"/>
	<spring:message var="formatDate2" code="event.format.date"/>
	<display:column property="publicationDate" title="${publicationDate}" format="${formatDate2}" sortable="true" />


	<security:authorize access="hasRole('USER')" >
		<display:column >
			<jstl:if test="${newspaper.modePrivate eq false }">
				<acme:button url="newsPaper/display.do?newsPaperId=${newspaper.id}" code="newsPaper.display"/>
			</jstl:if>
		</display:column>
	</security:authorize>


	<security:authorize access="isAnonymous()||hasAnyRole('AGENT','CUSTOMER')">
		<display:column >
			<jstl:if test="${newspaper.modePrivate eq false}">
				<acme:button url="newsPaper/displayAnonymous.do?newsPaperId=${newspaper.id}" code="newsPaper.display"/>
			</jstl:if>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('ADMINISTRATOR')" >
		<display:column>
				<acme:button url="newsPaper/administrator/display.do?newsPaperId=${newspaper.id}" code="newsPaper.display"/>
		</display:column>
	</security:authorize>


</display:table>
</fieldset>

<acme:cancel code="article.cancel" url="${cancelURI}"/>


