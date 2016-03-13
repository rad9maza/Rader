<%@ page import="ua.rbolck.rader.dao.UserDAOImpl" %>
<%@ page import="ua.rbolck.rader.entity.User" %>
<%@ page import="java.util.Collection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Users" />
</jsp:include>
<div class="container" style="width:80%;">
    <div class="bs-example">
        <div data-spy="scroll" data-target="#navbar-example2" data-offset="0" class="scrollspy-example">
            <%  UserDAOImpl userDAO = new UserDAOImpl();%>

            <%Collection<User> admins = (Collection<User>) request.getAttribute("admins");%>
            <%boolean isAdmins = admins == null || admins.isEmpty();%>
            <%=!isAdmins ? "<h4><a href=\"/user?group_id=" + admins.iterator().next().getGroup_id() + "\">@" +
                    (userDAO.getGroup(admins.iterator().next().getId())).getUsername() + "</a></h4>" : ""%>
            <%for (User admin : admins) {%>
            <%=!isAdmins ? "<p>&nbsp;&nbsp;   <a href=\"/user?id=" + admin.getId() + "\">" + admin.getUsername() + "</a></p>" : ""%>
            <%}%>

            <%Collection<User> moders = (Collection<User>) request.getAttribute("moders");%>
            <%boolean isModers = moders == null || moders.isEmpty();%>
            <%=!isModers ? "<h4><a href=\"/user?group_id=" + moders.iterator().next().getGroup_id() + "\">@" +
                    userDAO.getGroup(moders.iterator().next().getId()).getUsername() + "</a></h4>" : ""%>
            <%for (User moder : moders) {%>
            <%=!isModers ? "<p>&nbsp;&nbsp;<a href=\"/user?id=" + moder.getId() + "\">" + moder.getUsername() + "</a></p>" : ""%>
            <%}%>

            <%Collection<User> users = (Collection<User>) request.getAttribute("users");%>
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
