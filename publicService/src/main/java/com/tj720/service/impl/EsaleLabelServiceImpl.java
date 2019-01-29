package com.tj720.service.impl;

import com.alibaba.fastjson.JSON;
import com.tj720.controller.framework.JsonResult;
import com.tj720.dao.EsaleLabelMapper;
import com.tj720.model.EsaleLabel;
import com.tj720.service.EsaleLabelService;
import com.tj720.utils.Page;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EsaleLabelServiceImpl implements EsaleLabelService {
    @Autowired
    private EsaleLabelMapper esaleLabelMapper;

    /**
     * 标签列表查询
     */
    @Override
    public JSONObject getlabelList(Integer currentPage, Integer size) {
        JSONObject jsonObject = new JSONObject();
        try {

            Page page = new Page();
            page.setSize(size);
            page.setCurrentPage(currentPage);

            Map<String,Object> map = new HashMap<String,Object>();


            //符合条件总数
            Integer count = esaleLabelMapper.countAllLabel();
            System.out.println("count : "+count);
            page.setAllRow(count);
            map.put("start",page.getStart());
            map.put("end",page.getSize());

            //查询分页数据
            List<EsaleLabel> esaleLabelList = esaleLabelMapper.queryAllLabel(map);
            String jsonString = JSON.toJSONString(esaleLabelList);
            System.out.println("jsonString  :   "+jsonString);
            jsonObject.put("code", 0);
            jsonObject.put("msg", "");
            jsonObject.put("count", page.getAllRow());
            jsonObject.put("data", jsonString);
            return jsonObject;

        }catch (Exception e){
            jsonObject.put("code", 1);
            jsonObject.put("msg", e.getMessage());
            jsonObject.put("count", 0);
            jsonObject.put("data", null);
            return jsonObject;
       }
    }

    @Override
    public JsonResult delLabel(String id) {
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("id",id);
            map.put("updateDate", new Date());
            esaleLabelMapper.updateLable(map);
            return  new JsonResult(1);
        }catch (Exception e){
            e.printStackTrace();
            return  new JsonResult(0,"系统异常");
        }
    }

    @Override
    public JsonResult updatelabel(EsaleLabel esaleLabel) {
        try {
            esaleLabel.setUpdateDate(new Date());
            esaleLabelMapper.updateByPrimaryKeySelective(esaleLabel);
            return  new JsonResult(1);
        }catch (Exception e){
            return  new JsonResult(0,"系统异常");
        }
    }

    /**
     * 信息统计
     * @param id
     * @return
     */
    @Override
    public JsonResult countInfo(String id) {
        try {
            List<EsaleLabel> esaleLabelList = esaleLabelMapper.countInfo(id);
            EsaleLabel esaleLabel1 = esaleLabelMapper.selectByPrimaryKey(id);
            EsaleLabel label = new EsaleLabel();
            if (esaleLabel1!=null&&esaleLabel1.getLabelName()!=null){
            label.setLabelName(esaleLabel1.getLabelName());}
            for (EsaleLabel esaleLabel:esaleLabelList
                 ) {
                System.out.println("esaleLabel.getVidoeType() ===  "+esaleLabel.getVidoeType());
                //宣传视频
                if(esaleLabel.getVidoeType().equals("1")){
                    int i = esaleLabel.getCountVideo() + 1;
                    label.setCountVideo(i);
                }else {
                    label.setCountVideo(0);
                }
                //动漫
                if(esaleLabel.getVidoeType().equals("2")){
                    int i = esaleLabel.getCountComic() + 1;
                    label.setCountComic(i);
                }else {
                    label.setCountComic(0);
                }
                //会议
                if(esaleLabel.getVidoeType().equals("3")){
                    int i = esaleLabel.getCountMetting() + 1;
                    label.setCountMetting(i);
                }else {
                    label.setCountMetting(0);
                }
                //讲座
                if(esaleLabel.getVidoeType().equals("4")){
                    int i = esaleLabel.getCountLecture() + 1;
                    label.setCountLecture(i);
                }else {
                    label.setCountLecture(0);
                }
            }
            return  new JsonResult(1,label);
        }catch (Exception e){
            return  new JsonResult(0,"系统异常");
        }
    }

    @Override
    public JsonResult queryLabelById(String id) {
        try {
            EsaleLabel esaleLabel = esaleLabelMapper.selectByPrimaryKey(id);
            return  new JsonResult(1,esaleLabel);
        }catch (Exception e){
            return  new JsonResult(0,"系统异常");
        }
    }

}
