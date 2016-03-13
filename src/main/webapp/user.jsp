<%@ page import="ua.rbolck.rader.entity.User" %>
<%@ page import="ua.rbolck.rader.dao.UserDAOImpl" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%User user = (User) request.getAttribute("user");%>
<%UserDAOImpl userDAO = new UserDAOImpl();%>

<jsp:include page="header.jsp">
    <jsp:param name="title" value="User <%=user.getUsername()%>"/>
</jsp:include>
<div class="container" style="width:80%;">
    <div class="bs-example">
        <div data-spy="scroll" align="center" data-target="#navbar-example2" data-offset="0" class="scrollspy-example">
            <h4 id="users">User information</h4>

            <p><b>username: </b><%=user.getUsername()%>
            </p>

            <p><b>password: </b><%=user.getPassword()%>
            </p>

            <p><b>groupname: </b><%=userDAO.getGroup(user.getId()).getUsername()%>
            </p>

            <form role="form" action="/user?action=remove&id=<%=user.getId()%>" method="post">
                <a href="/user?action=edit&id=<%=user.getId()%>" class="btn btn-default btn-lg" role="button">Edit
                </a>
                <button type="submit" class="btn btn-default btn-lg">Delete</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
