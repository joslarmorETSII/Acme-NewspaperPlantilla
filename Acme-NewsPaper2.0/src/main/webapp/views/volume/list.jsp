<%--
  Created by IntelliJ IDEA.
  User: Félix
  Date: 25/04/2018
  Time: 12:05
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

<display:table id="row" name="volumes" requestURI="${requestURI}" pagesize="5">

    <display:column>
        <security:authorize access="hasRole('USER')" >
            <jstl:if test="${row.user eq user}">
                <acme:button url="volume/user/edit.do?volumeId=${row.id}" code="volume.edit" />
            </jstl:if>
        </security:authorize>
    </display:column>

    <acme:column code="volume.title" value="${row.title}"/>
    <acme:column code="volume.description" value="${row.description}"/>
    <acme:column code="volume.anyo" value="${row.anyo}" />

    <security:authorize access="isAnonymous()">
    <display:column>
         <acme:button url="newsPaper/listNewsPapersV.do?volumeId=${row.id}" code="volume.newsPapers.list"/>
    </display:column>
    </security:authorize>

    <security:authorize access="hasRole('USER')">
        <display:column>
            <jstl:if test="${row.user eq user}">
             <acme:button url="volume/user/addNewsPaper.do?volumeId=${row.id}" code="volume.newsPapers.add"/>
            </jstl:if>
        </display:column>
    </security:authorize>
    <security:authorize access="hasRole('USER')">
        <display:column>
            <jstl:if test="${row.user eq user}">
            <acme:button url="volume/user/removeNewsPaper.do?volumeId=${row.id}" code="volume.newsPapers.remove"/>
            </jstl:if>
        </display:column>
    </security:authorize>

    <security:authorize access="hasAnyRole('CUSTOMER','AGENT')">
    <display:column>
        <acme:button url="newsPaper/listNewsPapersV.do?volumeId=${row.id}" code="volume.newsPapers.list"/>
    </display:column>
    </security:authorize>

    <security:authorize access="hasRole('USER')">
        <display:column>
            <acme:button url="newsPaper/user/listNewspaperUserVolume.do?volumeId=${row.id}" code="volume.newsPapers.list"/>
        </display:column>
    </security:authorize>

</display:table>

<security:authorize access="hasRole('USER')">
    <acme:button code="volume.create" url="volume/user/create.do"/>
</security:authorize>

<input type="button" name="cancel" value="<spring:message code="general.cancel" />"
       onclick="javascript: relativeRedir('/welcome/index.do');" />
