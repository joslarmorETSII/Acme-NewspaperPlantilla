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


<display:table name="audits" id="row" pagesize="5" class="displaytag" requestURI="${requestUri}">



    <display:column>
        <security:authorize access="hasRole('ADMINISTRATOR')" >
            <jstl:if test="${row.finalMode eq false}">
                <acme:button url="audit/administrator/edit.do?auditId=${row.id}" code="audit.edit" />
            </jstl:if>
        </security:authorize>
    </display:column>

    <acme:column code="audit.title" value="${row.title} " />
    <acme:column code="audit.description" value="${row.description}"/>
    <acme:column code="audit.gauge" value="${row.gauge}"/>
    <acme:column code="audit.code" value="${row.code}"/>

    <spring:message var="moment" code="audit.moment"/>
    <spring:message var="formatDate" code="event.format.date"/>
    <display:column property="moment" title="${moment}" format="${formatDate}" sortable="true" />


    <security:authorize access="hasRole('ADMINISTRATOR')" >
        <display:column>
            <jstl:if test="${row.finalMode ne false && row.newsPaper eq null}">
                <acme:button url="audit/administrator/asociateNewsPaper.do?auditId=${row.id}" code="audit.asociateNewsPaper"/>
            </jstl:if>

            <jstl:if test="${row.newsPaper ne null} ">
                <spring:message code="audit.asociated" var="asociated"/> <jstl:out value="${asociated}" />
            </jstl:if>

            <jstl:if test="${row.finalMode eq false} ">
                <spring:message code="audit.noFinalMode" var="noFinalMode"/> <jstl:out value="${noFinalMode}" />
            </jstl:if>
        </display:column>
    </security:authorize>

    <security:authorize access="hasRole('ADMINISTRATOR')" >
    <display:column >

            <acme:button url="audit/administrator/display.do?auditId=${row.id}" code="audit.display"/>

    </display:column>
    </security:authorize>



    <security:authorize access="hasRole('ADMINISTRATOR')" >
        <display:column>
            <acme:button url="audit/administrator/delete.do?auditId=${row.id}" code="audit.delete" />
        </display:column>
    </security:authorize>



</display:table>
</fieldset>
<br/>




<security:authorize access="hasRole('ADMINISTRATOR')">
    <acme:button code="audit.create" url="audit/administrator/create.do"/>
</security:authorize>

<input type="button" name="cancel" value="<spring:message code="general.cancel" />"
       onclick="javascript: relativeRedir('welcome/index.do');" />
