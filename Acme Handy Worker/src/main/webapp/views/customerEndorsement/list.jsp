+<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

	<!-- PARAMETERS FROM CONTROLLER:
									 customerEndorsements Collection<CustomerEndorsement>
									 customer Customer
									 handyWorker HandyWorker
									 -->
									 
		<security:authorize access="hasRole('HANDYWORKER')">
		
			<display:table name="customerEndorsements" id="row" requestURI="customerEndorsements/handyWorker/list.do" pagesize="5">
				
				<display:column titleKey="customerEndorsement.options">
					<a href="customerEndorsement/handyWorker/show.do?customerEndorsementId=${row.id}">
								<spring:message	code="customerEndorsement.show" />
					</a>
					<br/>
					<a href="customerEndorsement/handyWorker/edit.do?customerEndorsementId=${row.id}">
						<spring:message	code="customerEndorsement.edit" />
					</a><br/>	
					<a href="customerEndorsement/handyWorker/delete.do?customerEndorsementId=${row.id}">
						<spring:message	code="customerEndorsement.delete" />
					</a><br/>			
				</display:column>
				
				<spring:message code="customerEndorsement.moment.format" var="formatMoment"/>
				<display:column property="moment" titleKey="customerEndorsement.moment" format="{0,date,${formatMoment}}" />	
			
				<display:column property="text" titleKey="customerEndorsement.text" />
			
			</display:table>
			
			<a href="customerEndorsement/handyWorker/create.do"><spring:message code="customerEndorsement.create"/></a>
			
		</security:authorize>
			
		