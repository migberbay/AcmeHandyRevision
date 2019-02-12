<%--
 * edit.jsp
 *
 * Copyright (C) 2015 Universidad de Sevilla
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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="handyWorker/section/edit.do" modelAttribute="section">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:hidden path="tutorial" />

	<security:authorize access="hasRole('HANDYWORKER')">

		<form:label path="title">
			<spring:message code="section.title" />:
		</form:label>
		<form:input path="title" />
		<form:errors cssClass="error" path="title" />
		<br />

		<form:label path="text">
			<spring:message code="section.text" />:
		</form:label>
		<form:input path="text" />
		<form:errors cssClass="error" path="text" />
		<br />

		<form:label path="pictures">
			<spring:message code="section.pictures" />:
		</form:label>
		<form:textarea path="pictures" />
		<form:errors cssClass="error" path="pictures" />
		<br />

		<input type="submit" name="save" value="<spring:message code="section.save" />" />&nbsp;
		
		<input type="button" name="cancel"
			value="<spring:message code="section.cancel" />"
			onclick="javascript: window.location.replace('')" />
		<br />

	</security:authorize>

</form:form>