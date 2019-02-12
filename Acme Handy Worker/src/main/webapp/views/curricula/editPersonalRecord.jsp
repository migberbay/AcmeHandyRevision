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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- 
	Recibe: PersonalRecord - personalRecord
 -->

<form:form action="curricula/handyworker/editPersonalRecord.do" modelAttribute="personalRecord">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<security:authorize access="hasRole('HANDYWORKER')">
	
	
	
	<form:label path="fullName">
		<spring:message code="curricula.personalRecord.fullName" />
	</form:label>
	
	<form:input path="fullName" />
	<form:errors cssClass="error" path="fullName" />
	<br />
	
	<!--  -->
	<form:label path="photo">
		<spring:message code="curricula.personalRecord.photo" />
	</form:label>
	
	<form:input path="photo" />
	<form:errors cssClass="error" path="photo" />
	<br />
	
	<!--  -->
	<form:label path="email">
		<spring:message code="curricula.personalRecord.email" />
	</form:label>
	
	<form:input path="email" />
	<form:errors cssClass="error" path="email" />
	<br />
	
	<!--  -->
	<form:label path="phone">
		<spring:message code="curricula.personalRecord.phone" />
	</form:label>
	
	<form:input path="phone" />
	<form:errors cssClass="error" path="phone" />
	<br />
	
	<!--  -->
	<form:label path="linkedInUrl">
		<spring:message code="curricula.personalRecord.linkedInUrl" />
	</form:label>
	
	<form:input path="linkedInUrl" />
	<form:errors cssClass="error" path="linkedInUrl" />
	<br />
		
	
	<input type="submit" name="save" value="<spring:message code="curricula.save" />" />
				
	<input type="button" name="cancel"
		value="<spring:message code="curricula.cancel" />"
		onclick="javascript: window.location.replace('curricula/handyworker/show.do')" />
	<br />
	
	</security:authorize>

</form:form>