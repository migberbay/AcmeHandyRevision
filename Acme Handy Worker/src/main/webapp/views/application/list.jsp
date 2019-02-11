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

	<security:authorize access="hasRole('HANDYWORKER')">
	
		<display:table name="applications" id="row" requestURI="handyWorker/application/list.do" pagesize="5">
			
			<display:column> 
				<a href="handyWorker/application/show.do?appId=${row.id}"><spring:message code="application.show"/></a> 
			</display:column>
		
			<display:column titleKey="application.fixUpTask" property="fixUpTask.description" />
			
			<spring:message code="application.moment.format" var="formatMoment"/>
			<display:column titleKey="application.moment" property="moment" format="{0,date,${formatMoment} }"/>
			
			<display:column titleKey="application.price" property="price" />
			
			<display:column titleKey="application.status" property="status" />
			
			
		
		</display:table>
		
	</security:authorize>
	
	<security:authorize access="hasRole('CUSTOMER')">
	
		<display:table name="applications" id="row" requestURI="customer/application/list.do" pagesize="5">
			
			<display:column>
				<jstl:if test="${row.status == 'PENDING'}">
					 <a href="customer/application/edit.do?appId=${row.id}"><spring:message code="application.edit" /></a> 
				</jstl:if>
			</display:column>
			
			<display:column titleKey="application.fixUpTask" property="fixUpTask.description" />
			
			<spring:message code="application.moment.format" var="formatMoment"/>
			<display:column titleKey="application.moment" property="moment" format="{0,date,${formatMoment} }"/>
			
			<display:column titleKey="application.price" property="price" />
			
			<display:column titleKey="application.status" property="status" />
		
		</display:table>
		
	</security:authorize>