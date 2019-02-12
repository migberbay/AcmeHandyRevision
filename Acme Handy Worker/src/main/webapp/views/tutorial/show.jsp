<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="tutorial" id="row" requestURI="tutorial/handyWorker/show.do">
	
	<display:column>
		<b><spring:message code="tutorial.title" />: </b>
		<jstl:out value="${tutorial.title}" /> <br/>

		<b><spring:message code="tutorial.summary" />: </b>
		<jstl:out value="${tutorial.summary}" /> <br/>

		<b><spring:message code="tutorial.moment" />: </b>
		<jstl:out value="${tutorial.moment}" /> <br/>

		<b><spring:message code="tutorial.pictures" />: </b>
		<jstl:out value="${tutorial.pictures}" /> <br/>

		<b><spring:message code="tutorial.handyWorker" />: </b>
		<a href="actor/show.do?actorId=${row.handyWorker.id}">
			${row.handyWorker.userAccount.username } </a>
	</display:column>
	
</display:table>

<security:authorize access="hasRole('HANDYWORKER')">
	<a href="handyWorker/section/create.do?tutorialId=${row.id}"><spring:message code="tutorial.section.add" /></a>
</security:authorize>

<security:authorize access="hasRole('HANDYWORKER')">

	<display:table name="sections" id="row" requestURI="tutorial/handyWorker/show.do">
	
		<display:column>
	
			<b><spring:message code="tutorial.title" />: </b>
			<jstl:out value="${row.title}" /> <br/>
	
			<b><spring:message code="tutorial.section.text" />: </b>
			<jstl:out value="${row.text}" /> <br/>
	
			<b><spring:message code="tutorial.pictures" />: </b>
			<jstl:out value="${row.pictures}" /> <br/>
	
		</display:column>
		
		<display:column>
			
			<a href="handyWorker/section/edit.do?sectionId=${row.id}"><spring:message code="tutorial.edit" /></a> <br/>
			<a href="handyWorker/section/delete.do?sectionId=${row.id}"><spring:message code="tutorial.delete" /></a>
			
		</display:column>
	
	</display:table>

</security:authorize>

<a href="${sponsorship.link}"><img src="${sponsorship.banner}" height="100" width="1000"/></a>


<security:authorize access="hasRole('HANDYWORKER')">

	<input type="button" name="back"
		value="<spring:message code="application.back" />"
		onclick="javascript: window.location.replace('tutorial/handyWorker/list.do')" />
	<br />
	
</security:authorize>

<security:authorize access="isAnonymous()">

	<input type="button" name="back"
		value="<spring:message code="application.back" />"
		onclick="javascript: window.location.replace('tutorial/list.do')" />
	<br />
	
</security:authorize>

