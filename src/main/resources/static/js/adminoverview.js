/**
 * Created by kj on 2017/4/6.
 */
function getInfo() {
    var id = null;
    $.post({
        url:"/choosepaper/user/getUserId",
        async:true,
        dataType:'json',
        contentType:"application/json",
        success:function (data) {
            id=data;
            $.post({
                url:"/choosepaper/admin/overview",
                async:true,
                dataType:'json',
                data:{id:id},
                success:function (data) {
                    $("#paperCount").text(data.paperCount);
                    $("#studentCount").text(data.studentCount);
                    $("#teacherCount").text(data.teacherCount);
                },
                error:function () {
                    alert("fail2")
                }
            })
        },
        error:function () {
            alert("fail")
        }
    })
}