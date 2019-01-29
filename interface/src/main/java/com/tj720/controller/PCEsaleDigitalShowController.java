package com.tj720.controller;

import com.tj720.controller.framework.JsonResult;
import com.tj720.dto.PCEsaleDigitalShowDto;
import com.tj720.service.PCEsaleDigitalShowService;
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
@RequestMapping("pc/esaleDigitalShow")
public class PCEsaleDigitalShowController {

    @Autowired
    private PCEsaleDigitalShowService pcEsaleDigitalShowService;

    /**
     * 功能描述: 获取数字展厅列表
     * museumId： 所属博物馆
     * @param: [museumId]
     * @return: com.tj720.controller.framework.JsonResult
     * @auther: 刘修
     */
    @RequestMapping(value="getListData", produces="application/json;charset=UTF-8")
    @ResponseBody
    public JsonResult getListData(String museumId) throws Exception{
        List<PCEsaleDigitalShowDto> pcEsaleDigitalShowList = pcEsaleDigitalShowService.getEsaleDigitalShowList(museumId);
        JsonResult jsonResult = new JsonResult(1, pcEsaleDigitalShowList);
//        return jsonResult.getJsonpString("cb");
        return jsonResult;
    }

    /**
     * @author wyf
     * @description  数字展厅浏览次数+1
     * @date  2018/12/9 14:33
     * @param
     * @return com.tj720.controller.framework.JsonResult
     */
    @RequestMapping("digitalBrowseNum")
    public JsonResult digitalBrowseNum(String id) {

        JsonResult jsonResult = pcEsaleDigitalShowService.digitalBrowseNum(id);

        return jsonResult;
    }



}
