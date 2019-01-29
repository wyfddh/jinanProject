package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import net.sf.json.JSONObject;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface EsaleFiledataActivityService  {

    JSONObject getFileListById(String key, Integer currentPage, Integer size);

    JsonResult saveFileActivity(CommonsMultipartFile file, String id);

    void download(String id, HttpServletResponse response);

    JsonResult deleteFileActivity(String id) throws  Exception;

    JsonResult renameFileActivity(String id,String fileRealname) throws  Exception;
	
}
