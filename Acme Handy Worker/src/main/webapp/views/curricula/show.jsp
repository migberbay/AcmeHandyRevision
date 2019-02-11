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
	
	Curricula curricula, si tiene una / null si no la tiene (se le ofrece la posibilidad de crearla).
	
-->

<security:authorize access="hasRole('HANDYWORKER')">
	<jstl:if test="${curricula == null}">
		<spring:message code="curricula.notCreated"/>
		<h4><a href="curricula/handyworker/createPersonalRecord.do"><spring:message code="curricula.create" /></a></h4>
	</jstl:if>
	<jstl:if test="${curricula != null}">
		<h2>Ticker:${curricula.ticker}</h2>
		<h4><a href="curricula/handyworker/deleteCurricula.do?curriculaId=${curricula.id}"><spring:message code="curricula.deleteCurricula"/></a></h4>
		<h3><spring:message code="curricula.personalRecord"/></h3>
		<h4><a href="curricula/handyworker/editPersonalRecord.do?personalRecordId=${curricula.personalRecord.id}"><spring:message code="curricula.editPersonalRecord"/></a></h4>
		<spring:message code="curricula.fullName"/>: <jstl:out value="${curricula.personalRecord.fullName }"/><br>
		<spring:message code="curricula.photo"/>: <jstl:out value="${curricula.personalRecord.photo }"/><br>
		<spring:message code="curricula.email"/>: <jstl:out value="${curricula.personalRecord.email }"/><br>
		<spring:message code="curricula.phone"/>: <jstl:out value="${curricula.personalRecord.phone }"/><br>
		<spring:message code="curricula.linkedInProfile"/>: <jstl:out value="${curricula.personalRecord.linkedInUrl}"/><br>
		<hr>
		
		<h3><spring:message code="curricula.endorserRecords"/></h3>
		<h4><a href="curricula/handyworker/createEndorserRecord.do"><spring:message code="curricula.createEndorserRecord"/></a></h4><hr>
		<jstl:forEach var="i" items="${curricula.endorserRecords }">
		<h4><a href="curricula/handyworker/editEndorserRecord.do?endorserRecordId=${i.id}"><spring:message code="curricula.editEndorserRecord"/></a></h4>
		<h4><a href="curricula/handyworker/deleteEndorserRecord.do?endorserRecordId=${i.id}"><spring:message code="curricula.deleteEndorserRecord"/></a></h4>
		<spring:message code="curricula.endorserName"/>: <jstl:out value="${i.endorserName }"/><br>
		<spring:message code="curricula.email"/>: <jstl:out value="${i.email }"/><br>
		<spring:message code="curricula.phone"/>: <jstl:out value="${i.phone }"/><br>
		<spring:message code="curricula.linkedInProfile"/>: <jstl:out value="${i.linkedInProfile }"/><br>
		<spring:message code="curricula.comments"/>: <jstl:out value="${i.comments }"/><br>
		<hr>
		</jstl:forEach>
		
		
		<h3><spring:message code="curricula.educationRecords"/></h3>
		<h4><a href="curricula/handyworker/createEducationRecord.do"><spring:message code="curricula.createEducationRecord"/></a></h4><hr>
		<jstl:forEach var="i" items="${curricula.educationRecords }">
		<h4><a href="curricula/handyworker/editEducationRecord.do?educationRecordId=${i.id}"><spring:message code="curricula.editEducationRecord"/></a></h4>
		<h4><a href="curricula/handyworker/deleteEducationRecord.do?educationRecordId=${i.id}"><spring:message code="curricula.deleteEducationRecord"/></a></h4>
		<spring:message code="curricula.diplomaTitle"/>: <jstl:out value="${i.diplomaTitle }"/><br>
		<spring:message code="curricula.startDate"/>: <jstl:out value="${i.startDate }"/><br>
		<spring:message code="curricula.endDate"/>: <jstl:out value="${i.endDate }"/><br>
		<spring:message code="curricula.institution"/>: <jstl:out value="${i.institution }"/><br>
		<spring:message code="curricula.attachment"/>: <jstl:out value="${i.attachment }"/><br>
		<spring:message code="curricula.comments"/>: <jstl:out value="${i.comments }"/><br>
		<hr>
		</jstl:forEach>
		
		<h3><spring:message code="curricula.professionalRecords"/></h3>
		<h4><a href="curricula/handyworker/createProfessionalRecord.do"><spring:message code="curricula.createProfessionalRecord"/></a></h4><hr>
		<jstl:forEach var="i" items="${curricula.professionalRecords }">
		<h4><a href="curricula/handyworker/editProfessionalRecord.do?professionalRecordId=${i.id}"><spring:message code="curricula.editProfessionalRecord"/></a></h4>
		<h4><a href="curricula/handyworker/deleteProfessionalRecord.do?prosfessionalRecordId=${i.id}"><spring:message code="curricula.deleteProfessionalRecord"/></a></h4>
		<spring:message code="curricula.companyName"/>: <jstl:out value="${i.companyName}"/><br>
		<spring:message code="curricula.startDate"/>: <jstl:out value="${i.startDate}"/><br>
		<spring:message code="curricula.endDate"/>: <jstl:out value="${i.endDate }"/><br>
		<spring:message code="curricula.role"/>: <jstl:out value="${i.role }"/><br>
		<spring:message code="curricula.attachment"/>: <jstl:out value="${i.attachment }"/><br>
		<spring:message code="curricula.comments"/>: <jstl:out value="${i.comments }"/><br>
		<hr>
		</jstl:forEach>
		
		<h3><spring:message code="curricula.miscellaneousRecords"/></h3>
		<h4><a href="curricula/handyworker/createMiscellaneousRecord.do"><spring:message code="curricula.createMiscellaneousRecord"/></a></h4>
		<jstl:forEach var="i" items="${curricula.miscellaneousRecords }">
		<h4><a href="curricula/handyworker/editMiscellaneousRecord.do?miscellaneousRecordId=${i.id}"><spring:message code="curricula.editMiscellaneousRecord"/></a></h4>
		<h4><a href="curricula/handyworker/deleteMiscellaneousRecord.do?miscellaneousRecordId=${i.id}"><spring:message code="curricula.deleteMiscellaneousRecord"/></a></h4>
		<spring:message code="curricula.title"/>: <jstl:out value="${i.title }"/><br>
		<spring:message code="curricula.attachment"/>: <jstl:out value="${i.attachment }"/><br>
		<spring:message code="curricula.comments"/>: <jstl:out value="${i.comments }"/><br>
		<hr>
		</jstl:forEach>
		
	</jstl:if>
	
</security:authorize>