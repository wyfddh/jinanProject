package com.tj720.controller;

import com.tj720.controller.framework.JsonResult;
import com.tj720.dto.PCEsaleMuseumDto;
import com.tj720.service.PCEsaleMuseumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: 刘修
 * @Description:
 */
@RestController
@RequestMapping("pc/esaleMuseum")
public class PCEsaleMuseumController {

    @Autowired
    private PCEsaleMuseumService pcEsaleMuseumService;

    /**
     * 功能描述: 获取博物馆列表
     * @param: [museumId, type,  currentPage, size]
     * @return: com.tj720.controller.framework.JsonResult
     * @auther: 刘修
     */
    @RequestMapping(value="getListData", produces="application/json;charset=UTF-8")
    @ResponseBody
    public JsonResult getListData() throws Exception{
        List<PCEsaleMuseumDto> esaleMuseumList = pcEsaleMuseumService.getEsaleMuseumList();
        JsonResult jsonResult = new JsonResult(1, esaleMuseumList);
//        return jsonResult.getJsonpString("cb");
        return jsonResult;
    }

    /**
     * 功能描述: 获取博物馆详情
     * id 展览id
     * @param: [id]
     * @return: com.tj720.controller.framework.JsonResult
     * @auther: 刘修
     */
    @RequestMapping(value="getMuseumDetail", produces="application/json;charset=UTF-8")
    @ResponseBody
    public JsonResult getMuseumDetail(String id) throws Exception{
        PCEsaleMuseumDto pcEsaleMuseumDto = pcEsaleMuseumService.getEsaleMuseumById(id);
        if(null == pcEsaleMuseumDto){
            return new JsonResult(0,null,"111116");
        }
        JsonResult jsonResult = new JsonResult(1, pcEsaleMuseumDto);
//        return jsonResult.getJsonpString("cb");
        return jsonResult;
    }



}
