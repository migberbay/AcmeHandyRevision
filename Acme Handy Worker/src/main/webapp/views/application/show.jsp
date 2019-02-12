<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('HANDYWORKER')">
					 
	<display:table name="application" id="row" requestURI="handyWorker/application/list.do">
			<display:column>
				<spring:message code="application.moment.format" var="momentFormat"/>
				<b><spring:message code="application.moment"/>: </b> <fmt:formatDate value="${application.moment}" pattern="${momentFormat}" /> <br/>
				<b><spring:message code="application.price"/>: </b> <jstl:out value="${application.price}"/><br/>
				<b><spring:message code="application.status"/>: </b> <jstl:out value="${application.status}"/><br/>
				<b><spring:message code="application.hwcomment"/>: </b> <jstl:out value="${application.handyWorkerComment}"/><br/>
				<b><spring:message code="application.ccomment"/>: </b> <jstl:out value="${application.customerComment}"/><br/>
			</display:column>
			<display:column>
				<b>Ticker: </b> <jstl:out value="${application.fixUpTask.ticker}"/><br/>
				<b><spring:message code="application.fixUpTask.description"/>: </b> <jstl:out value="${application.fixUpTask.description}"/><br/>
				<b><spring:message code="application.handyWorker.name"/>: </b> <jstl:out value="${application.handyWorker.name}"/><br/>
				<b><spring:message code="application.handyWorker.middleName"/>: </b> <jstl:out value="${application.handyWorker.middleName}"/><br/>
				<b><spring:message code="application.handyWorker.surname"/>: </b> <jstl:out value="${application.handyWorker.surname}"/><br/>
			</display:column>		
		</display:table>
		
	<input type="button" name="back"
		value="<spring:message code="application.back" />"
		onclick="javascript: window.location.replace('handyWorker/application/list.do')" />
	<br />
		
</security:authorize>			
