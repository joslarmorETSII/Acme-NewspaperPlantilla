<%--
  Created by IntelliJ IDEA.
  User: F�lix
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

<form:form action="customer/edit.do" modelAttribute="customer" onsubmit="return validatePhone()">

    <form:hidden path="id"/>
    <form:hidden path="version"/>
    <form:hidden path="subscriptionsToNewspapers"/>
    <form:hidden path="subscriptionsToVolumes" />

    <acme:textbox path="name" code="customer.name"/>
    <br />

    <acme:textbox path="surname" code="customer.surname"/>
    <br />

    <acme:textbox path="postalAdress" code="customer.postalAdress"/>
    <br />

    <form:label path="phone"><spring:message code="customer.phone" /></form:label>:&nbsp;
    <form:input id="phoneId" path="phone" placeholder="+34 611222333" />
    <form:errors cssClass="error" path="phone" />
    <br />

    <acme:textbox path="email" code="customer.email"/>
    <br />
    <acme:textbox path="postalAddresses" code="customer.postalAddresses"/>
    <br />

    <security:authorize access="hasRole('CUSTOMER')">
        <acme:submit name="save" code="announcement.save"/>
    </security:authorize>

    <acme:cancel code="announcement.cancel" url="${cancelUri}"/>

    <input type="submit" name="save" value="<spring:message code="customer.save"/>" />

    <input type="button" name="cancel" value="<spring:message code="customer.cancel" />"
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