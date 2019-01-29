/**
 * author: zhangwei
 * 账户设置
 */
var pageType = "edit";
var basePassword = ''
var userID = '';
var main = {

    init: function (){
        if (pageType == 'edit') {
            $('#submit').text('保存');
            var  id = JSON.parse(localStorage.userInfo).userId;
            loadData(id);
        }
        this.initTable();
    },

    initTable: function () {
        var _this = this;
        layui.use('form', function () {
            var form = layui.form;

            form.verify({
                'oldpassword': function () {
                    var arrays = $("#userForm").serializeArray();
                    var keyArrays = arrays.map(obj => obj.name);
                    var valueArrays = arrays.map(obj => obj.value);
                    var jsons = yc.arrayToJsonObject(keyArrays, valueArrays);
                    var oldpassword = jsons['oldpassword'];
                    oldpassword = hex_md5(oldpassword)
                    if(oldpassword != basePassword)  {
                        return '密码不正确，请输入正确的密码'
                    }
                },
                'surePassword':function () {
                    var arrays = $("#userForm").serializeArray();
                    var keyArrays = arrays.map(obj => obj.name);
                    var valueArrays = arrays.map(obj => obj.value);
                    var jsons = yc.arrayToJsonObject(keyArrays, valueArrays);
                    var password = jsons['newpassword'];
                    var surePassword = $("#surePassword").val();
                    if(surePassword != password) {
                        return '密码和确认密码不一致'
                    }
                }
            })
            //监听提交
            form.on('submit(formSubmit)', function (data) {
                var arrays = $("#userForm").serializeArray();
                var url = null;
                var keyArrays = arrays.map(obj => obj.name);
                var valueArrays = arrays.map(obj => obj.value);
                var jsons = yc.arrayToJsonObject(keyArrays, valueArrays);

                if (pageType == "edit"){
                    url = "sysUser/changePassword.do";

                    var newPassword = jsons['newpassword'];
                    var surePassword = $("#surePassword").val();

                    newPassword = hex_md5(newPassword)
                    surePassword = hex_md5(surePassword)

                }
                jsons['surePassword'] = surePassword;

                var  data = {
                    id: userID,
                    password: newPassword,
                    surePassword: surePassword,
                    oldPassword: basePassword
                }

                $.ajax({
                    contentType: 'application/json;charset=UTF-8',
                    url: property.getProjectPath() + yc.checkDo(url),
                    async: false,
                    data: JSON.stringify(data),
                    dataType: 'json',
                    type: 'post',
                    success: function (result) {
                        if (result.success == 1) {
                            top.layer.msg("修改用户成功");
                            parent.$t.goback("page/systemSetting/userManage/list.html");
                        } else {
                            //top.layer.msg(result.error);
                            top.layer.msg("修改用户失败");
                        }
                    },
                    error: function (result) {
                        top.layer.msg("系统异常");
                    }
                });
                return false;
            })
        });

        //监听重置
        $("#cancel").click(function(){
            layer.confirm('确认取消吗?', function(){
                parent.$t.goback("page/systemSetting/userManage/list.html");
            });
            return false;
        });

        // 监听密码是否修改
        $("#password").click(function () {
            $("#password").val('');
            $("#surePassword").val('');
        })


    },
}


main.init();


/**
 * 加载表单数据
 * @param id 角色id
 */
function loadData(id) {
    layui.use('form', function () {
        var form = layui.form;
        var index = parent.layer.getFrameIndex(window.name);
        var  id= JSON.parse(localStorage.userInfo).userId;
        var data = {"id": id};
        //加载数据
        var datas = yc.ajaxGetByParams('sysUser/getSysUserById.do', data, null, null);
        var jsonData = datas.data;
        jsonData.passwordId = jsonData.password;
        jsonData.surePasswordId = jsonData.surePassword;
        var show = yc.password;
        jsonData.password = show;
        jsonData.surePassword = show;
        jsonData.surePassword = "";  //清空密码值
        form.render();
        setFormData(jsonData);

    });
}


/**
 * 设置表单数据
 * @param data
 */
function setFormData(data) {
    property.setForm($("#userForm"), data);
    $("#userName").attr("disabled","disabled");
    $("#email").attr("disabled","disabled");
    basePassword = data.passwordId
    userID = data.id
}




function checkPassword(password, surePassword){

    if (yc.isNull(password)) {
        top.layer.msg("密码不能为空！");
        return false;
    } else {
        if (password != surePassword) {
            top.layer.msg("前后密码不一致!");
            return false;
        } else {
            return true;
        }
    }
}



