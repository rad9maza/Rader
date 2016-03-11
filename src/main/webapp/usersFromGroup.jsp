<%@ page import="java.util.Collection" %>
<%@ page import="ua.rbolck.rader.entity.User" %>
<%@ page import="ua.rbolck.rader.dao.UserDAOI" %>
<%@ page import="ua.rbolck.rader.dao.UserDAOImpl" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Users</title>
    <link rel='StyleSheet' type='text/css' href='css/bootstrap.css'/>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container" style="width:80%;">
    <div class="bs-example">
        <div data-spy="scroll" data-target="#navbar-example2" data-offset="0" class="scrollspy-example">
            <%  UserDAOImpl userDAO = new UserDAOImpl();
                Collection<User> users = (Collection<User>) request.getAttribute("users");%>
            <%boolean isUsers = users == null || users.isEmpty();%>
            <%=!isUsers ? "<h4><a href=\"/user?group_id=" + users.iterator().next().getGroup_id() + "\">@" +
                    userDAO.getGroup(users.iterator().next().getId()).getUsername() + "</a></h4>" : ""%>
            <%for (User user : users) {%>
            <%=!isUsers ? "<p>&nbsp;&nbsp;<a href=\"/user?id=" + user.getId() + "\">" + user.getUsername() + "</a></p>" : ""%>
            <%}%>
        </div>
    </div>
</div>
</body>
</html>
