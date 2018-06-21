<%--
  Created by IntelliJ IDEA.
  User: Félix
  Date: 25/04/2018
  Time: 13:15
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
<%@taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>


<display:table id="row" name="volumes" requestURI="${requestURI}" pagesize="5">

    <acme:column code="volume.title" value="${row.title}"/>
    <acme:column code="volume.description" value="${row.description}"/>
    <acme:column code="volume.anyo" value="${row.anyo}" />



    <display:column>
            <acme:button url="volume/customer/unsubscribe.do?volumeId=${row.id}" code="newsPaper.unsubscribe"/>
    </display:column>


    <display:column>
        <acme:button url="newsPaper/customer/listNewsPapersPerVolume.do?volumeId=${row.id}" code="volume.newsPapers.list"/>
    </display:column>

</display:table>

<acme:button code="general.cancel" url="${cancelUri}"/>
