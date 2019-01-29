package com.tj720.controller;

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.EsaleLabel;
import com.tj720.service.EsaleLabelService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("labelManager")
public class EsaleLabelController {
    @Autowired
    private EsaleLabelService esaleLabelService;

    /**
     * 标签管理列表查询
     */
    @RequestMapping("/getlabelList")
    @ResponseBody
    public JSONObject getlabelList(@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer size) throws Exception{
        return esaleLabelService.getlabelList(currentPage,size);
    }

    /**
     * 修改标签
     */
    @RequestMapping("/updatelabel")
    @ResponseBody
    public JsonResult updatelabel(EsaleLabel esaleLabel) throws Exception{
           return esaleLabelService.updatelabel(esaleLabel);
    }

    /**
     * 删除
     */
    @RequestMapping("/delLabel")
    @ResponseBody
    public JsonResult delLabel(EsaleLabel esaleLabel) throws Exception{
        if (esaleLabel.getId()!=null){

            return esaleLabelService.delLabel(esaleLabel.getId());
        }
        return new JsonResult(0,"id为空！");

    }

    /**
     * 统计信息
     */
    @RequestMapping("/countInfo")
    @ResponseBody
    public JsonResult countInfo(EsaleLabel esaleLabel) throws Exception{
        if (esaleLabel.getId()!=null){
            return esaleLabelService.countInfo(esaleLabel.getId());
        }
        return new JsonResult(0,"id为空！");

    }

    /**
     * 查询标签通过id
     */
    @RequestMapping("/queryLabelById")
    @ResponseBody
    public JsonResult queryLabelById(EsaleLabel esaleLabel) throws Exception{
        if (esaleLabel.getId()!=null){
            return esaleLabelService.queryLabelById(esaleLabel.getId());
        }
        return new JsonResult(0,"id为空！");

    }



}
