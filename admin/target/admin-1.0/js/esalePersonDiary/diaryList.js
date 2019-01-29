var orgList = null;
var userType = "0";
var form1;
var main={
    init:function () {
        setSelect();
        property.setUserInfo();
        this.initTable();
        this.tabBind();
    },
    initTable:function(){
        var _this=this;
        loadTable();
    },
    tabBind:function () {
        layui.use(['form'], function () {
            var form = layui.form;
            form1 = form;
            //监听查询
            form.on('submit(formDemo)', function(data){
                loadTable();
                return false;
            });
            //监听重置
            $("[type='reset']").click(function () {
                $(this).parents(".layui-form").find("input").val("");
                $(this).prev().click();
            });

            //监听收起
            form.on('submit(moreSearch)', function (data) {
                if($(this).children().hasClass("fa-chevron-down")){
                    //显示更多条件
                    $(this).parents(".layui-form").find(".more-search").show();
                    //修改更多按钮图标
                    $(this).html('<i class="fa fa-chevron-up">&nbsp;</i>收起筛选');
                }else{
                    //显示更多条件
                    $(this).parents(".layui-form").find(".more-search").hide();
                    //修改更多按钮图标
                    $(this).html('<i class="fa fa-chevron-down">&nbsp;</i>展开筛选');
                }
                return false;
            });

        });

        layui.use('laydate', function(){
            var laydate = layui.laydate;
            laydate.render({
                elem: '#showDate'
                ,range: true //或 range: '~' 来自定义分割字符
            });
        });

        //查询事件
        layui.use('form', function(){
            var form = layui.form;

        });

        $('#creator').keypress(function(e){
            if(e.keyCode==13){
                loadTable();
            }
        });

        $('#orgid').keypress(function(e){
            if(e.keyCode==13){
                loadTable();
            }
        });

        $('#showDate').keypress(function(e){
            if(e.keyCode==13){
                loadTable();
            }
        });


    }
}
main.init();

/**
 * 加载表格数据
 */
function loadTable() {
    layui.use('table', function(){
        var table = layui.table;
        var creatorName = $("#creator").val();
        var orgid = $("#orgid").val()== 'null'?null:$("#orgid").val();
        var showDate = $("#showDate").val();
        var tableIns = table.render({
            elem: '#test'
            ,url:property.getProjectPath()+"personDiary/getDiaryList.do?creatorName="+creatorName+"&orgid="+orgid+"&showDate="+showDate
            ,request:{
                pageName: 'currentPage',
                limitName: 'size'
            }
            ,toolbar: '#toolbarDemo'
            ,title: '日报列表'
            ,cols: [[
                {type: 'checkbox', fixed: 'left'}
                ,{field:'creattime', title:'提交时间',width:'200', templet:function(res){
                    return formatDate(res.creattime);
                }}
                ,{field:'creatorName', title:'提交人',width:'200'}
                ,{field:'content', title:'内容',width:'750'}
                ,{field:'department_name', title:'所属部门',width:'200'}
                ,{title:'操作', toolbar: '#barDemo', width:300}
            ]]
            ,page: true
        });

        function addMuseumInfo(edit){
            //0:添加 1：查看 2：修改
            var title;
            var url;
            if(edit == "2"){
                title = "修改日报";
                url = "../../page/esalePersonDiary/diaryEdit.html";
            }
            var index = layui.layer.open({
                title : title,
                type : 2,
                area: ['80%', '500px'],
                content : url,
                end :function() {
                    loadTable();
                }
            })
            layui.layer.full(index);
            // window.sessionStorage.setItem("index",index);
            // //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
            // $(window).on("resize",function(){
            //     layui.layer.full(window.sessionStorage.getItem("index"));
            // })
        }

        //监听行工具事件
        table.on('tool(test)', function(obj){
            var data = obj.data;
            //console.log(obj)
            if(obj.event === 'del'){
                var index = layer.confirm('真的删除行么', function(index){
                   deletePostVideo(data.id);
                    layer.close(index);
                    loadTable();
                });
            } else if(obj.event === 'edit'){
                localStorage.diaryId = data.id;
                localStorage.diaryContent = data.content;
                addMuseumInfo("2");
            }
        });
    });
}

function setSelect() {
    orgList = property.getAllOrgList();
    var statusSelect  = component.getSelectSimplePlus(orgList,null,"orgid","departmentId","departmentName");
    $("#orgid").append(statusSelect);
}

//删除
function deletePostVideo(id) {
    var json = {"id":id};
    $.ajax({
        type:"post",
        data:json,
        async:false,
        url:property.getProjectPath()+"personDiary/deleteDiary.do",
        success:function(result) {
            if (result.success == 1) {
                top.layer.msg("删除成功");
            } else {
                top.layer.msg(result.error.message);
            }
        },
        error:function(result) {
            top.layer.msg("系统异常");
        }
    });
}

function checkMyOrg() {
    checkOrg(userInfo.userId);
}




