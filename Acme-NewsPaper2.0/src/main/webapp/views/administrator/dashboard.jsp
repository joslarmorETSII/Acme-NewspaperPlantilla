<%--
  Created by IntelliJ IDEA.
  User: F?lix
  Date: 22/02/2018
  Time: 12:02
  To change this template use File | Settings | File Templates.
--%>


<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<!-- Queries level c -->

<fieldset>
	<div class="panel-body">
		<b><spring:message code="dash.avgStdOfNewspapersPerUser"/></b>
		<br/>
		<h4><jstl:out value=" AVG: "/><jstl:out value="${avgStdOfNewspapersPerUser[0]}"/> <br/></h4>
		<h4><jstl:out value=" STDDEV: "/><jstl:out value="${avgStdOfNewspapersPerUser[1]}"/> <br/></h4>
	</div>
</fieldset>
<br/>

<fieldset>
	<div class="panel-body">
		<b><spring:message code="dash.avgStdOfArticles"/></b>
		<br/>
		<h4><jstl:out value=" AVG: "/><jstl:out value="${avgStdOfArticles[0]}"/> <br/></h4>
		<h4><jstl:out value=" STDDEV: "/><jstl:out value="${avgStdOfArticles[1]}"/> <br/></h4>
	</div>
</fieldset>
<br/>
<fieldset>
	<div class="panel-body">
		<b><spring:message code="dash.avgStdOfArticlesPerNewspaper"/></b>
		<br/>
		<h4><jstl:out value=" AVG: "/><jstl:out value="${avgStdOfArticlesPerNewspaper[0]}"/> <br/></h4>
		<h4><jstl:out value=" STDDEV: "/><jstl:out value="${avgStdOfArticlesPerNewspaper[1]}"/> <br/></h4>
	</div>
</fieldset>
<br/>

<fieldset>
	<b><spring:message code="dash.newspapers10PercentMoreArticles"/></b>
	<display:table name="newspapersWith10PercentMoreArticlesThanAvg" id="row" pagesize="10" class="displaytag" requestURI="${requestUri}">

		<acme:column code="newsPaper.publisher" value="${row.publisher.name} " />
		<acme:column code="newsPaper.title" value="${row.title}"/>
		<acme:column code="newsPaper.description" value="${row.description}"/>
		<spring:message code="newsPaper.picture" var="pic"/>
		<display:column title="${pic}"><img src="${row.picture}" alt="no image" width="130" height="100"></display:column>

		<spring:message var="publicationDate" code="newsPaper.publicationDate"/>
		<spring:message var="formatDate" code="event.format.date"/>
		<display:column property="publicationDate" title="${publicationDate}" format="${formatDate}" sortable="true" />

		<display:column >
			<acme:button url="newsPaper/display.do?newsPaperId=${row.id}" code="newsPaper.display"/>
		</display:column>

		<display:column>
			<acme:button url="newsPaper/administrator/edit.do?newsPaperId=${row.id}" code="newsPaper.delete" />
		</display:column>

	</display:table>
</fieldset>
<br/>

<fieldset>
    <b><spring:message code="dash.newspapers10PercentFewerArticles"/></b>
    <display:table name="newspapersWith10PercentFewerArticlesThanAvg" id="row" pagesize="10" class="displaytag" requestURI="${requestUri}">

        <acme:column code="newsPaper.publisher" value="${row.publisher.name} " />
        <acme:column code="newsPaper.title" value="${row.title}"/>
        <acme:column code="newsPaper.description" value="${row.description}"/>
        <spring:message code="newsPaper.picture" var="pic"/>
        <display:column title="${pic}"><img src="${row.picture}" alt="no image" width="130" height="100"></display:column>

        <spring:message var="publicationDate" code="newsPaper.publicationDate"/>
        <spring:message var="formatDate" code="event.format.date"/>
        <display:column property="publicationDate" title="${publicationDate}" format="${formatDate}" sortable="true" />

        <display:column >
            <acme:button url="newsPaper/display.do?newsPaperId=${row.id}" code="newsPaper.display"/>
        </display:column>

        <display:column>
            <acme:button url="newsPaper/administrator/edit.do?newsPaperId=${row.id}" code="newsPaper.delete" />
        </display:column>

    </display:table>
</fieldset>
<br/>

<fieldset>
	<div class="panel-body">
		<legend><b><spring:message code="dash.ratioOfUsersThatCreatedNewspaper"/></b></legend>

		<jstl:out value="${ratioOfUsersThatCreatedNewspaper}"/><br>
	</div>
