<%--
  Created by IntelliJ IDEA.
  User: yuzi
  Date: 4/25/18
  Time: 11:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<!-- todo: if newsPapers is empty...-->

<form:form action="audit/administrator/asociateNewsPaper.do" modelAttribute="audit">

    <form:hidden path="id" />
    <form:hidden path="version" />
    <form:hidden path="administrator"/>
    <form:hidden path="code"/>
    <form:hidden path="title"/>
    <form:hidden path="description"/>
    <form:hidden path="gauge"/>
    <form:hidden path="finalMode"/>
    <form:hidden path="moment"/>

    <acme:select path="newsPaper" code="audit.newsPaper" items="${newsPapers}" itemLabel="title"/>

    <br />

    <acme:submit name="save" code="general.save"/>
    <acme:cancel url="welcome/index.do" code="general.cancel"/>

</form:form>