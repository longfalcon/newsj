<%--
  ~ Copyright (c) 2016. Sten Martinez
  ~
  ~ This program is free software; you can redistribute it and/or modify
  ~ it under the terms of the GNU General Public License as published by
  ~ the Free Software Foundation; either version 2 of the License, or
  ~ (at your option) any later version.
  ~
  ~ This program is distributed in the hope that it will be useful,
  ~ but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~ MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~ GNU General Public License for more details.
  ~
  ~ You should have received a copy of the GNU General Public License along
  ~ with this program; if not, write to the Free Software Foundation, Inc.,
  ~ 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
  --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="date" uri="http://java.longfalcon.net/jsp/jstl/date" %>
<%@ taglib prefix="text" uri="http://java.longfalcon.net/jsp/jstl/text" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="admin_common_head.jsp" %>
    <style type="text/css">
        .deprecated {
            text-decoration: line-through;
        }
    </style>
</head>
<body>
<div id="logo" style="cursor: pointer;">
    <h1><a href="${pageContext.request.contextPath}"></a></h1>

    <p><em></em></p>
</div>
<hr/>

<div id="header">
    <div id="menu">
    </div>
    <!-- end #menu -->
</div>

<div id="page">

    <div id="adpanel">

    </div>

    <div id="content">
        <%--START PAGE CONTENT--%>

            <h1>${title}</h1>

            <c:if test="${!text:isNull(error)}">
                <div class="error">${error}</div>    
            </c:if>                                      


            <form:form modelAttribute="blacklistEntry" action="${pageContext.request.contextPath}/admin/binaryblacklist-edit" method="POST">

                <table class="input">


                    <tr>
                        <td>Group:</td>
                        <td>
                            <form:hidden path="id" name="id" value="${blacklistEntry.id}" />
                            <form:input path="groupName" id="groupname" name="groupname" value="${text:escapeHtml(blacklistEntry.groupName)}" />
                            <div class="hint">The full name of a valid newsgroup. (Wildcard in the format 'alt.binaries.*')</div>
                        </td>
                    </tr>

                    <tr>
                        <td>Regex:</td>
                        <td>
                            <form:textarea path="regex" id="regex" name="regex" />
                            <div class="hint">The regex to be applied. (Note: Beginning and Ending / are already included)</div>
                        </td>
                    </tr>

                    <tr>
                        <td>Description:</td>
                        <td>
                            <form:textarea path="description" id="description" name="description" />
                            <div class="hint">A description for this regex</div>
                        </td>
                    </tr>

                    <tr>
                        <td><label for="msgcol">Message Field</label>:</td>
                        <td>
                            <form:radiobuttons path="msgCol" id="msgcol" items="${columnMap}"/>
                            <div class="hint">Which field in the message to apply the black/white list to.</div>
                        </td>
                    </tr>

                    <tr>
                        <td><label for="status">Active</label>:</td>
                        <td>
                            <form:radiobuttons path="status" id="status" items="${yesNoMap}"/>
                            <div class="hint">Only active regexes are applied during the release process.</div>
                        </td>
                    </tr>

                    <tr>
                        <td><label for="optype">Type</label>:</td>
                        <td>
                            <form:radiobuttons path="opType" id="optype" items="${opTypeMap}"/>
                            <div class="hint">Black will exclude all messages for a group which match this regex. White will include only those which match.</div>
                        </td>
                    </tr>

                    <tr>
                        <td></td>
                        <td>
                            <input type="submit" value="Save" />
                        </td>
                    </tr>

                </table>

            </form:form>
        <%--END PAGE CONTENT--%>
    </div>
    <!-- end #content -->

    <div id="sidebar">
        <ul>
            <li>
                <%@include file="admin_menu.jsp"%>
            </li>

        </ul>
    </div>
    <!-- end #sidebar -->

    <div style="clear: both;">&nbsp;</div>

</div>
<!-- end #page -->

<div id="searchfooter">
    <center>
    </center>
</div>

<div class="footer">
    <p>
        ${site.footer}
        <br/><br/><br/>Copyright &copy; ${year} ${site.title}. All rights reserved.
    </p>
</div>
<!-- end #footer -->

<c:if test="${!(empty site.googleAnalyticsAcc)}">
    <%@include file="../common/google_analytics.jsp"%>
</c:if>
</body>
</html>