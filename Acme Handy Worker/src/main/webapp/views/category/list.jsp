<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="${categories}" id="row" pagesize="5"
	requestURI="category/admin/list.do">

	<security:authorize access="hasRole('ADMIN')">
		<display:column>
			<a href="category/admin/edit.do?categoryId=${row.id}"> <spring:message
					code="category.edit" />
			</a> <br/>
			
			<a href="category/admin/show.do?categoryId=${row.id}"> <spring:message
						code="category.show" />
				</a>
		</display:column>
	</security:authorize>

	<display:column titleKey="category.name" property="name" />
	
	<display:column titleKey="category.parentCategory" property="parentCategory.name" />

</display:table>

<div>
	<a href="category/admin/create.do"> <spring:message
			code="category.create" />
	</a>
</div>

