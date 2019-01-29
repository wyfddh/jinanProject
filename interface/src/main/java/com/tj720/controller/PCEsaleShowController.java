package com.tj720.controller;

import com.tj720.controller.framework.JsonResult;
import com.tj720.dto.PCEsaleShowDto;
import com.tj720.service.PCEsaleShowService;
import com.tj720.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: 刘修
 * @Description:
 */
@RestController
@RequestMapping("pc/esaleShow")
public class PCEsaleShowController {

    @Autowired
    private PCEsaleShowService pcEsaleShowService;

    /**
     * 功能描述: 获取展览列表
     * type-1 基本陈列
     * type-2 专题展览
     *type-3 流动展览
     * @param: [museumId, type,  currentPage, size]
     * @return: com.tj720.controller.framework.JsonResult
     * @auther: 刘修
     */
    @RequestMapping(value="getListData", produces="application/json;charset=UTF-8")
    @ResponseBody
    public JsonResult getListData(String museumId,@RequestParam(defaultValue = "1") String type,@RequestParam(defaultValue = "1") Integer currentPage,@RequestParam(defaultValue = "10") Integer size) throws Exception{
        if(currentPage == 0 || size == 0){
            return new JsonResult(0);
        }
        Page page = new Page();
        page.setCurrentPage(currentPage);
        page.setSize(size);
        List<PCEsaleShowDto> pcEsaleShowList = pcEsaleShowService.getEsaleShowList(museumId, type, page);
        JsonResult jsonResult = new JsonResult(1, pcEsaleShowList, page);
//        return jsonResult.getJsonpString("cb");
        return jsonResult;
    }

    /**
     * 功能描述: 获取展览详情
     * id 展览id
     * @param: [id]
     * @return: com.tj720.controller.framework.JsonResult
     * @auther: 刘修
     */
    @RequestMapping(value="getShowDetail", produces="application/json;charset=UTF-8")
    @ResponseBody
    public JsonResult getShowDetail(String id ,String userId) throws Exception{
        PCEsaleShowDto pcEsaleShow = pcEsaleShowService.getEsaleShowById(id,userId);
        if(null == pcEsaleShow){
            return new JsonResult(0,null,"111116");
        }
        JsonResult jsonResult = new JsonResult(1, pcEsaleShow);
        return jsonResult;
    }



}
