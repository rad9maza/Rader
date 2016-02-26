<%@ page import="ua.rbolck.rader.entity.Post" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Post</title>
    <meta charset="utf-8">
    <link rel='StyleSheet' type='text/css' href='css/bootstrap.css'/>
</head>
<body>
<jsp:include page="header.jsp" />
<div class="container" style="width:80%;">
    <%Post post = (Post) request.getAttribute("post");%>
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title"><b><%=post.getAuthor().getUsername()%>: </b><%=post.getTitle()%>
                <a href="" style="float: right;"><span class="badge"><%=post.getLikes()%></span> <span
                        class="glyphicon glyphicon-chevron-down"></span></a>
                <a href="" style="float: right;"><span class="badge"><%=post.getDislikes()%></span> <span
                        class="glyphicon glyphicon-chevron-up">&nbsp;</span></a>
            </h3>
        </div>

        <div class="panel-body">
            <%=post.getContent()%>
        </div>

    </div>
</div>
</body>
</html>
