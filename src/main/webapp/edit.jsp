<%@ page import="ua.rbolck.rader.entity.Post" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit</title>
</head>
<body>
<%
    Post post = (Post) request.getAttribute("post");
%>
Post ID: <%=post.getId()%><br/>
Post Title: <%=post.getTitle()%><br/>

</body>
</html>
