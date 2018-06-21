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

<form:form action="user/editProfile.do" modelAttribute="user" onsubmit="return validatePhone()">

    <form:hidden path="id"/>
    <form:hidden path="version"/>
    <form:hidden path="userAccount" />
    <form:hidden path="followers" />
    <form:hidden path="newsPapers" />
    <form:hidden path="followings" />
    <form:hidden path="chirps" />
    <form:hidden path="follower"/>
    <form:hidden path="followed"/>




    <acme:textbox code="user.name" path="name" />
    <br />

    <acme:textbox code="user.surname" path="surname"/>
    <br />

    <form:label path="phone"><spring:message code="user.phone" /></form:label>:&nbsp;
    <form:input id="phoneId" path="phone" placeholder="+34 611222333" />
    <form:errors cssClass="error" path="phone" />
    <br />

    <acme:textbox code="user.email" path="email" />
    <br />

    <acme:textbox code="user.postalAddresses" path="postalAddresses"/>
    <br/>


    <input type="submit" name="save" value="<spring:message code="user.save"/>" />

    <input type="button" name="cancel" value="<spring:message code="user.cancel" />"
           onclick="javascript: relativeRedir('welcome/index.do');" />

</form:form>

<script>

    function validatePhone() {
        <spring:message code="user.phone.ask" var="ask"/>
        var x = document.getElementById("phoneId").value;
        var patt = new RegExp("^\\+([3][4])( )(\\d{9})|()$");
        if(x != "" && !patt.test(x)){
            return confirm('<jstl:out value="${ask}"/>');
        }
    }

</script>