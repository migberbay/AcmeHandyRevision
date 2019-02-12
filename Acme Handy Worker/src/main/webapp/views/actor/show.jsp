<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
								
	<div>					
		<b><spring:message code="actor.name"/></b>: <jstl:out value="${actor.name}"/> <br/>
		<b><spring:message code="actor.middleName"/></b>: <jstl:out value="${actor.middleName}"/> <br/>	 
		<b><spring:message code="actor.surname"/></b>: <jstl:out value="${actor.surname}"/> <br/>		 				 
		<b><spring:message code="actor.photo"/></b>: <jstl:out value="${actor.photo}"/> <br/>				 				 
		<b><spring:message code="actor.email"/></b>: <jstl:out value="${actor.email}"/> <br/>			 				 
		<b><spring:message code="actor.phone"/></b>: <jstl:out value="${actor.phone}"/> <br/>
		<jstl:if test="${hw ||cust}">
			<b><spring:message code="actor.score"/></b>: <jstl:out value="${score}"/> <br/>			
		</jstl:if>	 
	</div>
	<br/>
		<jstl:if test="${logged}">
		<b><a href="actor/edit.do"><spring:message code="actor.edit" /></a></b>
	<br/>
		</jstl:if>
	
		<h3><spring:message code="actor.socialProfile"/>:</h3>
		<display:table name="socialProfiles" id="row" requestURI="actor/show.do" pagesize="5">
		    <display:column>
			<b><spring:message code="actor.socialProfile.nick"/></b>: <jstl:out value="${actor.socialProfiles.nick}"/> <br/>
			<b><spring:message code="actor.socialProfile.socialNetwork"/></b>: <jstl:out value="${actor.socialProfiles.socialNetwork}"/> <br/>
			<b><spring:message code="actor.socialProfile.link"/></b>: <jstl:out value="${actor.socialProfiles.link}"/> <br/>
			</display:column>
		</display:table>
		<jstl:if test="${hw }">
		<h3><spring:message code="actor.tutorials"/>:</h3>
		<display:table name="tutorials" id="row" requestURI="actor/show.do" pagesize="5">		
			<display:column>
				<security:authorize access="hasRole('HANDYWORKER')">
					<a href="tutorial/handyWorker/edit.do?tutorialId=${row.id}"> <spring:message code="tutorial.edit" /> </a> <br/>
					<a href="tutorial/handyWorker/delete.do?tutorialId=${row.id}"> <spring:message code="tutorial.delete" /> </a> <br/>
				</security:authorize>
				<a href="tutorial/show.do?tutorialId=${row.id}"> <spring:message code="tutorial.show" /></a>
			</display:column>
		
			<display:column titleKey="tutorial.title" property="title" />
		
			<display:column titleKey="tutorial.summary" property="summary" />
		
			<spring:message code="tutorial.moment.format" var="formatMoment" />
			<display:column titleKey="tutorial.moment" property="moment" format="{0,date,${formatMoment} }" />
	
	</display:table>
	</jstl:if>
		
		<jstl:if test="${custProfileHw}">
			<h3><spring:message code="actor.fixUpTasks"/>:</h3>
<display:table name="fixUpTasks" id="row" requestURI="fixUpTask/list.do" pagesize="5">
			<display:column titleKey="task.options">
					<a href="fixUpTask/show.do?fixUpTaskId=${row.id}">
						<spring:message	code="task.show" />
					</a><br/>
				<security:authorize access="hasRole('HANDYWORKER')">
					<jstl:set var="stat" value="0"/>
					<jstl:forEach var="x" items="${row.applications}">
						<jstl:if test="${x.status=='PENDING'}">
						<jstl:if test="${stat=='0' }">
							<jstl:set var="stat" value="1"/>
							<a href="handyWorker/application/apply.do?fixUpTaskId=${row.id}">
								<spring:message code="task.apply"/>
							</a><br/>
						</jstl:if>
						</jstl:if>
					</jstl:forEach>	
				</security:authorize>
				<security:authorize access="hasRole('CUSTOMER')">
					
					<jstl:if test="${empty row.applications && empty row.complaints}">
							<a href="fixUpTask/customer/edit.do?fixUpTaskId=${row.id}">
								<spring:message	code="task.edit" />
							</a><br/>	
						</jstl:if>
						<jsp:useBean id="today" class="java.util.Date"/>
						<jstl:if test="${row.startMoment > today}">
							<a href="fixUpTask/customer/delete.do?fixUpTaskId=${row.id}">
								<spring:message	code="task.delete" />
							</a><br/>			
					</jstl:if>	
					<a href="complaint/customer/edit.do?fixUpTaskId=${row.id}">
						<spring:message	code="task.complain" />
					</a><br/>
				</security:authorize>		
			</display:column>
				
				<display:column property="ticker" titleKey="task.ticker" />	
			
				<display:column property="description" titleKey="task.description" />
				
				<spring:message code="task.moment.format" var="formatMoment"/>
				<display:column property="moment" titleKey="task.moment" format="{0,date,${formatMoment} }"/>			
				<display:column property="startMoment" titleKey="task.startMoment" format="{0,date,${formatMoment} }"/>
				
				<display:column property="maxPrice" titleKey="task.maxPrice" />	
			
			</display:table>	
</jstl:if>
	<br/>
		
	<input type="button" name="back"
		value="<spring:message code="actor.show.back" />"
		onclick="javascript: window.location.replace('')" />
	<br/>
		