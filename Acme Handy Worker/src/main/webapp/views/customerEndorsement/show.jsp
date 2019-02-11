<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

	<!-- PARAMETERS FROM CONTROLLER: customerEndorsement: CustomerEndorsement,
									 moment: Date,
					 				 text: String,
					 				 handyWorker: HandyWorker
					 				 customer: Customer
									 					 -->
	<fieldset>				 					 
		<spring:message code="customerEndorsement.moment.format" var="momentFormat"/>
		<b><spring:message code="customerEndorsement.moment"/>: </b> <fmt:formatDate value="${customerEndorsement.moment}" pattern="${momentFormat}" /> <br/>
		<b><spring:message code="customerEndorsement.text"/>: </b> <jstl:out value="${customerEndorsement.text}"/><br/>
		<b><spring:message code="customerEndorsement.publisher"/>: </b> <a href="actor/show.do?actorId=${customerEndorsement.handyWorker.id}">
		<jstl:out value="${customerEndorsement.handyWorker.userAccount.username}"/></a><br/>
		<b><spring:message code="customerEndorsement.referedTo"/>: </b> <a href="actor/show.do?actorId=${customerEndorsement.customer.id}">
		<jstl:out value="${customerEndorsement.customer.userAccount.username}"/></a>
	</fieldset>

		
	<input type="button" name="back"
		value="<spring:message code="customerEndorsement.show.back" />"
		onclick="javascript: window.location.replace('/Acme-Handy-Worker/customerEndorsement/handyWorker/list.do')" />
	<br />
		
				
		
	