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

<form:form action="actor/edit.do" modelAttribute="actor">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="isSuspicious" />
	<form:hidden path="isBanned" />
	
	<form:hidden path="userAccount.authorities" />
	
	<form:hidden path="socialProfiles" />
	
	<jstl:if test="${type=='customer'}">
		<form:hidden path="fixUpTasks"/>
	</jstl:if>
	
	<form:label path="name">
		<spring:message code="actor.name" />:
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />
	<br />

	<form:label path="surname">
		<spring:message code="actor.surname" />:
	</form:label>
	<form:input path="surname" />
	<form:errors cssClass="error" path="surname" />
	<br />

	<form:label path="middleName">
		<spring:message code="actor.middleName" />:
	</form:label>
	<form:input path="middleName" />
	<form:errors cssClass="error" path="middleName" />
	<br />

	<form:label path="photo">
		<spring:message code="actor.photo" />:
	</form:label>
	<form:input path="photo" />
	<form:errors cssClass="error" path="photo" />
	<br />

	<form:label path="email">
		<spring:message code="actor.email" />:
	</form:label>
	<form:input path="email" />
	<form:errors cssClass="error" path="email" />
	<br />

	<form:label path="phone">
		<spring:message code="actor.phone" />:
	</form:label>
	<form:input path="phone" />
	<form:errors cssClass="error" path="phone" />
	<br />

	<form:label path="address">
		<spring:message code="actor.address" />:
	</form:label>
	<form:input path="address" />
	<form:errors cssClass="error" path="address" />
	<br />

	<security:authorize access="hasRole('HANDYWORKER')">
		<form:label path="make">
			<spring:message code="actor.make" />:
	</form:label>
		<form:input path="make" />
		<form:errors cssClass="error" path="make" />
		<br />
	</security:authorize>

	<h3>
		<spring:message code="actor.userAccount" />
	</h3>
	<jstl:if test="${!newActor}">
	<div style="color:red;"><b><spring:message code="actor.useraccount.change"/></b></div>
	</jstl:if>
	<form:label path="userAccount.username">
		<spring:message code="actor.username" />:
	</form:label>
	<form:input path="userAccount.username" />
	<form:errors cssClass="error" path="userAccount.username" />
	<br />

	<form:label path="userAccount.password">
		<spring:message code="actor.password" />:
	</form:label>
	
	<form:password path="userAccount.password" /> 
	<jstl:if test="${!newActor}">
	<div style="color:red;"><spring:message code="actor.password.again"/></div>
	</jstl:if>
	<form:errors cssClass="error" path="userAccount.password" />
	<br />
	<jstl:out value="${type }"/>
	<jstl:if test="${type=='customer'}">
		<input type="submit" name="saveCustomer" value="<spring:message code="actor.save" />" />
	</jstl:if>
	<jstl:if test="${type=='handyworker'}">
		<input type="submit" name="saveHandyWorker" value="<spring:message code="actor.save" />" />
	</jstl:if>
	<jstl:if test="${type=='sponsor'}">
		<input type="submit" name="saveSponsor" value="<spring:message code="actor.save" />" />
	</jstl:if>
	<jstl:if test="${type=='referee'}">
		<input type="submit" name="saveReferee" value="<spring:message code="actor.save" />" />
	</jstl:if>
	<jstl:if test="${type=='administrator'}">
		<input type="submit" name="saveSponsor" value="<spring:message code="actor.save" />" />
	</jstl:if>
	<input type="button" name="cancel"
		value="<spring:message code="actor.cancel" />"
		onclick="javascript: window.location.replace('actor.show.do')" />
	<br />

</form:form>