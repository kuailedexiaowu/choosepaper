function tabelbuilder(data) {
    $("#table").append("<table id='tpaper'><thead><tr><th><input id='leader' type='checkbox'></th>"+
        "<th>编号</th><th>名称</th><th>描述</th><th>要求</th><th>是否被选</th><th>发布时间</th></tr></thead><tbody></tbody></table>");
    for (var i=0;i<data.list.length;i++){
        $("tbody").append("<tr></tr>")

        $("tr:last").append("<td><input type='checkbox'></td>");
        $("tr:last").append("<td>"+data.list[i].id+"</td>");
        $("tr:last").append("<td>"+data.list[i].name+"</td>");
        $("tr:last").append("<td>"+data.list[i].description.substring(0,12)+"......"+"</td>");
        $("tr:last").append("<td>"+data.list[i].demand.substring(0,12)+"......"+"</td>");
        if(data.list[i].studentId==null){
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
        $("tr:last").append("<td>"+data.list[i].description.substring(0,12)+"......"+"</td>");
        $("tr:last").append("<td>"+data.list[i].demand.substring(0,12)+"......"+"</td>");
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
                    teacherpaper2(page,5)
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
                    teacherpaper2(page,5)
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
    addcheck();
    $("#add").attr("data-target","#addModel").attr("data-toggle","modal");
    $("#addtopic").attr({"disabled":"disabled"});
}

function addcheck() {
    $('#addForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            name: {
                message: '名称验证失败',
                validators: {
                    notEmpty: {
                        message: '名称不能为空'
                    }
                }
            },
            description: {
                message: '描述验证失败',
                validators: {
                    notEmpty: {
                        message: '描述不能为空'
                    }
                }
            },
            demand: {
                message: '要求验证失败',
                validators: {
                    notEmpty: {
                        message: '要求不能为空'
                    },
                    /* regexp: {
                     regexp: /^1[3|5|8]{1}[0-9]{9}$/,
                     message: '请输入正确的手机号码'
                     }*/
                }
            },

        },
    });
};

$('#addModel').on('hidden.bs.modal', function() {
    $("#addForm").data('bootstrapValidator').destroy();
    $('#addForm').data('bootstrapValidator', null);
    $("#addForm input").val("");
    $("#addForm textarea").val("");
});

$("#addForm input").keyup(function () {
    if($("#addForm").data('bootstrapValidator').isValid()) {
        $("#addtopic").removeAttr("disabled");
    }
    else{
        $("#addtopic").attr({"disabled":"disabled"});
    }
})

$("#addForm textarea").keyup(function () {
    if($("#addForm").data('bootstrapValidator').isValid()) {
        $("#addtopic").removeAttr("disabled");
    }
    else{
        $("#addtopic").attr({"disabled":"disabled"});
    }
})

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
            location.reload();
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
        $.post({
            url:"/choosepaper/paper/detail",
            async:true,
            dataType:'json',
            data:{id:trele},
            success:function (data) {
                $("#name2").val(data.name);
                $("#id2").val(data.id);
                $("#description2").val(data.description);
                $("#demand2").val(data.demand);

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

$(function () {
    $('#updateForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            name2: {
                message: '名称验证失败',
                validators: {
                    notEmpty: {
                        message: '名称不能为空'
                    }
                }
            },
            description2: {
                message: '描述验证失败',
                validators: {
                    notEmpty: {
                        message: '描述不能为空'
                    }
                }
            },
            demand2: {
                message: '要求验证失败',
                validators: {
                    notEmpty: {
                        message: '要求不能为空'
                    },
                    /* regexp: {
                     regexp: /^1[3|5|8]{1}[0-9]{9}$/,
                     message: '请输入正确的手机号码'
                     }*/
                }
            },

        },
    });
});

$("#updateForm input").keyup(function () {
    $("#updateForm").data('bootstrapValidator').validate();
    if($("#updateForm").data('bootstrapValidator').isValid()) {
        $("#updatetopic").removeAttr("disabled");
    }
    else{
        $("#updatetopic").attr({"disabled":"disabled"});
    }
})
$("#updateForm textarea").keyup(function () {
    $("#updateForm").data('bootstrapValidator').validate();
    if($("#updateForm").data('bootstrapValidator').isValid()) {
        $("#updatetopic").removeAttr("disabled");
    }
    else{
        $("#updatetopic").attr({"disabled":"disabled"});
    }
})

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
            location.reload()
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

function detail(){
    if($("input:checkbox:checked").length==0) {
        alert("您还没有选择");
        return ;
    }
    else if($("input:checkbox:checked:not(#leader)").length>1) {
        if($("#detail").val("data-target")!=null&&$("#detail").val("data-toggle")!=null) {
            $("#detail").removeAttr("data-target");
            $("#detail").removeAttr("data-toggle");
            alert("目前版本只支持单一查看");
        }
        else
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
                $("#name3").val(data.name);
                $("#create_time3").val(format(data.createTime));
                $("#description3").val(data.description);
                $("#demand3").val(data.demand);
                if(data.student != null){
                 $("#sid").val(data.student.id);
                 $("#sname").val(data.student.name);
                 $("#smajor").val(data.student.major);
                }
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