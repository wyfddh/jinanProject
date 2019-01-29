package com.tj720.service.impl;

import com.alibaba.fastjson.JSON;
import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.springbeans.Config;
import com.tj720.dao.EsaleFiledataActivityMapper;
import com.tj720.model.EsaleFiledataActivity;
import com.tj720.model.common.FileType;
import com.tj720.service.EsaleFiledataActivityService;
import com.tj720.utils.DateFormartUtil;
import com.tj720.utils.FtpUtil;
import com.tj720.utils.Page;
import com.tj720.utils.Tools;
import com.tj720.utils.common.IdUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.*;

@Service
public class EsaleFiledataActivityServiceImpl implements EsaleFiledataActivityService {
    @Autowired
    private EsaleFiledataActivityMapper esaleFiledataActivityMapper;

    @Autowired
    private Config config;

    private FtpUtil ftpUtil;

    @Override
    public JSONObject getFileListById(String key, Integer currentPage, Integer size) {
        JSONObject jsonObject = new JSONObject();

        try {
            if (StringUtils.isBlank(Tools.getUserId())) {
                throw new Exception("登录异常");
            }

            Page page = new Page();
            page.setSize(size);
            page.setCurrentPage(currentPage);

            Map<String, Object> map = new HashMap<String, Object>();
            if (StringUtils.isNotBlank(key)) {
                map.put("key", key);
            }
            //符合条件总数
            Integer count = esaleFiledataActivityMapper.countFile(map);
            page.setAllRow(count);
            map.put("start", page.getStart());
            map.put("end", page.getSize());
            //查询分页数据
            List<EsaleFiledataActivity> list = esaleFiledataActivityMapper.getFileListById(map);
            for (EsaleFiledataActivity esaleFiledataActivity: list) {
                esaleFiledataActivity.setFilePath(config.getRootUrl()+esaleFiledataActivity.getFilePath());
            }

            String jsonString = JSON.toJSONString(list);
            jsonObject.put("code", 0);
            jsonObject.put("msg", "");
            jsonObject.put("count", page.getAllRow());
            jsonObject.put("data", jsonString);
        } catch (Exception e) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", e.getMessage());
            jsonObject.put("count", 0);
            jsonObject.put("data", null);
        }
        return jsonObject;
    }

    @Override
    public JsonResult saveFileActivity(CommonsMultipartFile file, String id) {

        String userId = Tools.getUserId();
        if (StringUtils.isBlank(id)) {
            return new JsonResult(0, "参数异常");
        }

        JsonResult result = new JsonResult(0);
        int errorCode = 0;        //错误代码
        String msg = "";        //提示信息
        EsaleFiledataActivity resultFile = null;        //成功的文件对象

        String realFileName = file.getOriginalFilename();        //获取上传文件名称
        String destDir = config.getRootPath();        //获取配置的上传路径
        String suffix = realFileName.substring(realFileName.lastIndexOf(".") + 1).toLowerCase();        //获取文件扩展名，并统一大写
        int typeCode = this.getFileTypeByAttachSuffix(suffix);
        String typeUrl = FileType.getName(typeCode);
        String saveUrl = "";        //保存的相对路径
        String resultPath = "";            //文件保存到服务器后，的相对路径

        if (StringUtil.isBlank(userId)) {
            //判断是否登录
            errorCode = 1001;
            msg = "还未登录";
        } else if (StringUtil.isBlank(id)) {
            //参数判断
            errorCode = 1002;
            msg = "参数异常";
        } else if (StringUtil.isBlank(typeUrl)) {
            //文件类型判断
            errorCode = 1002;
            msg = "文件类型为非法类型";
        } else if (file.getSize() > 1024 * 1024 * config.getFileSize()) {
            //文件大小判断
            errorCode = 1003;
            msg = "文件大小超出限制，限制为" + config.getFileSize() + "M";
        } else {
            saveUrl += "esaleActivity/" + typeUrl + "/" + DateFormartUtil.getDateByFormat(DateFormartUtil.YYYY_MM_DD) + "/";
            String targetFileName = UUID.randomUUID().toString().replace("-", "") + "." + suffix;
            // 保存
            try {
                //现在版本：上传到ftp
                ftpUtil = new FtpUtil(config.getFtpUrl(), Integer.valueOf(config.getFtpPort()), config.getFtpUserName(), config.getFtpPassWord());
                boolean uploadFtpFile = ftpUtil.uploadFtpFile(destDir + saveUrl, targetFileName, file.getInputStream());
                if (uploadFtpFile) {
                    resultPath = saveUrl + targetFileName;

                    //保存附件
                    resultFile = new EsaleFiledataActivity();
                    resultFile.setId(IdUtils.nextId(resultFile));
                    resultFile.setFileType(String.valueOf(typeCode));
                    resultFile.setFileName(targetFileName);
                    resultFile.setFileRealname(realFileName);
                    resultFile.setFileFormat(suffix);
                    resultFile.setFileSize(file.getSize());
                    resultFile.setFilePath(resultPath);
                    resultFile.setActivityId(id);
                    resultFile.setFilePublic("0");
                    resultFile.setFileDownCount(0);
                    resultFile.setCreateBy(userId);
                    resultFile.setCreateDate(new Date());
                    resultFile.setUpdateBy(userId);
                    resultFile.setUpdateDate(new Date());
                    resultFile.setDataState("1");
                    int insert = esaleFiledataActivityMapper.insert(resultFile);
                    if (insert > 0) {
                        errorCode = 1;
                        msg = "文件上传成功";
                    } else {
                        errorCode = 1004;
                        msg = "文件保存失败";
                    }
                } else {
                    errorCode = 1005;
                    msg = "文件上传失败";
                }
            } catch (Exception e) {
                e.printStackTrace();
                errorCode = 1005;
                msg = "系统错误";
            }
        }
        result.setCode(errorCode);
        result.setMsg(msg);
        result.setData(resultFile);
        return result;
    }

    @Override
    public void download(String id, HttpServletResponse response) {

        EsaleFiledataActivity esaleFiledataActivity = esaleFiledataActivityMapper.selectByPrimaryKey(id);

        String downLoadPath = config.getFtpRootPath() + config.getRootPath() + esaleFiledataActivity.getFilePath().replace(config.getRootUrl(),"");  //注意不同系统的分隔符
        //	String downLoadPath =filePath.replaceAll("/", "\\\\\\\\");   //replace replaceAll区别 *****
        System.out.println(downLoadPath);

        try {
            response.setContentType("application/x-msdownload;charset=utf-8");
            response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(esaleFiledataActivity.getFileRealname(), "UTF-8"));
            ftpUtil = new FtpUtil(config.getFtpUrl(), Integer.valueOf(config.getFtpPort()), config.getFtpUserName(), config.getFtpPassWord());
            ftpUtil.downFtpFile(downLoadPath, response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        esaleFiledataActivity.setFileDownCount(esaleFiledataActivity.getFileDownCount() + 1);
        esaleFiledataActivityMapper.updateByPrimaryKeySelective(esaleFiledataActivity);
    }

    @Override
    public JsonResult deleteFileActivity(String id) throws Exception {
        try {
            if (StringUtils.isBlank(id)) {
                return new JsonResult(0, "参数错误");
            }
            String userId = Tools.getUserId();
            if (StringUtils.isBlank(userId)) {
                return new JsonResult(0, "未登录");
            }

            EsaleFiledataActivity esaleFiledataActivity = esaleFiledataActivityMapper.selectByPrimaryKey(id);

            if(esaleFiledataActivity != null && !StringUtils.isBlank(esaleFiledataActivity.getFilePath())) {
                String resultPath = esaleFiledataActivity.getFilePath();
                String path = config.getFtpRootPath() + config.getRootPath() + resultPath;
                try {
                    ftpUtil = new FtpUtil(config.getFtpUrl(), Integer.valueOf(config.getFtpPort()), config.getFtpUserName(), config.getFtpPassWord());
                    boolean delFtpFile = ftpUtil.delFtpFile(path);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            esaleFiledataActivity.setDataState("0");
            esaleFiledataActivity.setUpdateBy(userId);
            esaleFiledataActivity.setUpdateDate(new Date());
            int num = esaleFiledataActivityMapper.updateByPrimaryKeySelective(esaleFiledataActivity);
            if (num == 0) {
                return new JsonResult(0, "删除失败");
            }
            return new JsonResult(1, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0, "系统异常");
        }
    }

    @Override
    public JsonResult renameFileActivity(String id,String fileRealname) throws Exception {
        try {
            if (StringUtils.isBlank(id)) {
                return new JsonResult(0, "参数错误");
            }
            String userId = Tools.getUserId();
            if (StringUtils.isBlank(userId)) {
                return new JsonResult(0, "未登录");
            }

            EsaleFiledataActivity info = new EsaleFiledataActivity();
            info.setId(id);
            info.setFileRealname(fileRealname);
            info.setUpdateBy(userId);
            info.setUpdateDate(new Date());
            int num = esaleFiledataActivityMapper.updateByPrimaryKeySelective(info);
            if (num == 0) {
                return new JsonResult(0, "重命名失败");
            }
            return new JsonResult(1, "重命名成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0, "系统异常");
        }
    }

    /**
     * 根据文件扩展名，返回文件类型
     *
     * @param fileSuffix
     * @return 文件类型 img：图片
     * doc：文档
     * audio：音频
     * video：视频
     * other：其他
     */
    @SuppressWarnings("unused")
    private int getFileTypeByAttachSuffix(String fileSuffix) {
        int fileType = -1;
        if (config.getImageType().indexOf(fileSuffix) > -1) {
            fileType = 1;
        } else if (config.getDocType().indexOf(fileSuffix) > -1) {
            fileType = 2;
        } else if (config.getAudioType().indexOf(fileSuffix) > -1) {
            fileType = 3;
        } else if (config.getVideoType().indexOf(fileSuffix) > -1) {
            fileType = 4;
        } else if (config.getFileType().indexOf(fileSuffix) > -1) {
            fileType = 5;
        } else {
            fileType = -1;
        }
        return fileType;
    }



}
