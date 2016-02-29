<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Header</title>
</head>
<body>
<div class="navbar navbar-default" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="/index.jsp">Rader</a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <%
                    String[] urls = request.getRequestURL().toString().split("/");
                    String url = urls[urls.length - 1];
                %>
                <li
                        <% if ("allPosts.jsp".equals(url)) {%><%="class=\"active\""%><%}%>
                ><a href="/post">Feed</a>
                </li>
                <li
                        <% if ("allUsers.jsp".equals(url)) {%><%="class=\"active\""%><%}%>
                ><a href="/allUsers.jsp">Users</a></li>
            </ul>
            <form action="LogoutServlet" method="post" id="super">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="#">User</a></li>
                    <li><a href="#" onclick="document.getElementById('super').submit(value='Logout')">Logout</a></li>
                </ul>
            </form>
        </div>
    </div>
</div>
</body>
</html>
