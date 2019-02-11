<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

	<!-- PARAMETERS FROM CONTROLLER: handyWorkerEndorsement: HandyWorkerEndorsement,
									 moment: Date,
					 				 text: String,
					 				 handyWorker: HandyWorker
					 				 customer: Customer
									 					 -->
									 					 
		<display:table name="handyWorkerEndorsement" id="row" requestURI="handyWorkerEndorsement/show.do">
			<display:column>
				<spring:message code="handyWorkerEndorsement.moment.format" var="momentFormat"/>
				<b><spring:message code="handyWorkerEndorsement.moment"/>: </b> <fmt:formatDate value="${handyWorkerEndorsement.moment}" pattern="${momentFormat}" /> <br/>
				<b><spring:message code="handyWorkerEndorsement.text"/>: </b> <jstl:out value="${handyWorkerEndorsement.text}"/><br/>
				<b><spring:message code="handyWorkerEndorsement.publisher"/>: </b> <a href="actor/profile.do?actorId=${handyWorkerEndorsement.customer.id}">
				<jstl:out value="${row.customer.userAccount.username}"/></a>
				<b><spring:message code="handyWorkerEndorsement.referedTo"/>: </b> <a href="actor/profile.do?actorId=${handyWorkerEndorsement.handyWorker.id}">
					<jstl:out value="${row.handyWorker.userAccount.username}"/></a>
			</display:column>		
		</display:table>

		
	<input type="button" name="back"
		value="<spring:message code="handyWorkerEndorsement.show.back" />"
		onclick="javascript: window.location.replace('')" />
	<br />
		
				
		
	