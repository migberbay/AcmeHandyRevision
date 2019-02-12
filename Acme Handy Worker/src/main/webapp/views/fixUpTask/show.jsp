<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

	<!-- PARAMETERS FROM CONTROLLER: fixUpTask: FixUpTask, task a mostrar
									 moment: Date, momento de creación de la task, bien formateada
					 				 startMoment: Date, momento de comienzo de la task, bien formateada
									 endMoment: Date, momento de finalización de la task, bien formateada
									 complaints: Collection<Complaint>, lista de complaints sobre la task correspondiente -->
									 
	<!-- PARAMETERS CREATED IN VIEW: customerName: String, unión del name,middleName y surname del customer que publica la task -->
					 
				<div style="float:left;width:250px;">
					<spring:message code="task.moment.format" var="momentFormat"/>
					<b>Ticker:</b> <jstl:out value="${fixUpTask.ticker}"/><br/>
					<b><spring:message code="task.description"/>: </b> <jstl:out value="${fixUpTask.description}"/><br/>
					<b><spring:message code="task.address"/>: </b> <jstl:out value="${fixUpTask.address}"/><br/>
					<b><spring:message code="task.startMoment"/>: </b> <fmt:formatDate value="${fixUpTask.startMoment}" pattern="${momentFormat}" /> <br/>
					<b><spring:message code="task.endMoment"/>: </b> <fmt:formatDate value="${fixUpTask.endMoment}" pattern="${momentFormat}" /> <br/>
				</div>
				<div  style="margin:0 auto;width:250px;">
					<b><spring:message code="task.category"/>: </b> <jstl:out value="${fixUpTask.category.name}"/><br/>
					<b><spring:message code="task.moment"/>: </b> <fmt:formatDate value="${fixUpTask.moment}" pattern="${momentFormat}" /> <br/>
					<b><spring:message code="task.maxPrice"/>: </b> <jstl:out value="${fixUpTask.maxPrice}"/><br/>
					<b><spring:message code="task.publisher"/>: </b> <a href="actor/show.do?actorId=${fixUpTask.customer.id}"><jstl:out value="${fullName}"/></a>
				</div>		
				<br/>
				
				<h3><spring:message code="task.warranty"/>:</h3>
				<div style="float:left;width:250px;">
					<b><spring:message code="task.warranty.title"/>: </b> <jstl:out value="${fixUpTask.warranty.title}"/><br/>
					<b><spring:message code="task.warranty.terms"/>: </b><br/> <jstl:out value="${fixUpTask.warranty.terms}"/>
				</div>
				<div  style="margin:0 auto;width:250px;">
					<b><spring:message code="task.warranty.laws"/>:</b> <jstl:forEach var="law" items="${fixUpTask.warranty.laws}"> <jstl:out value="${law}"/><br/></jstl:forEach>
				</div>		
				<br/>
		<h3><spring:message code="task.workplan"/>:</h3>
		<display:table name="workPlanPhases" id="row" requestURI="fixUpTask/show.do" pagesize="5">
		<jstl:if test="${hw}">
			<display:column titleKey="task.options">
				<a href="workPlanPhase/handyWorker/edit.do?workPlanPhaseId=${row.id}">
					<spring:message	code="task.edit" />
				</a><br/>	
				
				<a href="workPlanPhase/handyWorker/delete.do?workPlanPhaseId=${row.id}">
					<spring:message	code="task.delete" />
				</a><br/>			
				</display:column>
			</jstl:if>
			<display:column property="title" titleKey="task.workplan.title" />
			
			<display:column property="description" titleKey="task.description" />
			
			<spring:message code="task.moment.format" var="formatMoment"/>
			<display:column property="startMoment" titleKey="task.startMoment" format="{0,date,${formatMoment} }"/>
			
			<display:column property="endMoment" titleKey="task.endMoment" format="{0,date,${formatMoment} }"/>

			<display:column titleKey="task.workplan.handyWorker">
				<a href="actor/show.do?actorId=${row.handyWorker.id}"><jstl:out value="${row.handyWorker.userAccount.username}"/></a>
			</display:column>
		</display:table>
		<jstl:if test="${app}">
			<a href="workPlanPhase/handyWorker/create.do?fixUpTaskId=${fixUpTask.id}"><spring:message code="task.workPlanPhase.create"/></a><br/>
		</jstl:if>

		<h3><spring:message code="task.complaints"/>:</h3>
		<display:table name="complaints" id="row" requestURI="fixUpTask/show.do" pagesize="5">
			
			<display:column property="ticker" titleKey="task.ticker" />
			
			<display:column property="description" titleKey="task.description" />
			
			<display:column property="moment" titleKey="task.moment" />

			<display:column titleKey="task.complaint.customer">
				<a href="actor/actor.do?actorId=${row.customer.id}"><jstl:out value="${row.customer.userAccount.username}"/></a>
			</display:column>
		</display:table>
		
	<input type="button" name="back"
		value="<spring:message code="task.show.back" />"
		onclick="javascript: window.location.replace('fixUpTask/list.do')" />
	<br />
		
				
		
	