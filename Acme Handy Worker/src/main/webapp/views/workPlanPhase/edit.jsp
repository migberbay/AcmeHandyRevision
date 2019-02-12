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

	<!-- PARAMETERS FROM CONTROLLER: workPlanPhase: WorkPlanPhase, task a editar
					 				 -->
									 

	<security:authorize access="hasRole('HANDYWORKER')">

<form:form action="workPlanPhase/handyWorker/edit.do" modelAttribute="workPlanPhase">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:hidden path="fixUpTask" />
	<form:hidden path="handyWorker" />
	
	<form:label path="title">
		<spring:message code="workplan.title" />:
	</form:label>
	<form:textarea path="title" />
	<form:errors cssClass="error" path="title" />
	<br />

	<form:label path="description">
		<spring:message code="workplan.description" />:
	</form:label>
	<form:textarea path="description" />
	<form:errors cssClass="error" path="description" />
	<br />
	
	<form:label path="startMoment">
		<spring:message code="workplan.startMoment" />:
	</form:label>
	<form:input path="startMoment" placeholder="01/01/2001 12:00"/>
	<form:errors cssClass="error" path="startMoment" />
	<br />
	
	<form:label path="endMoment">
		<spring:message code="workplan.endMoment" />:
	</form:label>
	<form:input path="endMoment" placeholder="01/01/2001 12:00"/>
	<form:errors cssClass="error" path="endMoment" />
	<br />

	<input type="submit" name="save" value="<spring:message code="workplan.save" />" />
	
	<jstl:if test="${workPlanPhase.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="task.delete" />"
			onclick="return confirm('<spring:message code="workplan.confirm.delete" />')" />&nbsp;
	</jstl:if>
				
	<input type="button" name="cancel"
		value="<spring:message code="workplan.cancel" />"
		onclick="javascript: window.location.replace('')" />
	<br />
	
</form:form>
	</security:authorize>
