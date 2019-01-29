layui.use(['form','layer','laydate','upload','table'],function(){
    var form = layui.form,
    layer = parent.layer === undefined ? layui.layer : top.layer,
    $ = layui.jquery,
    laydate = layui.laydate,
    table = layui.table;
    var upload = layui.upload;

    var pathName=window.document.location.pathname;
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    dataObject = JSON.parse(localStorage["activityData"]);

    //活动列表
    var tableIns = table.render({
        elem: '#esaleFileActivity',
        url : projectName + '/esaleActivity/getFileListById.do',
        toolbar: '#fileToolbar',
        defaultToolbar:[],
        request:{
            pageName: 'currentPage',
            limitName: 'size'
        },
        cellMinWidth : 95,
        where: {
            key: dataObject.id
        },
        page : true,
        loading:false,
        limits : [10,15,20,25],
        limit : 10,
        id : "esaleFileActivityTable",
        cols : [[
            {type:"checkbox",width:'5%',   align:'center'},
            // {field: 'fileType', title: '类型',width:'10%',  align:'center'},
            {field: 'fileRealname', title: '附件名称',width:'30%',  align:"center"},
            {field: 'fileFormat', title: '格式',width:'15%',  align:'center'},
            {field: 'fileSize', title: '大小',width:'10%',  align:"center",  templet:function(d){
                    if(d.fileSize == null){
                        return 0;
                    }else{
                        if(d.fileSize < 1024)
                        {
                            d.fileSize = d.fileSize+"KB";
                        } else {
                            d.fileSize = eval(d.fileSize / 1024).toFixed(2)+"MB";
                        }
                        return d.fileSize;
                    }
                }},
            {field: 'fileDownCount', title: '下载次数',width:'10%',   align:'center'},
            {field: 'updateDate', title: '更新时间', align:'center',width:'20%',  templet:function(d){
                    if(d.updateDate == null){
                        return "-";
                    }else{
                        return timestampToDateTime(d.updateDate);
                    }
                }},
            {title: '操作',  toolbar:'#esaleFileActivityBar',width:'10%', align:"center"}
        ]],
        done:function(d){
        }
    });

    function reloadTable() {
        table.reload("esaleFileActivityTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                key:dataObject.id
            }
        });
        toolbarListener();
    }

    toolbarListener();
    function toolbarListener(){
        //头工具栏事件
        table.on('toolbar(esaleFileActivity)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id);
            switch(obj.event){
                case 'download':
                    var data = checkStatus.data;
                    var ids = "";
                    for(var i = 0; i<data.length;i++){
                        ids += data[i].id+",";
                    }
                    if(ids.length != 0){
                        batch_download(ids);
                    }else {
                        layer.msg("请选择您需要下载的附件");
                    }
                    break;
                case 'upload':
                    break;
            }
        });

        upload.render({
            elem: '#upload',
            url: projectName +'/esaleFiledataActivity/upload.do',
            data:{
                id : dataObject.id
            },
            accept: 'file' ,
            before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
                layer.msg("上传中", {
                    icon:16,
                    shade:[0.5],
                    time:false  //取消自动关闭
                })
            },
            done: function(res){
                layer.closeAll('msg'); //关闭loading
                layer.msg(res.msg);
                reloadTable();
            },
            error: function(index, upload){
                return layer.msg('文件上传失败');
            }
        });
    }

    //列表操作
    table.on('tool(esaleFileActivity)', function(obj){
        var layEvent = obj.event,
            data = obj.data;
        if(layEvent === 'down'){ //下载
            batch_download(data.id);
        }else if(layEvent === 'del'){ //删除
            layer.confirm('是否确定删除该资料？',{icon:3, title:'提示信息'},function(index){
                $.get(projectName + '/esaleFiledataActivity/deleteFile.do',{
                    id : data.id,
                },function(result){
                    if (result.success == 1) {
                        layer.open({
                            type: 1,
                            title: false, //不显示标题
                            closeBtn: 0,
                            time:1000,
                            shadeClose: true,
                            skin: "msg",
                            content: "<div class='msg successMsg'><div class='msg-icon'></div><div class='msg-title'>删除成功！</div><div class='msg-txt'></div></div>"
                        });
                    } else {
                        layer.open({
                            type: 1,
                            title: false, //不显示标题
                            closeBtn: 0,
                            shadeClose: true,
                            skin: "msg",
                            content: "<div class='msg errorMsg'><div class='msg-icon'></div><div class='msg-title'>删除失败</div><div class='msg-txt'>"+result.data+"</div></div>"
                        });
                    }
                    tableIns.reload();
                    layer.close(index);
                });
            });
        }else if(layEvent === 'show'){//查看

        }else if(layEvent === 'rename'){//重命名
            layer.prompt({
                formType: 0,
                value: data.fileRealname,
                title: '重命名',
                area: ['250px', '30px'] //自定义文本域宽高
            }, function(value, index, elem){
                if(value== null || value == ""){
                    return false;
                };
                $.get(projectName + '/esaleFiledataActivity/renameFile.do',{
                    id : data.id,
                    fileRealname : value
                },function(result){
                    if (result.success == 1) {
                        layer.open({
                            type: 1,
                            title: false, //不显示标题
                            closeBtn: 0,
                            time:1000,
                            shadeClose: true,
                            skin: "msg",
                            content: "<div class='msg successMsg'><div class='msg-icon'></div><div class='msg-title'>重命名成功！</div><div class='msg-txt'></div></div>"
                        });
                    } else {
                        layer.open({
                            type: 1,
                            title: false, //不显示标题
                            closeBtn: 0,
                            shadeClose: true,
                            skin: "msg",
                            content: "<div class='msg errorMsg'><div class='msg-icon'></div><div class='msg-title'>重命名失败</div><div class='msg-txt'>"+result.data+"</div></div>"
                        });
                    }
                    tableIns.reload();
                });
                layer.close(index);
            });
        }
    });

    function batch_download(ids)
    {
        var tmp_array = [];
        tmp_array = ids.split(",");
        $(".downloadiframe").remove();
        window.ids_array = tmp_array;
        download();
        // $("#downloadiframe").remove();

        layer.open({
            type: 1,
            title: false, //不显示标题
            closeBtn: 0,
            time:1000,
            shadeClose: true,
            skin: "msg",
            content: "<div class='msg successMsg'><div class='msg-icon'></div><div class='msg-title'>下载成功！</div><div class='msg-txt'></div></div>"
        });
    }

    function download()
    {
        if(window.ids_array.length > 0)

        {
            var url = projectName+"/esaleFiledataActivity/download.do?id="+window.ids_array.pop();
            window.open(url) ;
            // $("body").append("<iframe class='downloadiframe' src="+projectName+"/esaleFiledataActivity/download.do?id="+window.ids_array.pop()+" style='display: none'></iframe>"); //download为下载地址
            // setTimeout(download, 1); //等待1毫秒后执行递归
        }
    }

//时间戳转时间格式
    function timestampToDateTime(obj) {
        var date =  new Date(obj);
        var y = 1900+date.getYear();
        var m = "0"+(date.getMonth()+1);
        var d = "0"+date.getDate();
        var h = date.getHours() + ':';
        var mi = date.getMinutes();
        var s = date.getSeconds();
        return y+"-"+m.substring(m.length-2,m.length)+"-"+d.substring(d.length-2,d.length)+" "+h+mi + ':'+s;
    };

    function timestampToDate(obj) {
        var date =  new Date(obj);
        var y = 1900+date.getYear();
        var m = "0"+(date.getMonth()+1);
        var d = "0"+date.getDate();
        var h = date.getHours() + ':';
        var mi = date.getMinutes();
        var s = date.getSeconds();
        return y+"-"+m.substring(m.length-2,m.length)+"-"+d.substring(d.length-2,d.length);
    };
})
