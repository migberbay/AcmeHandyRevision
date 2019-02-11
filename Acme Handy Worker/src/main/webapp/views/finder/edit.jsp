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

	<!-- PARAMETERS FROM CONTROLLER: finder: Finder, finder a editar
									 fixUpTasks: Collection<FixUpTask>
					 				 categories: Collection<Category>
					 				 -->
									 

<form:form action="handyWorker/finder/filter.do" modelAttribute="finder">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:hidden path="moment" />
    <form:hidden path="handyWorker" />
	
	<security:authorize access="hasRole('HANDYWORKER')">
	
	<form:label path="keyword">
		<spring:message code="finder.keyword" />:
	</form:label>
	<form:input path="keyword" />
	<form:errors cssClass="error" path="keyword" />
	<br />
	
	<form:label path="minPrice">
		<spring:message code="finder.minPrice" />:
	</form:label>
	<form:input path="minPrice" />
	<form:errors cssClass="error" path="minPrice" />
	
	<form:label path="maxPrice">
		<spring:message code="task.maxPrice" />:
	</form:label>
	<form:input path="maxPrice" />
	<form:errors cssClass="error" path="maxPrice" />
	<br />
	
	<form:label path="startDate">
		<spring:message code="finder.startDate" />:
	</form:label>
	<form:input path="startDate" placeholder="01/01/2010 12:00"/>
	<form:errors cssClass="error" path="startDate" />
	
	<form:label path="endDate">
		<spring:message code="finder.endDate" />:
	</form:label>
	<form:input path="endDate" placeholder="01/01/2010 12:00"/>
	<form:errors cssClass="error" path="endDate" />
	<br />

    <form:label path="category">
    <spring:message code="finder.category" />:
    </form:label>
	<form:select id="category" path="category">
		<form:option label = "-----" value="${null}" />
		<form:options items="${categories}" itemLabel="name" itemValue="id" />
	</form:select>
	<form:errors cssClass="error" path="category" />
	<br />
    <form:label path="warranty">
        <spring:message code="finder.warranty" />:
    </form:label>
    <form:select id="warranty" path="warranty">
        <form:option label = "-----" value="${null}" />
        <form:options items="${warranties}" itemLabel="title" itemValue="id" />
    </form:select>
    <form:errors cssClass="error" path="category" />
    <br />
	
	<input type="submit" name="filter" value="<spring:message code="finder.search" />" />
				
	<input type="button" name="cancel"
		value="<spring:message code="finder.cancel" />"
		onclick="javascript: window.location.replace('')" />
	<br/>
	
	</security:authorize>

</form:form>
<br/>
<jstl:if test="${cachedMessage != null}">
	<p style="color: #3a3a3a"><spring:message code="${cachedMessage}" /></p>
	<p style="color: #3a3a3a"><spring:message code="finder.cachedMoment"/>
		<jstl:out value="${finder.moment}"/></p>
	<br/>
</jstl:if>

<display:table name="fixUpTasks" id="row" requestURI="handyWorker/finder/filter.do" pagesize="5">

	<display:column titleKey="task.options">
		<a href="fixUpTask/show.do?fixUpTaskId=${row.id}">
			<spring:message	code="task.show" />
		</a><br/>
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

		<a href="actor/show.do?actorId=${row.customer.id}">
			<spring:message code="task.publisher"/>
		</a><br/>

	</display:column>

	<display:column property="ticker" titleKey="task.ticker" />

	<display:column property="description" titleKey="task.description" />

	<spring:message code="task.moment.format" var="formatMoment"/>
	<display:column property="moment" titleKey="task.moment" format="{0,date,${formatMoment} }"/>
	<display:column property="startMoment" titleKey="task.startMoment" format="{0,date,${formatMoment} }"/>

	<display:column property="maxPrice" titleKey="task.maxPrice" />

</display:table>

