
package com.tj720.utils;

import com.tj720.controller.springbeans.Config;
import com.tj720.model.common.wf.Attachment;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipOutputStream;


/**
 * @Auther: caiming
 * @Date: 2018/11/22 21:14
 * @Description:
 */

public class FileUploadConfig {

    private FtpUtil ftpUtil;
    private Config config;
    private byte uploadType = 1;    //1-ftp    2-oss

    public FileUploadConfig(Config config){
        this.config = config;
    }


/**
     *
     * 功能描述: 上传文件
     *
     * @param: [path, fileName, file]
     * @return: boolean
     * @auther: caiming
     * @date: 2018/11/22 21:40
     */

    public boolean uploadFile(String path, String fileName, InputStream file){
        if(uploadType == 1){
            ftpUtil = new FtpUtil(config.getFtpUrl(), Integer.valueOf(config.getFtpPort()), config.getFtpUserName(), config.getFtpPassWord());
            boolean uploadFtpFile = ftpUtil.uploadFtpFile(path, fileName, file);
            return uploadFtpFile;
        }
        return false;
    }


/**
     *
     * 功能描述: 删除文件
     *
     * @param: [path]
     * @return: boolean
     * @auther: caiming
     * @date: 2018/11/22 21:40
     */

    public boolean deleteFile(String path){
        if(uploadType == 1){
            ftpUtil = new FtpUtil(config.getFtpUrl(), Integer.valueOf(config.getFtpPort()), config.getFtpUserName(), config.getFtpPassWord());
            boolean delFtpFile = ftpUtil.delFtpFile(path);
            return delFtpFile;
        }
        return false;
    }


/**
     *
     * 功能描述: 下载文件
     *
     * @param: [path, out]
     * @return: void
     * @auther: caiming
     * @date: 2018/11/22 21:40
     */

    public void downloadFile(String path, OutputStream out){
        if(uploadType == 1){
            ftpUtil = new FtpUtil(config.getFtpUrl(), Integer.valueOf(config.getFtpPort()), config.getFtpUserName(), config.getFtpPassWord());
            ftpUtil.downFtpFile(path, out);
        }
    }


    /**
     *
     * 功能描述: 下载文件toZip
     *
     * @param: [path, out]
     * @return: void
     * @auther: caiming
     * @date: 2018/11/22 21:40
     */
    public void downloadFileToZip(List<Attachment> files, ZipOutputStream out, HttpServletResponse response){
        if(uploadType == 1){
            ftpUtil = new FtpUtil(config.getFtpUrl(), Integer.valueOf(config.getFtpPort()), config.getFtpUserName(), config.getFtpPassWord());
            ftpUtil.downFtpFiletoZip(files, out, response);
        }
    }

    /**
     *
     * 功能描述: 下载文件toZip
     *
     * @param: [path, out]
     * @return: void
     * @auther: caiming
     * @date: 2018/11/22 21:40
     */
    public void downloadFileToZipPlus(List<HashMap<String,Object>> files, ZipOutputStream out, HttpServletResponse response){
        if(uploadType == 1){
            ftpUtil = new FtpUtil(config.getFtpUrl(), Integer.valueOf(config.getFtpPort()), config.getFtpUserName(), config.getFtpPassWord());
            ftpUtil.downFtpFiletoZipPlus(files, out, response);
        }
    }


}
