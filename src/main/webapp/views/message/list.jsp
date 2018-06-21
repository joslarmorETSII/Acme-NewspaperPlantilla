<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>



<display:table name="messages" pagesize="5" class="displaytag" requestURI="${requestURI}" id="row">
	
	<spring:message code="message.send" var="headerTag" />
	<display:column property="actorSender.name" title="${headerTag}"/>
	
	<spring:message code="message.recipient" var="headerTag" />
	<display:column title="${headerTag}">
		[<jstl:forEach items="${row.actorReceivers}" var="receive"> ${receive.name}; </jstl:forEach>]
	</display:column>
	
	<spring:message code="message.subject" var="headerTag" />
	<display:column property="subject" title="${headerTag}"/>
	
	<spring:message code="message.view" var="headerTag" />
	<display:column title="${headerTag}">
		<a href="message/actor/details.do?messageId=${row.id}">
			<spring:message code="message.view" />
		</a>
	</display:column>
	
	<spring:message code="message.mover" var="headerTag" />
	<display:column title="${headerTag}">
		<a href="message/actor/mover.do?messageId=${row.id}">
			<spring:message code="message.mover" />
		</a>
	</display:column>
	
	<spring:message code="message.delete" var="headerTag" />
	<display:column title="${headerTag}">
		<a href="message/actor/trash.do?messageId=${row.id}">
			<input type="submit" name="delete"
				value="<spring:message code="message.delete" />"
				onclick="return confirm('<spring:message code="message.confirm.delete" />')" />&nbsp;
		</a>	
	</display:column>
</display:table>



	<acme:button code="message.sendAMessage" url="message/actor/create.do"/>

    <!-- boton para volver atrás -->
	<input type="button" name="volver" onclick="history.back()"
		value="<spring:message code="message.volver"/>" />
