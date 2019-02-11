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


<jstl:if test="${report.isDraft}">
	<h3 class="draft-report-text"><spring:message code="report.draft"/></h3>
</jstl:if>
<spring:message code="report.moment.format" var="momentFormat"/>
<b><spring:message code="report.moment" />: </b>
<fmt:formatDate value="${report.moment}" pattern="${momentFormat}" /> <br/>


<p><jstl:out value="${report.description}" /></p>
<jstl:forEach var="attachment" items="${report.attachments}">
	<ul>
		<li><a href="<jstl:out value="${attachment}" />"><jstl:out value="${attachment}" /></a></li>
	</ul>
</jstl:forEach>


<display:table name="report.notes" id="row" requestURI="report/show.jsp"  pagesize="5">
	<display:column titleKey="report.note.options">
		<security:authorize access="hasRole('REFEREE')">
			<jstl:if test="${row.refereeComment == null || empty row.refereeComment}">
				<a href="report/note/edit.do?noteId=${row.id}">
					<spring:message	code="note.comment" />
				</a>
			</jstl:if>
		</security:authorize>

		<security:authorize access="hasRole('CUSTOMER')">
			<jstl:if test="${row.customerComment == null || empty row.customerComment}">
				<a href="report/note/edit.do?noteId=${row.id}">
					<spring:message	code="note.comment" />
				</a>
			</jstl:if>
		</security:authorize>

		<security:authorize access="hasRole('HANDYWORKER')">
			<jstl:if test="${row.handyWorkerComment == null || empty row.handyWorkerComment}">
				<a href="report/note/edit.do?noteId=${row.id}">
					<spring:message	code="note.comment" />
				</a>
			</jstl:if>
		</security:authorize>

	</display:column>
	<display:column titleKey="report.note.moment">
		<fmt:formatDate value="${report.moment}" pattern="${momentFormat}" />
	</display:column>
	<display:column property="refereeComment" titleKey="report.note.refereeComment"/>
	<display:column property="customerComment" titleKey="report.note.customerComment"/>
	<display:column property="handyWorkerComment" titleKey="report.note.handyWorkerComment"/>
</display:table>
<br/>
<a href="report/note/create.do?reportId=${report.id}">
	<spring:message	code="note.create" />
</a>
