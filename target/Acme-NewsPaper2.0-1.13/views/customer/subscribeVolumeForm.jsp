<%--
  Created by IntelliJ IDEA.
  User: Félix
  Date: 07/04/2018
  Time: 19:39
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
    <form:form  action="volume/customer/subscribe.do" modelAttribute="subscribeVolumeForm">

    <form:hidden path="volume"/>

    <div class="form-group">
        <acme:textbox code="customer.holder" path="holder" />
        <br/>

        <div class="form-group">
            <acme:textbox code="customer.brand" path="brand" />
        </div>

        <div class="form-group">
            <acme:textbox code="customer.number" path="number" />
        </div>
        <div class="form-group">
            <acme:textbox code="customer.expirationMonth" path="expirationMonth" />
        </div>
        <div class="form-group">
            <acme:textbox code="customer.expirationYear" path="expirationYear"/>
        </div>
        <div class="form-group">
            <acme:textbox code="customer.cvv" path="cvv"/>
        </div>


        <input type="submit" name="save" value="<spring:message code="customer.save"/>" />

        <input type="button" name="cancel" value="<spring:message code="customer.cancel" />"
               onclick="javascript: relativeRedir('volume/customer/listAllVolumes.do');" />

        </form:form>
    </div>

