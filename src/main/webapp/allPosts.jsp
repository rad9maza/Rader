<%@ page import="ua.rbolck.rader.entity.Post" %>
<%@ page import="java.util.Collection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Feed" />
</jsp:include>
<div class="container" style="width:80%;">
    <%Collection<Post> posts = (Collection<Post>) request.getAttribute("posts");%>
    <%for (Post post : posts) {%>

    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">
                <b><a href="<%=(post.getAuthor() == null) ? "" : "/user?id=" + post.getAuthor().getId()%>">
                    <%=(post.getAuthor() == null) ? "DELETED_USER" : post.getAuthor().getUsername()%>:</a></b>
                <a href="<%="/post?id=" + post.getId()%>"><%=" " + post.getTitle()%>
                </a>
                <div style="float: right;">
                    <form role="form" id="removeId=<%=post.getId()%>"
                          action="<%="/post?action=remove&id=" + post.getId()%>" method="post">
                        <a onclick="like(<%=post.getId()%>)">
                            <span class="badge likes<%=post.getId()%>"><%=post.getLikes()%></span>
                            <span class="glyphicon glyphicon-thumbs-up"></span></a>
                        <a onclick="dislike(<%=post.getId()%>)">
                            <span class="badge dislikes<%=post.getId()%>"><%=post.getDislikes()%></span>
                            <span class="glyphicon glyphicon-thumbs-down"></span></a>
                        <a href="<%="/post?action=edit&id=" + post.getId()%>">
                            <span class="glyphicon glyphicon-pencil"></span></a>
                        <a href="#" onclick="document.getElementById('removeId=<%=post.getId()%>').submit()">
                            <span class="glyphicon glyphicon-trash"></span></a>
                    </form>
                </div>
            </h3>
        </div>

        <div class="panel-body">
            <%=post.getContent()%>
        </div>

    </div>
    <%}%>
    <%--TODO http://stackoverflow.com/questions/3898130/check-if-a-user-has-scrolled-to-the-bottom--%>
</div>
</body>
<jsp:include page="scripts.jsp"/>
<script src="js/post_ajax.js"></script>
</html>
