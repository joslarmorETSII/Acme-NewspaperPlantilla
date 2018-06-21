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

<jstl:if test="${not empty newsPapers}">
<div class="input-group">
    <form:form  action="advertisement/agent/edit.do" modelAttribute="registerAdvertisementForm">

        <fieldset>
            <legend><spring:message code="advertisement.add.inf"/> </legend>
            <acme:textbox path="title" code="advertisement.title"/>
            <br />

                <acme:textbox path="banner" code="advertisement.banner"/>
            <br />

                <acme:textbox path="targetPage" code="advertisement.targetPage"/>
            <br />
        </fieldset>
        <br/>
        <fieldset>
            <legend><spring:message code="general.creditCard"/> </legend>
        <div class="form-group">
            <acme:textbox code="advertisement.holder" path="holder" />
            <br/>

            <div class="form-group">
                <acme:textbox code="advertisement.brand" path="brand" />
            </div>

            <div class="form-group">
                <acme:textbox code="advertisement.number" path="number" />
            </div>
            <div class="form-group">
                <acme:textbox code="advertisement.expirationMonth" path="expirationMonth" />
            </div>
            <div class="form-group">
                <acme:textbox code="advertisement.expirationYear" path="expirationYear"/>
            </div>
            <div class="form-group">
                <acme:textbox code="advertisement.cvv" path="cvv"/>
            </div>
        </fieldset>
        <br/>
        <fieldset>
            <legend><spring:message code="general.newspaper"/> </legend>
        <form:label path="newsPaperId"><spring:message code="advertisement.newsPapers" /></form:label>
        <form:select path="newsPaperId">
            <form:options items="${newsPapers}" itemLabel="title" itemValue="id"/>
        </form:select>
        <form:errors path="newsPaper" cssClass="error"/>
        <br/>
        </fieldset>
        <input type="submit" name="save" value="<spring:message code="advertisement.save"/>" />

        <input type="button" name="cancel" value="<spring:message code="advertisement.cancel" />"
               onclick="javascript: relativeRedir('advertisement/agent/list.do');" />

        </form:form>
    </div>
</jstl:if>
<jstl:if test="${empty newsPapers}">
    <b><spring:message code="advertisement.NoNewsPapers"/></b>
</jstl:if>


