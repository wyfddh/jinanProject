package com.tj720.controller;

import com.alibaba.fastjson.JSON;
import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.auth.AuthPassport;
import com.tj720.model.EsaleIntercept;
import com.tj720.model.EsalePubUser;
import com.tj720.model.EsalePubUserVo;
import com.tj720.service.EsalePubUserService;
import com.tj720.service.EsaleUserActivityService;
import net.sf.json.JSONObject;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;

@RequestMapping("/esalePubUserManager")
@Controller
public class EsalePubUserController {

    @Autowired
    private EsalePubUserService esalePubUserService;

    @Autowired
    private EsaleUserActivityService esaleUserActivityService;
    /**
     * 加载公众用户列表
     */
    @RequestMapping("/getEsalePubUserList")
    @ResponseBody
    public JSONObject getIntroduceList(String key, String phone, String isYoung , String orderBy,
                                       @RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer size) throws Exception{
        return esalePubUserService.getEsalePubUserList(key,phone,isYoung,orderBy,size,currentPage);
    }

    /**
     * 查看用户详情
     */
    @RequestMapping("/getUserInfomation")
    @ResponseBody
    public JSONObject getUserInfomation(String id){
        JSONObject jsonObject = new JSONObject();
        try {
            EsalePubUser user = esalePubUserService.queryUserById(id);
            String jsonString = JSON.toJSONString(user);
            System.out.println("jsonString  :   "+jsonString);
            jsonObject.put("code", 0);
            jsonObject.put("msg", "");
            jsonObject.put("count", 0);
            jsonObject.put("data", jsonString);
        }catch (Exception e){
            jsonObject.put("code", 1);
            jsonObject.put("msg", e.getMessage());
            jsonObject.put("count", 0);
            jsonObject.put("data", null);
        }
        return jsonObject;

    }
    /**
     * 统计信息
     */
    @RequestMapping("/countInfomation")
    @ResponseBody
    public JSONObject countInfomation(String uid){
        System.out.println("uid  :   "+uid);
        return  esalePubUserService.countInfomation(uid);
    }

    /**
     * 编辑用户信息
     */
    @RequestMapping("/updateUserInfo")
    @ResponseBody
    public JsonResult updateUserInfo(EsalePubUser esalePubUser){
        System.out.println("esalePubUser  :   "+esalePubUser);
        return  esalePubUserService.updateUser(esalePubUser);
    }

    /**
     * 冻结
     */
    @RequestMapping("/freezeUser")
    @ResponseBody
    public JsonResult freezeUser(@RequestParam("id") String id){
        System.out.println("id  :   "+id);
        return esalePubUserService.freezeUser(id);
    }

    /**
     * 批量冻结
     */
    @RequestMapping("/freezeUsers")
    @ResponseBody
    public JsonResult freezeUsers(EsalePubUserVo vo){
        return esalePubUserService.freezeUsers(vo.getIds());
    }

    /**
     * 黑名单列表查询
     */
    @RequestMapping("/getUserBlacklist")
    @ResponseBody
    public JSONObject getUserBlacklist(String key, String phone, String isYoung , String orderBy,
                                       @RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer size) throws Exception{
        return esalePubUserService.getEsaleUserBlacklist(key,phone,isYoung,orderBy,size,currentPage);
    }

    /**
     * 移除黑名单
     */
    @RequestMapping("/moveOutUser")
    @ResponseBody
    public JsonResult moveOutUser(@RequestParam("id") String id){
        System.out.println("uid  :   "+id);
        return esalePubUserService.moveOutUser(id);
    }

    /**
     * 批量移除
     */
    @RequestMapping("/moveOutUsers")
    @ResponseBody
    public JsonResult moveOutUsers(EsalePubUserVo vo){
        return esalePubUserService.moveOutUsers(vo.getIds());
    }

    /**
     * 查询我的活动记录
     */
    @RequestMapping("/getActivityRecordByUserId")
    @ResponseBody
    public JSONObject getActivityRecordByUserId(String uid, Integer currentPage, Integer size){
        return esaleUserActivityService.getActivityRecordByUserId(uid,currentPage,size);
    }

    /**
     * 公众用户数据导出
     */
    @RequestMapping("/exportUserData")
    @ResponseBody
    public JsonResult exportUserData(EsalePubUserVo vo , HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        try {
            // 下载文件的默认名称
            response.setHeader("Content-disposition",
                    "attachment;filename=" + URLEncoder.encode("公众用户数据列表.xlsx", "UTF-8"));// 默认Excel名称
            // 创建Excel对象
            Workbook wb = esalePubUserService.exportUserData(vo);
            // 写出流
            wb.write(response.getOutputStream());
            return  new JsonResult(1);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0,"导出失败");
        }
    }


    /**
     * 黑名单数据导出
     */
    @RequestMapping("/exportBlacklistData")
    @ResponseBody
    public JsonResult exportBlacklistData(EsalePubUserVo vo , HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        try {
            // 下载文件的默认名称
            response.setHeader("Content-disposition",
                    "attachment;filename=" + URLEncoder.encode("黑名单数据列表.xlsx", "UTF-8"));// 默认Excel名称
            // 创建Excel对象
            Workbook wb = esalePubUserService.exportBlacklistData(vo);
            // 写出流
            wb.write(response.getOutputStream());
            return  new JsonResult(1);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0,"导出失败");
        }
    }

}
