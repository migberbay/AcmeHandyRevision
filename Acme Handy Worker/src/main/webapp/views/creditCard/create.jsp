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


<security:authorize access="hasRole('CUSTOMER')">

	<form:form action="creditCard/edit.do" modelAttribute="creditCard">
		
		<form:label path="holder">
		<spring:message code="application.creditCard.holder" />:
		</form:label>
		
		<form:input path="holder" placeholder="Santiago Segura"/>
		<form:errors cssClass="error" path="holder" />
		<br />
		
		<form:label path="brand">
			<spring:message code="application.creditCard.brand" />:
		</form:label>
		
		<form:input path="brand" placeholder="VISA"/>
		<form:errors cssClass="error" path="brand" />
		<br />
		
		<form:label path="CVV">
			<spring:message code="application.creditCard.cvv" />:
		</form:label>
		
		<form:input path="CVV" placeholder="123"/>
		<form:errors cssClass="error" path="CVV" />
		<br />
		
		<form:label path="number">
			<spring:message code="application.creditCard.number" />:
		</form:label>
		
		<form:input path="number" placeholder="8754729503472464"/>
		<form:errors cssClass="error" path="number" />
		<br />
		
		<form:label path="expirationDate">
			<spring:message code="application.creditCard.date" />:
		</form:label>
		
		<form:input path="expirationDate" placeholder="MM/dd/yyyy HH:mm"/>
		<form:errors cssClass="error" path="expirationDate" />
		<br />
		
		<input type="submit" name="save" value="<spring:message code="application.save" />" />
					
		<input type="submit" name="cancel" value="<spring:message code="application.cancel" />" /><br/>
		
	</form:form>
	
</security:authorize>