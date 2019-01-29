package com.tj720.service.impl;

import com.alibaba.fastjson.JSON;
import com.tj720.dao.PostVideosMapper;
import com.tj720.model.EsaleCollectionInfo;
import com.tj720.model.PostVideo;
import com.tj720.service.EsaleCollectionInfoService;
import com.tj720.service.PostVideosService;
import com.tj720.utils.Page;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostVideosServiceImpl implements PostVideosService {
    @Autowired
    private PostVideosMapper postVideoMapper;

    @Autowired
    private EsaleCollectionInfoService esaleCollectionInfoService;

    @Override
    public JSONObject getPostVideo(String labelId, Integer currentPage, Integer size) {
        JSONObject jsonObject = new JSONObject();
        try {
            Page page = new Page();
            page.setSize(size);
            page.setCurrentPage(currentPage);
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("labelId",labelId);
            //符合条件总数
            Integer count = postVideoMapper.countPostVideoByLabelId(map);
            System.out.println("count : "+count);
            page.setAllRow(count);
            map.put("start",page.getStart());
            map.put("end",page.getSize());

            //查询分页数据
            List<PostVideo> postVideos = postVideoMapper.queryPostVideoByLabelId(map);

            for (PostVideo pv:postVideos
                 ) {
                //设置藏品名称
                if(pv.getRelativeCollection()!=null){
                    EsaleCollectionInfo esaleCollectionInfo = esaleCollectionInfoService.queryColl(pv.getRelativeCollection());
                    if(null!=esaleCollectionInfo){
                        pv.setRelativeCollectionName(esaleCollectionInfo.getCollectionName());
                    }else {
                        pv.setRelativeCollectionName("无");
                    }
                }else {
                    pv.setRelativeCollectionName("无");
                }
                //资料分类
                //宣传视频
                if(pv.getVideoType().equals("1")){
                    pv.setVideoType("宣传视频");
                }
                //动漫
                if(pv.getVideoType().equals("2")){
                    pv.setVideoType("动漫");
                }
                //会议
                if(pv.getVideoType().equals("3")){
                    pv.setVideoType("会议");
                }
                //讲座
                if(pv.getVideoType().equals("4")){
                    pv.setVideoType("讲座");
                }

                //设置资料状态
                if(null!= pv.getStatus() && pv.getStatus().equals("4")){
                    pv.setStatus("已发布");
                }
                if(null!= pv.getStatus() && pv.getStatus().equals("3")){
                    pv.setStatus("待审批");
                }
                //设置下载设置
                if(null!= pv.getAuthSetting()){
                    if (pv.getAuthSetting().equals("2")) {
                        pv.setStatus("公开仅查看");
                    }
                    if (pv.getAuthSetting().equals("3")) {
                        pv.setStatus("公开仅下载");
                    }
                    if (pv.getAuthSetting().equals("1")) {
                        pv.setStatus("不公开");
                    }
                }

                System.out.println("pv.getAuthSetting() ===  "+pv.getAuthSetting());
            }
            String jsonString = JSON.toJSONString(postVideos);
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
}
