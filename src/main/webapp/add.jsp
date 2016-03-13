<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Add" />
</jsp:include>
<div class="container" style="width:80%;">
    <form role="form" action="/post?action=new" method="post">
        <div class="form-group">
            <label for="title">Заголовок</label>
            <input type="text" class="form-control" id="title" placeholder="Title" name="title" value="">
        </div>
        <div class="form-group">
            <label for="textOfPost">Содержимое</label>
            <textarea class="form-control" id="textOfPost" rows="3" name="content" placeholder="Text of post"></textarea>
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-default btn-lg">Add post</button>
        </div>
    </form>
</div>
</body>
</html>
