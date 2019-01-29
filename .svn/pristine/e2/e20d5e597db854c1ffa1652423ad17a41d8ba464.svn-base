package com.tj720.controller;

import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.auth.AuthPassport;
import com.tj720.dto.PCEsaleActivityDto;
import com.tj720.dto.PCEsalePubUserDto;
import com.tj720.service.PCActivityService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("pc/activity")
public class PCActivityController {
    @Autowired
    private PCActivityService activityService;

    /**
     * 查询我的活动列表
     */
    @RequestMapping("/getActivityListByUid")
    @ResponseBody
    public JsonResult getActivityListByUid(String uid,String key,@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "5") Integer size) throws Exception{
        return activityService.getActivityListByUid(uid,key,currentPage,size);
    }

}
