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

	<!-- PARAMETERS FROM CONTROLLER: fixUpTask: FixUpTask, task a editar
									 categories: Collection<Category>, colección de categorías
					 				 warranty: Warranty, garantía a añadir a la task 
					 				 -->
									 

<form:form action="sponsorship/sponsor/edit.do" modelAttribute="sponsorship">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:hidden path="sponsor" />

	<security:authorize access="hasRole('SPONSOR')">
	
	<form:label path="banner">
		<spring:message code="sponsorship.banner" />:
	</form:label>
	<form:input path="banner" />
	<form:errors cssClass="error" path="banner" />
	<br />
	
	<form:label path="link">
		<spring:message code="sponsorship.link" />:
	</form:label>
	<form:input path="link" />
	<form:errors cssClass="error" path="link" />
	<br />
	
	<h3><spring:message code="sponsorship.creditCard"/></h3>
	
	<form:label path="creditCard.holder">
		<spring:message code="sponsorship.creditCard.holder" />:
	</form:label>	
	<form:input path="creditCard.holder" placeholder="Jose Antonio Rodriguez"/>
	<form:errors cssClass="error" path="creditCard.holder" />
	<br />
		
	<form:label path="creditCard.brand">
		<spring:message code="sponsorship.creditCard.brand" />:
	</form:label>
	<form:input path="creditCard.brand" placeholder="VISA"/>
	<form:errors cssClass="error" path="creditCard.brand" />
	<br />
		
	<form:label path="creditCard.CVV">
		<spring:message code="sponsorship.creditCard.CVV" />:
	</form:label>	
	<form:input path="creditCard.CVV" placeholder="123"/>
	<form:errors cssClass="error" path="creditCard.CVV" />
	<br />
	
	<form:label path="creditCard.number">
		<spring:message code="sponsorship.creditCard.number" />:
	</form:label>
	<form:input path="creditCard.number" placeholder="8754729503472464"/>
	<form:errors cssClass="error" path="creditCard.number" />
	<br />
		
	<form:label path="creditCard.expirationDate">
		<spring:message code="sponsorship.creditCard.date" />:
	</form:label>
	<form:input path="creditCard.expirationDate" placeholder="MM/dd/yyyy HH:mm"/>
	<form:errors cssClass="error" path="creditCard.expirationDate" />
	<br />
	
	<form:label path="tutorial">
		<spring:message code="sponsorship.tutorial" />:
	</form:label>
	<form:select path="tutorial">
		<form:option label = "-----" value="0" />
		<form:options items="${tutorials}" itemLabel="title" itemValue="id" />
	</form:select>
	<form:errors cssClass="error" path="tutorial" />
	<br />
	
	
	
	<input type="submit" name="save" value="<spring:message code="sponsorship.save" />" />
					
	<input type="button" name="cancel"
		value="<spring:message code="sponsorship.cancel" />"
		onclick="javascript: window.location.replace('sponsorship/sponsor/list.do')" />
	<br />
	
	</security:authorize>

</form:form>