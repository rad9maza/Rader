<%@ page import="ua.rbolck.rader.entity.Post" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Post</title>
    <meta charset="utf-8">
    <link rel='StyleSheet' type='text/css' href='css/bootstrap.css'/>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container" style="width:80%;">
    <%Post post = (Post) request.getAttribute("post");%>
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">
                <b><a href="<%=(post.getAuthor() == null) ? "" : "/user?id=" + post.getAuthor().getId()%>">
                    <%=(post.getAuthor() == null) ? "DELETED_USER" : post.getAuthor().getUsername()%>:</a></b>
                <%=" " + post.getTitle()%>
                <div style="float: right;">
                    <form role="form" id="removeId=<%=post.getId()%>"
                          action="<%="/post?action=remove&id=" + post.getId()%>" method="post">
                        <a href="">
                            <span class="badge"><%=post.getLikes()%></span>
                            <span class="glyphicon glyphicon-thumbs-up"></span></a>
                        <a href="">
                            <span class="badge"><%=post.getDislikes()%></span>
                            <span class="glyphicon glyphicon-thumbs-down"></span></a>
                        <a href="<%="/post?action=edit&id=" + post.getId()%>">
                            <span class="glyphicon glyphicon-pencil"></span></a>
                        <%--<a href="#" onclick="document.getElementById('super').submit(value='Logout')">Logout</a>--%>
                        <a href="#" onclick="document.getElementById('removeId=<%=post.getId()%>').submit()">
                            <span class="glyphicon glyphicon-trash"></span></a>
                    </form>
                </div>
            </h3>
        </div>

        <div class="panel-body">
            <%=post.getContent()%>
        </div>

    </div>
</div>
</body>
</html>
