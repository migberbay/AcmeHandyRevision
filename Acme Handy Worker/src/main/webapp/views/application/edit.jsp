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

<security:authorize access="hasRole('HANDYWORKER')">

	<form:form action="handyWorker/application/edit.do" modelAttribute="application">
	
		<form:hidden path="id" />
		<form:hidden path="version" />
		
		<form:hidden path="moment" />
		<form:hidden path="status" />
		<form:hidden path="fixUpTask" />
		<form:hidden path="handyWorker" />	
		
		
			
			<form:label path="price">
				<spring:message code="application.price" />:
			</form:label>	
			<form:input path="price" />
			<form:errors cssClass="error" path="price" />
			<br />
			
			<form:label path="handyWorkerComment">
				<spring:message code="application.comment" />:
			</form:label>		
			<form:input path="handyWorkerComment" />
			<form:errors cssClass="error" path="handyWorkerComment" />
			<br />
			
			<input type="submit" name="save" value="<spring:message code="application.save" />" />
						
			<input type="button" name="cancel"
				value="<spring:message code="application.cancel" />"
				onclick="javascript: window.location.replace('')" />
			<br />
		
		
	
	</form:form>
	
</security:authorize>

<security:authorize access="hasRole('CUSTOMER')">

	<form:form action="customer/application/edit.do" modelAttribute="application">
	
		<form:hidden path="id" />
		<form:hidden path="version" />
		
		<form:hidden path="handyWorkerComment" />
		<form:hidden path="handyWorker" />
		<form:hidden path="moment" />
		<form:hidden path="price" />
		<form:hidden path="fixUpTask" />
	
			
		<form:label path="status">
			<spring:message code="application.status" />:
		</form:label>
		
 		<form:select id="status" path="status">
			<form:option label="----" value="0"/>
			<form:option label="ACCEPTED" value="ACCEPTED"/>
			<form:option label="REJECTED" value="REJECTED"/>
		</form:select>  
		<form:errors cssClass="error" path="status" />
		<br />

		
		<input type="submit" name="save" value="<spring:message code="application.next" />" />
					
		<input type="button" name="cancel"
			value="<spring:message code="application.cancel" />"
			onclick="javascript: window.location.replace('customer/application/list.do')" />
		<br />
		
	</form:form>
	
</security:authorize>
