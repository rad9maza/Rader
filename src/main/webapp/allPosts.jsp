<%@ page import="ua.rbolck.rader.entity.Post" %>
<%@ page import="java.util.Collection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Feed</title>
    <link rel='StyleSheet' type='text/css' href='css/bootstrap.css'/>
</head>
<body>
<div class="navbar navbar-default" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="/index.jsp">Rader</a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">Feed</a></li>
                <li><a href="users.html">Users</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">User</a></li>
            </ul>
        </div>
    </div>
</div>

<div class="container" style="width:80%;">
    <%
        Collection<Post> posts = (Collection<Post>) request.getAttribute("posts");
    %>
    <%for (Post post : posts) {%>

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
    <%}%>
</div>
</body>
</html>
