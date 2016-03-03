<%@ page import="ua.rbolck.rader.entity.Post" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New user</title>
    <meta charset="utf-8">
    <link rel='StyleSheet' type='text/css' href='css/bootstrap.css'/>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container" style="width:80%;">
    <form role="form" action="/user?action=new" method="post">
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" class="form-control" id="username" placeholder="Enter name of user" value=""
                   name="username">
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" class="form-control" id="password" placeholder="Enter password" value=""
                   name="password">
        </div>
        <label class="radio-inline">
            <input type="radio" name="group" value="2">Admin<Br>
        </label>
        <label class="radio-inline">
            <input type="radio" name="group" value="3">Moderator<Br>
        </label>
        <label class="radio-inline">
            <input type="radio" name="group" value="4" checked>User<Br>
        </label>
        <br> <br>
        <div class="form-group">
            <button type="submit" class="btn btn-default btn-lg">Add user</button>
        </div>
    </form>
</div>
</body>
</html>
