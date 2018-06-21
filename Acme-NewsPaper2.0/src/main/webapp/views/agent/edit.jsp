<%--
  Created by IntelliJ IDEA.
  User: Félix
  Date: 06/04/2018
  Time: 16:55
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1" %>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="agent/edit.do" modelAttribute="agent" onsubmit="return validatePhone()">

    <form:hidden path="id"/>
    <form:hidden path="version"/>

    <acme:textbox path="title" code="agent.n"/>
    <br />

    <acme:textbox path="surname" code="agent.surname"/>
    <br />

    <acme:textbox path="postalAdress" code="agent.postalAdress"/>
    <br />

    <form:label path="phone"><spring:message code="agent.phone" /></form:label>:&nbsp;
    <form:input id="phoneId" path="phone" placeholder="+34 611222333" />
    <form:errors cssClass="error" path="phone" />
    <br />

    <acme:textbox path="email" code="agent.email"/>
    <br />
    <acme:textbox path="postalAddresses" code="agent.postalAddresses"/>
    <br />

    <security:authorize access="hasRole('CUSTOMER')">
        <acme:submit name="save" code="agent.save"/>
    </security:authorize>

    <acme:cancel code="agent.cancel" url="${cancelUri}"/>

    <input type="submit" name="save" value="<spring:message code="agent.save"/>" />

    <input type="button" name="cancel" value="<spring:message code="agent.cancel" />"
           onclick="javascript: relativeRedir('welcome/index.do');" />

</form:form>

<script>

    function validatePhone() {
        <spring:message code="customer.phone.ask" var="ask"/>
        var x = document.getElementById("phoneId").value;
        var patt = new RegExp("^\\+([3][4])( )(\\d{9})|()$");
        if(x != "" && !patt.test(x)){
            return confirm('<jstl:out value="${ask}"/>');
        }
    }

</script>