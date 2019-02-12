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

<!-- 
	Recieves: Box box: box a editar.
 -->

<form:form action="box/edit.do" modelAttribute="box">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:hidden path="systemBox" />
	<form:hidden path="actor" />
	<form:hidden path="messages" />
	
	
	<security:authorize access="isAuthenticated()">
		
	
	<form:label path="name">
		<spring:message code="box.name" />:
	</form:label>
	
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />
	<br />
	
	<input type="submit" name="save" value="<spring:message code="box.save" />" />
				
	<input type="button" name="cancel"
		value="<spring:message code="box.cancel" />"
		onclick="javascript: window.location.replace('box/list.do')" />
	<br />
	
	</security:authorize>

</form:form>