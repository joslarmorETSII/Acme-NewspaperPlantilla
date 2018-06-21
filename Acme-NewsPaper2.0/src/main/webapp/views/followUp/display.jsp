<%--
 * edit.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
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



<fieldset>
<h3><b><spring:message code="followUp.title"/>:&nbsp;</b><jstl:out value="${followUp.title}"/></h3>


	<fieldset>
		<legend><b><spring:message code="followUp.summary"/></b></legend>
			<jstl:out value="${followUp.summary}"/>
	</fieldset>
	<br/>
	<fieldset>
		<legend><b><spring:message code="followUp.text"/></b></legend>
			<jstl:out value="${followUp.text}"/>
	</fieldset>
	<br/>
	<fieldset>
		<legend><b><spring:message code="article.picture"/></b></legend>
		<jstl:forEach items="${followUp.pictures}" var="picture">
			<img src="${picture.url}" width="500px" height="100%" class="center"/>
			<br/>
		</jstl:forEach>
	</fieldset>
	<br/>
	<jstl:if test="${pageContext.response.locale.language == 'es'}">

		<b><spring:message code="followUp.moment"/>:&nbsp;</b><jstl:out value="${momentEs}" />
		<br/>
	</jstl:if>

	<jstl:if test="${pageContext.response.locale.language == 'en'}">
		<b><spring:message code="followUp.moment"/>:&nbsp;</b><jstl:out value="${momentEn}" />
		<br/>
	</jstl:if>
</fieldset>
<br>
<input type="button" name="cancel" value="<spring:message code="followUp.cancel" />"
	   onclick="javascript: relativeRedir('${cancelUriSession}');" />