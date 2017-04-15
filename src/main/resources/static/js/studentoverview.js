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
                url:"/choosepaper/student/overview",
                async:true,
                dataType:'json',
                data:{id:id},
                success:function (data) {
                    $("#total").text(data.total);
                    $("#choosed").text(data.choosed/data.total*100+"%")
                        .css("width",data.choosed/data.total*100+"%");
                    $("#unchoosed").text(data.unchoosed/data.total*100+"%")
                        .css("width",data.unchoosed/data.total*100+"%");
                },
                error:function () {
                    alert("fail2")
                }
            })
        },
        error:function (data) {
            var response=data.responseText;
            if("indexPage".indexOf(response)){
                window.location.href="/choosepaper/index.html"
            }
            else {
                alert("网络异常")
            }
        }
    })
}