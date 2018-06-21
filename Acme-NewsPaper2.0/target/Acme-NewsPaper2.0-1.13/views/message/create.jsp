<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

	<form:form action="${requestURI}" modelAttribute="sendMessage">
	
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="actorSender" />
		<form:hidden path="moment" />
		<form:hidden path="folder" />


	
		<div>
			<form:label path="priority">
				<spring:message code="message.priority" />
			</form:label>	
			<form:select class="form-control" id="priority" path="priority">
				<spring:message code="message.priority.low" var="lowHeader"/><form:option value="LOW" label="${lowHeader }" />
				<spring:message code="message.priority.neutral" var="neutralHeader"/><form:option value="NEUTRAL" label="${neutralHeader }" />
				<spring:message code="message.priority.high" var="highHeader"/><form:option value="HIGH" label="${highHeader }" />
			</form:select>
			<form:errors path="priority" cssClass="error" />
		</div><br/><br/>
	
		<acme:textbox code="message.subject" path="subject" />
		<br />
		
		<acme:textarea code="message.body" path="body" />
		<br />
		<jstl:if test="${isBroadcast ne true}">
		<form:label path="actorReceivers">
			<spring:message code="message.recipient" />
		</form:label>
		<form:select path="actorReceivers" items="${recipients}" itemLabel="name"/>
		<form:errors path="actorReceivers" cssClass="error" />
		<br />
		</jstl:if>

		<acme:submit code="message.send" name="send" />&nbsp;
		<input type="button" name="cancel" value="<spring:message code="general.cancel" />"
			   onclick="javascript: relativeRedir('folder/actor/list.do');" />

	</form:form>