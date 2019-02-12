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
			<li><a class="fNiv"><spring:message	code="master.page.system" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="admin/admin/configuration.do"><spring:message code="master.page.configuration" /></a></li>
					<li><a href="admin/admin/dashboard.do"><spring:message code="master.page.dashboard" /></a></li>
					<li><a href="message/createBroadcast.do"><spring:message code="master.page.broadcast" /></a></li>
					<li><a href="admin/admin/score.do"><spring:message code="master.page.score" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.category" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="category/admin/list.do"><spring:message code="master.page.category.list" /></a></li>
				</ul>
			</li>		
			<li><a class="fNiv"><spring:message	code="master.page.actor" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="userAccount/admin/createAdmin.do"><spring:message code="master.page.administrator.create" /></a></li>
					<li><a href="actor/admin/list.do"><spring:message code="master.page.actor.list" /></a></li>
				</ul>
			</li>		
			<li><a class="fNiv"><spring:message	code="master.page.warranty" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="warranty/admin/list.do"><spring:message code="master.page.warranty.list" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('CUSTOMER')">
			<li><a class="fNiv"><spring:message	code="master.page.fixuptask" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="fixUpTask/customer/list.do"><spring:message code="master.page.task.list" /></a></li>
					<li><a href="fixUpTask/customer/create.do"><spring:message code="master.page.task.create" /></a></li>	
				</ul>
			</li>		
			<li><a class="fNiv"><spring:message	code="master.page.complaint" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="complaint/customer/list.do"><spring:message code="master.page.complaint.list" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.application" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="customer/application/list.do"><spring:message code="master.page.app.list" /></a></li>	
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.endorsement" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="handyWorkerEndorsement/customer/create.do"><spring:message code="master.page.handyWorkerEndorsement.create" /></a></li>			
					<li><a href="handyWorkerEndorsement/customer/list.do"><spring:message code="master.page.handyWorkerEndorsement.list" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv" ><spring:message code="master.page.tutorial" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="tutorial/list.do"><spring:message code="master.page.tutorial.list" /></a>
				</ul>
			</li>
		</security:authorize>

		
		<security:authorize access="hasRole('HANDYWORKER')">
			<li><a class="fNiv" href="handyWorker/finder/filter.do" class="fNiv"><spring:message	code="master.page.finder" /></a>
       		 </li>
			<li><a class="fNiv"><spring:message	code="master.page.fixuptask" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="fixUpTask/list.do"><spring:message code="master.page.task.list" /></a></li>
				</ul>
			</li>	
			<li><a class="fNiv"><spring:message	code="master.page.complaint" /></a>
				<ul>
					<li class="arrow"></li>
						<li><a href="complaint/handyWorker/list.do"><spring:message code="master.page.complaint.list" /></a></li>
				</ul>
			</li>	
			<li><a class="fNiv"><spring:message	code="master.page.application" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="handyWorker/application/list.do"><spring:message code="master.page.app.list" /></a></li>	
				</ul>
			</li>
			
			<li><a class="fNiv"><spring:message	code="master.page.endorsement" /></a>
				<ul>
					<li class="arrow"></li>
						<li><a href="customerEndorsement/handyWorker/list.do"><spring:message code="master.page.customerEndorsement.list" /></a></li>
						<li><a href="customerEndorsement/handyWorker/create.do"><spring:message code="master.page.customerEndorsement.create" /></a></li>			
				</ul>
			</li>
			
			<li><a class="fNiv" ><spring:message code="master.page.tutorial" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="tutorial/handyWorker/list.do"><spring:message code="master.page.tutorial.list" /></a>
				</ul>
			</li>
			
			<li><a class="fNiv" ><spring:message code="master.page.curricula" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="curricula/handyworker/show.do"><spring:message code="master.page.curricula.show" /></a>
				</ul>
			</li>

		</security:authorize>
		
		<security:authorize access="hasRole('REFEREE')">
			<li><a class="fNiv"><spring:message	code="master.page.complaint" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="complaint/referee/list.do"><spring:message code="master.page.complaint.list" /></a></li>
					<li><a href="complaint/referee/listNoReport.do"><spring:message code="master.page.complaint.listNoReport" /></a></li>
				</ul>
			</li>
			
			<li><a class="fNiv" ><spring:message code="master.page.tutorial" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="tutorial/list.do"><spring:message code="master.page.tutorial.list" /></a>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('SPONSOR')">
			<li><a class="fNiv" ><spring:message code="master.page.sponsorship" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="sponsorship/sponsor/list.do"><spring:message code="master.page.sponsorship.list" /></a>
					<li><a href="sponsorship/sponsor/create.do"><spring:message code="master.page.sponsorship.create" /></a>
				</ul>
			</li>
			<li><a class="fNiv" ><spring:message code="master.page.tutorial" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="tutorial/list.do"><spring:message code="master.page.tutorial.list" /></a>
				</ul>
			</li>
		</security:authorize>
		
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			
			<li><a class="fNiv" ><spring:message code="master.page.tutorial" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="tutorial/list.do"><spring:message code="master.page.tutorial.list" /></a>
				</ul>
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

