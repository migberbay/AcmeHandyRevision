<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- 
Recieves: 
Double avgFixUpPerUser, minFixUpPerUser, maxFixUpPerUser, stdvFixUpPerUser :
The average, the minimum, the maximum, and the standard deviation of the number of fix-up tasks per user.

Double avgApplicationsPerFixUp, minApplicationsPerFixUp, maxApplicationsPerFixUp, stdvApplicationsPerFixUp:
The average, the minimum, the maximum, and the standard deviation of the number of applications per fix-up task.

Double avgMaxPricePerFixUp, minMaxPricePerFixUp, maxMaxPricePerFixUp, stdvMaxPricePerFixUp:
The average, the minimum, the maximum, and the standard deviation of the maximum price of the fix-up tasks.

Double avgPriceOfferedApplication, minPriceOfferedApplication, maxPriceOfferedApplication, stdvPriceOfferedApplication:
The average, the minimum, the maximum, and the standard deviation of the price offered in the applications.

Double avgComplaintsPerFixUp, minComplaintsPerFixUp, maxComplaintsPerFixUp, stdvComplaintsPerFixUp:
The minimum, the maximum, the average, and the standard deviation of the number of complaints per fix-up task.

Double avgNotesPerReport, minNotesPerReport, maxNotesPerReport, NotesPerReport:
The minimum, the maximum, the average, and the standard deviation of the number of notes per referee report.

Double ratioPendingApplications - The ratio of pending applications.
Double ratioAcceptedApplications - The ratio of accepted applications.
Double ratioRejectedApplications - The ratio of rejected applications.
Double ratioOvertimeApplications - The ratio of pending applications that cannot change its status because their time period's elapsed.
Double ratioFixUpComplaint - The ratio of fix-up tasks with a complaint.
List<Customer> topThreeCustomerComplaints - The top-three customers in terms of complaints.
List<HandyWorker> topThreeHandyWorkersComplaints - The top-three handy workers in terms of complaints.

List<Customer> customerPublishers10 - The listing of customers who have published at least 10% more fix-up tasks than the average, ordered by number of applications.
List<HandyWorker> handyWorkersPublishers10 - The listing of handy workers who have got accepted at least 10% more ap-plications than the average, ordered by number of applications.
	
-->

