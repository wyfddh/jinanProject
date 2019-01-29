package com.tj720.controller.collectinferface;

import com.tj720.controller.base.controller.BaseController;
import com.tj720.controller.framework.JsonResult;
import com.tj720.service.EsaleInterfaceCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 外部藏品接口controller
 * @author 杜昶
 */
@RestController
@RequestMapping("/esaleInterfaceColelct")
public class EsaleInterfaceCollectController extends BaseController {

    @Autowired
    private EsaleInterfaceCollectService esaleInterfaceCollectService;

    /**
     * 获取藏品种类数据
     * @param typegroupid 字典类别ID
     * @return
     */
    @RequestMapping("/getInterfaceCollectSelectType")
    public JsonResult getInterfaceCollectSelectType(String typegroupid) {
        try {
            return esaleInterfaceCollectService.getInterfaceCollectSelectType(typegroupid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult("111116");
    }
}
