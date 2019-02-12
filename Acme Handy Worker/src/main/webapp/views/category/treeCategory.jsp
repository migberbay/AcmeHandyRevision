<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table pagesize="5" class="category" name="categories"
	requestURI="${requestURI}" id="row" >

	<spring:message code="category.display.task" var="displayTask" />
	<display:column title="${displayTask}">
		<a href="fixUpTask/listTaskByCategory.do?categoryId=${row.id}">
			<jstl:out value="${displayTask}" />
		</a>
	</display:column>


	<spring:message code="category.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" />

	<spring:message code="category.name" var="categ" />
	<display:column property="categories" title="${category.categories} " />


</display:table>
