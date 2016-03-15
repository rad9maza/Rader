window.onscroll = function (ev) {
    if ((window.innerHeight + window.scrollY) >= document.body.scrollHeight) {
        addPosts()
        console.log("Bottom of page");
    }
};

function addPosts() {
    $.ajax({
        url: "ajax/post",
        data: {
            action: "getNextFivePosts",
            fromPost: 10,
            delta: 5
        },
        method: "POST",
        success: function ($data) {
            for (var i = 0; i < ($data.posts).length; i++) {

                var tmpHTML = (
                "<div class=\"panel panel-default\">"+
                    "<div class=\"panel-heading\">" +
                        "<h3 class=\"panel-title\">" +
                            "<b><a href=" + /*$data.posts[i].author[0].authorID == null ? "\"\"" : */"\"/user?id=" + $data.posts[i].author[0].authorID + "\">" +
                            /*$data.posts[i].author[0].authorName == null ? "\"DELETED_USER\"" : */$data.posts[i].author[0].authorName + ":</a></b>" +
                            "<a href=\"/post?id=" + $data.posts[i].id + "\"> " + $data.posts[i].title + "</a>" +
                            "<div style=\"float : right;\">" +
                                "<form role=\"form\" id=\"removeId=" + $data.posts[i].id + "\" action=\"/post?action=remove&id=" + $data.posts[i].id + "\" method=\"post\">" +
                                    "<a onclick=\"like(" + $data.posts[i].id + ")\">" +
                                        "<span class=\"badge likes" + $data.posts[i].id + "\">" + $data.posts[i].likes + "</span>" +
                                        "<span class=\"glyphicon glyphicon-thumbs-up\"></span></a>" +
                                    "<a onclick=\"dislike(" + $data.posts[i].id + ")\">" +
                                        "<span class=\"badge dislikes" + $data.posts[i].id + "\">" + $data.posts[i].dislikes + "</span>" +
                                        "<span class=\"glyphicon glyphicon-thumbs-down\"></span></a>" +
                                    "<a href=\"/post?action=edit&id=" + $data.posts[i].id + "\">" +
                                        "<span class=\"glyphicon glyphicon-pencil\"></span></a>" +
                                    "<a href=\"#\" onclick=\"document.getElementById('removeId=" + $data.posts[i].id + "').submit()\">" +
                                        "<span class=\"glyphicon glyphicon-trash\"></span></a>" +
                                "</form>" +
                            "</div>" +
                        "</h3>" +
                    "</div>" +

                    "<div class=\"panel-body\">" +
                        $data.posts[i].content +
                    "</div>" +
                "</div>");

                $("div.container").append(tmpHTML);
            }
        },
        error: function (error) {
            console.log("error");
            console.error(error);
        }
    })
}