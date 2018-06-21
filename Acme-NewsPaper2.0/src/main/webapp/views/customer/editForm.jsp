<%--
  Created by IntelliJ IDEA.
  User: Félix
  Date: 08/04/2018
  Time: 17:35
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<div class="input-group">
    <form:form  action="customer/register.do" modelAttribute="userForm">

    <div class="form-group">
        <acme:textbox code="customer.username" path="username" />
    </div>

    <div class="form-group">
        <acme:password code="customer.password" path="password" />
        <br/>

        <div class="form-group">
            <acme:password code="customer.repeatPassword" path="repeatPassword" />
        </div>

        <div class="form-group">
            <acme:textbox code="customer.name" path="name" />
        </div>
        <div class="form-group">
            <acme:textbox code="customer.surname" path="surname" />
        </div>
        <div class="form-group">
            <acme:textbox path="phone" code="customer.phone"/>
        </div>
        <div class="form-group">
            <acme:textbox code="customer.email" path="email"/>
        </div>
        <div class="form-group">
            <acme:textbox code="customer.postalAddresses" path="postalAddresses"/>
        </div>



        <div class="form-group">
            <form:checkbox id="checkTerm" onclick="comprobar();" path="check"/>
            <form:label path="check">
                <spring:message code="customer.accept" />
                <a href="termAndCondition/termAndCondition.do"><spring:message code="customer.lopd"/></a>
            </form:label>
        </div>


        <input type="submit" name="save" id="saveButton" value="<spring:message code="customer.save"/>" />

        <input type="button" name="cancel" value="<spring:message code="customer.cancel" />"
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
