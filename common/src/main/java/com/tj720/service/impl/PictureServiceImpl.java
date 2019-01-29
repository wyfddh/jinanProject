package com.tj720.service.impl;/**
 * Created by Administrator on 2018/9/25.
 */

import com.tj720.controller.springbeans.Config;
import com.tj720.model.common.MipAttachment;
import com.tj720.service.MipAttachmentService;
import com.tj720.service.PictureService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author wyf
 * @Description //TODO $
 * @Date $ $
 * @Param $
 * @return $
 **/
@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    private MipAttachmentService mipAttachmentService;

    @Autowired
    private Config config;
    /**
     * @Author wyf
     * @Description  获取完整url的图片集合
     * @Date  2018/9/29 9:54
     * @Param
     * @return java.util.List<com.tj720.model.common.MipAttachment>
     */
    @Override
    public List<MipAttachment> getPicList(String picIds) {
        List<MipAttachment> picList = mipAttachmentService.getListByIds(picIds);
        if (picList.size() > 0) {
            for (MipAttachment mipAttachment : picList) {
                String attPath = mipAttachment.getAttPath();
                if(!attPath.startsWith(config.getRootUrl())){
                    mipAttachment.setAttPath(config.getRootUrl() + attPath);
                }
            }
        }
        return picList;
    }

    /**
     * 设置主图和删除图片
     * @param picids
     * @param isMain
     * @param delpicids
     * @return
     * @throws Exception
     */
    @Override
    public Boolean setMain(String picids,String isMain,String delpicids) throws Exception{
        Boolean flag = true;
        try {
            //  设置主图
            List<MipAttachment> picList = mipAttachmentService.getListByIds(picids);
            if (picList.size() > 0) {
                for (MipAttachment mipAttachment : picList) {
                    if (mipAttachment.getAttId().equals(isMain)) {
                        mipAttachment.setIsMain("1");
                    } else {
                        mipAttachment.setIsMain("0");
                    }
                }
                mipAttachmentService.batchUpdate(picList);
            }
            //删除图片
            if (StringUtils.isNotBlank(delpicids)) {
                mipAttachmentService.batchDelete(delpicids);
            }
        }catch(Exception e){
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }
}
