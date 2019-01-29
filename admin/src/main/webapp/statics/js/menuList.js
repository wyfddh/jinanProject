var typeList = [{"value":0,"text":"菜单"},{"value":1,"text":"按钮"}];
var parentidList = [{"value":0,"text":"菜单"},{"value":1,"text":"按钮"}];
var renderTable;
var main={

    init:function () {
        this.initTable();
        this.tabBind()
    },
    initTable:function(){
        var _this=this;
        setSelect();
        loadTable();
    },
    tabBind:function () {
        layui.use(['form','layer', 'treetable'], function () {
            var form = layui.form;
            var layer = parent.layer === undefined ? layui.layer : top.layer,
                $ = layui.jquery;
            //监听查询
            form.on('submit(formDemo)', function(data){
                loadTable();
                return false;
            });

       /*     $('.searchVal').bind('keydown', function (event) {
                var event = window.event || arguments.callee.caller.arguments[0];
                if (event.keyCode == 13){
                    searKeywordCache = $('.search-data-input').val();

                }
            });
            */
            //监听重置
            $("[type='reset']").click(function () {
                $(this).parents(".layui-form").find("input").val("");
                $(this).prev().click();
            });
        });

        $("#addFunction").click(function () {
            localStorage.menuType=0;
            var index = layui.layer.open({
                title : '添加菜单',
                type : 2,
                area: ['80%', '700px'],
                content : '../../page/menu/menuDetail.html',
                end :function() {
                    loadTable();
                }
            })
            layui.layer.full(index);
            window.sessionStorage.setItem("index",index);
            //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
            $(window).on("resize",function(){
                layui.layer.full(window.sessionStorage.getItem("index"));
            })
            return false;
        })
    }
}

/**
 * 加载表格数据
 */
function loadTable() {
    //获取业务字典
    // getDictData();
    //设置下拉框

    layui.config({
        base: '../../statics/module/'
    }).extend({
        treetable: 'treetable-lay/treetable'
    }).use(['layer', 'table', 'treetable'], function () {
        var $ = layui.jquery;
        var table = layui.table;
        var layer = layui.layer;
        var treetable = layui.treetable;
        var functionName = $("#functionName").val();
        var type = $("#typeSelect").val();
        // 渲染表格
        renderTable = function () {
            layer.load(2);
            treetable.render({
                treeColIndex: 1,
                treeSpid: -1,
                treeIdName: 'id',
                treePidName: 'parentId',
                treeDefaultClose: true,
                treeLinkage: false,
                elem: '#test',
                url: property.getProjectPath()+"esaleSysMenu/getMenuList.do?functionName="+functionName+"&type="+type,
                page: false,
                cols: [[
                    {type: 'numbers'},
                    {field: 'title', title: '功能名称', width:200},
                    {field: 'type', title: '类别', width:200,templet: function(res){
                        if (res.type == 1) {
                            return '菜单';
                        } else {
                            return '页面';
                        }
                    }},
                    {field: 'sort', title: '排序', width:200},
                    {field: 'icon', title: '图标', width:180},
                    {field: 'href', title: '链接地址', width:350},
                    {templet: '#oper-col', title: '操作', width:120,fixed: 'right'}
                ]],
                done: function () {
                    layer.closeAll('loading');
                }
            });
        };

        table.on('tool(test)', function(obj){
            var data = obj.data;
            var title = '';
            var url = '';
            //console.log(obj)
            if(obj.event === 'del'){
                layer.confirm('您确定要删除么', function(index){
                    // obj.del();
                    layer.close(index);
                    deleteMenu(data.id);
                });
            } else if(obj.event === 'edit'){
               // editMenu(data.id);
                localStorage.menuType=1;
                title = '修改菜单'
                url = "../../page/menu/menuDetail.html?id=" + data.id;
            }else if(obj.event === 'detail'){

            }
            if (title != '' && url != '') {
                var index = layui.layer.open({
                    title : title,
                    type : 2,
                    area: ['80%', '700px'],
                    content : url,
                    end :function() {
                        loadTable();
                    }
                })
                layui.layer.full(index);
                window.sessionStorage.setItem("index",index);
                //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
                $(window).on("resize",function(){
                    layui.layer.full(window.sessionStorage.getItem("index"));
                })
            }
        });

        renderTable();

        $('#btn-expand').click(function () {
            treetable.expandAll('#test');
        });

        $('#btn-fold').click(function () {
            treetable.foldAll('#test');
        });

        $('#btn-refresh').click(function () {
            renderTable();
        });



    });
}

/**
 * 删除菜单
 */
function deleteMenu(id) {
    var json = {"functionId":id};
    $.ajax({
        type:"get",
        data:json,
        async:false,
        url:property.getProjectPath()+"esaleSysMenu/deleteMenu.do",
        success:function(result) {
            if (result.success == 1) {
                top.layer.msg("删除功能成功");
                loadTable();
            } else {
                top.layer.msg(result.error.message);
            }
        },
        error:function(result) {
            top.layer.msg("系统异常");
        }
    });
}


function getDictData() {
    $.ajax({
        type:"get",
        data:json,
        async:false,
        url:property.getProjectPath()+"Dict/getDictData.do",
        success:function(result) {
            if (result.success == 1) {
               typeList = result.data.typeList;
               parentidList = result.data.typeList;
            } else {
                top.layer.msg(result.error.message);
            }
        },
        error:function(result) {
            top.layer.msg("系统异常");
        }
    });
}

function setSelect() {
    // var typeSelect = component.getSelect(typeList,null,"type");
    // $("#typeSelect").html(typeSelect);
}
main.init();

