package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.EsaleActivity;
import com.tj720.model.EsalePersonDiary;
import com.tj720.utils.Page;
import net.sf.json.JSONObject;

public interface EsalePersonDiaryService {

    /**
     *
     * 功能描述: 新增或者编辑
     *
     * @param: [personDiary]
     * @return: com.tj720.controller.framework.JsonResult
     * @auther: caiming
     * @date: 2018/11/29 11:37
     */
    JsonResult insertOrUpdate(EsalePersonDiary personDiary);

    /**
     *
     * 功能描述: 获取分页
     *
     * @param: [personDiary, page]
     * @return: com.tj720.controller.framework.JsonResult
     * @auther: caiming
     * @date: 2018/11/29 17:20
     */
    JsonResult getList(EsalePersonDiary personDiary, Page page);

    /**
     *
     * 功能描述: 获取分页
     *
     * @param: [personDiary, page]
     * @return: com.tj720.controller.framework.JsonResult
     * @auther: caiming
     * @date: 2018/11/29 17:20
     */
    JSONObject getDiaryList(EsalePersonDiary personDiary, Page page);

    /**
     *
     * 功能描述: 删除个人日记
     *
     * @param: [id]
     * @return: com.tj720.controller.framework.JsonResult
     * @auther: caiming
     * @date: 2018/11/30 16:37
     */
    JsonResult deleteDiary(String id);

    /**
     *
     * 功能描述: 获取日记
     *
     * @param: [id]
     * @return: com.tj720.controller.framework.JsonResult
     * @auther: caiming
     * @date: 2018/11/30 17:40
     */
    JsonResult getDiaryById(String id);

    /**
     *
     * 功能描述: 根据年月，获取报表数据
     *
     * @param: [year, month]
     * @return: com.tj720.controller.framework.JsonResult
     * @auther: caiming
     * @date: 2018/12/9 17:27
     */
    JsonResult getDataByMonth(String year, String month);

}
