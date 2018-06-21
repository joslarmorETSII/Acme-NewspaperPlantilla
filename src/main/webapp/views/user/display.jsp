<%--
  Created by IntelliJ IDEA.
  User: yuzi
  Date: 2/23/18
  Time: 6:30 PM
  To change this template use File | Settings | File Templates.
--%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<jstl:if test="${pageContext.response.locale.language == 'es' }">
    <jstl:set value="{0,date,dd/MM/yyyy HH:mm}" var="formatDate"/>
</jstl:if>

<jstl:if test="${pageContext.response.locale.language == 'en' }">
    <jstl:set value="{0,date,yyyy/MM/dd HH:mm}" var="formatDate"/>
</jstl:if>

<br/>
<br/>
<fieldset>
    <h1><spring:message code="user.nameAccount"/>:&nbsp;<jstl:out value="${user.userAccount.username}" /></h1>

    <b><spring:message code="user.name"/>:&nbsp;<jstl:out value="${user.name}" /></b>
    <br/>

    <b><spring:message code="user.surname"/>:&nbsp;</b><jstl:out value="${user.surname}" />
    <br/>

    <b><spring:message code="user.phone"/>:&nbsp;</b><jstl:out value="${user.phone}" />
    <br/>

    <b><spring:message code="user.email"/>:&nbsp;</b><jstl:out value="${user.email}" />
    <br/>

    <b><spring:message code="user.postalAddresses"/>:&nbsp;</b><jstl:out value="${user.postalAddresses}" />
    <br/>
</fieldset>
<br/>
<fieldset>
    <b><spring:message code="article.publishedArticles"/></b>

    <display:table name="articles" id="row" pagesize="5" class="displaytag" requestURI="${requestURI}">
        <acme:column code="article.title" value="${row.title}" />
        <spring:message var="moment" code="article.moment"/>
        <spring:message var="formatDate" code="event.format.date"/>
        <display:column property="moment" title="${moment}" format="${formatDate}" sortable="true" />
        <acme:column code="article.summary" value="${row.summary}" sortable="true"/>
        <security:authorize access="isAuthenticated()" >
            <display:column>
                <acme:button url="article/display.do?articleId=${row.id}" code="article.display" />
            </display:column>
        </security:authorize>
    </display:table>
</fieldset>

<br/>
<fieldset>
    <b><spring:message code="user.postedChirps"/></b>

    <display:table name="chirps" id="chirp" pagesize="5" class="displaytag" requestURI="${requestURI}">

        <acme:column code="chirp.title" value="${chirp.title}" />

        <spring:message var="moment" code="chirp.moment"/>
        <spring:message var="formatDate2" code="event.format.date"/>
        <display:column property="moment" title="${moment}" format="${formatDate2}" sortable="true" />

        <acme:column code="chirp.description" value="${chirp.description}" sortable="true"/>


        <security:authorize access="hasRole('ADMINISTRATOR')" >
            <display:column>
                <acme:button url="chirp/administrator/edit.do?chirpId=${chirp.id}" code="chirp.delete" />
            </display:column>
        </security:authorize>

        <security:authorize access="isAuthenticated()" >
            <display:column>
                <acme:button url="chirp/user/display.do?chirpId=${chirp.id}" code="chirp.display" />
            </display:column>
        </security:authorize>

    </display:table>
</fieldset>
<br/>
<security:authorize access="hasRole('USER')">
    <jstl:if test="${!esSeguido && principal.id ne user.id}">
        <acme:button code="user.follow" url="user/user/follow.do?userId=${user.id}"/>
    </jstl:if>
    <jstl:if test="${esSeguido}">
        <acme:button code="user.unfollow" url="user/user/unfollow.do?userId=${user.id}"/>
    </jstl:if>
</security:authorize>

<input type="button" name="cancel" value="<spring:message code="general.cancel" />" onclick="javascript: relativeRedir('${cancelUriSession}');" />

