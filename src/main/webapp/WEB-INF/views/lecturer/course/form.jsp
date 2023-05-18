<%--
- form.jsp
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

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<acme:form>
    <acme:input-textbox code="lecturer.course.form.label.code" path="code"/>
    <acme:input-textbox code="lecturer.course.form.label.title" path="title"/>
    <acme:input-textarea code="lecturer.course.form.label.courseAbstract" path="courseAbstract"/>
    
	<label>
		<acme:message code="lecturer.course.form.label.courseType"/>
	</label>
	<br>
	<select name="courseType">
 		<c:set var="courseType" value="${empty courseType ? null : courseType.toString()}"/>
  		<acme:input-option code="acme.entities.enums.Type.THEORY_SESSION" value="THEORY_SESSION" selected="${courseType == 'THEORY_SESSION'}" />
  		<acme:input-option code="acme.entities.enums.Type.HANDS_ON_SESSION" value="HANDS_ON_SESSION" selected="${courseType == 'HANDS_ON_SESSION'}" />
  		<acme:input-option code="acme.entities.enums.Type.BALANCE" value="BALANCE" selected="${courseType == 'BALANCE'}" />
	</select>
	<br>
	<br>
	
    <acme:input-money code="lecturer.course.form.label.retailPrice" path="retailPrice"/>
    <acme:input-url code="lecturer.course.form.label.link" path="link"/>
    
    <jstl:choose>
        <jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
            <acme:submit code="lecturer.course.form.button.update" action="/lecturer/course/update"/>
            <acme:submit code="lecturer.course.form.button.delete" action="/lecturer/course/delete"/>
            <acme:submit code="lecturer.course.form.button.publish" action="/lecturer/course/publish"/>
        </jstl:when>
        <jstl:when test="${_command == 'create'}">
            <acme:submit code="lecturer.course.form.button.create" action="/lecturer/course/create"/>
        </jstl:when>
    </jstl:choose>
</acme:form>