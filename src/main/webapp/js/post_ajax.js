function like(id) {
    var likes_span = $("span.likes" + id);
    dislikes_now = parseInt(likes_span.html());
    $.ajax({
        url: "ajax/post",
        data: {
            action: "rating",
            id: id,
            delta: 1
        },
        method: "POST",
        success: function (data) {
            //todo handle success=false answers
            console.log("success");
            likes_span.html(dislikes_now + 1);
        },
        error: function (error) {
            console.log("error");
            console.error(error);
        }
    })
}
function dislike(id) {
    var dislikes_span = $("span.dislikes" + id);
    dislikes_now = parseInt(dislikes_span.html());
    $.ajax({
        url: "ajax/post",
        data: {
            action: "rating",
            id: id,
            delta: -1
        },
        method: "POST",
        success: function (data) {
            console.log("success");
            dislikes_span.html(dislikes_now + 1);
        },
        error: function (error) {
            console.log("error");
            console.error(error);
        }
    })
}