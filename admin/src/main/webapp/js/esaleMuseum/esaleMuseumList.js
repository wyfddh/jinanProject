layui.use(['form','layer','table','laytpl'],function(){
  var form = layui.form
      layer = parent.layer === undefined ? layui.layer : top.layer,
      $ = layui.jquery,
      laytpl = layui.laytpl,
      table = layui.table;


  var pathName=window.document.location.pathname;
  var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);


    $('.searchVal').bind('keydown', function (event) {
        var event = window.event || arguments.callee.caller.arguments[0];
        if (event.keyCode == 13){
            searKeywordCache = $('.search-data-input').val();
            reloadTable();
        }
    });
  //用户列表
  var tableIns = table.render({
    elem: '#museumManager',
    url : projectName + '/esaleMuseum/museumBaseInfoList.do',
    request:{
      pageName: 'currentPage',
      limitName: 'size'
    },
    cellMinWidth : 95,
    page : true,
    loading:false,
    limits : [10,15,20,25],
    limit : 10,
    id : "museumManagerTable",
    cols : [[
      {type:"numbers",title: '序号', width:70, align:"center"},
      {field:'mainPicUrl', title: '博物馆图片',width:110, style:'height:88px;', align:'center',templet:"#mainPicUrl"},
      {field: 'museumName', title: '博物馆名称', align:"center"},
      {field: 'museumType', title: '类别', align:'center',templet:function(d){
        if(d.museumType == "1"){
          return "主馆";
        }else{
          return "分馆";
        }
      }},
      {field: 'collectionCount', title: '公开藏品量', align:'center',width:150},
      {field: 'ticket', title: '门票',  align:'center'},
      {field: 'createDate', title: '创建时间',  align:'center', templet:function(d){
        if(d.createDate == null){
            return "-";
        }else{
            return timestampToTime(d.createDate);
        }
    }},
      {title: '操作',width:200,  toolbar:'#museumManagerBar',align:"left"}
    ]],
    done:function(d){
      $(".layui-table-body [data-field = 'mainPicUrl']").children(".layui-table-cell").css({"height":"100%","max-width":"100%","position":"relative"});
      $(".layui-table-body [data-field = 'mainPicUrl']").find("img").css({"max-width":"100%","max-height":"100%","position":"absolute","top":"50%","left":"50%","transform":"translate(-50%, -50%)"});
    }
  });






  $(".search_btn").on("click",function(){
    reloadTable();
  });
  $("#resetBtn").on("click",function(){
    resetTable();
  })

  function resetTable() {
    table.reload("museumManagerTable",{
      page: {
        curr: 1 //重新从第 1 页开始
      },
      where: {
        key:"",
        museumType:"",
        orderBy:""
      }
    })
  }

  function reloadTable() {
    table.reload("museumManagerTable",{
      page: {
        curr: 1 //重新从第 1 页开始
      },
      where: {
        key:$("#key").val(),
        museumType:$("#museumType").val(),
        orderBy:$("#orderBy").val()
      }
    })
  }

    form.on('select(orderBy)',function(){
        reloadTable();
    })


  //添加
  function addMuseumInfo(edit){
    var title;
    if (edit) {
      if (edit.operationStatus == "1") {
        title = "修改博物馆信息"
      } else {
        title = "查看博物馆信息"
      }
      localStorage["dataObject"]=JSON.stringify(edit);
    } else {
      title = "添加博物馆";
      localStorage["dataObject"]=JSON.stringify("");
    }
    var index = layui.layer.open({
      title : title,
      type : 2,
      area: ['80%', '700px'],
      content : "../../page/esaleMuseum/esaleMuseumAdd.html",
      success : function(layero, index){
        var body = layui.layer.getChildFrame('body', index);
        if(edit){
          // body.find("#type").val("2");

          form.render();
        }

      },
      end :function() {
        localStorage.removeItem('dataObject');
        tableIns.reload();
      }
    })
    layui.layer.full(index);
    // window.sessionStorage.setItem("index",index);
    // //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
    // $(window).on("resize",function(){
    //   layui.layer.full(window.sessionStorage.getItem("index"));
    // })
  }
  $(".addNews_btn").click(function(){
    addMuseumInfo();
  })


  function getSelectData() {
    var data = {arr:['museum_type_es','order_by']};
    $.ajax({
      type:"post",
      url:projectName + '/esaleMuseum/getSelectData.do',
      data:data,
      success:function(result) {
        if (result.success == 1) {
          var map = result.data;
          var museum_type_es = map.museum_type_es;
          var levelStr = "";
          for(var i = 0;i < museum_type_es.length;i++) {
            levelStr +="<option value='"+museum_type_es[i].dictCode+"' >"+museum_type_es[i].dictName+"</option>"
          }
          $("#museumType").append(levelStr);

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
  var initForm = function() {
    $(".hide").hide();
    getSelectData();
  }

  initForm();

  //列表操作
  table.on('tool(museumManager)', function(obj){
    var layEvent = obj.event,
        data = obj.data;

    if(layEvent === 'edit'){ //编辑
      data.operationStatus = "1";
      addMuseumInfo(data);
    }else if(layEvent === 'del'){ //删除
      layer.confirm('是否确定删除该博物馆？',{icon:3, title:'提示信息'},function(index){
        $.get(projectName + '/esaleMuseum/deleteDataState.do',{
          id : data.id,
          dataState:"0"
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
        })
      });
    }else if(layEvent === 'show'){//查看
      data.operationStatus = "2";
      addMuseumInfo(data);
    }
  });
//时间戳转时间格式
    function timestampToTime(obj) {
        var date =  new Date(obj);
        var y = 1900+date.getYear();
        var m = "0"+(date.getMonth()+1);
        var d = "0"+date.getDate();
        var h = date.getHours() + ':';
        var mi = date.getMinutes();
        var s = date.getSeconds();
        return y+"-"+m.substring(m.length-2,m.length)+"-"+d.substring(d.length-2,d.length);
    }
})
