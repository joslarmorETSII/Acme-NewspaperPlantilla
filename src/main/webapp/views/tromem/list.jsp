<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>



<jstl:if test="${pageContext.response.locale.language == 'es' }">
	<jstl:set value="{0,date,dd/MM/yyyy HH:mm}" var="formatDate"/>
</jstl:if>

<jstl:if test="${pageContext.response.locale.language == 'en' }">
	<jstl:set value="{0,date,yyyy/MM/dd HH:mm}" var="formatDate"/>
</jstl:if>


<display:table name="tromems" pagesize="5" class="displaytag" requestURI="tromem/administrator/list.do" id="row">

	<jstl:choose>
		<jstl:when test="${row.gauge eq '1'}">  <jstl:set var="style" value="background-color: DarkOrange; color: black"/> </jstl:when>
		<jstl:when test="${row.gauge eq '2'}">  <jstl:set var="style" value="background-color: GreenYellow; color: black"/> </jstl:when>
		<jstl:when test="${row.gauge eq '3'}">  <jstl:set var="style" value="background-color: DarkRed; color: white"/> </jstl:when>
	</jstl:choose>

	<display:column title="${headerTag}" >
		<jstl:if test="${row.finalMode eq false}">
			<a href="tromem/administrator/edit.do?tromemId=${row.id}">
				<spring:message code="tromem.edit" />
			</a>
		</jstl:if>
	</display:column>

	<spring:message code="tromem.ticker" var="headerTag" />
	<display:column property="ticker" title="${headerTag}" style="${style}"/>

	<spring:message code="tromem.title" var="headerTag" />
	<display:column property="title" title="${headerTag}" style="${style}"/>

	<spring:message code="tromem.description" var="headerTag" />
	<display:column property="description" title="${headerTag}" style="${style}"/>

	<spring:message code="tromem.gauge" var="headerTag" />
	<display:column property="gauge" title="${headerTag}" style="${style}"/>

	<spring:message code="tromem.newsPaper" var="headerTag" />
	<display:column property="newsPaper.title" title="${headerTag}" style="${style}"/>


	<spring:message code="tromem.displayMoment" var="headerTag" />
	<display:column property="displayMoment" title="${headerTag}" format="${formatDate}" style="${style}" />

	<display:column>
		<jstl:if test="${row.finalMode eq true and row.newsPaper eq null}">
			<a href="tromem/administrator/addToNewsPaper.do?tromemId=${row.id}">
				<spring:message code="tromem.addNewsPaper" />
			</a>
		</jstl:if>

		<jstl:if test="${row.finalMode eq true and row.newsPaper ne null}">
			<spring:message code="general.asociated"/>
		</jstl:if>
	</display:column>

	<display:column>
		<a href="tromem/administrator/delete.do?tromemId=${row.id}">
				<spring:message code="tromem.delete" />
			</a>
	</display:column>

</display:table>


<acme:button code="tromem.create" url="tromem/administrator/create.do"/>


<input type="button" name="cancel" value="<spring:message code="general.cancel" />" onclick="javascript: relativeRedir('welcome/index.do');" />

