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

	<!-- PARAMETERS FROM CONTROLLER: handyWorkerEndorsement: HandyWorkerEndorsement, 
					 				 -->
									 

<form:form action="handyWorkerEndorsement/customer/edit.do" modelAttribute="handyWorkerEndorsement">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:hidden path="moment" />
	<form:hidden path="customer" />		
	<form:hidden path="handyWorker" />	
	
	<security:authorize access="hasRole('CUSTOMER')">
	
	<form:label path="text">
		<spring:message code="handyWorkerEndorsement.text" />:
	</form:label>
	<form:textarea path="text" />
	<form:errors cssClass="error" path="text" />
	<br />
	
	<input type="submit" name="save" value="<spring:message code="handyWorkerEndorsement.save" />" />
	
	<jstl:if test="${handyWorkerEndorsement.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="handyWorkerEndorsement.delete" />"
			onclick="return confirm('<spring:message code="handyWorkerEndorsement.confirm.delete" />')" />&nbsp;
	</jstl:if>
				
	<input type="button" name="cancel"
		value="<spring:message code="handyWorkerEndorsement.cancel" />"
		onclick="javascript: window.location.replace('')" />
	<br />
	
	</security:authorize>

</form:form>