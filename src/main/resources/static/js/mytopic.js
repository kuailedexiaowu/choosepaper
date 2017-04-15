/**
 * Created by kj on 2017/4/12.
 */
function tabelbuilder(data) {
    $("#table").append("<table id='mytopic'><thead><tr><th><input id='leader' type='checkbox'></th>"+
        "<th>编号</th><th>标题</th><th>内容</th><th>创建时间</th></tr></thead><tbody></tbody></table>");
    for (var i=0;i<data.list.length;i++){
        $("tbody").append("<tr></tr>")

        $("tr:last").append("<td><input type='checkbox'></td>");
        $("tr:last").append("<td>"+data.list[i].id+"</td>");
        $("tr:last").append("<td>"+data.list[i].title+"</td>");
        $("tr:last").append("<td>"+data.list[i].context+"</td>");
        $("tr:last").append("<td>"+format(data.list[i].createTime)+"</td>");

    }

    $("table").attr("class","table table-bordered table-hover");
    $("#leader").click(
        function () {
            if($("#leader").is(':checked'))
                $("input:checkbox:not(:checked)").prop("checked",true);
            else
                $("input:checkbox:checked").prop("checked",false);
        })
    $(":input:checkbox:not(:first)").click(

        function () {
            if($(":input:checkbox:not(:first):checked").length==$(":input:checkbox:not(:first)").length)
                $("#leader").prop("checked",true);
            if($(":input:checkbox:not(:first):checked").length!=$(":input:checkbox:not(:first)").length)
                $("#leader").prop("checked",false);

        }
    )
}

function tabelupdate(data) {

    for (var i=0;i<data.list.length;i++){
        $("tbody").append("<tr></tr>")

        $("tr:last").append("<td><input type='checkbox'></td>");
        $("tr:last").append("<td>"+data.list[i].id+"</td>");
        $("tr:last").append("<td>"+data.list[i].title+"</td>");
        $("tr:last").append("<td>"+data.list[i].context+"</td>");
        $("tr:last").append("<td>"+format(data.list[i].createTime)+"</td>");
    }

    $("table").attr("class","table table-bordered table-hover");
    $("#leader").click(
        function () {
            if($("#leader").is(':checked'))
                $("input:checkbox:not(:checked)").prop("checked",true);
            else
                $("input:checkbox:checked").prop("checked",false);
        })
    $(":input:checkbox:not(:first)").click(

        function () {
            if($(":input:checkbox:not(:first):checked").length==$(":input:checkbox:not(:first)").length)
                $("#leader").prop("checked",true);
            if($(":input:checkbox:not(:first):checked").length!=$(":input:checkbox:not(:first)").length)
                $("#leader").prop("checked",false);

        }
    )
}

