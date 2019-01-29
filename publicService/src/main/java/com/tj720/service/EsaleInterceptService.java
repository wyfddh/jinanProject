package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.EsaleIntercept;
import net.sf.json.JSONObject;
import org.apache.poi.ss.usermodel.Workbook;

public interface EsaleInterceptService  {


    /**
     * 查询敏感词列表
     * @param interceptName
     * @param orderBy
     * @param size
     * @param currentPage
     * @return
     */
    JSONObject queryInterceptWords(String interceptName, String orderBy, Integer size, Integer currentPage);

    /**
     * 删除
     * @param id
     * @return
     */
    JsonResult delInterceptWords(String id);


    /**
     * 修改
     * @param esaleIntercept
     * @return
     */
    JsonResult updateInterceptWords(EsaleIntercept esaleIntercept);

    /**
     * 批量刪除
     * @param id
     * @return
     */
    JsonResult delAllInterceptWords(String[] id);


    EsaleIntercept toUpdate(Integer id);

    /**
     * 下载模板
     * @return
     */
    Workbook generateTemplate();

    /**
     * 上传模板
     * @param path
     * @return
     */
    JsonResult parseExcel(String path);

    /**
     * 数据导出
     * @param intercept
     * @param
     * @return
     */
    Workbook export(EsaleIntercept intercept);
}
