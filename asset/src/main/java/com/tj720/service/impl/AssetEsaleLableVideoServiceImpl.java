package com.tj720.service.impl;

import com.tj720.dao.AssetEsaleLabelMapper;
import com.tj720.dao.AssetEsaleLableVideoMapper;
import com.tj720.model.AssetEsaleLabel;
import com.tj720.model.AssetEsaleLableVideo;
import com.tj720.service.AssetEsaleLableVideoService;
import com.tj720.utils.Tools;
import com.tj720.utils.common.IdUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AssetEsaleLableVideoServiceImpl implements AssetEsaleLableVideoService {
	@Autowired
	private AssetEsaleLableVideoMapper esaleLableVideoMapper;
	@Autowired
	private AssetEsaleLabelMapper esaleLabelMapper;

	@Override
	public int insertOrUpdate(List<String> stringList, String videoId) {
		int result = 0;
		if(stringList == null || stringList.size() == 0){
			return result;
		}
		String userId = Tools.getUserId();
		List<AssetEsaleLabel> saveLabelList = new ArrayList<>();
		List<AssetEsaleLableVideo> saveLabelVideoList = new ArrayList<>();
		for(String item: stringList){
			if(StringUtils.isBlank(item)){
				continue;
			}
			List<AssetEsaleLabel> infoListByName = esaleLabelMapper.getInfoListByName(item);
			String currentLabelId = "";
			if(infoListByName == null || infoListByName.size() == 0){
				AssetEsaleLabel currentLabel = new AssetEsaleLabel();
				currentLabelId = IdUtils.nextId(new AssetEsaleLabel());
				currentLabel.setId(currentLabelId);
				currentLabel.setLabelName(item);
				currentLabel.setCreateBy(userId);
				currentLabel.setCreateDate(new Date());
				currentLabel.setUpdateBy(userId);
				currentLabel.setUpdateDate(new Date());
				currentLabel.setDataState("1");
				saveLabelList.add(currentLabel);
			}else{
				currentLabelId = infoListByName.get(0).getId();
			}
			AssetEsaleLableVideo currentLableVideo = new AssetEsaleLableVideo();
			currentLableVideo.setId(IdUtils.nextId(new AssetEsaleLableVideo()));
			currentLableVideo.setLabelId(currentLabelId);
			currentLableVideo.setVideoId(videoId);
			currentLableVideo.setCreateBy(userId);
			currentLableVideo.setCreateDate(new Date());
			currentLableVideo.setUpdateBy(userId);
			currentLableVideo.setUpdateDate(new Date());
			saveLabelVideoList.add(currentLableVideo);
		}

		//先清空关联表
		esaleLableVideoMapper.deleteByVideoId(videoId);

		//批量保存标签
		if(saveLabelList == null || saveLabelList.size() == 0){
			int i1 = esaleLableVideoMapper.teachInsert(saveLabelVideoList);
			result = i1;
		}else{
			int i = esaleLabelMapper.teachInsert(saveLabelList);
			if(i>0){
				int i1 = esaleLableVideoMapper.teachInsert(saveLabelVideoList);
				result = i + i1;
			}
		}
		return result;
	}
}
