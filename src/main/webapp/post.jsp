<%@ page import="ua.rbolck.rader.dao.PostDAOI" %>
<%@ page import="ua.rbolck.rader.dao.PostDAOImpl" %>
<%@ page import="ua.rbolck.rader.entity.Post" %>
<%@ page import="java.util.Collection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Post" />
</jsp:include>
<div class="container" style="width:80%;">
    <%Post post = (Post) request.getAttribute("post");%>
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">
                <b><a href="<%=(post.getAuthor() == null) ? "" : "/user?id=" + post.getAuthor().getId()%>">
                    <%=(post.getAuthor() == null) ? "DELETED_USER" : post.getAuthor().getUsername()%>:</a></b>
                <%=" " + post.getTitle()%>
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
    <%PostDAOI dbPost = new PostDAOImpl();
        Collection<Post> posts = (Collection<Post>) dbPost.getComments(post.getId());
    for (Post comment : posts) {%>

    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">
                <b><a href="<%=(comment.getAuthor() == null) ? "" : "/user?id=" + comment.getAuthor().getId()%>">
                    <%=(comment.getAuthor() == null) ? "DELETED_USER" : comment.getAuthor().getUsername()%></a></b>
                </a>
                <div style="float: right;">
                    <form role="form" id="removeId=<%=comment.getId()%>"
                          action="<%="/post?action=removeComment&id=" + comment.getId()%>" method="post">
                        <a onclick="like(<%=comment.getId()%>)">
                            <span class="badge likes<%=comment.getId()%>"><%=comment.getLikes()%></span>
                            <span class="glyphicon glyphicon-thumbs-up"></span></a>
                        <a onclick="dislike(<%=comment.getId()%>)">
                            <span class="badge dislikes<%=comment.getId()%>"><%=comment.getDislikes()%></span>
                            <span class="glyphicon glyphicon-thumbs-down"></span></a>
                        <a href="#" onclick="document.getElementById('removeId=<%=comment.getId()%>').submit()">
                            <span class="glyphicon glyphicon-trash"></span></a>
                    </form>
                </div>
            </h3>
        </div>

        <div class="panel-body">
            <%=comment.getContent()%>
        </div>

    </div>

    <%}%>
</div>
<div class="container" style="width:80%;">
    <form role="form" action="/post?action=newComment&id=<%=post.getId()%>" method="post">
        <div class="form-group">
            <label for="textOfPost">Комментарий</label>
            <textarea class="form-control" id="textOfPost" rows="3" name="content" placeholder="Text of comment"></textarea>
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-default btn-lg">Add comment</button>
        </div>
    </form>
</div>
</body>
<jsp:include page="scripts.jsp"/>
<script src="js/post_ajax.js"></script>
</html>
