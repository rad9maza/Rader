<%@ page import="ua.rbolck.rader.entity.Post" %>
<%@ page import="java.util.Collection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Post</title>
</head>
<body>
<%
    Collection<Post> posts = (Collection<Post>) request.getAttribute("posts");
%>
<%for (Post post : posts) {%>
Post ID: <%=post.getId()%><br/>
Post Title: <%=post.getTitle()%><br/>
<%}%>
</body>
</html>
