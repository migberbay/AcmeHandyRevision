<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
	
	<!-- PARAMETERS FROM CONTROLLER: sponsorships: Collection<Sponsorship>, sponsorships del sponsor logeado
									 customer: Customer, customer logeado para comprobar si la task le pertenece -->
									 	
	<security:authorize access="hasRole('SPONSOR')">
	
			<display:table name="sponsorships" id="row" requestURI="sponsorship/sponsor/list.do" pagesize="5">
				
				<display:column titleKey="sponsorship.options">
					<a href="sponsorship/sponsor/edit.do?sponsorshipId=${row.id}">
						<spring:message	code="sponsorship.edit" />
					</a><br/>	
					
					<a href="sponsorship/sponsor/delete.do?sponsorshipId=${row.id}">
						<spring:message	code="sponsorship.delete" />
					</a><br/>			
				</display:column>
				
				<display:column property="banner" titleKey="sponsorship.banner" />	
			
				<display:column property="link" titleKey="sponsorship.link" />

				<display:column property="creditCard.number" titleKey="sponsorship.creditCardNumber" />	
				
				<display:column property="tutorial.title" titleKey="sponsorship.tutorial" />	
			
			</display:table>
			
		<a href="sponsorship/sponsor/create.do"><spring:message code="sponsorship.create"/></a>
		
	</security:authorize>