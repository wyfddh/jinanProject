var projectName = property.getProjectPath();
var main = {

  init: function () {
    this.initTable();
  },
  initTable: function () {
    var _this = this;
    layui.use(['form', 'table'], function () {
      var form = layui.form,
      table = layui.table;
      var tableIns = table.render({
        elem: '#openLiterature'
        , url: projectName + '/postLiterature/postLiteratureList.do'
        , request:{
          pageName: 'currentPage',
          limitName: 'size'
        }
        ,where:{
          open:"1"
        }
        , title: '公开文献资料列表'
        , cols: [[
            {type: 'numbers', title: '序号'}
          , {field: 'dataName', title: '文献名称', width: 100}
          , {field: 'dataTypeName', title: '文献类型', width: 120}
          , {field: 'number', title: '数量', width: 85}
          , {field: 'isbnNum', title: 'ISBN', width: 130}
          , {field: 'press', title: '出版社'}
          , {field: 'publishingTime', title: '出版时间', width: 120, sort: true}
          , {field: 'literatureTypeName', title: '文献分类', width: 120}
          , {field: 'literatureTypeIndex', title: '分类索书号', width: 120}
          , {field: 'inventoryStateName', title: '库存状态', width: 120}
          , {fixed: 'right', title: '操作', toolbar: '#barDemo', width: 180}
        ]]
        , page: true
        , limits : [10,15,20,25]
        , limit : 10
        , id : "openLiteratureTable"
      });

      //监听行工具事件
      table.on('tool(openLiterature)', function (obj) {
        var data = obj.data;
        if (obj.event === 'show') {
          localStorage["dataObject"]=JSON.stringify(data);
          parent.$t.goToPage(this, "page/literature/openLiterature.html");
        } else if (obj.event === 'borrow') {
          localStorage["dataObject"]=JSON.stringify(data);
          parent.$t.goToPage(this, "page/literature/openLiterature.html");
        } else if (obj.event === 'down') {
          localStorage["dataObject"]=JSON.stringify(data);
          parent.$t.goToPage(this, "page/literature/openLiterature.html");
        }
      });
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

        table.reload("openLiteratureTable",{
          page: {
            curr: 1 //重新从第 1 页开始
          },
          where: {
            key:$("#key").val(),
            dataType:$("#dataType").val(),
            inventoryState:$("#inventoryState").val(),
            orderBy:$("#orderBy").val()
          }
        });
        return false;
      });

      //监听重置
      $("[type='reset']").click(function () {
        $(this).parents(".layui-form").find("input").val("");
        table.reload("openLiteratureTable",{
          page: {
            curr: 1 //重新从第 1 页开始
          },
          where: {
            key:"",
            dataType:"",
            inventoryState:"",
            orderBy:""
          }
        });
      });

      getSelectData();

      function getSelectData() {
        var data = {arr:['literature_type','inventory_state','order_by']};
        $.ajax({
          type:"post",
          url:projectName + '/sysDict/getSelectDataByArea.do',
          data:data,
          success:function(result) {
            if (result.success == 1) {
              var map = result.data;

              var inventory_state = map.inventory_state;
              var inventoryStateStr = "";
              for(var i = 0;i < inventory_state.length;i++) {
                inventoryStateStr +="<option value='"+inventory_state[i].dictCode+"' >"+inventory_state[i].dictName+"</option>"
              }
              $("#inventoryState").append(inventoryStateStr);

              var literature_type = map.literature_type;
              var literatureTypeStr = "";
              for(var j = 0;j < literature_type.length;j++) {
                literatureTypeStr +="<option value='"+literature_type[j].dictCode+"' >"+literature_type[j].dictName+"</option>"
              }
              $("#dataType").append(literatureTypeStr);

              var order_by = map.order_by;
              var orderStr = "";
              for(var k = 0;k < order_by.length;k++) {
                orderStr +="<option value='"+order_by[k].dictCode+"' >"+order_by[k].dictName+"</option>"
              }
              $("#orderBy").append(orderStr);

              form.render();
            }
          }
        })
      }
    });
  }
}
main.init();

