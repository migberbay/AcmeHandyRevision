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

<security:authorize access="hasRole('ADMIN')">
	<h2>Create Admin</h2> 
<form:form action="userAccount/admin/createAdmin.do" modelAttribute="admin">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="isSuspicious"/>
	<form:hidden path="isBanned"/>
	
	
	<form:hidden path="userAccount.id"/>
	<form:hidden path="userAccount.version"/>
	<form:hidden path="userAccount.authorities"/>
	

	<form:label path="userAccount.username">
		<spring:message code="admin.userAccount.username" />:
	</form:label>
	<form:input path="userAccount.username" />
	<form:errors cssClass="error" path="userAccount.username" />
	<br />
	
	<!--  -->

	<form:label path="userAccount.password">
		<spring:message code="admin.userAccount.password" />:
	</form:label>
	<form:input type="password" path="userAccount.password" />
	<form:errors cssClass="error" path="userAccount.password" />
	<br />
	
	<!--  -->

	<form:label path="name">
		<spring:message code="administrator.name" />:
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />
	<br />
	
	<!--  -->
	
	<form:label path="surname">
		<spring:message code="administrator.surname" />:
	</form:label>
	<form:input path="surname" />
	<form:errors cssClass="error" path="surname" />
	<br />
	
	<!--  -->
	
	<form:label path="middleName">
		<spring:message code="administrator.middleName" />:
	</form:label>
	<form:input path="middleName" />
	<form:errors cssClass="error" path="middleName" />
	<br />
	
	<!--  -->
	
	<form:label path="photo">
		<spring:message code="administrator.photo" />:
	</form:label>
	<form:input path="photo" />
	<form:errors cssClass="error" path="photo" />
	<br />
	
	<!--  -->
	
	<form:label path="email">
		<spring:message code="administrator.email" />:
	</form:label>
	<form:input path="email" />
	<form:errors cssClass="error" path="email" />
	<br />
	
	<!--  -->
	
	<form:label path="phone">
		<spring:message code="administrator.phone" />:
	</form:label>
	<form:input path="phone" />
	<form:errors cssClass="error" path="phone" />
	<br />
	
	<!--  -->
	
	<form:label path="address">
		<spring:message code="administrator.address" />:
	</form:label>
	<form:input path="address" />
	<form:errors cssClass="error" path="address" />
	<br />
	
	<!--  -->
	
	<input type="submit" name="saveAdmin"
		value="<spring:message code="admin.save" />" />&nbsp; 

	<input type="button" name="cancel"
		value="<spring:message code="admin.cancel" />"
		onclick="javascript: window.location.replace('master.page')" />
	<br />

</form:form>
	
	<h2>Create referee </h2>
<form:form action="userAccount/admin/createAdmin.do" modelAttribute="referee">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="isSuspicious"/>
	<form:hidden path="isBanned"/>
	
	<form:hidden path="userAccount.id"/>
	<form:hidden path="userAccount.version"/>
	<form:hidden path="userAccount.authorities"/>
	
	

	<form:label path="userAccount.username">
		<spring:message code="admin.userAccount.username" />:
	</form:label>
	<form:input path="userAccount.username" />
	<form:errors cssClass="error" path="userAccount.username" />
	<br />
	
	<!--  -->

	<form:label path="userAccount.password">
		<spring:message code="admin.userAccount.password" />:
	</form:label>
	<form:input type="password" path="userAccount.password" />
	<form:errors cssClass="error" path="userAccount.password" />
	<br />
	
	<!--  -->

	<form:label path="name">
		<spring:message code="administrator.name" />:
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />
	<br />
	
	<!--  -->
	
	<form:label path="surname">
		<spring:message code="administrator.surname" />:
	</form:label>
	<form:input path="surname" />
	<form:errors cssClass="error" path="surname" />
	<br />
	
	<!--  -->
	
	<form:label path="middleName">
		<spring:message code="administrator.middleName" />:
	</form:label>
	<form:input path="middleName" />
	<form:errors cssClass="error" path="middleName" />
	<br />
	
	<!--  -->
	
	<form:label path="photo">
		<spring:message code="administrator.photo" />:
	</form:label>
	<form:input path="photo" />
	<form:errors cssClass="error" path="photo" />
	<br />
	
	<!--  -->
	
	<form:label path="email">
		<spring:message code="administrator.email" />:
	</form:label>
	<form:input path="email" />
	<form:errors cssClass="error" path="email" />
	<br />
	
	<!--  -->
	
	<form:label path="phone">
		<spring:message code="administrator.phone" />:
	</form:label>
	<form:input path="phone" />
	<form:errors cssClass="error" path="phone" />
	<br />
	
	<!--  -->
	
	<form:label path="address">
		<spring:message code="administrator.address" />:
	</form:label>
	<form:input path="address" />
	<form:errors cssClass="error" path="address" />
	<br />
	
	<!--  -->

	<input type="submit" name="saveReferee" value="<spring:message code="admin.save" />" />&nbsp; 

	<input type="button" name="cancel"
		value="<spring:message code="admin.cancel" />"
		onclick="javascript: window.location.replace('master.page')" />
	<br />

</form:form>
	
</security:authorize>
