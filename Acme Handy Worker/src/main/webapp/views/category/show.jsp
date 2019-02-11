<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="category" id="row"
	requestURI="category/administrator/show.do">

	<display:column>

		<b><spring:message code="category.name" />: </b>
		<jstl:out value="${category.name}" /> <br/>

		<b><spring:message code="category.parentCategory" />: </b>
		<jstl:out value="${category.parentCategory.name}" /> <br/>

	</display:column>

</display:table>

