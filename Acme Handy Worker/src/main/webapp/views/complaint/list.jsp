<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('CUSTOMER')">

	<display:table name="${complaints}" id="row" pagesize="5"
		requestURI="complaint/customer/list.do">

		<security:authorize access="hasRole('CUSTOMER')">
			<display:column>
				<a href="complaint/customer/edit.do?complaintId=${row.id}"> <spring:message
						code="complaint.edit" />
				</a> <br/>

				<a href="complaint/show.do?complaintId=${row.id}"> <spring:message
						code="complaint.show" />
				</a>
			</display:column>

		</security:authorize>

		<display:column titleKey="complaint.ticker" property="ticker" />

		<display:column titleKey="complaint.description"
			property="description" />

		<spring:message code="complaint.moment.format" var="formatMoment" />
		<display:column titleKey="complaint.moment" property="moment"
			sortable="true" format="{0, date, ${formatMoment}}" />

		<display:column titleKey="complaint.customer">
			<a href="actor/show.do?actorId=${row.customer.id}">
				${row.customer.userAccount.username } </a>
		</display:column>

		<display:column titleKey="complaint.fixUpTask"
			property="fixUpTask.ticker" />

	</display:table>

</security:authorize>

<security:authorize access="hasRole('REFEREE')">


	<display:table name="complaints" id="row" pagesize="5"
		requestURI="complaint/referee/listNoReport.do">
	
		<display:column>
			<a href="complaint/show.do?complaintId=${row.id}"> <spring:message	code="complaint.show" />
			</a>
		</display:column>

		<display:column titleKey="complaint.ticker" property="ticker" />

		<display:column titleKey="complaint.description"
			property="description" />

		<spring:message code="complaint.moment.format" var="formatMoment" />
		<display:column titleKey="complaint.moment" property="moment"
			sortable="true" format="{0, date, ${formatMoment}}" />

		<display:column titleKey="complaint.fixUpTask"
			property="fixUpTask.ticker" />

	</display:table>

</security:authorize>

<security:authorize access="hasRole('HANDYWORKER')">
	<display:table name="complaints" id="row" pagesize="5"
		requestURI="complaint/handyWorker/list.do">
		
		<display:column>
			<a href="complaint/show.do?complaintId=${row.id}"> <spring:message	code="complaint.show" />
			</a>
		</display:column>

		<display:column titleKey="complaint.ticker" property="ticker" />

		<display:column titleKey="complaint.description"
			property="description" />

		<spring:message code="complaint.moment.format" var="formatMoment" />
		<display:column titleKey="complaint.moment" property="moment"
			sortable="true" format="{0, date, ${formatMoment}}" />

		<display:column titleKey="complaint.fixUpTask"
			property="fixUpTask.ticker" />

	</display:table>

</security:authorize>
