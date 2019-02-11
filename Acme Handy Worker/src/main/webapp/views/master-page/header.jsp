<%--
 * header.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<a href="">
<div class="page-header" style="background: url(${banner}) center no-repeat; background-size: cover">
	<!--<img class="banner" src="${banner}" alt="${systemName}" />-->
</div>
</a>

<div>
	<ul id="jMenu" class="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="admin/admin/configuration.do"><spring:message code="master.page.administrator.configuration" /></a></li>
					<li><a href="admin/admin/dashboard.do"><spring:message code="master.page.administrator.dashboard" /></a></li>
			   <!-- <li><a href="actor/show.do"><spring:message code="master.page.administrator.actor.show" /></a></li> -->
					<li><a href="category/administrator/list.do"><spring:message code="master.page.administrator.category.list" /></a></li>
					<li><a href="userAccount/admin/createAdmin.do"><spring:message code="master.page.administrator.administrator.create" /></a></li>
					<li><a href="actor/admin/list.do"><spring:message code="master.page.administrator.actor.list" /></a></li>
					<li><a href="warranty/admin/list.do"><spring:message code="master.page.administrator.warranty.list" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('CUSTOMER')">
			<li><a class="fNiv"><spring:message	code="master.page.customer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="complaint/customer/list.do"><spring:message code="master.page.customer.complaint.list" /></a></li>
					<li><a href="fixUpTask/customer/list.do"><spring:message code="master.page.customer.task.list" /></a></li>
					<li><a href="fixUpTask/customer/create.do"><spring:message code="master.page.customer.task.create" /></a></li>	
					<li><a href="customer/application/list.do"><spring:message code="master.page.handyWorker.app" /></a></li>	
					<li><a href="handyWorkerEndorsement/customer/create.do"><spring:message code="master.page.customer.handyWorkerEndorsement.create" /></a></li>			
					<li><a href="handyWorkerEndorsement/customer/list.do"><spring:message code="master.page.customer.handyWorkerEndorsement.list" /></a></li>
			   <!-- <li><a href="actor/show.do"><spring:message code="master.page.customer.actor.show" /></a></li>	-->	
				</ul>
			</li>
		</security:authorize>

    <security:authorize access="hasRole('HANDYWORKER')">
        <li><a href="handyWorker/finder/filter.do" class="fNiv"><spring:message	code="master.page.finder" /></a>
        </li>
    </security:authorize>
		
		<security:authorize access="hasRole('HANDYWORKER')">
			<li><a class="fNiv"><spring:message	code="master.page.handyWorker" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="complaint/handyWorker/list.do"><spring:message code="master.page.handyWorker.complaint.list" /></a></li>
					<li><a href="fixUpTask/list.do"><spring:message code="master.page.customer.task.list" /></a></li>
					<li><a href="handyWorker/application/list.do"><spring:message code="master.page.handyWorker.app" /></a></li>
					<li><a href="tutorial/handyWorker/list.do"><spring:message code="master.page.tutorial.list" /></a></li>
					<li><a href="curricula/handyworker/show.do"><spring:message code="master.page.handyWorker.curricula" /></a></li>
					<li><a href="customerEndorsement/handyWorker/create.do"><spring:message code="master.page.handyWorker.customerEndorsement.create" /></a></li>			
					<li><a href="customerEndorsement/handyWorker/list.do"><spring:message code="master.page.handyWorker.customerEndorsement.list" /></a></li>
			   <!-- <li><a href="actor/show.do"><spring:message code="master.page.handyWorker.actor.show" /></a></li>		-->
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('REFEREE')">
			<li><a class="fNiv"><spring:message	code="master.page.referee" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="complaint/referee/list.do"><spring:message code="master.page.referee.complaint.list" /></a></li>
					<li><a href="complaint/referee/listNoReport.do"><spring:message code="master.page.referee.complaint.listNoReport" /></a></li>
			   <!-- <li><a href="actor/show.do"><spring:message code="master.page.referee.actor.show" /></a></li> -->		
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			
			<li><a class="fNiv" href="tutorial/list.do"> <spring:message code="master.page.tutorial.list" /></a>
			</li>
			
			<li><a class="fNiv"> <spring:message code="master.page.actor.register" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/createSponsor.do"><spring:message code="master.page.actor.sponsor" /></a></li>
					<li><a href="actor/createCustomer.do"><spring:message code="master.page.actor.customer" /></a></li>
					<li><a href="actor/createHandyWorker.do"><spring:message code="master.page.actor.handyWorker" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/show.do"><spring:message code="master.page.profile" /></a></li>
					<li><a href="box/list.do"><spring:message code="master.page.boxlist" /></a></li>					
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

