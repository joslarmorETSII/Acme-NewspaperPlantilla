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

<display:table name="notes" pagesize="5" class="displaytag" requestURI="note/administrator/list.do" id="row">

	<jstl:choose>
		<jstl:when test="${row.gauge eq '1'}">  <jstl:set var="style" value="background-color: LightYellow; color: black"/> </jstl:when>
		<jstl:when test="${row.gauge eq '2'}">  <jstl:set var="style" value="background-color: Moccasin; color: black"/> </jstl:when>
		<jstl:when test="${row.gauge eq '3'}">  <jstl:set var="style" value="background-color: Blue; color: white"/> </jstl:when>
	</jstl:choose>

	<display:column title="${headerTag}" >
		<jstl:if test="${row.finalMode eq false}">
			<a href="note/administrator/edit.do?noteId=${row.id}">
				<spring:message code="note.edit" />
			</a>
		</jstl:if>
	</display:column>

	<spring:message code="note.title" var="headerTag" />
	<display:column property="title" title="${headerTag}" style="${style}"/>

	<spring:message code="note.description" var="headerTag" />
	<display:column property="description" title="${headerTag}" style="${style}"/>

	<spring:message code="note.gauge" var="headerTag" />
	<display:column property="gauge" title="${headerTag}" style="${style}"/>

	<spring:message code="note.newsPaper" var="headerTag" />
	<display:column property="newsPaper.title" title="${headerTag}" style="${style}"/>

	<display:column style="${style}">
		<jstl:if test="${row.finalMode eq true and row.newsPaper eq null}">
			<a href="note/administrator/addToNewsPaper.do?noteId=${row.id}">
				<spring:message code="note.addNewsPaper" />
			</a>
		</jstl:if>
	</display:column>

</display:table>


<acme:button code="note.create" url="note/administrator/create.do"/>


<input type="button" name="cancel" value="<spring:message code="general.cancel" />" onclick="javascript: relativeRedir('welcome/index.do');" />

