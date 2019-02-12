<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('ADMIN')">

	<fieldset>
		<legend><spring:message code="admin.score"/></legend>
		<spring:message code="admin.customerEndorsement.score"/>: <br/>
		<jstl:forEach var="x" items="${customersScore}">
			<jstl:out value="${x.key.userAccount.username}"/>: <jstl:out value="${x.value}"/><br/>
		</jstl:forEach>
		<spring:message code="admin.handyworkerEndorsement.score"/>: <br/>
		<jstl:forEach var="y" items="${handyworkersScore}">
			<jstl:out value="${y.key.userAccount.username}"/>: <jstl:out value="${y.value}"/><br/>
		</jstl:forEach>
	</fieldset>
	

	<input type="button" name="cancel"
			value="<spring:message code="application.cancel" />"
			onclick="javascript: window.location.replace('')" />
	<br />

</security:authorize>