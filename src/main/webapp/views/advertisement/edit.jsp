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

<form:form action="advertisement/agent/edit.do" modelAttribute="advertisement" >

    <form:hidden path="id"/>
    <form:hidden path="version"/>
    <form:hidden path="agent"/>
    <form:hidden path="taboo"/>


    <acme:textbox path="title" code="advertisement.title"/>
    <br />

    <acme:textbox path="banner" code="advertisement.banner"/>
    <br />

    <acme:textbox path="targetPage" code="advertisement.targetPage"/>
    <br />

    <form:label path="creditCard.holder"><spring:message code="advertisement.holder"/></form:label>
    <form:input path="creditCard.holder"/>
    <form:errors path="creditCard.holder" cssClass="error"/>
    <br/>

    <form:label path="creditCard.brand"><spring:message code="advertisement.brand"/></form:label>
    <form:input path="creditCard.brand"/>
    <form:errors path="creditCard.brand" cssClass="error"/>
    <br/>

    <form:label path="creditCard.number"><spring:message code="advertisement.number"/></form:label>
    <form:input path="creditCard.number"/>
    <form:errors path="creditCard.number" cssClass="error"/>
    <br/>

    <form:label path="creditCard.expirationMonth"><spring:message code="advertisement.expirationMonth"/></form:label>
    <form:input path="creditCard.expirationMonth"/>
    <form:errors path="creditCard.expirationMonth" cssClass="error"/>
    <br/>

    <form:label path="creditCard.expirationYear"><spring:message code="advertisement.expirationYear"/></form:label>
    <form:input path="creditCard.expirationYear"/>
    <form:errors path="creditCard.expirationYear" cssClass="error"/>
    <br/>

    <form:label path="creditCard.cvv"><spring:message code="advertisement.cvv"/></form:label>
    <form:input path="creditCard.cvv"/>
    <form:errors path="creditCard.cvv" cssClass="error"/>
    <br/>

    <form:label path="newsPapers">
        <spring:message code="advertisement.newsPapers" />
    </form:label>
    <form:select path="newsPapers" items="${newsPapers}" itemLabel="title"/>
    <form:errors path="newsPapers" cssClass="error" />
    <br/>

    <input type="submit" name="save" value="<spring:message code="advertisement.save"/>" />

    <input type="button" name="cancel" value="<spring:message code="advertisement.cancel" />"
           onclick="javascript: relativeRedir('welcome/index.do');" />

</form:form>
