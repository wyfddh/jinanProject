layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;

    var pathName=window.document.location.pathname;
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);

    //用户列表
    var tableIns = table.render({
        elem: '#userList',
        url : projectName + '/orgManager/getOrgInfoList.do',
        request:{
            pageName: 'currentPage',
            limitName: 'size'
        },
        cellMinWidth : 95,
        page : true,
      loading:false,
        limits : [10,15,20,25],
        limit : 10,
        id : "userListTable",
        cols : [[
            {type:"numbers", title: '序号', width:70, align:"center"},
            {field: 'name', title: '组织名称', minWidth:100, align:"center"},
            {field: 'orgTypeId', title: '组织类型', align:'center',templet:function(d){
                if(d.orgTypeId == "1"){
                    return "政府";
                }else if(d.orgTypeId == "2"){
                    return "博物馆";
                }
            }},
            {field: 'createdTimeName', title: '添加时间', align:'center',minWidth:150},
            /*{field: 'userStatus', title: '用户状态',  align:'center',templet:function(d){
             return d.userStatus == "0" ? "正常使用" : "限制使用";
             }},*/
            {title: '操作', minWidth:175, templet:'#userListBar',fixed:"right",align:"center"}
        ]]
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
        table.reload("newsListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                key: $("#key").val(),
                orgTypeId:$("#orgTypeId").val()
            }
        })
    });

    //添加用户
    function addUser(edit){
        /*layer.open({
            type: 2,
            content: 'test/iframe.html',
            success: function(layero, index){
                var body = layer.getChildFrame('body', index);
                var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                console.log(body.html()) //得到iframe页的body内容
                body.find('input').val('Hi，我是从父页来的')
            }
        });
*/
       /* layer.open({
            type: 2
            ,title: '添加管理员'
            ,content: 'adminform.html'
            ,area: ['420px', '420px']
            ,btn: ['确定', '取消']
            ,yes: function(index, layero){
                var iframeWindow = window['layui-layer-iframe'+ index]
                    ,submitID = 'LAY-user-back-submit'
                    ,submit = layero.find('iframe').contents().find('#'+ submitID);

                //监听提交
                iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
                    var field = data.field; //获取提交的字段

                    //提交 Ajax 成功后，静态更新表格中的数据
                    //$.ajax({});
                    table.reload('LAY-user-front-submit'); //数据刷新
                    layer.close(index); //关闭弹层
                });

                submit.trigger('click');
            }
        });*/

        var index = layui.layer.open({
            title : "添加用户",
            type : 2,
            content : "orAdd.html",
            area: ['520px', '450px'],
            btn: ['确定', '取消'],
            success : function(layero,index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find(".userName").val(edit.userName);  //登录名
                    body.find(".userEmail").val(edit.userEmail);  //邮箱
                    body.find(".userSex input[value="+edit.userSex+"]").prop("checked","checked");  //性别
                    body.find(".userGrade").val(edit.userGrade);  //会员等级
                    body.find(".userStatus").val(edit.userStatus);    //用户状态
                    body.find(".userDesc").text(edit.userDesc);    //用户简介
                    form.render();
                }
                /*setTimeout(function(){
                    layui.layer.tips('点击此处返回用户列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)*/
            },
            yse:function (index, layero) {
                layer.close(index); //关闭弹层
            }
        })
        /*layui.layer.full(index);
        window.sessionStorage.setItem("index",index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize",function(){
            layui.layer.full(window.sessionStorage.getItem("index"));
        })*/
    }
    $(".addNews_btn").click(function(){
        addUser();
    })

    //批量删除
    $(".delAll_btn").click(function(){
        var checkStatus = table.checkStatus('userListTable'),
            data = checkStatus.data,
            newsId = [];
        if(data.length > 0) {
            for (var i in data) {
                newsId.push(data[i].newsId);
            }
            layer.confirm('确定删除选中的用户？', {icon: 3, title: '提示信息'}, function (index) {
                // $.get("删除文章接口",{
                //     newsId : newsId  //将需要删除的newsId作为参数传入
                // },function(data){
                tableIns.reload();
                layer.close(index);
                // })
            })
        }else{
            layer.msg("请选择需要删除的用户");
        }
    })

    //列表操作
    table.on('tool(userList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            addUser(data);
        }else if(layEvent === 'usable'){ //启用禁用
            var _this = $(this),
                usableText = "是否确定禁用此用户？",
                btnText = "已禁用";
            if(_this.text()=="已禁用"){
                usableText = "是否确定启用此用户？",
                btnText = "已启用";
            }
            layer.confirm(usableText,{
                icon: 3,
                title:'系统提示',
                cancel : function(index){
                    layer.close(index);
                }
            },function(index){
                _this.text(btnText);
                layer.close(index);
            },function(index){
                layer.close(index);
            });
        }else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此组织？',{icon:3, title:'提示信息'},function(index){
                // $.get("删除文章接口",{
                //     newsId : data.newsId  //将需要删除的newsId作为参数传入
                // },function(data){
                    tableIns.reload();
                    layer.close(index);
                // })
            });
        }
    });

})
