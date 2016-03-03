<%@ page import="ua.rbolck.rader.entity.User" %>
<%@ page import="ua.rbolck.rader.dao.UserDAOImpl" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%User user = (User) request.getAttribute("user");%>
<%UserDAOImpl userDAO = new UserDAOImpl();%>
<head>
    <title>User <%=user.getUsername()%>
    </title>
    <meta charset="utf-8">
    <link rel='StyleSheet' type='text/css' href='css/bootstrap.css'/>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container" style="width:80%;">
    <div class="bs-example">
        <div data-spy="scroll" align="center" data-target="#navbar-example2" data-offset="0" class="scrollspy-example">
            <h4 id="users">User information</h4>
            <p><b>username: </b><%=user.getUsername()%></p>
            <p><b>password: </b><%=user.getPassword()%></p>
            <p><b>groupname: </b><%=userDAO.getGroup(user.getId()).getUsername()%></p>
        </div>
    </div>
</div>
</body>
</html>
