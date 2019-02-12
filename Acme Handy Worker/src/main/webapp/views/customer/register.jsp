<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="actor/createCustomer.do" modelAttribute="customer">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:hidden path="socialProfiles" />
	<form:hidden path="isBanned" />
	<form:hidden path="isSuspicious" />
	<form:hidden path="userAccount.authorities" />

	<spring:message code="customer.userAccount.username.example"
		var="username" />
	<spring:message code="customer.userAccount.password.example"
		var="password" />

	<spring:message code="customer.name.example" var="name" />
	<spring:message code="customer.surname.example" var="surname" />
	<spring:message code="customer.middleName.example" var="middleName" />
	<spring:message code="customer.email.example" var="email" />
	<spring:message code="customer.phone.example" var="phone" />
	<spring:message code="customer.photo.example" var="photo" />
	<spring:message code="customer.address.example" var="address" />

	<form:label path="userAccount.username">
		<spring:message code="customer.userAccount.username" />:
	</form:label>
	<form:input path="userAccount.username" placeholder="${username}" />
	<form:errors cssClass="error" path="userAccount.username" />
	<br />

	<form:label path="userAccount.password">
		<spring:message code="customer.userAccount.password" />:
	</form:label>
	<form:input type="password" path="userAccount.password"
		placeholder="${password}" />
	<form:errors cssClass="error" path="userAccount.password" />
	<br />

	<!--	<form:select id="authorities.authority" path="userAccount.authorities">
		<form:option label="----" value="0" />
		<form:options items="${authorities}" itemLabel="authority" />
	</form:select>
	<form:errors cssClass="error" path="userAccount.authorities" /> -->

	<form:label path="name">
		<spring:message code="customer.name" />:
	</form:label>
	<form:input path="name" placeholder="${name}" />
	<form:errors cssClass="error" path="name" />
	<br />

	<form:label path="surname">
		<spring:message code="customer.surname" />:
	</form:label>
	<form:input path="surname" placeholder="${surname}" />
	<form:errors cssClass="error" path="surname" />
	<br />

	<form:label path="middleName">
		<spring:message code="customer.middleName" />:
	</form:label>
	<form:input path="middleName" placeholder="${middleName}" />
	<form:errors cssClass="error" path="middleName" />
	<br />

	<form:label path="email">
		<spring:message code="customer.email" />:
	</form:label>
	<form:input path="email" placeholder="${email}" />
	<form:errors cssClass="error" path="email" />
	<br />

	<form:label path="phone">
		<spring:message code="customer.phone" />:
	</form:label>
	<form:input path="phone" placeholder="${phone}" />
	<form:errors cssClass="error" path="phone" />
	<br />

	<form:label path="photo">
		<spring:message code="customer.photo" />:
	</form:label>
	<form:input path="photo" placeholder="${photo}" />
	<form:errors cssClass="error" path="photo" />
	<br />

	<form:label path="address">
		<spring:message code="customer.address" />:
	</form:label>
	<form:textarea path="address" placeholder="${address}" />
	<form:errors cssClass="error" path="address" />
	<br />

	<form:label path="${socialProfile.nick}">
		<spring:message code="customer.socialProfile.nick" />:
	</form:label>
	<form:input path="${socialProfile.nick}"
		placeholder="<spring:message code="customer.socialProfile.nick.example"/>" />
	<form:errors cssClass="error" path="${socialProfile.nick}" />
	<br />

	<form:label path="${socialProfile.socialNetwork}">
		<spring:message code="customer.socialProfile.socialNetwork" />:
	</form:label>
	<form:input path="${socialProfile.socialNetwork}"
		placeholder="<spring:message code="customer.socialProfile.socialNetwork.example"/>" />
	<form:errors cssClass="error" path="${socialProfile.socialNetwork}" />
	<br />

	<form:label path="${socialProfile.link}">
		<spring:message code="customer.socialProfile.link" />:
	</form:label>
	<form:input path="${socialProfile.link}"
		placeholder="<spring:message code="customer.socialProfile.link.example"/>" />
	<form:errors cssClass="error" path="${socialProfile.link}" />
	<br />

	<input type="submit" name="save"
		value="<spring:message code="customer.save" />" />&nbsp; 
		
	<input type="button" name="cancel"
		value="<spring:message code="customer.cancel" />"
		onclick="javascript: window.location.replace('master.page')" />
	<br />

</form:form>
