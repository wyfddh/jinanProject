layui.use(['form','layer','table','laytpl'],function(){
  var form = layui.form
      layer = parent.layer === undefined ? layui.layer : top.layer,
      $ = layui.jquery,
      laytpl = layui.laytpl,
      table = layui.table;


  var pathName=window.document.location.pathname;
  var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);

  var tableIns = table.render({
    elem: '#collectionManager',
    url : projectName + '/esaleCollection/collectionInfoList.do',
    request:{
      pageName: 'currentPage',
      limitName: 'size'
    },
    cellMinWidth : 95,
    page : true,
    loading:false,
    limits : [10,15,20,25],
    limit : 10,
    id : "collectionManagerTable",
    cols : [[
      {type:"numbers",title: '序号', width:70, align:"center"},
      {field: 'museumName', title: '博物馆', align:"center"},
      {field:'picUrl', title: '藏品图片',width:110, style:'height:88px;', align:'center',templet:"#picUrl"},
      {field: 'collectionName', title: '藏品名称', align:"center"},
      {field: 'collectionTypeDes', title: '类别',  align:'center'},
      {field: 'collectionYearName', title: '年代',  align:'center'},
      {field: 'clickNum', title: '浏览量',  align:'center'},
      {field: 'collectNum', title: '收藏量',  align:'center'},
      {field: 'hotRecommend', title: '推荐', align:'center',templet: '#recommendBar',width:150},
      {field: 'dataState', title: '状态',  align:'center',templet:function(d){
          if(d.dataState == 1){
              return "下线";
          }else{
              return "上线";
          }
          }},
      {title: '操作',width:200,  toolbar:'#collectionManagerBar',align:"center"}
    ]],
    done:function(d){
      $("[data-field = 'picUrl']").children(".layui-table-cell").css({"height":"100%","max-width":"100%","position":"relative"});
      $("[data-field = 'picUrl']").find("img").css({"max-width":"100%","max-height":"100%","position":"absolute","top":"50%","left":"50%","transform":"translate(-50%, -50%)"});
    }
  });


  $(".search_btn").on("click",function(){
    reloadTable();
  });
  $("#resetBtn").on("click",function(){
    resetTable();
  })

  var initForm = function() {
    getSelectData();
      getCollectYear();
    getCollectCategory();
  }

  initForm();

  function resetTable() {
    table.reload("collectionManagerTable",{
      page: {
        curr: 1 //重新从第 1 页开始
      },
      where: {
        key:"",
          collectionType:"",
          collectionYear:"",
        orderBy:""
      }
    })
  }

  function reloadTable() {
    table.reload("collectionManagerTable",{
      page: {
        curr: 1 //重新从第 1 页开始
      },
      where: {
        key:$("#key").val(),
          collectionType:$("#collectionType").val(),
          collectionYear:$("#collectionYear").val(),
        orderBy:$("#orderBy").val()
      }
    })
  }

    form.on('select(orderBy)',function(){
        reloadTable();
    })

  form.on('switch(recommend)', function(data){
    var id = data.elem.value;
    var recommendStatus;
    if (data.elem.checked) {
          recommendStatus = "1";
    } else {
      recommendStatus = "0";
    }
    $.ajax({
      type:"post",
      url:projectName + '/esaleCollection/modifyRecommend.do',
      data:{"id":id,"recommendStatus":recommendStatus},
      success:function(result) {
        if (result.success == 1) {
          reloadTable();
          form.render();
        } else {
          var resultMsg = result.error.message;
          layer.open({
            type: 1,
            title: false,
            closeBtn: 0,
            time:2000,
            shadeClose: true,
            skin: "msg",
            content: "<div class='msg alertMsg'><div class='msg-icon'></div><div class='msg-title'>"
            + resultMsg
            + "</div><div class='msg-txt'></div></div>"
          });
          $(data.elem).attr("checked",false);
          form.render();
        }
      },
      error:function(result) {
      }
    })
  });

  //添加
  function addCollection(edit){
    var title;
      title = "查看藏品";
      localStorage["dataObject"]=JSON.stringify(edit);
    var index = layui.layer.open({
      title : title,
      type : 2,
      area: ['80%', '700px'],
      content : "../../page/esaleCollectionInfo/esaleCollectionInfoAdd.html",
      success : function(layero, index){
        var body = layui.layer.getChildFrame('body', index);
        if(edit){
          // body.find("#type").val("2");
          form.render();
        }
      },
      end :function() {
        tableIns.reload();
      }
    });
    layui.layer.full(index);
    // window.sessionStorage.setItem("index",index);
    // //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
    // $(window).on("resize",function(){
    //   layui.layer.full(window.sessionStorage.getItem("index"));
    // })
  }

  $(".addNews_btn").click(function(){
        addCollection();
    });


  function getSelectData() {
    var data = {arr:['order_by']};
    $.ajax({
      type:"post",
      url:projectName + '/esaleMuseum/getSelectData.do',
      data:data,
      success:function(result) {
        if (result.success == 1) {
          var map = result.data;
          var order_by = map.order_by;
          var orderStr = "";
          for(var k = 0;k < order_by.length;k++) {
            orderStr +="<option value='"+order_by[k].dictCode+"' >"+order_by[k].dictName+"</option>"
          }
          $("#orderBy").append(orderStr);
          form.render();
        }
      }
    });
  }

  function getCollectCategory() {
    $.ajax({
      type:"post",
      url:projectName + '/esaleInterfaceColelct/getInterfaceCollectSelectType.do?typegroupid=A0211',
      success:function(result) {
        if (result.success == "1") {
          var collectionCategory = result.data;
          var collectionCategoryStr = "";
          for(var k = 0;k < collectionCategory.length;k++) {
            collectionCategoryStr +="<option value='"+collectionCategory[k].typecode+"' >"+collectionCategory[k].typename+"</option>"
          }
            $("#collectionType").append(collectionCategoryStr);
          console.log(collectionCategoryStr);
        }
      }
    });
  }

    function getCollectYear() {
        $.ajax({
            type:"post",
            url:projectName + '/esaleInterfaceColelct/getInterfaceCollectSelectType.do?typegroupid=A0361',
            success:function(result) {
                if (result.success == "1") {
                    var collectionYear = result.data;
                    var collectionYearStr = "";
                    for(var k = 0;k < collectionYear.length;k++) {
                        collectionYearStr +="<option value='"+collectionYear[k].typecode+"' >"+collectionYear[k].typename+"</option>"
                    }
                    $("#collectionYear").append(collectionYearStr);
                    console.log(collectionYearStr);
                }
            }
        });
    }


  //列表操作
  table.on('tool(collectionManager)', function(obj){
    var layEvent = obj.event,
        data = obj.data;
    var dataStatus = 2;
    var content = "";
    if(layEvent === 'up'){ //上线
        dataStatus = 2;
        content = "上线";
    }else if(layEvent === 'down'){ //下线
        dataStatus = 1;
        content = "下线";
    }else if(layEvent === 'del'){ //删除
        dataStatus = 0;
        content = "删除";
    }else if(layEvent === 'show'){ //查看
        window.open("http://118.190.16.36:9988/glaccountController.do?jumpLifecycle&cid="+data.id);
        return false;
    }
      layer.confirm('是否确定'+content+'该藏品？',{icon:3, title:'提示信息'},function(index){
          $.get(projectName + '/esaleCollection/updateStatus.do',{
              id : data.id,
              dataStatus:dataStatus
          },function(result){
              if (result.success == 1) {
                  layer.open({
                      type: 1,
                      title: false, //不显示标题
                      closeBtn: 0,
                      time:1000,
                      shadeClose: true,
                      skin: "msg",
                      content: "<div class='msg successMsg'><div class='msg-icon'></div><div class='msg-title'>"+content+"成功！</div><div class='msg-txt'></div></div>"
                  });
              } else {
                  var resultMsg = result.error.message;
                  layer.open({
                      type: 1,
                      title: false,
                      closeBtn: 0,
                      time:2000,
                      shadeClose: true,
                      skin: "msg",
                      content: "<div class='msg errorMsg'><div class='msg-icon'></div><div class='msg-title'>"
                          + resultMsg
                          + "</div><div class='msg-txt'></div></div>"
                  });
              }
              tableIns.reload();
              layer.close(index);
          });
      });
  });
})
