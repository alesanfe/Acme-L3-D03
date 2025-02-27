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

<acme:form>
    <acme:input-textbox code="assistant.tutorial.form.label.code" path="code"/>
    <acme:input-select code="assistant.tutorial.form.label.course" path="course" choices="${course}"/>
    <acme:input-textbox code="assistant.tutorial.form.label.title" path="title"/>
    <acme:input-textarea code="assistant.tutorial.form.label.summary" path="summary"/>
    <acme:input-textarea code="assistant.tutorial.form.label.goals" path="goals"/>
    <acme:input-double code="assistant.tutorial.form.label.estimatedTime" path="estimatedTime"/> 
    <acme:hidden-data path="id"/>
	<acme:hidden-data path="draftMode"/>
    
    <jstl:choose>
        <jstl:when test="${_command == 'show' && draftMode == false}">
            <acme:button code="assistant.tutorial.form.button.tutorial" action="/assistant/tutorial/list-all"/>
        	<acme:button code="assistant.session.form.button.session" action="/assistant/session/list?id=${id}"/>
			<acme:button code="assistant.session.list.button.create" action="/assistant/session/create?id=${id}"/>
        </jstl:when>
        <jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
            <acme:button code="assistant.tutorial.form.button.tutorial" action="/assistant/tutorial/list-all"/>
            <acme:submit code="assistant.tutorial.form.button.update" action="/assistant/tutorial/update"/>
            <acme:submit code="assistant.tutorial.form.button.delete" action="/assistant/tutorial/delete"/>
            <acme:submit code="assistant.tutorial.form.button.publish" action="/assistant/tutorial/publish"/>
        </jstl:when>
        <jstl:when test="${_command == 'create'}">
            <acme:submit code="assistant.tutorial.form.button.create" action="/assistant/tutorial/create"/>
        </jstl:when>
    </jstl:choose>
</acme:form>