</fieldset>
<br/>
<fieldset>
	<div class="panel-body">
		<legend><b><spring:message code="dash.ratioOfUserCreatingArticle"/></b></legend>

		<jstl:out value="${ratioOfUserCreatingArticle}"/><br>
	</div>
</fieldset>
<br/>

<%-- Queries Level B --%>
<fieldset>
	<div class="panel-body">
		<legend><b><spring:message code="dash.avgFollowUpsPerArticle"/></b></legend>

		<jstl:out value="${avgFollowUpsPerArticle}"/><br>
	</div>
</fieldset>
<br/>
<fieldset>
	<div class="panel-body">
		<legend><b><spring:message code="dash.avgFollowUpsPerArticleAfter1week"/></b></legend>

		<jstl:out value="${avgFollowUpsPerArticleAfter1weekNewspaprerPublished}"/><br>
	</div>
</fieldset>
<br/>
<fieldset>
	<div class="panel-body">
		<legend><b><spring:message code="dash.avgFollowUpsPerArticleAfter2weeks"/></b></legend>

		<jstl:out value="${avgFollowUpsPerArticleAfter2weekNewspaprerPublished}"/><br>
	</div>
</fieldset>
<br/>
<fieldset>
	<div class="panel-body">
		<b><spring:message code="dash.avgStdChirpsPerUser"/></b>
		<br/>
		<h4><jstl:out value=" AVG: "/><jstl:out value="${avgStdChirpsPerUser[0]}"/> <br/></h4>
		<h4><jstl:out value=" STDDEV: "/><jstl:out value="${avgStdChirpsPerUser[1]}"/> <br/></h4>
	</div>
</fieldset>
<br/>
<%-- Queries Level A --%>

<fieldset>
	<div class="panel-body">
		<legend><b><spring:message code="dash.ratioUsersWith75PercentMoreChirpsPostedThanAVG"/></b></legend>

		<jstl:out value="${ratioUsersWith75PercentMoreChirpsPostedThanAVG}"/><br>
	</div>
</fieldset>
<br/>
<fieldset>
	<div class="panel-body">
		<legend><b><spring:message code="dash.ratioPublicVSPrivateNewspapers"/></b></legend>

		<jstl:out value="${ratioPublicVSPrivateNewspapers}"/><br>
	</div>
</fieldset>
<br/>
<br/>
<fieldset>
	<div class="panel-body">
		<legend><b><spring:message code="dash.avgArticlesPerNewsPapersPrivate"/></b></legend>

		<jstl:out value="${avgArticlesPerNewsPapersPrivate}"/><br>
	</div>
</fieldset>
<br/>
<br/>
<fieldset>
	<div class="panel-body">
		<legend><b><spring:message code="dash.avgArticlesPerNewsPapersPublic"/></b></legend>

		<jstl:out value="${avgArticlesPerNewsPapersPublic}"/><br>
	</div>
</fieldset>
<br/>
<br/>
<fieldset>
	<div class="panel-body">
		<legend><b><spring:message code="dash.ratioPrivateNewsPapersVsCustomers"/></b></legend>

		<jstl:out value="${ratioPrivateNewsPapersVsCustomers}"/><br>
	</div>
</fieldset>
<br/>
<br/>
<fieldset>
	<div class="panel-body">
		<legend><b><spring:message code="dash.avgServisesInEachCategory"/></b></legend>

		<jstl:out value="${avgServisesInEachCategory}"/><br>
	</div>
</fieldset>
<br/>

<!-- Queries Newspaper 2.0 -->
<fieldset>
	<div class="panel-body">
		<legend><b><spring:message code="dash.ratioNpAdvertisementsVsNpWithOut"/></b></legend>

		<jstl:out value="${ratioNpAdvertisementsVsNpWithOut}"/><br>
	</div>
</fieldset>
<br/>
<fieldset>
	<div class="panel-body">
		<legend><b><spring:message code="dash.ratioAdverTabooVsAdvertisement"/></b></legend>

		<jstl:out value="${ratioAdverTabooVsAdvertisement}"/><br>
	</div>
</fieldset>
<br/>
<fieldset>
	<div class="panel-body">
		<legend><b><spring:message code="dash.avgNewsPapersPerVolume"/></b></legend>

		<jstl:out value="${avgNewsPapersPerVolume}"/><br>
	</div>
</fieldset>
<br/>
<fieldset>
	<div class="panel-body">
		<legend><b><spring:message code="dash.rationSubscribedNewsPVsSubscribedVolume"/></b></legend>

		<jstl:out value="${rationSubscribedNewsPVsSubscribedVolume}"/><br>
	</div>
</fieldset>



