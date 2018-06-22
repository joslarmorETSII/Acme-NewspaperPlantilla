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

<jstl:if test="${pageContext.response.locale.language == 'es' }">
    <jstl:set value="{0,date,dd/MM/yyyy HH:mm}" var="formatDate"/>
</jstl:if>

<jstl:if test="${pageContext.response.locale.language == 'en' }">
    <jstl:set value="{0,date,yyyy/MM/dd HH:mm}" var="formatDate"/>
</jstl:if>

<h3><b><spring:message code="audit.title"/>:&nbsp; </b><jstl:out value="${audit.title}"/></h3>


<b><spring:message code="audit.description"/>:&nbsp; </b><jstl:out value="${audit.description}"/>
<br/>

<b><spring:message code="audit.code"/>:&nbsp; </b><jstl:out value="${audit.code}"/>
<br/>

<b><spring:message code="audit.gauge"/>:&nbsp; </b><jstl:out value="${audit.gauge}"/>
<br/>

<jstl:if test="${pageContext.response.locale.language == 'es'}">

    <b><spring:message code="audit.moment"/>:&nbsp;</b><fmt:formatDate value="${audit.moment}" pattern="${momentEs}"/>
    <br/>
</jstl:if>

<jstl:if test="${pageContext.response.locale.language == 'en'}">
    <b><spring:message code="audit.moment"/>:&nbsp;</b><fmt:formatDate value="${audit.moment}" pattern="${momentEn}"/>
    <br/>
</jstl:if>


<br/>

<input type="button" name="cancel" value="<spring:message code="general.cancel" />"
       onclick="javascript: relativeRedir('${cancelUri}');" />
