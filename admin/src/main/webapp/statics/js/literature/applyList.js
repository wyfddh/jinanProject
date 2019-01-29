var projectName = property.getProjectPath();
var main = {
  init: function () {
    this.initTable();
    $('#addNew').click(function () {
      parent.$t.goToPage(this, "page/literature/applyList.html");
    })
  },
  initTable: function () {
    var _this = this;
    layui.use(['form', 'table'], function () {
      var table = layui.table,
          form = layui.form;
      var tableIns = table.render({
        elem: '#applyList'
        , url: projectName + '/postLiteratureProcess/postLiteratureProcessList.do'
        , request:{
          pageName: 'currentPage',
          limitName: 'size'
        }
        , toolbar: '#toolbarDemo'
        , title: '申请审批列表'
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
          , {type: 'numbers', title: '序号'}
          , {field: 'literatureName', title: '文献名称', width: 100}
          , {field: 'literatureTypeName', title: '文献类型', width: 100}
          , {field: 'applyTypeName', title: '申请事项', width: 100}
          , {field: 'applicantName', title: '申请人', width: 130}
          , {field: 'departmentName', title: '所属部门'}
          , {field: 'applyReasons', title: '申请原因', width: 120, sort: true}
          , {field: 'applyDate', title: '申请日期', width: 120}
          , {field: 'approveStatusName', title: '申请状态', width: 120}
          , {field: 'applyRemark', title: '备注', width: 120}
          , {fixed: 'right', title: '操作', toolbar: '#barDemo', width: 180}
        ]]
        , page: true
        ,limits : [10,15,20,25]
        , limit : 10
        , id : "applyListTable"
      });

      getSelectData();
      function getSelectData() {

        $.ajax({
          type:'post',
          url:projectName + '/sysdepartment/getDeptOptions.do',
          contentType:"application/json; charset=utf-8",
          success:function(res) {
            if (res.code == 0) {
              var list = res.data;
              var departmentStr = "";
              for(var j = 0;j < list.length;j++) {
                departmentStr +="<option value='"+list[j].departmentId+"' >"+list[j].departmentName+"</option>"
              }
              $("#department").append(departmentStr);
              form.render();
            }
          }
        });

        var data = ['approve_status','order_by'];
        var map = property.getDictDataMulti(data);

        var approve_status = map.approve_status;
        var approveStatusStr = "";
        for(var i = 0;i < approve_status.length;i++) {
          approveStatusStr +="<option value='"+approve_status[i].dictCode+"' >"+approve_status[i].dictName+"</option>"
        }
        $("#approveStatus").append(approveStatusStr);

        var order_by = map.order_by;
        var orderStr = "";
        for(var k = 0;k < order_by.length;k++) {
          orderStr +="<option value='"+order_by[k].dictCode+"' >"+order_by[k].dictName+"</option>"
        }
        $("#orderBy").append(orderStr);

        form.render();
      }

      //头工具栏事件
      table.on('toolbar(applyList)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
          case 'getCheckData':
            var data = checkStatus.data;
            layer.alert(JSON.stringify(data));
            break;
          case 'getCheckLength':
            var data = checkStatus.data;
            layer.msg('选中了：' + data.length + ' 个');
            break;
          case 'isAll':
            layer.msg(checkStatus.isAll ? '全选' : '未全选');
            break;
        }
        ;
      });

      //监听行工具事件
      table.on('tool(applyList)', function (obj) {
        var data = obj.data;

        if (obj.event === 'edit') {

          sessionStorage.setItem("applyData",JSON.stringify(data));
          parent.$t.goToPage(this, "page/literature/applyList.html");

        } else if (obj.event === 'show') {
          sessionStorage.setItem("applyData",JSON.stringify(data));
          parent.$t.goToPage(this, "page/literature/applyList.html");
        } else if (obj.event === 'detail') {
          layer.prompt({
            formType: 2
            , value: data.email
          }, function (value, index) {
            obj.update({
              email: value
            });
            layer.close(index);
          });
        }
      });

      //监听收起
      form.on('submit(moreSearch)', function (data) {
        if ($(this).children().hasClass("fa-chevron-down")) {
          //显示更多条件
          $(this).parents(".layui-form").find(".aa").show();
          //修改更多按钮图标
          $(this).html('<i class="fa fa-chevron-up">&nbsp;</i>收起筛选');
        } else {
          //显示更多条件
          $(this).parents(".layui-form").find(".aa").hide();
          //修改更多按钮图标
          $(this).html('<i class="fa fa-chevron-down">&nbsp;</i>展开筛选');
        }
        return false;
      });

      //监听查询
      form.on('submit(formDemo)', function (data) {
        table.reload("applyListTable",{
          page: {
            curr: 1 //重新从第 1 页开始
          },
          where: {
            key:$("#key").val(),
            department:$("#department").val(),
            approveStatus:$("#approveStatus").val(),
            orderBy:$("#orderBy").val()
          }
        });
        return false;
      });
      //监听重置
      $("[type='reset']").click(function () {
        $(this).parents(".layui-form").find("input").val("");
        $(this).parents(".layui-form").find("select").val("");
        form.render();
        table.reload("applyListTable",{
          page: {
            curr: 1 //重新从第 1 页开始
          },
          where: {
            key:"",
            department:"",
            approveStatus:"",
            orderBy:""
          }
        });
        return false;
      });



    });
  }
}
main.init();

