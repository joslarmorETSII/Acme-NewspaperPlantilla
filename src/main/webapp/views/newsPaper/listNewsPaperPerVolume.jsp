<%--
  Created by IntelliJ IDEA.
  User: Félix
  Date: 3/04/2018
  Time: 18:51
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
<%@taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<jstl:if test="${pageContext.response.locale.language == 'es' }">
    <jstl:set value="{0,date,dd/MM/yyyy HH:mm}" var="formatDate"/>
</jstl:if>

<jstl:if test="${pageContext.response.locale.language == 'en' }">
    <jstl:set value="{0,date,yyyy/MM/dd HH:mm}" var="formatDate"/>
</jstl:if>

    <display:table id="newspaper" name="newsPapers" requestURI="${requestUri}" pagesize="5">

        <acme:column code="newsPaper.publisher" value="${newspaper.publisher.name} " />
        <acme:column code="newsPaper.title" value="${newspaper.title}"/>
        <acme:column code="newsPaper.description" value="${newspaper.description}"/>
        <spring:message code="newsPaper.picture" var="pic"/>
        <display:column title="${pic}"><img src="${newspaper.picture}" alt="no image" width="130" height="100"></display:column>

        <spring:message var="publicationDate" code="newsPaper.publicationDate"/>
        <spring:message var="formatDate" code="event.format.date"/>
        <display:column property="publicationDate" title="${publicationDate}" format="${formatDate}" sortable="true" />
        <security:authorize access="hasRole('USER')" >
        <display:column>
            <jstl:if test="${newspaper.publisher eq user}">
                <acme:button url="newsPaper/display.do?newsPaperId=${newspaper.id}" code="newsPaper.display"/>
            </jstl:if>
        </display:column>
        </security:authorize>

        <security:authorize access="hasRole('CUSTOMER')" >
        <display:column>
            <jstl:if test="${customerIsSuscribed or newspaper.modePrivate eq false}">
                <acme:button url="newsPaper/customer/display.do?newsPaperId=${newspaper.id}" code="newsPaper.display"/>
            </jstl:if>
        </display:column>
        </security:authorize>
    </display:table>

    <acme:button code="general.cancel" url="${cancelUri}"/>



