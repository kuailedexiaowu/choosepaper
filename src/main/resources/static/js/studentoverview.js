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

function myPaperDetail() {
    var id = null;
    $.post({
        url: "/choosepaper/user/getUserId",
        async: true,
        dataType: 'json',
        contentType: "application/json",
        success:function (data) {
            id=data;
            $.post({
                url:"/choosepaper/paper/getMyPaper",
                async:true,
                dataType:'json',
                data:{id:id},
                success:function (data) {
                    if(data.id != "-1"){
                         var ele11 = $("<label></label>").text(data.teacher.name);
                         var ele1 = $("<label>导师名称:</label>").append(ele11).attr("class","col-md-8");
                         var ele22 = $("<label></label>").text(ranktostring(data.teacher.rank));
                         var ele2 = $("<label>导师职称:</label>").append(ele22).attr("class","col-md-8");
                         var ele33 = $("<label></label>").text(data.name);
                         var ele3 = $("<label>论文题目:</label>").append(ele33).attr("class","col-md-8");
                         var ele44 = $("<label></label>").text(data.description);
                         var ele4 =$("<label>论文描述:</label>").append(ele44).attr("class","col-md-8");
                        var ele55 = $("<label></label>").text(data.demand);
                         var ele5 =$("<label>论文要求:</label>").append(ele55).attr("class","col-md-8");
                        $("#mypaper").text(null);
                        $("#mypaper").append(ele1).append(ele2).append(ele3).append(ele4).append(ele5)
                    }
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