function mytopiclist(no,size) {
    $.post({
        url:"/choosepaper/topic/listTopicByUserId",
        async:true,
        dataType:'json',
        data:{pageNo:no,pageSize:size},
        success:function (data) {
            tabelbuilder(data)
            var op={
                currentPage: data.currentPage,
                totalPages: data.pageCount,
                numberOfPages:4,
                bootstrapMajorVersion:3,
                onPageClicked:function (event, originalEvent, type, page) {
                    $("tbody").children().remove();
                    teacherpaper2(page,2)
                }
            }
            $('#paginator').bootstrapPaginator(op);

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
    });
}

function addin() {
    $("#add").attr("data-target","#addModel").attr("data-toggle","modal");
}

function addsub() {
    var topicAddInvo = {
        title : $("#title").val().trim(),
        context : $("#context").val().trim(),
    };
    $.post({
        url: "/choosepaper/topic/add",
        async: true,
        dataType: 'json',
        data: JSON.stringify(topicAddInvo),
        contentType: "application/json",
        success: function (data) {
            alert(data.message)
        },
        error: function (data) {
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

function detail(){
    if($("input:checkbox:checked").length==0) {
        alert("您还没有选择");
        return ;
    }
    else if($("input:checkbox:checked:not(#leader)").length>1) {
        alert("目前版本只支持单一查看");
        return ;
    }
    else{
        $("#detail").attr("data-target","#detailModel").attr("data-toggle","modal");
        var trnum=$(":input:checkbox:checked").parent().parent().index();
        var trele=$("#table").find('tr').eq(trnum+1).find("td").eq(1).text();
        $.post({
            url:"/choosepaper/topic/detail",
            async:true,
            dataType:'json',
            data:{id:trele},
            success:function (data) {
                $("#title2").val(data.title);
                $("#create_time").val(format(data.createTime));
                $("#context2").val(data.context);
                $("#commented").empty();
                for(var i=0;i<data.commentInfos.length;i++) {
                    var element = $("<label></label>").text(format(data.commentInfos[i].createTime)+data.commentInfos[i].speakName+":"
                        +data.commentInfos[i].context).attr({"class":"col-sm-10 col-sm-offset-2 commentId","id":data.commentInfos[i].id});
                    var element2 = $( "<button>回复</button>").attr({"class":"col-sm-1 col-sm-offset-2 replay"})
                        .css({"width":"65px","height":"25px"});
                    $("#commented").append(element).append(element2).after($("<br>"));

                    for(var j=0;j<data.commentInfos[i].replays.length;j++){
                        var ele3=$("<label></label>").text(format(data.commentInfos[i].replays[j].createTime)+data.commentInfos[i].replays[j].speakName+":"
                        +data.commentInfos[i].replays[j].context).attr({"class":"col-sm-10 col-sm-offset-3"});
                        var ele4= $( "<button>回复</button>").attr({"class":"col-sm-1 col-sm-offset-3 replay"})
                            .css({"width":"65px","height":"25px"});
                        element2.after(ele4).after(ele3);
                    }
                }
                $(".replay").click(function () {
                    $(".replay").siblings(".send").remove();
                    var element3 = $(" <textarea></textarea>").attr({"class":"col-sm-5 send","rows":"1"})
                    var element4 = $("<button>发送</button>").attr({"class":"col-sm-5 send"}) .css({"width":"65px","height":"25px"});
                    $(this).after(element4).after(element3);
                    $(":button.send").click(
                        function () {
                           var replayAddInvo={context:$(".send:first").val(),commentId:$(this).prevAll().filter(".commentId").first().attr("id")};
                            $.post({
                                url:"/choosepaper/replay/add",
                                async:true,
                                dataType:'text',
                                data:JSON.stringify(replayAddInvo),
                                contentType:"application/json",
                                success:function (data) {
                                    alert("success")
                                },
                                error:function () {
                                    alert("网络问题,回复失败")
                                }

                            })
                        }
                    )
                })

                $.post({
                    url:"/choosepaper/user/getUserName",
                    async:true,
                    dataType:'text',
                    data:{id:data.ownerId},
                    success:function (data) {
                        $("#owner").val(data)
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
}

function deletein(){
    if($("input:checkbox:checked").length==0) {
        if($("#delete").val("data-target")!=null&&$("#update").val("data-toggle")!=null) {
            $("#delete").removeAttr("data-target");
            $("#delete").removeAttr("data-toggle");
            alert("您还没有选择");
        }
        else
            alert("您还没有选择");
        return;
    }

    var $ids=$("input:checkbox:checked:not(#leader)");
    var ids=[];
    for(var i=0;i<$ids.length;i++){
        var trnum=$($ids[i]).parent().parent().index().toString();
        trnum++;
        var id=$("#table").find('tr').eq(trnum).find("td").eq(1).text();
        ids.push(id);
    };

    $.post({
        url: "/choosepaper/topic/delete",
        async: true,
        dataType: 'json',
        data:JSON.stringify(ids),
        contentType:"application/json",
        success: function (data) {
            alert(data.message)
        },
        error: function (data) {
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

function commenting() {
    var trnum=$(":input:checkbox:checked").parent().parent().index();
    var trele=$("#table").find('tr').eq(trnum+1).find("td").eq(1).text();
    var commentAddInvo = {topicId:trele,context:$("#comment").val().trim()}
    $.post({
        url: "/choosepaper/comment/add",
        async: true,
        dataType: 'json',
        data:JSON.stringify(commentAddInvo),
        contentType:"application/json",
        success: function (data) {
            alert(data.message)
        },
        error: function (data) {
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

function format(timestamp)
{
    //timestamp是整数，否则要parseInt转换,不会出现少个0的情况
    var time = new Date(timestamp);
    var year = time.getFullYear();
    var month = time.getMonth()+1;
    var date = time.getDate();
    var hours = time.getHours();
    var minutes = time.getMinutes();
    var seconds = time.getSeconds();
    return year+'-'+add0(month)+'-'+add0(date)+' '+add0(hours)+':'+add0(minutes)+':'+add0(seconds);
}
function add0(m){return m<10?'0'+m:m }