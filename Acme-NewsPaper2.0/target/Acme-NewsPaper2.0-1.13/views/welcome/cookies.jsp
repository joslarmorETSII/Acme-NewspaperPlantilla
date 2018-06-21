<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<body style="text-align: justify;">
	<p><spring:message code="welcome.cookies.p1" /></p>
	<p><spring:message code="welcome.cookies.p2" /></p>
</body>
<br>
<br>
<br>
<button onclick="javascript:window.close();"><spring:message code="welcome.close" /></button>

</html>

