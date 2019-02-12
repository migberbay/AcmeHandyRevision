<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

		<b><spring:message code="complaint.ticker" />: </b>
		<jstl:out value="${complaint.ticker}" /> <br/>

		<b><spring:message code="complaint.description" />: </b>
		<jstl:out value="${complaint.description}" /> <br/>

		<b><spring:message code="complaint.attachments" />: </b>
		<jstl:out value="${complaint.attachments}" /> <br/>

        <spring:message code="complaint.moment.format" var="momentFormat"/>
		<b><spring:message code="complaint.moment" />: </b>
		<fmt:formatDate value="${complaint.moment}" pattern="${momentFormat}" /> <br/>

		<b><spring:message code="complaint.fixUpTask" />: </b>
		<jstl:out value="${complaint.fixUpTask.ticker}" />


<h3><spring:message code="complaint.reports"/></h3>
		<display:table name="reports" id="row" requestURI="complaint/show.do" pagesize="5">
			<display:column titleKey="report.options">
				<a href="report/show.do?reportId=${row.id}"><spring:message	code="complaint.report.show" /></a>
				<br/>
				<jstl:if test="${row.isDraft}">
					<security:authorize access="hasRole('REFEREE')">

							<a href="report/referee/edit.do?reportId=${row.id}">
								<spring:message	code="complaint.edit" />
							</a><br/>	
					
							<a href="report/referee/delete.do?reportId=${row.id}">
								<spring:message	code="complaint.delete" />
							</a><br/>
					</security:authorize>
				</jstl:if>

			</display:column>

			<jstl:set var="ref" value="${row.referee}"/>
			<display:column property="moment" titleKey="complaint.moment" />
						
			<display:column property="description" titleKey="complaint.description" />
			<display:column titleKey="complaint.attachments">
				<jstl:forEach var="x" items="${row.attachments}">
					<a href="${x}"><jstl:out value="${x }"/></a><br/>
				</jstl:forEach>
			</display:column>

			<jstl:if test="${row.referee !=null}">
			<display:column titleKey="complaint.referee">
				<a href="actor/show.do?actorId=${row.referee.id}"><jstl:out value="${row.referee.userAccount.username}"/></a>
			</display:column>
			</jstl:if>
		</display:table>
		
		<jstl:if test="${referee == ref}"> 
			<a href="report/referee/create.do?complaintId=${complaint.id}">
								<spring:message	code="complaint.report.create" />
			</a><br/>
		</jstl:if>

