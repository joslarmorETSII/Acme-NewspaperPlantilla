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

<fieldset>
		<security:authorize access="hasRole('ADMINISTRATOR')" >
			<b><spring:message code="advertisement.allTaboo"/></b>
			<display:table id="row" name="tabooAdvertisements" requestURI="${requestURI}"
					   pagesize="5">
				<acme:column code="advertisement.title" value="${row.title}" />
				<display:column title="${banner}"><img src="${row.banner}" alt="no image" width="130" height="100"></display:column>
				<acme:column code="advertisement.targetPage" value="${row.targetPage}"/>
				<acme:column code="advertisement.taboo" value="${row.taboo}"/>
				<acme:column code="advertisement.taboo" value="${row.taboo}"/>
					<display:column>
						<acme:button url="advertisement/administrator/delete.do?advertisementId=${row.id}" code="advertisement.delete" />
					</display:column>
			</display:table>
		</security:authorize>
</fieldset>
<br/>
<br/>

<fieldset>
<security:authorize access="hasRole('ADMINISTRATOR')" >
	<b><spring:message code="advertisement.allAdvertisements"/></b>
</security:authorize>
	<display:table id="row" name="advertisements" requestURI="${requestURI}"
			   pagesize="5">

	<acme:column code="advertisement.title" value="${row.title}" />
	<display:column title="${banner}"><img src="${row.banner}" alt="no image" width="130" height="100"></display:column>
	<acme:column code="advertisement.targetPage" value="${row.targetPage}"/>
	<acme:column code="advertisement.taboo" value="${row.taboo}"/>
	<security:authorize access="hasRole('ADMINISTRATOR')" >
		<display:column>
			<acme:button url="advertisement/administrator/delete.do?advertisementId=${row.id}" code="advertisement.delete" />
		</display:column>
	</security:authorize>
	</display:table>
</fieldset>
	<br/>

<security:authorize access="hasRole('AGENT')">
	<acme:button code="advertisement.create" url="advertisement/agent/create.do"/>
</security:authorize>
<input type="button" name="cancel" value="<spring:message code="advertisement.cancel" />"
	   onclick="javascript: relativeRedir('welcome/index.do');" />