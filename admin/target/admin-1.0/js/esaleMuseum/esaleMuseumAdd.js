layui.use(['form', 'layer', 'layedit', 'laydate', 'upload'], function () {
  var form = layui.form,
  layer = parent.layer === undefined ? layui.layer : top.layer,
      upload = layui.upload,
      layedit = layui.layedit,
      laydate = layui.laydate,
      $ = layui.jquery;


  var pathName=window.document.location.pathname;
  var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);

  var index = parent.layui.layer.getFrameIndex(window.name);


  var pageType = null;

  //上传缩略图
  upload.render({
    elem: '.thumbBox',
    url: '../../json/userface.json',
    method: "get",  //此处是为了演示之用，实际使用中请将此删除，默认用post方式提交
    done: function (res, index, upload) {
      var num = parseInt(4 * Math.random());  //生成0-4的随机数，随机显示一个头像信息
      $('.thumbImg').attr('src', res.data[num].src);
      $('.thumbBox').css("background", "#fff");
    }
  });

  //格式化时间
  function filterTime(val) {
    if (val < 10) {
      return "0" + val;
    } else {
      return val;
    }
  }

  //定时发布
  var time = new Date();
  var submitTime = time.getFullYear() + '-' + filterTime(time.getMonth() + 1)
      + '-' + filterTime(time.getDate()) + ' ' + filterTime(time.getHours())
      + ':' + filterTime(time.getMinutes()) + ':' + filterTime(
          time.getSeconds());
  laydate.render({
    elem: '#release',
    type: 'datetime',
    trigger: "click",
    done: function (value, date, endDate) {
      submitTime = value;
    }
  });
  form.on("radio(release)", function (data) {
    if (data.elem.title == "定时发布") {
      $(".releaseDate").removeClass("layui-hide");
      $(".releaseDate #release").attr("lay-verify", "required");
    } else {
      $(".releaseDate").addClass("layui-hide");
      $(".releaseDate #release").removeAttr("lay-verify");
      submitTime = time.getFullYear() + '-' + (time.getMonth() + 1) + '-'
          + time.getDate() + ' ' + time.getHours() + ':' + time.getMinutes()
          + ':' + time.getSeconds();
    }
  });


  //预览
  form.on("submit(look)", function () {
    layer.alert("此功能需要前台展示，实际开发中传入对应的必要参数进行文章内容页面访问");
    return false;
  })

  $(".cancelBtn").click(function() {
    parent.layui.layer.close(index);
    return false;
  })

  //创建一个编辑器
    var editIndex1 = layedit.build('introduct', {
    height: 214,
    uploadImage: {
      url: projectName+"/attach/uploadEditPic.do?projectName=esaleMuseumEditPic",
      type:"post"
    }
  });
  var editIndex2 = layedit.build('guide', {
    height: 214,
    uploadImage: {
      url: projectName+"/attach/uploadEditPic.do?projectName=esaleMuseumEditPic",
      type:"post"
    }
  });

  function isEmpty(obj){
    if(typeof obj == "undefined" || obj == null || obj == "")	{
      return true;
    }else{
      return false;
    }
  }
  var initData = function() {
    var dataObject = JSON.parse(localStorage["dataObject"]);
    if (!isEmpty(dataObject.id)) {
      getSelectData("init",dataObject);
      getAreaData("0","province","init",dataObject);
    } else {
      $(".showStatus").hide();
      getSelectData();
      getAreaData("0","province");
    }

  }

  initData();


  form.on("submit(saveMuseum)", function (data) {
    layedit.sync(editIndex1);
    layedit.sync(editIndex2);
    var picids = $("#picids").val();
    var newpicids = picids.substr(0,picids.length);
    $("#picids").val(newpicids);
    var delpicids = $("#delpicids").val();
    var newdelpicids = delpicids.substr(0,delpicids.length);
    $("#delpicids").val(newdelpicids);
    var indexLoading = layer.open({
      type: 1,
      title: false, //不显示标题
      closeBtn: 0,
      shadeClose: false,
      skin: "msg",
      content: "<div class='msg loadingMsg'><div class='msg-icon'></div><div class='msg-title'>数据提交中，请稍候</div><div class='msg-txt'></div></div>"
    });
    var formData = $("#form").serialize();
    $.ajax({
      type:"post",
      data:formData,
      url:projectName + '/esaleMuseum/museumBaseInfoSave.do',
      success:function(result) {
        top.layer.close(indexLoading);
        if (result.success == 1) {
          layer.open({
            type: 1,
            title: false, //不显示标题
            closeBtn: 0,
            shadeClose: false,
            time:1000,
            skin: "msg",
            content: "<div class='msg successMsg'><div class='msg-icon'></div><div class='msg-title'>保存成功</div><div class='msg-txt'></div></div>"
          });
          parent.layui.layer.close(index);
        } else {
            var errorMsg = result.error.message;
            layer.open({
                type: 1,
                title: false, //不显示标题
                closeBtn: 0,
                shadeClose: false,
                time:1000,
                skin: "msg",
                content: "<div class='msg errorMsg'>"
                    + "<div class='msg-icon'>"
                    + "</div>"
                    + "<div class='msg-title'>"
                    + errorMsg
                    + "</div>"
                    + "<div class='msg-txt'></div>"
                    + "</div>"
            });
        }
      }
    })
    return false;
  })

  //设置input数据
  function setData(dataObject) {

    // $("#province option[value='"+dataObject.province+"']").attr("selected",true);
    // $("#city option[value='"+dataObject.city+"']").attr("selected",true);
    // $("#area option[value='"+dataObject.area+"']").attr("selected",true);
    $("#id").val(dataObject.id);
    $("#museumName").val(dataObject.museumName);
    $("#ticket").val(dataObject.ticket);
    $("#openTime").val(dataObject.openTime);
    $("#address").val(dataObject.address);
    $("#viewAddress").val(dataObject.viewAddress);

      $("#upId option[value='"+dataObject.upId+"']").attr("selected",true);

    layedit.setContent(editIndex1, dataObject.introduct, false);
    layedit.setContent(editIndex2, dataObject.guide, false);

    var picList = dataObject.picList;
    //获取省市联动
    var province = dataObject.province;

    //1修改2查看
      if (dataObject.operationStatus === "1") {
          $("#picids").val(dataObject.pictureids);

          for (var i = 0;i < picList.length;i++) {
              var picStr1;
              if (picList[i].isMain === "1") {
                  picStr1 = '<div class="img" id="img'+ picList[i].attId +'">'
                      +'<div class="img1"><img src='+ picList[i].attPath +' alt="" ></div>'
                      +'<div class="img2"><span class="img3" id="span'+ picList[i].attId +'" mark='+ picList[i].attId +' style="color:rgba(59,130,255,1);">主图</span><span class="img4" mark='+ picList[i].attId +'>删除图片</span></div>'
                      +'</div>'
                  $("#isMain").val(picList[i].attId);
              } else {
                  picStr1 = '<div class="img" id="img'+ picList[i].attId +'">'
                      +'<div class="img1"><img src='+ picList[i].attPath +' alt="" ></div>'
                      +'<div class="img2"><span class="img3" id="span'+ picList[i].attId +'" mark='+ picList[i].attId +'>设为主图</span><span class="img4" mark='+ picList[i].attId +'>删除图片</span></div>'
                      +'</div>'
              }
              $(".uploadBtn").before(picStr1);
              $(".showStatus").hide();
          }
      } else if (dataObject.operationStatus === "2") {
          for (var j = 0;j < picList.length;j++) {
              var picStr2;
              if (picList[j].isMain === "1") {
                  picStr2 = '<div class="img" id="img'+ picList[j].attId +'">'
                      +'<div class="img1"><img src='+ picList[j].attPath +' alt="" ></div>'
                      +'<div class="img2"><span class="img3" id="span'+ picList[j].attId +'"  ='+ picList[j].attId +' style="color:rgba(59,130,255,1);">主图</span></div>'
                      +'</div>'
              } else {
                  picStr2 = '<div class="img" id="img'+ picList[j].attId +'">'
                      +'<div class="img1"><img src='+ picList[j].attPath +' alt="" ></div>'
                      +'<div class="img2"></div>'
                      +'</div>'
              }
              $(".uploadBtn").before(picStr2);

          }
          $(".uploadBtn").hide();
          $(".editStatus").hide();
          $('input,select,textarea').attr("disabled","disabled");
          form.render();
          $("iframe[textarea='introduct']").contents().find('body').attr("contenteditable",false);
          $("iframe[textarea='guide']").contents().find('body').attr("contenteditable",false);

          $(".layedit-tool-face").off();
          $(".layedit-tool-link").off();
      }
    form.render();
  }
//获取页面下拉数据
  function getSelectData(init,dataObject) {
    var data = {arr:['order_by']};
    $.ajax({
      type:"post",
      url:projectName + '/esaleMuseum/getSelectData.do',
      data:data,
      success:function(result) {
        if (result.success == 1) {
          var map = result.data;

            var museumList = map.museumList;
            var museumListStr = "";
            for(var t = 0;t < museumList.length;t++) {
                museumListStr +="<option value='"+museumList[t].id+"'>"+museumList[t].museumName+"</option>"
            }
            $("#upId").append(museumListStr);

          form.render();
          if (!isEmpty(init) && !isEmpty(dataObject)) {
            setData(dataObject);
          }
        }
      }
    })
  }

  //获取省市联动数据
  function getAreaData(pid,type,init,dataObject) {
    var data = {"pid":pid};
    $.ajax({
      type:"post",
      url:projectName + '/esaleMuseum/getAreaByPid.do',
      data:data,
      async:true,
      success:function(result) {
        if (result.success == 1) {
          var dataList = result.data;
          if (type == "province") {
            var provinceStr = "";
            for(var i = 0;i < dataList.length;i++) {
              provinceStr +="<option value='"+dataList[i].id+"' >"+dataList[i].name+"</option>"
            }
            $("#province").append(provinceStr);
            if(!isEmpty(init) && !isEmpty(dataObject)){
              $("#province option[value='"+dataObject.province+"']").attr("selected",true);
              getAreaData(dataObject.province,"city",init,dataObject);
            }
            form.render();
          } else if (type == "city") {
            var cityStr = "";
            for(var j = 0;j < dataList.length;j++) {
              cityStr +="<option value='"+dataList[j].id+"' >"+dataList[j].name+"</option>"
            }
            $("#city").append(cityStr);
            if(!isEmpty(init) && !isEmpty(dataObject)){
              $("#city option[value='"+dataObject.city+"']").attr("selected",true);
              getAreaData(dataObject.city,"area",init,dataObject);
            }
            form.render();
          } else if (type == "area") {
            var areaStr = "";
            for(var k = 0;k < dataList.length;k++) {
              areaStr +="<option value='"+dataList[k].id+"' >"+dataList[k].name+"</option>"
            }
            $("#area").append(areaStr);
            if(!isEmpty(init) && !isEmpty(dataObject)){
              $("#area option[value='"+dataObject.area+"']").attr("selected",true);
            }
            form.render();
          }
        }
      }
    })
  }

  form.on('select(province)', function(data){
    $("#city").empty();
    $("#area").empty();
    form.render();
    getAreaData(data.value,"city");
  });
  form.on('select(city)', function(data){
    $("#area").empty();
    form.render();
    getAreaData(data.value,"area");
  });

  $(".uploadBtn").click(function() {
      //最大只能上传10张图片
      var len =  $("#picUpload").find('img').length;
      if(len==10){
          layer.msg("可上传图片数量已达最大限度",{icon:2});
          return false;
      }
    var projectName = 'esaleMuseum';
    uploadPicture(projectName);
  })
})



