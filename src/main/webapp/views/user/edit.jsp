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

<div class="input-group">
<form:form  action="user/register.do" modelAttribute="userForm">

        <div class="form-group">
        <acme:textbox code="user.username" path="username" />
        </div>

        <div class="form-group">
        <acme:password code="user.password" path="password" />
        <br/>

        <div class="form-group">
        <acme:password code="user.repeatPassword" path="repeatPassword" />
        </div>

        <div class="form-group">
        <acme:textbox code="user.name" path="name" />
        </div>
        <div class="form-group">
        <acme:textbox code="user.surname" path="surname" />
        </div>
        <div class="form-group">
        <acme:textbox path="phone" code="user.phone"/>
        </div>
        <div class="form-group">
        <acme:textbox code="user.email" path="email"/>
        </div>
        <div class="form-group">
        <acme:textbox code="user.postalAddresses" path="postalAddresses"/>
        </div>



        <div class="form-group">
        <form:checkbox id="checkTerm" onclick="comprobar();" path="check"/>
        <form:label path="check">
        <spring:message code="user.accept" />
        <a href="termAndCondition/termAndCondition.do"><spring:message code="user.lopd"/></a>
        </form:label>
        </div>


	<input type="submit" name="save" id="saveButton" value="<spring:message code="user.save"/>" />
	
	<input type="button" name="cancel" value="<spring:message code="user.cancel" />"
			onclick="javascript: relativeRedir('welcome/index.do');" />

</form:form>
</div>

<script>

    document.getElementById("saveButton").disabled = true;
    document.getElementById("checkTerm").checked = false;

    function comprobar() {

        var aux = document.getElementById("checkTerm").checked;

        if(aux == true){
            document.getElementById("saveButton").disabled = false;
        }
        else{
            document.getElementById("saveButton").disabled = true;
        }
    }
</script>

