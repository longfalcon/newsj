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

            <form:form modelAttribute="jobConfigView" action="${pageContext.request.contextPath}/admin/job-edit" method="post">
                <table class="input">
                    <tr>
                        <td>Name:</td>
                        <td>
                            <form:hidden path="jobKey" name="jobKey" value="${jobConfigView.jobKey}" />
                            ${jobDisplayName}
                        </td>
                    </tr>
                    <tr>
                        <td>Frequency Type:</td>
                        <td>
                            <form:select path="frequencyType" name="frequencyType" itemValue="${jobConfigView.frequencyType}">
                                <form:options items="${frequencyOptionsMap}"/>
                            </form:select>
                        </td>
                    </tr>
                    <tr id="periodic_config">
                        <td>Periodic Interval:</td>
                        <td>
                            Run Every
                            <ul>
                                <li><form:input path="periodHours" name="periodHours"/> Hours </li>
                                <li><form:input path="periodMins" name="periodMins"/> Minutes </li>
                            </ul>
                        </td>
                    </tr>
                    <tr id="scheduled_config">
                        <td>Scheduled:</td>
                        <td>
                            <p>
                                At
                                <form:input path="scheduledHours" name="scheduledHours"/>
                                :
                                <form:input path="scheduledMinutes" name="scheduledMinutes"/>
                                (24-hour time)
                            </p>
                            <span>on every</span>
                            <ul>
                                <li><form:checkbox path="monday" name="monday"/>Monday</li>
                                <li><form:checkbox path="tuesday" name="tuesday"/>Tuesday</li>
                                <li><form:checkbox path="wednesday" name="wednesday"/>Wednesday</li>
                                <li><form:checkbox path="thursday" name="thursday"/>Thursday</li>
                                <li><form:checkbox path="friday" name="friday"/>Friday</li>
                                <li><form:checkbox path="saturday" name="saturday"/>Saturday</li>
                                <li><form:checkbox path="sunday" name="sunday"/>Sunday</li>
                            </ul>
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
<script type="application/javascript">
    var frequencyTypeSelect = $('#frequencyType');
    var freqType = frequencyTypeSelect.val();
    if (freqType == 'FREQ_NEVER') {
        $('#periodic_config').hide();
        $('#scheduled_config').hide();
    }
    if (freqType == 'FREQ_PERIODIC') {
        $('#periodic_config').show();
        $('#scheduled_config').hide();
    }
    if (freqType == 'FREQ_SCHEDULED') {
        $('#periodic_config').hide();
        $('#scheduled_config').show();
    }

    frequencyTypeSelect.change(function () {
        var freqType = frequencyTypeSelect.val();
        if (freqType == 'FREQ_NEVER') {
            $('#periodic_config').hide();
            $('#scheduled_config').hide();
        }
        if (freqType == 'FREQ_PERIODIC') {
            $('#periodic_config').show();
            $('#scheduled_config').hide();
        }
        if (freqType == 'FREQ_SCHEDULED') {
            $('#periodic_config').hide();
            $('#scheduled_config').show();
        }

    })
</script>
</body>
</html>