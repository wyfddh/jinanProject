package com.tj720.service.impl;

import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.springbeans.Config;
import com.tj720.dao.PCEsaleVideoMapper;
import com.tj720.dto.EsaleVideoDto;
import com.tj720.service.PCEsaleVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PCEsaleVideoServiceImpl implements PCEsaleVideoService {

	@Autowired
	private PCEsaleVideoMapper esaleVideoMapper;

	@Autowired
	private Config config;

	@Override
	public JsonResult getVideoList() throws Exception {
		try{
			List<EsaleVideoDto> list = esaleVideoMapper.selectVideoList();
			for(EsaleVideoDto videoDto:list){
				if (videoDto.getPlayNum() != null) {
					videoDto.setPlayNum(videoDto.getPlayNum()+1);
				}else {
					videoDto.setPlayNum(0);
				}

				String videoShowUrl = config.getRootUrl()+videoDto.getVideoUrl();
				videoDto.setVideoShowUrl(videoShowUrl);

				String provinceDes = videoDto.getProvinceDes() == null?"":videoDto.getProvinceDes();
				String cityDes = videoDto.getCityDes() == null?"":videoDto.getCityDes();
				String areaDes = videoDto.getAreaDes() == null?"":videoDto.getAreaDes();
				videoDto.setIntroducePlace(provinceDes+cityDes+areaDes);
			}

			return new JsonResult(1,list);

		}catch (Exception e){
			e.printStackTrace();
			return new JsonResult(0,null,"111116");
		}
	}

	@Override
	public JsonResult viewVideo(String videoId) throws Exception {
		int i = esaleVideoMapper.updatePlayNum(videoId);
		if(i > 0){
			return new JsonResult(1,"查看成功");
		}
		return new JsonResult(0,"查看失败");
	}


}
