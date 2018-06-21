<%--
  Created by IntelliJ IDEA.
  User: yuzi
  Date: 2/24/18
  Time: 12:12 AM
  To change this template use File | Settings | File Templates.
--%>

<%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%-- Attributes --%>

<%@ attribute name="code" required="true" %>
<%@ attribute name="value" required="true" %>





<%-- Definition --%>


<div >
    <h3><b><spring:message code="${code}"/>:&nbsp;</b><jstl:out value="${value}"/></h3>
</div>