<security:authorize access="hasRole('ADMIN')">
	<spring:message code="admin.datatable"/>
	<table style="width:'100%' border='0' align='center' ">
			<tr>
				<th><spring:message code="admin.type"/></th>
				<th><spring:message code="admin.fixUpPerUser"/></th>
				<th><spring:message code="admin.applicationsPerFixUp"/></th>
				<th><spring:message code="admin.maximumPriceFixUp"/></th>
				<th><spring:message code="admin.pricePerApplication"/></th>
				<th><spring:message code="admin.complaintsPerFixUp"/></th>
				<th><spring:message code="admin.notesPerReferee"/></th>
			</tr>
			<tr>
				<td><spring:message code="admin.average"/></td>
				<td><jstl:out value="${avgFixUpPerUser}"/></td>
				<td><jstl:out value="${avgApplicationsPerFixUp}"/></td>
				<td><jstl:out value="${avgMaxPricePerFixUp}"/></td>
				<td><jstl:out value="${avgPriceOfferedApplication}"/></td>
				<td><jstl:out value="${avgComplaintsPerFixUp}"/></td>
				<td><jstl:out value="${avgNotesPerReport}"/></td>
			</tr>
			<tr>
				<td><spring:message code="admin.minimum"/></td>
				<td><jstl:out value="${minFixUpPerUser}"/></td>
				<td><jstl:out value="${minApplicationsPerFixUp}"/></td>
				<td><jstl:out value="${minMaxPricePerFixUp}"/></td>
				<td><jstl:out value="${minPriceOfferedApplication}"/></td>
				<td><jstl:out value="${minComplaintsPerFixUp}"/></td>
				<td><jstl:out value="${minNotesPerReport}"/></td>
			</tr>	
			<tr>
				<td><spring:message code="admin.maximum"/></td>
				<td><jstl:out value="${maxFixUpPerUser}"/></td>
				<td><jstl:out value="${maxApplicationsPerFixUp}"/></td>
				<td><jstl:out value="${maxMaxPricePerFixUp}"/></td>
				<td><jstl:out value="${maxPriceOfferedApplication}"/></td>
				<td><jstl:out value="${maxComplaintsPerFixUp}"/></td>
				<td><jstl:out value="${maxNotesPerReport}"/></td>
			</tr>
			<tr>
				<td><spring:message code="admin.stdv"/></td>
				<td><jstl:out value="${stdvFixUpPerUser}"/></td>
				<td><jstl:out value="${stdvApplicationsPerFixUp}"/></td>
				<td><jstl:out value="${stdvMaxPricePerFixUp}"/></td>
				<td><jstl:out value="${stdvPriceOfferedApplication}"/></td>
				<td><jstl:out value="${stdvComplaintsPerFixUp}"/></td>
				<td><jstl:out value="${stdvNotesPerReport}"/></td>
			</tr>
	</table>
	
	
	<table style="width:'100%' border='0' align='center' ">
		<tr>
			<th><spring:message code="admin.type"/></th>
			<th><spring:message code="admin.ratio"/></th>
		</tr>
		<tr>
			<th><spring:message code="admin.rPendingApp"/></th>
			<th><jstl:out value="${ratioPendingApplications}"/></th>
		</tr>
		<tr>
			<th><spring:message code="admin.rAcceptedApp"/></th>
			<th><jstl:out value="${ratioAcceptedApplications}"/></th>
		</tr>
		<tr>
			<th><spring:message code="admin.rRejectedApp"/></th>
			<th><jstl:out value="${ratioRejectedApplications}"/></th>
		</tr>
		<tr>
			<th><spring:message code="admin.rOvertimedApp"/></th>
			<th><jstl:out value="${ratioOvertimeApplications}"/></th>
		</tr>
		<tr>
			<th><spring:message code="admin.rWithComplaint"/></th>
			<th><jstl:out value="${ratioFixUpComplaint}"/></th>
		</tr>
		 <tr>
			<th><spring:message code="admin.topThreeCustomerComplaint"/></th>
			<th>
			<jstl:forEach var="i" items="${topThreeCustomerComplaints}">
			<jstl:out value="${i.name} "/><jstl:out value="${i.surname}"/><br>
			</jstl:forEach>
			</th>
		</tr>
		<tr>
			<th><spring:message code="admin.topThreeHandyWorkerComplaint"/></th>
			<th>
			<jstl:forEach var="i" items="${topThreeHandyWorkersComplaints}">
			<jstl:out value="${i.name} "/><jstl:out value="${i.surname} "/><br>
			</jstl:forEach>
			</th>
		</tr> 
	</table>
	
	<spring:message code="admin.topPublishers"/>
	- <spring:message code="admin.customers"/>
	<table style="width:'100%' border='0' align='center' ">
		<tr>
			<th><spring:message code="admin.customerWhoPublish10"/></th>		
		</tr>
		<jstl:forEach var="i" items="${customerPublishers10}">
		<tr>
			<td><jstl:out value="${i.name}"/> <jstl:out value="${i.surname}"/> </td>
		</tr>			
		</jstl:forEach>
	</table>
	<br>
	 - <spring:message code="admin.handyWorkers"/>
	<table style="width:'100%' border='0' align='center' ">
		<tr>
			<th><spring:message code="admin.handyWorkersWhoPublish10"/></th>		
		</tr>
		<jstl:forEach var="i" items="${handyWorkerPublishers10}">
		<tr>
			<td><jstl:out value="${i.name}"/> <jstl:out value="${i.surname}"/> </td>
		</tr>			
		</jstl:forEach>
	</table>


</security:authorize>