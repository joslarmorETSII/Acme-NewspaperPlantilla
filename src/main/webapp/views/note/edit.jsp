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

<form:form action="note/administrator/edit.do" modelAttribute="note">

    <form:hidden path="id" />
    <form:hidden path="version" />
    <form:hidden path="administrator"/>
    <form:hidden path="ticker"/>
    <form:hidden path="newsPaper"/>

    <acme:textbox code="note.title" path="title"/>
    <acme:textarea code="note.description" path="description"/>

    <label>Gauge</label>
    <form:select path="gauge">
        <option>1</option>
        <option>2</option>
        <option>3</option>
    </form:select>

    <acme:textbox code="note.displayMoment" path="displayMoment"/>
    <acme:checkbox  code="note.finalMode" path="finalMode"/>

    <br />

    <acme:submit name="save" code="general.save"/>
    <acme:cancel url="welcome/index.do" code="general.cancel"/>

</form:form>