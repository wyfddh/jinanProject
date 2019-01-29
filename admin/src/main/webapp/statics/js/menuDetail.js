var pageType="add";
var form1;
var index;
var main={
    init:function () {
        property.setUserInfo();
        this.initTable();
        this.tabBind();
        this.loadForm();
    },
    initTable:function(){
        var cyProps = "url:'"+property.getProjectPath()+"esaleSysMenu/getSuperiorMenuList.do',name:'parentId'";
        $("#parentId").attr("cyProps",cyProps);
        layui.use(['element', 'layer'], function () {
            index = parent.layui.layer.getFrameIndex(window.name);
        });
        function submitCode() {
            var code = $("textarea").val();
            $("#result").html(code);
            $("#result").find("#parentId").treeTool();

        }
        $(document).ready(function () {
            $('#result').on('click','#parentId', function(){
                var obj=$(this);
                openZtree(obj);
            });
        });


        layui.use('form', function(){
            var form = layui.form;
            var index = parent.layer.getFrameIndex(window.name);
            //监听提交
            form.on('submit(formDemo)', function(data){
                var json = $("#functionForm").serialize();
                var url = "esaleSysMenu/addMenu.do";
                if (pageType == "edit"){
                    url = "esaleSysMenu/updateMenu.do";
                    delete json.parentId;
                    json.parentId = $("#parentId").attr("data-id");
                }
                $.ajax({
                    type:"get",
                    data:json,
                    async:false,
                    url:property.getProjectPath()+url,
                    success:function(result) {
                        if (result.success == 1) {
                            if(pageType == "edit"){
                                top.layer.msg("修改功能成功");
                            }else{
                                top.layer.msg("添加功能成功");
                            }
                            parent.layui.layer.close(index);
                        } else {
                            top.layer.msg(result.error.message);
                        }
                    },
                    error:function(result) {
                        top.layer.msg("系统异常");
                    }
                });

            });
        });

    },

    tabBind:function () {
        //监听重置
        $("#cancel").click(function () {
            parent.layui.layer.close(index);
            return false;
        });
    },
    loadForm:function () {
        var menuType = localStorage.menuType;
        if (menuType == 1){
            var menuId = getQueryStringFrame("id");
            loadData(menuId);
            // $("#title").text("编辑功能");
        }else {
            var ext1List = [{"id":"0","text":"否"},{"id":"1","text":"是"}];
            var ext1RadioStr = component.getRadio(ext1List,"0","ext1","id","text");
            $("#ext1Radio").append(ext1RadioStr);
        }
    }
}

function loadData(id) {
    pageType = "edit";
    $('#id').val(id);
    layui.use('form', function(){
        var form = layui.form;
        form1 = form;
        var index = parent.layer.getFrameIndex(window.name);
        var json = {"id":id};
        //加载数据
        $.ajax({
            type:"get",
            data:json,
            async:false,
            url:property.getProjectPath()+"esaleSysMenu/getMenuById.do",
            success:function(result) {
                if (result.success == 1) {

                    setFormData(result.data);
                    form.render('select');
                } else {
                    top.layer.msg(result.error.message);
                }
            },
            error:function(result) {
                top.layer.msg("系统异常");
            }
        });
    });
}

function setFormData(data) {
    property.setForm($("#functionForm"),data);
    $("#type").val(data.type);
    $('input[name=isQuick][value='+data.isQuick+']').prop('checked', 'checked');
    $('input[name=pageType][value='+data.pageType+']').prop('checked', 'checked');
    form1.render();
    if (data.parentId == -1){
        $("#parentId").val("根目录").attr("data-id",data.id);
        return;
    }
    var json = {"id":data.parentId};
    $.ajax({
        type:"get",
        data:json,
        async:false,
        url:property.getProjectPath()+"esaleSysMenu/getMenuById.do",
        success:function(result) {
            if (result.success == 1) {
                var data = result.data;
                $("#parentId").val(data.title).attr("data-id",data.id);
            } else {
                top.layer.msg(result.error.message);
            }
        },
        error:function(result) {
            top.layer.msg("系统异常");
        }
    });

}

main.init();

function getQueryStringFrame(name) {
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
    var urlArr = $(parent.$("iframe")).attr('src').split('?');
    var r;
    if(urlArr.length > 1) {
        r = urlArr[1].match(reg);
    }
    if (r != null) {
        return unescape(r[2]);
    }
    return null;
}



