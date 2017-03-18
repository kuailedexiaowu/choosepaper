function tabelbuilder(data) {
    $("#table").append("<table id='tpaper'><thead><tr><th><input id='leader' type='checkbox'></th>"+
        "<th>编号</th><th>名称</th><th>描述</th><th>要求</th><th>是否被选</th><th>发布时间</th></tr></thead><tbody></tbody></table>");
    for (var i=0;i<data.list.length;i++){
        $("tbody").append("<tr></tr>")

        $("tr:last").append("<td><input type='checkbox'></td>");
        $("tr:last").append("<td>"+data.list[i].id+"</td>");
        $("tr:last").append("<td>"+data.list[i].name+"</td>");
        $("tr:last").append("<td>"+data.list[i].description+"</td>");
        $("tr:last").append("<td>"+data.list[i].demand+"</td>");
        if(data.list[i].student_id==null){
            $("tr:last").append("<td>暂未选择</td>");}
        else{
            $("tr:last").append("<td>已被选择</td>");}
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
        if(data.list[i].student_id!=null){
            $("tr:last").append("<td>暂未选择</td>");}
        else{
            $("tr:last").append("<td>已被选择</td>");}
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

function teacherpaper(no,size) {
    $.post({
        url:"/choosepaper/paper/list",
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
                window.location.href="/index.html"
            }
        }
    });
}

function teacherpaper2(no,size) {
    $.post({
        url:"/choosepaper/paper/list",
        async:true,
        dataType:'json',
        data:{pageNo:no,pageSize:size},
        success:function (data) {
            tabelupdate(data)
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
                window.location.href="/index.html"
            }
        }
    });
}

function addin() {
    $("#add").attr("data-target","#addModel").attr("data-toggle","modal");
}

function addsub() {
    var addPaperIn = {
        name: $("#name").val().trim(),
        description: $("#description").val().trim(),
        demand: $("#demand").val().trim(),
    };
    $.post({
        url: "/choosepaper/paper/add",
        async: true,
        dataType: 'json',
        data: JSON.stringify(addPaperIn),
        contentType: "application/json",
        success: function (data) {
            alert(data.message)
        },
        error: function () {
            alert("fail")
        }
    })
}

function update() {
    if($("input:checkbox:checked").length==0) {
        if($("#update").val("data-target")!=null&&$("#update").val("data-toggle")!=null) {
            $("#update").removeAttr("data-target");
            $("#update").removeAttr("data-toggle");
            alert("您还没有选择");
        }
        else
            alert("您还没有选择");
        return ;
    }
    else if($("input:checkbox:checked:not(#leader)").length>1) {
        if($("#update").val("data-target")!=null&&$("#update").val("data-toggle")!=null) {
            $("#update").removeAttr("data-target");
            $("#update").removeAttr("data-toggle");
            alert("目前版本只支持单一更新");
        }
        else
            alert("目前版本只支持单一更新");
        return ;
    }

    else{
        $("#update").attr("data-target","#updateModel").attr("data-toggle","modal");
        var trnum=$(":input:checkbox:checked").parent().parent().index();
        var trele=$("#tpaper").find('tr').eq(trnum+1).find("td").eq(1).text();
        $("#id2").val(trele.trim());
        trele=$("#tpaper").find('tr').eq(trnum+1).find("td").eq(2).text();
        $("#name2").val(trele.trim());
        trele=$("#tpaper").find('tr').eq(trnum+1).find("td").eq(3).text();
        $("#description2").val(trele.trim());
        trele=$("#tpaper").find('tr').eq(trnum+1).find("td").eq(4).text();
        $("#demand2").val(trele.trim());
    }
}

function updatesub() {
    var updatePaperIn={id:$("#id2").val(),name:$("#name2").val().trim(),description:$("#description2").val().trim(),demand:$("#demand2").val()};
    $.post({
        url:"/choosepaper/paper/update",
        async:true,
        dataType:'json',
        data:JSON.stringify(updatePaperIn),
        contentType:"application/json",
        success:function (data) {
            alert(data.message)
        },
        error:function () {
            alert("fail")
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
            url:"/choosepaper/paper/findByTeacherId",
            async:true,
            dataType:'json',
            data:{id:trele},
            success:function (data) {
                $("#name3").val(data.name);
                $("#create_time3").val(format(data.createTime));
                $("#description3").val(data.description);
                $("#demand3").val(data.demand);
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
        url: "/choosepaper/paper/delete",
        async: true,
        dataType: 'json',
        data:JSON.stringify(ids),
        contentType:"application/json",
        success: function (data) {
            alert(data.message)
        },
        error: function () {
            alert("fail")
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