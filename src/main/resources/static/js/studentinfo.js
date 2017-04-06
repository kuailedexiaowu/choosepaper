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
                url:"/choosepaper/student/findById",
                async:true,
                dataType:'json',
                data:{id:id},
                success:function (data) {
                    $("#id").val(data.id);
                    $("#name").val(data.name);
                    $("#tel").val(data.tel);
                    $("#major").val(data.major);
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
    var updateStudentIn={id:$("#id").val(),name:$("#name").val().trim(),major:$("#major").val().trim(),
        tel:$("#tel").val(),gender:gendertoint($("#gender").val().trim())}
    $.post({
        url:"/choosepaper/admin/updateStudent",
        async:true,
        dataType:'json',
        data:JSON.stringify(updateStudentIn),
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


