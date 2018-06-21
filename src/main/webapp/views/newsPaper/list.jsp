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
    <security:authorize access="hasRole('ADMINISTRATOR')" >
    <b><spring:message code="newsPapers.allTaboo"/></b>
    </security:authorize>

<display:table name="newsPapers" id="row" pagesize="5" class="displaytag" requestURI="${requestUri}">

    <jstl:set var="forPublish" value="true" />
    <jstl:forEach var="item" items="${row.articles}" >
        <jstl:if test="${ item.finalMode eq false}" >
                <jstl:set var="forPublish" value="false" />
        </jstl:if>
    </jstl:forEach>

    <display:column>
        <security:authorize access="hasRole('USER')" >
            <jstl:if test="${row.publisher eq user and row.published eq false}">
                <acme:button url="newsPaper/user/edit.do?newsPaperId=${row.id}" code="newsPaper.edit" />
            </jstl:if>
        </security:authorize>
    </display:column>

    <acme:column code="newsPaper.publisher" value="${row.publisher.name} " />
    <acme:column code="newsPaper.title" value="${row.title}"/>
    <acme:column code="newsPaper.description" value="${row.description}"/>
    <spring:message code="newsPaper.picture" var="pic"/>
    <display:column title="${pic}"><img src="${row.picture}" alt="no image" width="130" height="100"></display:column>

    <spring:message var="publicationDate" code="newsPaper.publicationDate"/>
    <spring:message var="formatDate" code="event.format.date"/>
    <display:column property="publicationDate" title="${publicationDate}" format="${formatDate}" sortable="true" />

    <security:authorize access="hasRole('USER')">
        <display:column >
            <jstl:if test="${ row.published ne true && !empty row.articles && forPublish eq true }">
                <acme:button url="newsPaper/user/publish.do?newsPaperId=${row.id}" code="newsPaper.publish"/>
            </jstl:if>
            <jstl:if test="${row.published eq true}">
                <spring:message code="newsPaper.publicado" var="publicado"/> <jstl:out value="${publicado}" />
            </jstl:if>
        </display:column>
    </security:authorize>

    <security:authorize access="hasRole('CUSTOMER')">
        <display:column >
            <jstl:if test="${ row.modePrivate ne false}">
                <acme:button url="newsPaper/customer/subscribe.do?newsPaperId=${row.id}" code="newsPaper.subscribe"/>
            </jstl:if>
        </display:column>
    </security:authorize>

    <security:authorize access="hasRole('USER')" >
    <display:column >
        <jstl:if test="${row.modePrivate eq false or row.publisher eq user }">
            <acme:button url="newsPaper/display.do?newsPaperId=${row.id}" code="newsPaper.display"/>
        </jstl:if>
    </display:column>
    </security:authorize>

    <security:authorize access="hasRole('ADMINISTRATOR')">
        <display:column >
            <acme:button url="newsPaper/administrator/display.do?newsPaperId=${row.id}" code="newsPaper.display"/>
        </display:column>
    </security:authorize>

    <security:authorize access="isAnonymous()||hasAnyRole('AGENT','CUSTOMER')">
    <display:column >
        <jstl:if test="${row.modePrivate eq false}">
         <acme:button url="newsPaper/displayAnonymous.do?newsPaperId=${row.id}" code="newsPaper.display"/>
        </jstl:if>
    </display:column>
    </security:authorize>

    <security:authorize access="hasRole('ADMINISTRATOR')" >
        <display:column>
            <acme:button url="newsPaper/administrator/edit.do?newsPaperId=${row.id}" code="newsPaper.delete" />
        </display:column>
    </security:authorize>

</display:table>
</fieldset>
<br/>

<security:authorize access="hasRole('ADMINISTRATOR')">
<fieldset>
    <b><spring:message code="newsPaper.all"/></b>
<display:table name="allNewsPapers" id="row" pagesize="5" class="displaytag" requestURI="${requestUri}">

    <acme:column code="newsPaper.publisher" value="${row.publisher.name} " />
    <acme:column code="newsPaper.title" value="${row.title}"/>
    <acme:column code="newsPaper.description" value="${row.description}"/>
    <spring:message code="newsPaper.picture" var="pic"/>
    <display:column title="${pic}"><img src="${row.picture}" alt="no image" width="130" height="100"></display:column>

    <spring:message var="publicationDate" code="newsPaper.publicationDate"/>
    <spring:message var="formatDate" code="event.format.date"/>
    <display:column property="publicationDate" title="${publicationDate}" format="${formatDate}" sortable="true" />

    <display:column >
        <acme:button url="newsPaper/administrator/display.do?newsPaperId=${row.id}" code="newsPaper.display"/>
    </display:column>

    <display:column>
        <acme:button url="newsPaper/administrator/edit.do?newsPaperId=${row.id}" code="newsPaper.delete" />
    </display:column>

</display:table>
</fieldset>
</security:authorize>

<security:authorize access="hasRole('USER')">
    <acme:button code="newsPaper.create" url="newsPaper/user/create.do"/>
</security:authorize>


<acme:cancel code="newsPaper.cancel" url="${cancelUri}"/>
