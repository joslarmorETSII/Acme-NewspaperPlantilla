<%--
  Created by IntelliJ IDEA.
  User: yuzi
  Date: 4/6/18
  Time: 6:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table id="row" name="followers" requestURI="${requestURI}"
               pagesize="5">

    <acme:column code="user.name" value="${row.name}" />
    <acme:column code="user.surname" value="${row.surname}" />
    <acme:column code="user.email" value="${row.email}"/>
    <acme:column code="user.phone" value="${row.phone}"/>
    <acme:column code="user.postalAddresses" value="${row.postalAddresses}"/>
    <acme:columnButton url="user/display.do?userId=${row.id}" codeButton="general.display"/>

</display:table>

<input type="button" name="cancel" value="<spring:message code="general.cancel" />" onclick="javascript: relativeRedir('welcome/index.do');" />

