<%--
  Created by IntelliJ IDEA.
  User: khawla
  Date: 04/04/2018
  Time: 18:14
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

<h3><b><spring:message code="newsPaper.title"/>:&nbsp; </b><jstl:out value="${newsPaper.title}"/></h3>


<b><spring:message code="newsPaper.description"/>:&nbsp; </b><jstl:out value="${newsPaper.description}"/>
<br/>

<img src="${newsPaper.picture}" width="500px" height="100%" />
<br/>

<%--TABLE OF CONTENT --%>
<fieldset>
    <legend>Table of Contents</legend>
<jstl:forEach items="${newsPaper.articles}" var="article">
    <security:authorize access="hasAnyRole('USER','ADMINISTRATOR')">
    <b><spring:message code="newsPaper.publisher"/>:&nbsp;</b><a href="user/display.do?userId=${newsPaper.publisher.id}"><jstl:out value="${newsPaper.publisher.userAccount.username}"/></a>
    </security:authorize>

    <security:authorize access="hasRole('CUSTOMER')">
        <b><spring:message code="newsPaper.publisher"/>:&nbsp;</b><a href="user/display.do?userId=${newsPaper.publisher.id}"><jstl:out value="${newsPaper.publisher.userAccount.username}"/></a>
    </security:authorize>

    <security:authorize access="isAnonymous()">
        <b><spring:message code="newsPaper.publisher"/>:&nbsp;</b><a href="user/display.do?userId=${newsPaper.publisher.id}"><jstl:out value="${newsPaper.publisher.userAccount.username}"/></a>
    </security:authorize>
    <br/>
    <a href="article/display.do?articleId=${article.id}"><jstl:out value="${article.title}"/></a>
    <br/>
    <b> <spring:message code="article.summary"/>:&nbsp;</b>
    <details>
        <summary></summary>
            <jstl:out value="${article.summary}"/>
    </details>
</jstl:forEach>
</fieldset>
<security:authorize access="isAuthenticated()">

<fieldset>
    <legend>Notes</legend>

    <display:table name="newsPaper.notes" pagesize="5" class="displaytag" requestURI="note/administrator/list.do" id="row">

    <jstl:choose>
        <jstl:when test="${row.gauge eq '1'}">  <jstl:set var="style" value="background-color: LightYellow; color: black"/> </jstl:when>
        <jstl:when test="${row.gauge eq '2'}">  <jstl:set var="style" value="background-color: Moccasin; color: black"/> </jstl:when>
        <jstl:when test="${row.gauge eq '3'}">  <jstl:set var="style" value="background-color: Blue; color: white"/> </jstl:when>
    </jstl:choose>

    <spring:message code="note.title" var="headerTag" />
    <display:column property="title" title="${headerTag}" style="${style}"/>

    <spring:message code="note.description" var="headerTag" />
    <display:column property="description" title="${headerTag}" style="${style}"/>

    <spring:message code="note.gauge" var="headerTag" />
    <display:column property="gauge" title="${headerTag}" style="${style}"/>

    <spring:message code="note.newsPaper" var="headerTag" />
    <display:column property="newsPaper.title" title="${headerTag}" style="${style}"/>

</display:table>
</fieldset>
</security:authorize>


<br/>

<input type="button" name="cancel" value="<spring:message code="general.cancel" />"
       onclick="javascript: relativeRedir('${cancelUriSession}');" />
