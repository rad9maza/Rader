<%@ page import="ua.rbolck.rader.entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Edit user" />
</jsp:include>
<%
    User user = (User) request.getAttribute("user");
%>
<div class="container" style="width:80%;">
    <form role="form" action="/user?action=edit&id=<%=user.getId()%>" method="post">
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" class="form-control" id="username" placeholder="Enter name of user"
                   name="username" value="<%=user.getUsername()%>">
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" class="form-control" id="password" placeholder="Enter new password"
                   name="password" value="">
        </div>
        <input type="radio" name="group" value="2" <%=user.getGroup_id()==2 ? "checked" : ""%>>Admin<Br>
        <input type="radio" name="group" value="3" <%=user.getGroup_id()==3 ? "checked" : ""%>>Moderator<Br>
        <input type="radio" name="group" value="4" <%=user.getGroup_id()==4 ? "checked" : ""%>>User<Br>
        <div class="form-group">
            <button type="submit" class="btn btn-default btn-lg">Save user</button>
        </div>
    </form>
</div>
</body>
</html>
