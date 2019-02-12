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

	<!-- PARAMETERS FROM CONTROLLER: report: Report, report a editar
					 				 -->
									 

<security:authorize access="hasRole('REFEREE')">

<form:form action="report/referee/edit.do" modelAttribute="report">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:hidden path="moment"/>
	<form:hidden path="referee" />
	<form:hidden path="complaint" />
	<form:hidden path="notes" />


	<form:label path="description">
		<spring:message code="report.description" />:
	</form:label>
	<form:textarea path="description" />
	<form:errors cssClass="error" path="description" />
	<br />
	
	<form:label path="attachments">
		<spring:message code="report.attachments" />:
	</form:label>
	<form:textarea path="attachments" />
	<form:errors cssClass="error" path="attachments" />
	<br />
	
	<form:label path="isDraft">
		<spring:message code="report.isDraft" />:
	</form:label>
	<form:select id="isDraft" path="isDraft">
		<form:option label="Yes" value="True"/>
		<form:option label="No" value="False"/>
	</form:select>  
	<form:errors cssClass="error" path="isDraft" />
	<br />


	<input type="submit" name="save" value="<spring:message code="report.save" />" />
	
	<jstl:if test="${report.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="report.delete" />"
			onclick="return confirm('<spring:message code="report.confirm.delete" />')" />&nbsp;
	</jstl:if>
				
	<input type="button" name="cancel"
		value="<spring:message code="report.cancel" />"
		onclick="javascript: window.location.replace('report/show.do?reportId=${note.report.id}')" />
	<br />
	
</form:form>
	</security:authorize>
