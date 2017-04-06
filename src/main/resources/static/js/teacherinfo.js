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
                url:"/choosepaper/teacher/findById",
                async:true,
                dataType:'json',
                data:{id:id},
                success:function (data) {
                    $("#id").val(data.id);
                    $("#name").val(data.name);
                    $("#tel").val(data.tel);
                    $("#rank").val(ranktostring(data.rank));
                    $("#gender").val(gendertostring(data.gender))
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

function checkpass() {
    if($("#password1").val()!= $("#password2").val()){
        alert("两次输入的密码不一致");
        $("#password2").val(null);
        return;
    }
}

function submit1() {
    var updateTeacherIn={id:$("#id").val(),name:$("#name").val().trim(),rank:ranktoint($("#rank").val().trim()),
        tel:$("#tel").val(),gender:gendertoint($("#gender").val().trim())}
    $.post({
        url:"/choosepaper/admin/updateTeacher",
        async:true,
        dataType:'json',
        data:JSON.stringify(updateTeacherIn),
        contentType:"application/json",
        success:function (data) {
            if($("#password1").val().trim()=="" || $("#password1").val().trim()==""){
                alert(data.message)
                return;}
            var updateUserIn={userName:$("#id").val().trim(),password:$("#password2").val()}
            $.post({
                url:"/choosepaper/user/update",
                async:true,
                dataType:'json',
                data:JSON.stringify(updateUserIn),
                contentType:"application/json",
                success:function (data) {
                    alert(data.message)
                },
                error:function () {
                    alert("fail")
                }
            })
        },
        error:function () {
            alert("fail")
        }
    });

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