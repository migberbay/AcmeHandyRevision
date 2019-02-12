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
	Recibe: ProfessionalRecord : professionalRecord
 -->

<form:form action="curricula/handyworker/editProfessionalRecord.do" modelAttribute="professionalRecord">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<security:authorize access="hasRole('HANDYWORKER')">
	
	<form:label path="companyName">
		<spring:message code="curricula.professionalRecord.companyName" />
	</form:label>
	
	<form:input path="companyName" />
	<form:errors cssClass="error" path="companyName" />
	<br />
	
	<!--  -->
	<form:label path="startDate">
		<spring:message code="curricula.professionalRecord.startDate" />
	</form:label>
	
	<form:input path="startDate" />
	<form:errors cssClass="error" path="startDate" />
	<br />
	
	<!--  -->
	<form:label path="endDate">
		<spring:message code="curricula.professionalRecord.endDate" />
	</form:label>
	
	<form:input path="endDate" />
	<form:errors cssClass="error" path="endDate" />
	<br />
	
	<!--  -->
	<form:label path="role">
		<spring:message code="curricula.professionalRecord.role" />
	</form:label>
	
	<form:input path="role" />
	<form:errors cssClass="error" path="role" />
	<br />
	
	<!--  -->
	<form:label path="attachment">
		<spring:message code="curricula.professionalRecord.attachment" />
	</form:label>
	
	<form:input path="attachment" />
	<form:errors cssClass="error" path="attachment" />
	<br />
	
	<!--  -->
	<form:label path="comments">
		<spring:message code="curricula.professionalRecord.comments" />
	</form:label>
	
	<form:textarea path="comments" />
	<form:errors cssClass="error" path="comments" />
	<br />
	
	<input type="submit" name="save" value="<spring:message code="curricula.save" />" />
				
	<input type="button" name="cancel"
		value="<spring:message code="curricula.cancel" />"
		onclick="javascript: window.location.replace('curricula/handyworker/show.do')" />
	<br />
	
	</security:authorize>

</form:form>