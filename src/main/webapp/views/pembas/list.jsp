<%--
  Created by IntelliJ IDEA.
  User: khawla
  Date: 04/04/2018
  Time: 10:55
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="jstt" uri="http://java.sun.com/jsp/jstl/core" %>

<jstl:if test="${pageContext.response.locale.language == 'es' }">
    <jstl:set value="{0,date,dd/MM/yyyy HH:mm}" var="formatDate"/>
</jstl:if>

<jstl:if test="${pageContext.response.locale.language == 'en' }">
    <jstl:set value="{0,date,yyyy/MM/dd HH:mm}" var="formatDate"/>
</jstl:if>


<fieldset>


    <display:table name="pembass" id="row" pagesize="5" class="displaytag" requestURI="${requestURI}">

        <jstl:choose>
            <jstl:when test="${row.gauge eq '1'}">  <jstl:set var="style" value="background-color: Black; color: white"/> </jstl:when>
            <jstl:when test="${row.gauge eq '2'}">  <jstl:set var="style" value="background-color: PaleVioletRed; color: black"/> </jstl:when>
            <jstl:when test="${row.gauge eq '3'}">  <jstl:set var="style" value="background-color: DeepSkyBlue; color: white"/> </jstl:when>
        </jstl:choose>



        <display:column>
            <security:authorize access="hasRole('ADMINISTRATOR')" >
                <jstl:if test="${row.finalMode eq false}">
                    <acme:button url="pembas/administrator/edit.do?pembasId=${row.id}" code="pembas.edit" />
                </jstl:if>
            </security:authorize>
        </display:column>

        <spring:message code="pembas.title" var="headerTag" />
        <display:column property="title" title="${headerTag}" style="${style}"/>

        <spring:message code="pembas.description" var="headerTag" />
        <display:column property="description" title="${headerTag}" style="${style}"/>

        <spring:message code="pembas.gauge" var="headerTag" />
        <display:column property="gauge" title="${headerTag}" style="${style}"/>

        <spring:message code="pembas.code" var="headerTag" />
        <display:column property="code" title="${headerTag}" style="${style}"/>

        <spring:message var="moment" code="pembas.moment"/>
        <spring:message var="formatDate" code="event.format.date"/>
        <display:column property="moment" title="${moment}" format="${formatDate}" sortable="true" style="${style}" />


    <security:authorize access="hasRole('ADMINISTRATOR')" >
        <display:column>
            <jstl:if test="${row.finalMode ne false && row.newsPaper eq null}">
                <acme:button url="pembas/administrator/asociateNewsPaper.do?pembasId=${row.id}" code="pembas.asociateNewsPaper"/>
            </jstl:if>

            <jstl:if test="${row.finalMode ne false && row.newsPaper ne null}">
                <spring:message code="pembas.asociated"/>
            </jstl:if>
        </display:column>
        </security:authorize>

        <security:authorize access="hasRole('ADMINISTRATOR')" >
            <display:column >

                <acme:button url="pembas/administrator/display.do?pembasId=${row.id}" code="pembas.display"/>

            </display:column>
        </security:authorize>



        <security:authorize access="hasRole('ADMINISTRATOR')" >
            <display:column>
                <acme:button url="pembas/administrator/delete.do?pembasId=${row.id}" code="pembas.delete" />
            </display:column>
        </security:authorize>



    </display:table>
</fieldset>
<br/>




<security:authorize access="hasRole('ADMINISTRATOR')">
    <acme:button code="pembas.create" url="pembas/administrator/create.do"/>
</security:authorize>

<input type="button" name="cancel" value="<spring:message code="general.cancel" />"
       onclick="javascript: relativeRedir('welcome/index.do');" />
