function tabelbuilder(data) {
    $("#table").append("<table id='spaper'><thead><tr><th><input id='leader' type='checkbox'></th>"+
        "<th>编号</th><th>名称</th><th>描述</th><th>要求</th><th>教师编号</th><th>发布时间</th></tr></thead><tbody></tbody></table>");
    for (var i=0;i<data.list.length;i++){
        $("tbody").append("<tr></tr>")

        $("tr:last").append("<td><input type='checkbox'></td>");
        $("tr:last").append("<td>"+data.list[i].id+"</td>");
        $("tr:last").append("<td>"+data.list[i].name+"</td>");
        $("tr:last").append("<td>"+data.list[i].description+"</td>");
        $("tr:last").append("<td>"+data.list[i].demand+"</td>");
        $("tr:last").append("<td>"+data.list[i].teacherId+"</td>");
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
        $("tr:last").append("<td>"+data.list[i].name+"</td>");
        $("tr:last").append("<td>"+data.list[i].description+"</td>");
        $("tr:last").append("<td>"+data.list[i].demand+"</td>");
        $("tr:last").append("<td>"+data.list[i].teacherId+"</td>");
        $("tr:last").append("<td>"+data.list[i].createTime+"</td>");
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

function studentpaper(no,size) {
    $.post({
        url:"/choosepaper/paper/listUnchoosed",
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

function choose(){
    if($("input:checkbox:checked").length==0) {
        alert("您还没有选择");
        return ;
    }
    else if($("input:checkbox:checked:not(#leader)").length>1) {
        alert("目前版本只支持单一查看");
        return ;
    }
    else{
        var result=confirm("确认选择，确认后将无法取消");
        if(!result){
            return;
        }
        var trnum=$(":input:checkbox:checked").parent().parent().index();
        var trele=$("#table").find('tr').eq(trnum+1).find("td").eq(1).text();
        $.post({
            url:"/choosepaper/paper/choose",
            async:true,
            dataType:'json',
            data:{id:trele},
            success:function (data) {
               alert(data.message)
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
            url:"/choosepaper/paper/detail",
            async:true,
            dataType:'json',
            data:{id:trele},
            success:function (data) {
                $("#teachername").val(data.teacher.name);
                $("#rank").val(ranktostring(data.teacher.rank));
                $("#name").val(data.name);
                $("#create_time").val(format(data.createTime));
                $("#description").val(data.description);
                $("#demand").val(data.demand);
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
}

function ranktostring(flag){
    if(flag == 1)
        return "讲师";
    if(flag == 2)
        return "副教授";
    if(flag == 3)
        return "教授";
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