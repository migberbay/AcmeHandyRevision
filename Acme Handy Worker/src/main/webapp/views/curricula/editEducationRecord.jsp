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
	Recibe: EducationRecord : educationRecord
 -->

<form:form action="curricula/handyworker/editEducationRecord.do" modelAttribute="educationRecord">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<security:authorize access="hasRole('HANDYWORKER')">
	
	<form:label path="diplomaTitle">
		<spring:message code="curricula.educationRecord.diplomaTitle" />
	</form:label>
	
	<form:input path="diplomaTitle" />
	<form:errors cssClass="error" path="diplomaTitle" />
	<br />
	
	<!--  -->
	<form:label path="startDate">
		<spring:message code="curricula.educationRecord.startDate" />
	</form:label>
	
	<form:input path="startDate" />
	<form:errors cssClass="error" path="startDate" />
	<br />
	
	<!--  -->
	<form:label path="endDate">
		<spring:message code="curricula.educationRecord.endDate" />
	</form:label>
	
	<form:input path="endDate" />
	<form:errors cssClass="error" path="endDate" />
	<br />
	
	<!--  -->
	<form:label path="institution">
		<spring:message code="curricula.educationRecord.institution" />
	</form:label>
	
	<form:input path="institution" />
	<form:errors cssClass="error" path="institution" />
	<br />
	
	<!--  -->
	<form:label path="attachment">
		<spring:message code="curricula.educationRecord.attachment" />
	</form:label>
	
	<form:input path="attachment" />
	<form:errors cssClass="error" path="attachment" />
	<br />
	
	<!--  -->
	
	<form:label path="comments">
		<spring:message code="curricula.educationRecord.comments" />
	</form:label>
	
	<form:textarea path="comments" />
	<form:errors cssClass="error" path="comments" />
	<br />
	
	<!--  -->
	
	
	<input type="submit" name="save" value="<spring:message code="curricula.save" />" />
				
	<input type="button" name="cancel"
		value="<spring:message code="curricula.cancel" />"
		onclick="javascript: window.location.replace('curricula/handyworker/show.do')" />
	<br />
	
	</security:authorize>

</form:form>