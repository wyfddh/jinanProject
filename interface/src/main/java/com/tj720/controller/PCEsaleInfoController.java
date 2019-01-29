package com.tj720.controller;

import com.tj720.controller.base.controller.BaseController;
import com.tj720.controller.framework.JsonResult;
import com.tj720.dto.PCEsaleInfoDto;
import com.tj720.model.EsaleUserStatistics;
import com.tj720.service.EsaleUserStatisticsService;
import com.tj720.service.PCEsaleInfoService;
import com.tj720.utils.NetworkUtil;
import com.tj720.utils.Page;
import com.tj720.utils.common.IdUtils;
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
@RequestMapping("pc/esaleInfo")
public class PCEsaleInfoController extends BaseController {

    @Autowired
    private PCEsaleInfoService pcEsaleInfoService;

    @Autowired
    private EsaleUserStatisticsService esaleUserStatisticsService;

    /**
     * 功能描述: 获取资讯列表
     * @param: [museumId, type,  currentPage, size]
     * @return: com.tj720.controller.framework.JsonResult
     * @auther: 刘修
     */
    @RequestMapping(value="getListData", produces="application/json;charset=UTF-8")
    @ResponseBody
    public JsonResult getListData(String museumId,String type,@RequestParam(defaultValue = "1") Integer currentPage,@RequestParam(defaultValue = "10") Integer size) throws Exception{
        Page page = new Page();
        page.setCurrentPage(currentPage);
        page.setSize(size);
        List<PCEsaleInfoDto> pcEsaleShowList = pcEsaleInfoService.getEsaleInfoList(museumId, type, page);
        JsonResult jsonResult = new JsonResult(1, pcEsaleShowList, page);
        // 存入浏览记录
        EsaleUserStatistics esaleUserStatistics = new EsaleUserStatistics();
        esaleUserStatistics.setIpAddress(NetworkUtil.getIpAddress(request));
        esaleUserStatistics.setType(1);
        esaleUserStatistics.setId(IdUtils.nextId(esaleUserStatistics));
        esaleUserStatisticsService.insertUserStatistics(esaleUserStatistics);
//        return jsonResult.getJsonpString("cb");
        return jsonResult;
    }

    /**
     * 功能描述: 获取资讯详情
     * id 展览id
     * @param: [id]
     * @return: com.tj720.controller.framework.JsonResult
     * @auther: 刘修
     */
    @RequestMapping(value="getInfoDetail", produces="application/json;charset=UTF-8")
    @ResponseBody
    public JsonResult getShowDetail(String id ,String userId) throws Exception{
        PCEsaleInfoDto pcEsaleInfo = pcEsaleInfoService.getEsaleInfoById(id,userId);
        if(null == pcEsaleInfo){
            return new JsonResult(0,null,"111116");
        }
        JsonResult jsonResult = new JsonResult(1, pcEsaleInfo);
//        return jsonResult.getJsonpString("cb");
        return jsonResult;
    }



}
