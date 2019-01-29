layui.use(['form','layer','layedit','laydate','upload'],function(){
    var form = layui.form
        layer = parent.layer === undefined ? layui.layer : top.layer,
        laypage = layui.laypage,
        upload = layui.upload,
        layedit = layui.layedit,
        laydate = layui.laydate,
        $ = layui.jquery;

    //用于同步编辑器内容到textarea
    layedit.sync(editIndex);

    //上传缩略图
    upload.render({
        elem: '.thumbBox',
        url: '../../json/userface.json',
        method : "get",  //此处是为了演示之用，实际使用中请将此删除，默认用post方式提交
        done: function(res, index, upload){
            var num = parseInt(4*Math.random());  //生成0-4的随机数，随机显示一个头像信息
            $('.thumbImg').attr('src',res.data[num].src);
           // $('.thumbBox').css("background","#fff");
        }
    });

    //格式化时间
    function filterTime(val){
        if(val < 10){
            return "0" + val;
        }else{
            return val;
        }
    }
    //定时发布
    var time = new Date();
    var submitTime = time.getFullYear()+'-'+filterTime(time.getMonth()+1)+'-'+filterTime(time.getDate())+' '+filterTime(time.getHours())+':'+filterTime(time.getMinutes())+':'+filterTime(time.getSeconds());
    laydate.render({
        elem: '#release',
        type: 'datetime',
        trigger : "click",
        done : function(value, date, endDate){
            submitTime = value;
        }
    });
    form.on("radio(release)",function(data){
        if(data.elem.title == "定时发布"){
            $(".releaseDate").removeClass("layui-hide");
            $(".releaseDate #release").attr("lay-verify","required");
        }else{
            $(".releaseDate").addClass("layui-hide");
            $(".releaseDate #release").removeAttr("lay-verify");
            submitTime = time.getFullYear()+'-'+(time.getMonth()+1)+'-'+time.getDate()+' '+time.getHours()+':'+time.getMinutes()+':'+time.getSeconds();
        }
    });

    form.verify({
        newsName : function(val){
            if(val == ''){
                return "文章标题不能为空";
            }
        },
        content : function(val){
            if(val == ''){
                return "文章内容不能为空";
            }
        }
    })
    form.on("submit(addNews)",function(data){
        //截取文章内容中的一部分文字放入文章摘要
        var abstract = layedit.getText(editIndex).substring(0,50);
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        // 实际使用时的提交信息
        // $.post("上传路径",{
        //     newsName : $(".newsName").val(),  //文章标题
        //     abstract : $(".abstract").val(),  //文章摘要
        //     content : layedit.getContent(editIndex).split('<audio controls="controls" style="display: none;"></audio>')[0],  //文章内容
        //     newsImg : $(".thumbImg").attr("src"),  //缩略图
        //     classify : '1',    //文章分类
        //     newsStatus : $('.newsStatus select').val(),    //发布状态
        //     newsTime : submitTime,    //发布时间
        //     newsTop : data.filed.newsTop == "on" ? "checked" : "",    //是否置顶
        // },function(res){
        //
        // })
        setTimeout(function(){
            top.layer.close(index);
            top.layer.msg("文章添加成功！");
            layer.closeAll("iframe");
            //刷新父页面
            parent.location.reload();
        },500);
        return false;
    })

    //预览
    form.on("submit(look)",function(){
        layer.alert("此功能需要前台展示，实际开发中传入对应的必要参数进行文章内容页面访问");
        return false;
    })

    //创建一个编辑器
    var editIndex = layedit.build('news_content',{
        height : 214,
        uploadImage : {
            url : "../../json/newsImg.json"
        }
    });
    var editIndex1 = layedit.build('news_content1',{
        height : 214,
        uploadImage : {
            url : "../../json/newsImg.json"
        }
    });
    var editIndex2 = layedit.build('news_content2',{
        height : 214,
        uploadImage : {
            url : "../../json/newsImg.json"
        }
    });
    var editIndex3 = layedit.build('news_content3',{
        height : 214,
        uploadImage : {
            url : "../../json/newsImg.json"
        }
    });

    $(".comeon").on({
        'click':function () {
            layer.confirm('纳尼？', {
                title:'任务提示框',
                id:'maylay',
                area: ['450px', '218px'],
                skin: 'demo-class',
                content:"<span>You are superman!</span>",
                btn: ['取消', '确认'] //可以无限个按钮
               /* ,btn3: function(index, layero){
                    //按钮【按钮三】的回调
                }*/
            }, function(index, layero){
               layer.close(index);

            }, function(index){
                layer.alert('我是确认按钮的回调')

            });
            return false;
        }
    });

    //提示反馈
    $("#successBtn").on({
        'click': function() {
            layer.open({
                type: 1,
                title: false, //不显示标题
                closeBtn: 0,
                shadeClose: true,
                skin: "msg",
                content: "<div class='msg successMsg'><div class='msg-icon'></div><div class='msg-title'>操作成功</div><div class='msg-txt'>操作成功的提示文字</div></div>"
            });
            return false;
        }
    });
    $("#errorBtn").on({
        'click':function(){
            layer.open({
                type: 1,
                title: false, //不显示标题
                closeBtn: 0,
                shadeClose: true,
                skin: "msg",
                content: "<div class='msg errorMsg'><div class='msg-icon'></div><div class='msg-title'>操作失败</div><div class='msg-txt'>操作失败的提示文字</div></div>"
            });
            return false;
        }
    });
    $("#alertBtn").on({
        'click':function(){
            layer.open({
                type: 1,
                title: false, //不显示标题
                closeBtn: 0,
                shadeClose: true,
                skin: "msg",
                content: "<div class='msg alertMsg'><div class='msg-icon'></div><div class='msg-title'>无法完成操作</div><div class='msg-txt'>警告的提示文字</div></div>"
            });
            return false;
        }
    });
    $("#loadingBtn").on({
        'click':function(){
            layer.open({
                type: 1,
                title: false, //不显示标题
                closeBtn: 0,
                shadeClose: true,
                skin: "msg",
                content: "<div class='msg loadingMsg'><div class='msg-icon'></div><div class='msg-title'>正在处理</div><div class='msg-txt'>等待处理的提示文字</div></div>"
            });
            return false;
        }
    });

})
//验证 text 输入提示 start
    var wangxinForm = function (ele, opt) {
        this.$ele = ele,
            this.defaults = {
                name: "zsw"
            },
            this.options = $.extend({}, this.defaults, opt);
    }

    //增加方法
    wangxinForm.prototype = {
        AddHtml: function (obj,tip) {
            var html="<span class='tips'>"+tip.name+"</span>";

            $(obj).after(html);
            this.cssHtml();
           // this.bindEvent(inputElments);
        },
        removeHtml:function(obj){
             $(obj).parent().find(".tips").remove();
        },
        cssHtml:function(){
          $(".tips").css({
              "position":"absolute",
              "color":"red",
              "left":0
          })
        },
        bindEvent:function () {
            var _this=this;
            //var inputElments=$(this.$ele.find("input[type='text']"));
                  $(this.$ele).parent().css({"position":"relative"});
                  $(this.$ele).bind("input propertychange",
                      function () {
                          if(_this.options.empty&&$(this).val()==Number(_this.options.empty.len)){   //长度零
                              _this.removeHtml($(this));
                              _this.AddHtml( $(this),_this.options.empty);
                          }
                         else if(_this.options.maxLen&&$(this).val().length>=Number(_this.options.maxLen.len)){  //最大长度
                              _this.removeHtml($(this));
                              _this.AddHtml( $(this),_this.options.maxLen);
                          }else{
                              _this.removeHtml();
                          }
                      }
            )
        }
    }

    //注入
    $.fn.MyName = function (options) {
        var initName = new wangxinForm(this, options);
        return initName.bindEvent();
    }
//验证 text 输入提示 end
$("#name").MyName(
    {empty:{name:"输入不能为空",len:"0"},maxLen:{name:"长度不能超过2",len:"2"}});
$("#ticket").MyName(
    {exp:{name:"输入不能为空",len:"0"},maxLen:{name:"长度不能超过2",len:"2"}});



