function tabelbuilder(data) {
    $("#table").append("<table id='astudent'><thead><tr><th><input id='leader' type='checkbox'></th>"+
        "<th>编号</th><th>名称</th><th>专业</th><th>电话</th><th>性别</th><th>账号密码</th></tr></thead><tbody></tbody></table>");
    for (var i=0;i<data.list.length;i++){
        $("tbody").append("<tr></tr>")

        $("tr:last").append("<td><input type='checkbox'></td>");
        $("tr:last").append("<td>"+data.list[i].id+"</td>");
        $("tr:last").append("<td>"+data.list[i].name+"</td>");
        $("tr:last").append("<td>"+data.list[i].major+"</td>");
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
        $("tr:last").append("<td>"+data.list[i].major+"</td>");
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

function adminstudent(no,size) {
    $.post({
        url:"/choosepaper/admin/listStudent",
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

function adminstudent2(no,size) {
    $.post({
        url:"/choosepaper/admin/listStudent",
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
    var addStudentIn = {
        id : $("#id").val().trim(),
        password : $("#password").val().trim(),
        name: $("#name").val().trim(),
        gender : gendertoint($("#gender").val().trim()),
        tel: $("#tel").val().trim(),
        major:$("#major").val().trim()
    };
    $.post({
        url: "/choosepaper/admin/addStudent",
        async: true,
        dataType: 'json',
        data: JSON.stringify(addStudentIn),
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
        var trele=$("#astudent").find('tr').eq(trnum+1).find("td").eq(1).text();
        $("#id2").val(trele.trim());
        trele=$("#astudent").find('tr').eq(trnum+1).find("td").eq(2).text();
        $("#name2").val(trele.trim());
        trele=$("#astudent").find('tr').eq(trnum+1).find("td").eq(3).text();
        $("#major2").val(trele.trim());
        trele=$("#astudent").find('tr').eq(trnum+1).find("td").eq(4).text();
        $("#tel2").val(trele.trim());
        trele=$("#astudent").find('tr').eq(trnum+1).find("td").eq(5).text();
        $("#gender2").val(trele.trim());
    }
}

function updatesub() {
    var updateTeacherIn={id:$("#id2").val(),name:$("#name2").val().trim(),major:$("#major2").val().trim(),
        tel:$("#tel2").val(),gender:gendertoint($("#gender2").val().trim())};
    $.post({
        url:"/choosepaper/admin/updateStudent",
        async:true,
        dataType:'json',
        data:JSON.stringify(updateTeacherIn),
        contentType:"application/json",
        success:function (data) {
            alert(data.message)
        },
        error:function () {
            alert("fail")
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
        url: "/choosepaper/admin/deleteStudent",
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
        var trele=$("#astudent").find('tr').eq(trnum+1).find("td").eq(1).text();
        var resetTeacherIn ={ id:trele,password:trele};
        $.post({
            url: "/choosepaper/admin/resetStudent",
            async: true,
            dataType: 'json',
            data:JSON.stringify(resetTeacherIn),
            contentType:"application/json",
            success: function (data) {
                alert(data.message)
            },
            error: function () {
                alert("fail")
            }
        })
    }
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