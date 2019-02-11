<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

	<!-- PARAMETERS FROM CONTROLLER: referee: Referee
									 actor: Actor
					 				 socialProfile: SocialProfile
									 userAccount: UserAccount
									 -->
									 	
		<fieldset>
			<table>
				<tr>
					<td><b><spring:message code="referee.name"/></b> <jstl:out value="${name}"/></td>
				</tr>
				<tr>
					<td><b><spring:message code="referee.middleName"/></b> <jstl:out value="${middleName}"/></td>
				</tr>
				<tr>
					<td><b><spring:message code="referee.surname"/></b> <jstl:out value="${surname}"/></td>
				</tr>
				<tr>
					<td><b><spring:message code="referee.photo"/></b> <jstl:out value="${photo}"/></td>
				</tr>
				<tr>
					<td><b><spring:message code="referee.email"/></b> <jstl:out value="${email}"/></td>
				</tr>
				<tr>
					<td><b><spring:message code="referee.phone"/></b> <jstl:out value="${phone}"/></td>
				</tr>
				<tr>
					<td><b><spring:message code="referee.username"/></b> <jstl:out value="${username}"/></td>
				</tr>
				<tr>
					<td><b><spring:message code="referee.authority"/></b> <jstl:out value="${authority}"/></td>
				</tr>
			</table>
		</fieldset>
		<br/>
		<h1><spring:message code="referee.socialProfile"/>:</h1><br/>
		<fieldset>
			<table>
				<tr>
					<td><b><spring:message code="referee.socialProfile.nick"/>:</b> <jstl:out value="${referee.socialProfile.nick}"/></td>
				</tr>
				<tr>
					<td><b><spring:message code="referee.socialProfile.socialNetwork"/>:</b> <jstl:forEach var="law" items="${referee.socialProfile.socialNetwork}"> <jstl:out value="${law}"/><br/></jstl:forEach></td>
				</tr>
				<tr>
					<td><b><spring:message code="referee.socialProfile.link"/>:</b><br/> <jstl:out value="${referee.socialProfile.link}"/></td>
				</tr>
			</table>
		</fieldset>
		<br/>
	<input type="button" name="back"
		value="<spring:message code="referee.show.back" />"
		onclick="javascript: window.location.replace('')" />
	<br />
		
				
		
	