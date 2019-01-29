package com.tj720.controller;

import com.tj720.service.PostVideosService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/postVideosManager")
public class PostVideosController {
    @Autowired
    private PostVideosService postVideoService;

    /**
     * 标签管理列表查询
     */
    @RequestMapping("getPostVideos")
    @ResponseBody
    public JSONObject getPostVideos(@RequestParam("labelId") String labelId , @RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer size) throws Exception{
        return postVideoService.getPostVideo(labelId,currentPage,size);
    }
}
