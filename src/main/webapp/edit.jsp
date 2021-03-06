<%@ page import="ua.rbolck.rader.entity.Post" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit</title>
    <meta charset="utf-8">
    <link rel='StyleSheet' type='text/css' href='css/bootstrap.css'/>
</head>
<body>
<jsp:include page="header.jsp" />
<%
    Post post = (Post) request.getAttribute("post");
%>
<div class="container" style="width:80%;">
    <form role="form">
        <div class="form-group">
            <label for="title">Заголовок</label>
            <input type="text" class="form-control" id="title" placeholder="Title" value="<%=post.getTitle()%>">
        </div>
        <div class="form-group">
            <label for="textOfPost">Содержимое</label>
            <textarea class="form-control" id="textOfPost" rows="3"
                      placeholder="Text of post"><%=post.getContent()%></textarea>
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-default btn-lg">Update post</button>
        </div>
    </form>
</div>
</body>
</html>

