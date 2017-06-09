function tabelbuilder(data) {
    $("#table").append("<table id='ateacher'><thead><tr><th><input id='leader' type='checkbox'></th>"+
        "<th>编号</th><th>名称</th><th>职称</th><th>电话</th><th>性别</th><th>账号密码</th></tr></thead><tbody></tbody></table>");
    for (var i=0;i<data.list.length;i++){
        $("tbody").append("<tr></tr>")

        $("tr:last").append("<td><input type='checkbox'></td>");
        $("tr:last").append("<td>"+data.list[i].id+"</td>");
        $("tr:last").append("<td>"+data.list[i].name+"</td>");
        $("tr:last").append("<td>"+ranktostring(data.list[i].rank)+"</td>");
        $("tr:last").append("<td>"+data.list[i].tel+"</td>");
        $("tr:last").append("<td>"+gendertostring(data.list[i].gender)+"</td>");
        $("tr:last").append("<td>"+data.list[i].password+"</td>");
        
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
        $("tr:last").append("<td>"+ranktostring(data.list[i].rank)+"</td>");
        $("tr:last").append("<td>"+data.list[i].tel+"</td>");
        $("tr:last").append("<td>"+gendertostring(data.list[i].gender)+"</td>");
        $("tr:last").append("<td>"+data.list[i].password+"</td>");
       
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

function adminteacher(no,size) {
    $.post({
        url:"/choosepaper/admin/listTeacher",
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
                    adminteacher2(page,5)
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

function adminteacher2(no,size) {
    $.post({
        url:"/choosepaper/admin/listTeacher",
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
function addcheck() {
    $('#addForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            id: {
                message: '用户账号验证失败',
                validators: {
                    notEmpty: {
                        message: '用户账号不能为空'
                    }
                }
            },
            password: {
                message: '密码验证失败',
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    },
                    identical: {//相同
                        field: 'id', //需要进行比较的id值
                        message: '两次密码不一致'
                    }
                }
            },
            name: {
                message: '名称验证失败',
                validators: {
                    notEmpty: {
                        message: '名称不能为空'
                    }
                }
            },
            tel: {
                message: '电话验证失败',
                validators: {
                    notEmpty: {
                        message: '电话不能为空'
                    },
                    stringLength: {
                        min: 11,
                        max: 11,
                        message: '请输入11位手机号码'
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
    $("#addForm input").val("")
});
function addin() {
    addcheck();
    $("#add").attr("data-target","#addModel").attr("data-toggle","modal");
    $("#addTeacher").attr({"disabled":"disabled"});
}
$("#addForm input").keyup(function () {
    if($("#addForm").data('bootstrapValidator').isValid()) {
        $("#addTeacher").removeAttr("disabled");
    }
    else{
        $("#addTeacher").attr({"disabled":"disabled"});
    }
})
function addsub() {
    var addTeacherIn = {
        id : $("#id").val().trim(),
        password : $("#password").val().trim(),
        name: $("#name").val().trim(),
        gender : gendertoint($("#gender").val().trim()),
        tel: $("#tel").val().trim(),
        rank: ranktoint($("#rank").val().trim())
    };
    $.post({
        url: "/choosepaper/admin/addTeacher",
        async: true,
        dataType: 'json',
        data: JSON.stringify(addTeacherIn),
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
        var trele=$("#ateacher").find('tr').eq(trnum+1).find("td").eq(1).text();
        $("#id2").val(trele.trim());
        trele=$("#ateacher").find('tr').eq(trnum+1).find("td").eq(2).text();
        $("#name2").val(trele.trim());
        trele=$("#ateacher").find('tr').eq(trnum+1).find("td").eq(3).text();
        $("#rank2").val(trele.trim());
        trele=$("#ateacher").find('tr').eq(trnum+1).find("td").eq(4).text();
        $("#tel2").val(trele.trim());
        trele=$("#ateacher").find('tr').eq(trnum+1).find("td").eq(5).text();
        $("#gender2").val(trele.trim());
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
            tel2: {
                message: '电话验证失败',
                validators: {
                    notEmpty: {
                        message: '电话不能为空'
                    },
                    stringLength: {
                        min: 11,
                        max: 11,
                        message: '请输入11位手机号码'
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
        $("#updateTeacher").removeAttr("disabled");
    }
    else{
        $("#updateTeacher").attr({"disabled":"disabled"});
    }
})

function updatesub() {
    var updateTeacherIn={id:$("#id2").val(),name:$("#name2").val().trim(),rank:ranktoint($("#rank2").val().trim()),
                        tel:$("#tel2").val(),gender:gendertoint($("#gender2").val().trim())};
    $.post({
        url:"/choosepaper/admin/updateTeacher",
        async:true,
        dataType:'json',
        data:JSON.stringify(updateTeacherIn),
        contentType:"application/json",
        success:function (data) {
            alert(data.message)
            location.reload();
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
        url: "/choosepaper/admin/deleteTeacher",
        async: true,
        dataType: 'json',
        data:JSON.stringify(ids),
        contentType:"application/json",
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

function reset(){
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
            alert("目前版本只支持单一重置");
        }
        else
            alert("目前版本只支持单一重置");
        return ;
    }
    else{
        var trnum=$(":input:checkbox:checked").parent().parent().index();
        var trele=$("#ateacher").find('tr').eq(trnum+1).find("td").eq(1).text();
        var resetTeacherIn ={ id:trele,password:trele};
        $.post({
            url: "/choosepaper/admin/resetTeacher",
            async: true,
            dataType: 'json',
            data:JSON.stringify(resetTeacherIn),
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
}

function ranktostring(flag){
    if(flag == 1)
        return "讲师";
    if(flag == 2)
        return "副教授";
    if(flag == 3)
        return "教授";
}

function ranktoint( rank ) {
    if(rank == "讲师")
        return 1;
    if(rank == "副教授")
        return 2;
    if(rank == "教授")
        return 3;
}

function gendertostring(flag){
    if(flag == 0)
        return "女";
    if(flag == 1)
        return "男";
}

function gendertoint(gender){
    if(gender == "女")
        return 0;
    if(gender == "男")
        return 1;
}
