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

	<!-- PARAMETERS FROM CONTROLLER: Note
					 				 -->
<jstl:if test="${savedNote != null}">
<security:authorize access="hasRole('REFEREE')">

    <div>
        <h3><spring:message code="report.note.customerComment" /></h3>
        <p><jstl:out value="${note.customerComment}"/></p>
    </div>
    <div>
        <h3><spring:message code="report.note.handyWorkerComment" /></h3>
        <p><jstl:out value="${note.handyWorkerComment}"/></p>
    </div>

</security:authorize>

<security:authorize access="hasRole('CUSTOMER')">
    <div>
        <h3><spring:message code="report.note.refereeComment" /></h3>
        <p><jstl:out value="${note.refereeComment}"/></p>
    </div>
    <div>
        <h3><spring:message code="report.note.handyWorkerComment" /></h3>
        <p><jstl:out value="${note.handyWorkerComment}"/></p>
    </div>

</security:authorize>

<security:authorize access="hasRole('HANDYWORKER')">
    <div>
        <h3><spring:message code="report.note.customerComment" /></h3>
        <p><jstl:out value="${note.customerComment}"/></p>
    </div>

    <div>
        <h3><spring:message code="report.note.refereeComment" /></h3>
        <p><jstl:out value="${note.refereeComment}"/></p>
    </div>

</security:authorize>
</jstl:if>

<div>
<form:form action="report/note/edit.do" modelAttribute="note">
	<form:hidden path="id" />
	<form:hidden path="version" />
    <form:hidden path="moment" />

    <form:hidden path="report" />

	<security:authorize access="hasRole('REFEREE')">

			<form:label path="refereeComment">
				<spring:message code="report.note.refereeComment" />
			</form:label>
        <br/>
			<form:textarea path="refereeComment" />

	</security:authorize>

	<security:authorize access="hasRole('CUSTOMER')">

			<form:label path="customerComment">
				<spring:message code="report.note.customerComment" />
			</form:label>
        <br/>
			<form:textarea path="customerComment" />

	</security:authorize>

	<security:authorize access="hasRole('HANDYWORKER')">
		<form:label path="handyWorkerComment">
			<spring:message code="report.note.handyWorkerComment" />
		</form:label>
        <br/>
		<form:textarea path="handyWorkerComment" />
	</security:authorize>

    <br/>
    <input type="submit" name="save" value="<spring:message code="note.save" />" />

    <input type="button" name="cancel"
           value="<spring:message code="report.cancel" />"
           onclick="javascript: window.location.replace('report/show.do?reportId=${note.report.id}')" />
</form:form>
</div>
