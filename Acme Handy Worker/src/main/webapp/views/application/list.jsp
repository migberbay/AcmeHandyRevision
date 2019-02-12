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
		
		<jstl:set var="statushw" value="${row.status}"/>
			<jsp:useBean id="todayhw" class="java.util.Date"/>
			<jstl:if test="${statushw == 'PENDING' && row.fixUpTask.startMoment < todayhw }">
				<jstl:set var="statushw" value="MOMENT"/>
			</jstl:if>
			
			<display:column class="${statushw}"> 
				<a href="handyWorker/application/show.do?appId=${row.id}"><spring:message code="application.show"/></a> 
			</display:column>
		
			<display:column titleKey="application.fixUpTask" property="fixUpTask.description" class="${statushw}"/>
			
			<spring:message code="application.moment.format" var="formatMoment"/>
			<display:column titleKey="application.moment" property="moment" format="{0,date,${formatMoment} }" class="${statushw}"/>
			
			<display:column titleKey="application.price" property="price" class="${statushw}"/> 
			
			<display:column titleKey="application.status" property="status" class="${statushw}"/>
			
			
		
		</display:table>
		
	</security:authorize>
	
	<security:authorize access="hasRole('CUSTOMER')">
	
		<display:table name="applications" id="row" requestURI="customer/application/list.do" pagesize="5">
			
			<jstl:set var="status" value="${row.status}"/>
			<jsp:useBean id="today" class="java.util.Date"/>
			<jstl:if test="${status == 'PENDING' && row.fixUpTask.startMoment < today }">
				<jstl:set var="status" value="MOMENT"/>
			</jstl:if>
			
			<display:column class="${status }">
				<jstl:if test="${row.status == 'PENDING'}">
					 <a href="customer/application/edit.do?appId=${row.id}"><spring:message code="application.edit" /></a> 
				</jstl:if>
			</display:column>
			
			<display:column titleKey="application.fixUpTask" property="fixUpTask.description" class="${status }"/>
			
			<spring:message code="application.moment.format" var="formatMoment"/>
			<display:column titleKey="application.moment" property="moment" format="{0,date,${formatMoment} }" class="${status }"/>
			
			<display:column titleKey="application.price" property="price" class="${status }"/>
			
			<display:column titleKey="application.status" property="status" class="${status }"/>
		
		</display:table>
		
	</security:authorize>