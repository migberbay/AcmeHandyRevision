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

	<!-- PARAMETERS FROM CONTROLLER: customerEndorsement: CustomerEndorsement, 
					 				 -->
									 

<form:form action="customerEndorsement/handyWorker/edit.do" modelAttribute="customerEndorsement">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:hidden path="moment" />
	<form:hidden path="handyWorker" />	
	<jstl:if test="${customerEndorsement.id != 0}">
		<form:hidden path="customer" />
	</jstl:if>		
	
	<security:authorize access="hasRole('HANDYWORKER')">
	
	<form:label path="text">
		<spring:message code="customerEndorsement.text" />:
	</form:label>
	<form:textarea path="text" />
	<form:errors cssClass="error" path="text" />
	<br />
	
	<jstl:if test="${customerEndorsement.id == 0}">
		<form:label path="customer">
			<spring:message code="endorsement.customer" />:
		</form:label>
		<form:select path="customer">
			<form:option label = "-----" value="0" />
			<form:options items="${customers}" itemLabel="userAccount.username" itemValue="id" />
		</form:select>
		<form:errors cssClass="error" path="customer" />
		<br />
	</jstl:if>
	
	<input type="submit" name="save" value="<spring:message code="customerEndorsement.save" />" />
			
	<input type="button" name="cancel"
		value="<spring:message code="customerEndorsement.cancel" />"
		onclick="javascript: window.location.replace('/Acme-Handy-Worker/customerEndorsement/handyWorker/list.do')" />
	<br />
	
	</security:authorize>

</form:form>