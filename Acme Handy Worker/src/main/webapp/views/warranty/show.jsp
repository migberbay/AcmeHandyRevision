<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

	<!-- PARAMETERS FROM CONTROLLER: warranty: Warranty,
									 title: String,
					 				 terms: String,
									 laws: Collection<String>, 
									 isDraft: Boolean   -->
									 					 
	<div style="float:left;width:250px;">
		<b><spring:message code="warranty.title"/>:</b> <jstl:out value="${warranty.title}"/><br/>
		<b><spring:message code="warranty.terms"/>: </b> <jstl:out value="${warranty.terms}"/><br/>
		<b><spring:message code="warranty.isDraft"/>: </b> <jstl:out value="${warranty.isDraft}"/><br/>
	</div>
	<div  style="margin:0 auto;width:250px;">
		<b><spring:message code="warranty.laws"/>: </b> <jstl:out value="${warranty.laws}"/><br/>
	</div>	
	<br/>
		
	<input type="button" name="back"
		value="<spring:message code="warranty.back" />"
		onclick="javascript: window.location.replace('warranty/admin/list.do')" />
	<br />
		
				
		
	