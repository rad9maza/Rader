<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="utf-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Login Success</title></head>
<body><%
    //allow access only if session exists
    String user = (String) session.getAttribute("user");
    String userName = null;
    String sessionID = null;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("user")) userName = cookie.getValue();
            if (cookie.getName().equals("JSESSIONID")) sessionID = cookie.getValue();
        }
    } %>
<h3>Hi <%=userName %>, Login successful. Your Session ID=<%=sessionID %>
</h3> <br> User=<%=user %> <br> <a href="CheckoutPage.jsp">Checkout Page</a>
<form action="LogoutServlet" method="post"><input type="submit" value="Logout"></form>
</body>
</html>