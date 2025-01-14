<%--
- banner.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<div class="rounded" style="background: <acme:message code='master.banner.background'/>">
	<img src="images/banner.png" alt="<acme:message code='master.banner.alt'/>" class="img-fluid rounded"/>
	<a href="${banner.link}">
		<img src="${banner.picture}" alt="${banner.slogan}" class="img-fluid rounded" style="width: 10%; height: 10%;"/>
	</a>
</div>