package com.tj720.controller;

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.common.SysDict;
import com.tj720.service.SysDictSevice;
import com.tj720.utils.Page;
import com.tj720.video.LayUiTableJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wyf
 * @date 2018/10/16 10:41
 **/
@RestController
@RequestMapping("/sysDict")
public class SysDictController {

    @Autowired
    private SysDictSevice sysDictSevice;

    /**用户未登陆，请先登录用户未登陆，请先登录
     * @author wyf
     * @description  从字典表取下拉框数据
     * @date  2018/10/16 10:57
     * @param arr 请求的参数数组
     * @return com.tj720.controller.framework.JsonResult
     */
    @RequestMapping("getSelectDataByArea")
    public JsonResult getSelectDataByArea(@RequestParam(value = "arr[]") String[] arr) {

        if (arr == null || arr.length <= 0) {
            return new JsonResult(0);
        }
        Map<String, Object> map = sysDictSevice.getDictListByArr(arr);

        return new JsonResult(1, map);
    }

    @RequestMapping("/getSelectDataByKey")
    public JsonResult getSelectDataByKey(@RequestParam String key){
        try {
            List<SysDict> dictListByKey = sysDictSevice.getDictListByKey(key);
            return new JsonResult(1,dictListByKey);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,"查询失败");
        }
    }


}
