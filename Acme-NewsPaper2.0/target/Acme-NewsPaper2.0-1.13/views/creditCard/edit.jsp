<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="creditCard/edit.do" modelAttribute="creditCard">

	<form:hidden path="id"/>
	<form:hidden path="version"/>


	<acme:textbox path="holder" code="creditCard.holder"/>
	<acme:textbox path="brand" code="creditCard.brand"/>
	<acme:textbox path="number" code="creditCard.number"/>
	<acme:textbox path="expirationMonth" code="creditCard.expirationMonth"/>
	<acme:textbox path="expirationYear" code="creditCard.expirationYear"/>
	<acme:textbox path="cvv" code="creditCard.cvv"/>





	<acme:submit name="save" code="button.save"/>
	<jstl:if test="${creditCard.id!=0}">
		<acme:submit name="delete" code="button.delete"/>
	</jstl:if>
	<acme:cancel code="button.cancel" url="/welcome/index.do"/>


</form:form>
