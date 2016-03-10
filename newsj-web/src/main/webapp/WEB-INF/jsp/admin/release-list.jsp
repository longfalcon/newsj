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

            <c:choose>
                <c:when test="${releaseList.size() > 0}">
                    <tags:pager pagerTotalItems="${pagerTotalItems}" pagerItemsPerPage="${pagerItemsPerPage}"
                                pagerOffset="${pagerOffset}" pagerQueryBase="${pageContext.request.contextPath}/admin/release-list?offset="/>

                    <table style="margin-top:10px;" class="data Sortable highlight">

                        <tr>
                            <th>name</th>
                            <th>category</th>
                            <th>size</th>
                            <th>files</th>
                            <th>postdate</th>
                            <th>adddate</th>
                            <th>grabs</th>
                            <th>options</th>
                        </tr>

                        <c:forEach items="${releaseList}" var="release" varStatus="status">
                            <tr class='${(status.count % 2 == 0) ? "" : "alt"}'>
                                <td title="{$release.name}"><a href="${pageContext.request.contextPath}/admin/release-edit?id=${release.id}">${text:wordWrap(text:escapeHtml(release.searchName), 75)}</a></td>
                                <td class="less">${release.category.title}</td>
                                <td class="less">${text:formatFileSize(release.size, true)}</td>
                                <td class="less"><a href="${pageContext.request.contextPath}/admin/release-files?guid=${release.guid}">${release.totalpart}</a></td>
                                <td class="less">${date:formatDate(release.postDate)}</td>
                                <td class="less">${date:formatDate(release.addDate)}</td>
                                <td class="less">${release.grabs}</td>
                                <td>
                                    <%--TODO: Move to post--%>
                                    <a href="${pageContext.request.contextPath}/admin/release-delete?id=${release.id}">delete</a>
                                </td>
                            </tr>
                        </c:forEach>

                    </table>
                </c:when>
                <c:otherwise>
                    <p>No releases available.</p>
                </c:otherwise>
            </c:choose>
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