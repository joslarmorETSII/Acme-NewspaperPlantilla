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

	<form:form action="${requestURI}" modelAttribute="msg">
	
		<form:hidden path="id" />
		
		<acme:select code="message.mover" path="folder" items="${destinyFolders}" itemLabel="name"/>
		<br />
		
		<acme:submit code="message.mover" name="mover" />&nbsp;
		<br />
	
	</form:form>