package com.tj720.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tj720.controller.base.dao.BaseDao;
import com.tj720.controller.base.service.BaseServiceImpl;
import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.MyException;
import com.tj720.controller.springbeans.Config;
import com.tj720.dao.AttachmentMapper;
import com.tj720.dao.MipAttachmentMapper;
import com.tj720.model.common.FileType;
import com.tj720.model.common.MipAttachment;
import com.tj720.model.common.MipAttachmentExample;
import com.tj720.model.common.MipAttachmentExample.Criteria;
import com.tj720.model.common.wf.Attachment;
import com.tj720.service.MipAttachmentService;
import com.tj720.utils.*;
import com.tj720.utils.common.FileUtil;
import com.tj720.utils.common.IdUtils;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;



/**
*
* @author:cm
* @date:2018年7月2日15:34:24
*/
@Service("MipAttachmentService")
public class MipAttachmentServiceImpl extends BaseServiceImpl<MipAttachment> implements MipAttachmentService {

	@Autowired
	private MipAttachmentMapper mipAttachmentMapmper;
	@Autowired
	private AttachmentMapper attachmentMapmper;
	@Autowired
	private Config config;
	private FtpUtil ftpUtil;

	private FileUploadConfig fileUploadConfig;


	
	@Override  
    public BaseDao<MipAttachment> getBaseDao() {
        return mipAttachmentMapmper;  
    }

	@Override
	public JsonResult saveAttachment(CommonsMultipartFile file, String tableName, String tableid, String source) {
		JsonResult result = new JsonResult(0);
		int errorCode = 0;		//错误代码
		String msg = "";		//提示信息
		MipAttachment resultFile = null;		//成功的文件对象
		
		String realFileName = file.getOriginalFilename();		//获取上传文件名称
		String destDir = config.getRootPath();		//获取配置的上传路径
		String suffix = realFileName.substring(realFileName.lastIndexOf(".") + 1).toLowerCase();		//获取文件扩展名，并统一大写
		int typeCode = this.getFileTypeByAttachSuffix(suffix);
		String typeUrl = FileType.getName(typeCode);
		String saveUrl = "";		//保存的相对路径
		String resultPath = "";			//文件保存到服务器后，的相对路径
		
		if(StringUtil.isBlank(Tools.getUserId())) {
			//判断是否登录
			errorCode = 1001;
			msg = "还未登录";
		}else if(StringUtil.isBlank(typeUrl)) {
			//文件类型判断
			errorCode = 1002;
			msg = "文件类型为非法类型";
		}else if(file.getSize() > 1024 * 1024 * config.getFileSize()) {
			//文件大小判断
			errorCode = 1003;
			msg = "文件大小超出限制，限制为"+config.getFileSize()+"M";
		}else {
			saveUrl += typeUrl + "/" + DateFormartUtil.getDateByFormat(DateFormartUtil.YYYY_MM_DD) + "/";
			String uuidStr = UUID.randomUUID().toString().replace("-", "");
			String targetFileName = uuidStr + "." + suffix;
			// 保存
			try {
				/*if (!new File(destDir + saveUrl).exists()) {
					new File(destDir + saveUrl).mkdirs();
				}
				File targetFile = new File(destDir + saveUrl, targetFileName);*/
				//原来版本：上传到本地
//				file.transferTo(targetFile);
				//现在版本：上传到ftp
				ftpUtil = new FtpUtil(config.getFtpUrl(), Integer.valueOf(config.getFtpPort()), config.getFtpUserName(), config.getFtpPassWord());
				boolean uploadFtpFile = ftpUtil.uploadFtpFile(destDir + saveUrl, targetFileName, file.getInputStream());
				if(uploadFtpFile) {
					resultPath = saveUrl + targetFileName;
					
					//保存附件
					resultFile = new MipAttachment();
					resultFile.setAttId(IdUtils.nextId(resultFile));
					resultFile.setAttName(realFileName);
					resultFile.setAttSize(file.getSize());
					resultFile.setAttPath(resultPath);
					resultFile.setAttIsjunk("0");		//0：正常
					resultFile.setAttDate(new Date());
					resultFile.setAttType(tableName);		//业务表名称
					resultFile.setAttrUser(Tools.getUserId());
					resultFile.setAttFkId(tableid);		//业务表主键
					resultFile.setAttFileType(typeCode);
					resultFile.setAttSource(source);
					int insert = mipAttachmentMapmper.insert(resultFile);
					if(insert > 0) {
						errorCode = 1;
						msg = "文件上传成功";
					}else {
						errorCode = 1004;
						msg = "文件保存失败";
					}
				}else {
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
	public JsonResult uploadForPost(CommonsMultipartFile file, String tableName, String tableid, String source) {
		JsonResult result = new JsonResult(0);
		int errorCode = 0;		//错误代码
		String msg = "";		//提示信息
		Attachment resultFile = null;		//成功的文件对象

		String realFileName = file.getOriginalFilename();		//获取上传文件名称
		String destDir = config.getRootPath();		//获取配置的上传路径
		String suffix = realFileName.substring(realFileName.lastIndexOf(".") + 1).toLowerCase();		//获取文件扩展名，并统一大写
		int typeCode = this.getFileTypeByAttachSuffix(suffix);
		String typeUrl = FileType.getName(typeCode);
		String saveUrl = "";		//保存的相对路径
		String resultPath = "";			//文件保存到服务器后，的相对路径
		Integer size = 1024*1024*config.getFileSize();
		if(StringUtil.isBlank("1")) {
//			userId
			//判断是否登录
			errorCode = 1001;
			msg = "还未登录";
		}else if(StringUtil.isBlank(typeUrl)) {
			//文件类型判断
			errorCode = 1002;
			msg = "文件类型为非法类型";
		}else if(file.getSize() > size) {
			//文件大小判断
			errorCode = 1003;
			msg = "文件大小超出限制，限制为"+config.getFileSize()+"M";
		}else {
			saveUrl +=tableName + "/" + typeUrl + "/" + DateFormartUtil.getDateByFormat(DateFormartUtil.YYYY_MM_DD) + "/";
			String uuidStr = UUID.randomUUID().toString().replace("-", "");
			String targetFileName = uuidStr + "." + suffix;
			// 保存
			try {
				fileUploadConfig = new FileUploadConfig(config);
				boolean uploadFtpFile = fileUploadConfig.uploadFile(destDir + saveUrl, targetFileName, file.getInputStream());
				if(uploadFtpFile) {
					resultPath = saveUrl + targetFileName;

					//保存附件
					resultFile = new Attachment();
					resultFile.setAttId(IdUtils.nextId(resultFile));
					resultFile.setAttName(realFileName);
					resultFile.setAttSize(file.getSize());
					resultFile.setAttPath(resultPath);
					resultFile.setAttIsjunk("0");		//0：正常
					resultFile.setAttDate(new Date());
					resultFile.setAttType(tableName);		//业务表名称
					resultFile.setAttrUser("sysadmin");
					if (StringUtils.isNotBlank(tableid)) {
						resultFile.setAttFkId(tableid);		//业务表主键
					}
					resultFile.setAttFileType(typeCode);
					resultFile.setAttSource(source);
					int insert = attachmentMapmper.insert(resultFile);
					if(insert > 0) {
						errorCode = 1;
						msg = "文件上传成功";
						Map<String,Object> map = new HashMap<String, Object>();
						map.put("id", resultFile.getAttId());
						map.put("resultPath", resultPath);
						map.put("realFileName", realFileName);
						map.put("isjunk", 0);
						map.put("size", file.getSize());
						map.put("typeCode", typeCode);
						map.put("isnew", "1");
						map.put("absolutePath", config.getRootUrl() + resultPath);
						result.setData(map);
					}else {
						errorCode = 1004;
						msg = "文件保存失败";
					}
				}else {
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
		return result;
	}
	/**
	 * 未完待续...
	 * @param files
	 * @param tableName
	 * @param tableid
	 * @param source
	 * @return
	 */
	@Override
	public Map<String, Object> saveAttachment(List<CommonsMultipartFile> files, String tableName, String tableid, String source) {
		Map<String, Object> result = new HashMap<>();
		String errorCode = "";		//错误代码
		String msg = "";		//提示信息
		List<MipAttachment> resultFiles = null;		//成功的文件对象集合
		
		String destDir = config.getRootPath();		//获取配置的上传路径
		if(StringUtil.isBlank(Tools.getUserId())) {
			//判断是否登录
			errorCode = "1001";
			msg = "还未登录";
		}else {
			for (CommonsMultipartFile file : files) {
				MipAttachment resultFile = null;
				
				String realFileName = file.getOriginalFilename();		//获取上传文件名称
				String suffix = realFileName.substring(realFileName.lastIndexOf(".") + 1).toLowerCase();		//获取文件扩展名，并统一大写
				int typeCode = this.getFileTypeByAttachSuffix(suffix);
				String typeUrl = FileType.getName(typeCode);
				String saveUrl = "";		//保存的相对路径
				String resultPath = "";			//文件保存到服务器后，的相对路径
				
				if(StringUtil.isBlank(typeUrl) || "-1".equals(typeCode)) {
					//文件类型判断
					errorCode = "1002";
					msg = "文件类型为非法类型";
				}else if(file.getSize() > 1024 * 1024 * config.getFileSize()) {
					//文件大小判断
					errorCode = "1003";
					msg = "文件大小超出限制，限制为"+config.getFileSize()+"M";
				}else {
					saveUrl += typeUrl + "/" + DateFormartUtil.getDateByFormat(DateFormartUtil.YYYY_MM_DD) + "/";
					String uuidStr = UUID.randomUUID().toString().replace("-", "");
					String targetFileName = uuidStr + "." + suffix;
					// 保存
					try {
						if (!new File(destDir + saveUrl).exists()) {
							new File(destDir + saveUrl).mkdirs();
						}
						File targetFile = new File(destDir + saveUrl, targetFileName);
						file.transferTo(targetFile);
						resultPath = saveUrl + targetFileName;
						
						//保存附件
						resultFile = new MipAttachment();
						resultFile.setAttId(IdUtils.nextId(resultFile));
						resultFile.setAttName(realFileName);
						resultFile.setAttSize(file.getSize());
						resultFile.setAttPath(resultPath);
						resultFile.setAttIsjunk("0");		//0：正常
						resultFile.setAttDate(new Date());
						resultFile.setAttType(tableName);		//业务表名称
						resultFile.setAttrUser(Tools.getUserId());
						resultFile.setAttFkId(tableid);		//业务表主键
						resultFile.setAttFileType(typeCode);
						resultFile.setAttSource(source);
						int insert = mipAttachmentMapmper.insert(resultFile);
						if(insert > 0) {
							errorCode = "1";
							msg = "文件上传成功";
						}else {
							errorCode = "1004";
							msg = "文件保存失败";
						}
					} catch (Exception e) {
						e.printStackTrace();
						errorCode = "1005";
						msg = "系统错误";
					}
				}
			}
		}
		
		result.put("errorCode", errorCode);
		result.put("msg", msg);
		result.put("resultFile", resultFiles);
		return result;
	}
	
	/**
	 * 根据文件扩展名，返回文件类型
	 * @param fileSuffix
	 * @return	文件类型
	 * 			img：图片
	 * 			doc：文档
	 * 			audio：音频
	 * 			video：视频
	 * 			other：其他
	 */
	@SuppressWarnings("unused")
	private int getFileTypeByAttachSuffix(String fileSuffix) {
		int fileType = -1;
		if(config.getImageType().indexOf(fileSuffix)>-1) {
			fileType = 1;
		}else if(config.getDocType().indexOf(fileSuffix)>-1) {
			fileType = 2;
		}else if(config.getAudioType().indexOf(fileSuffix)>-1) {
			fileType = 3;
		}else if(config.getVideoType().indexOf(fileSuffix)>-1) {
			fileType = 4;
		}else if(config.getFileType().indexOf(fileSuffix)>-1) {
			fileType = 5;
		}else {
			fileType = -1;
		}
		return fileType;
	}

	@Override
	/**
	 * projectName : js里面配置的模块名
	 */
	public JsonResult uploadEditPic(CommonsMultipartFile file, String projectName) {
		JsonResult result = new JsonResult(0);
		int errorCode = 0;		//错误代码
		String msg = "";		//提示信息
		
		String realFileName = file.getOriginalFilename();		//获取上传文件名称
		String destDir = config.getRootPath();		//获取配置的上传路径
		String suffix = realFileName.substring(realFileName.lastIndexOf(".") + 1).toLowerCase();		//获取文件扩展名，并统一大写
		int typeCode = this.getFileTypeByAttachSuffix(suffix);
		String typeUrl = FileType.getName(typeCode);
		String saveUrl = "";		//保存的相对路径
		String resultPath = "";			//文件保存到服务器后，的相对路径
		
		if(StringUtil.isBlank(Tools.getUserId())) {
			//判断是否登录
			errorCode = 1001;
			msg = "还未登录";
		}else if(StringUtil.isBlank(typeUrl) || typeCode == -1) {
			//文件类型判断
			errorCode = 1002;
			msg = "文件类型为非法类型";
		}else if(file.getSize() > 1024 * 1024 * config.getFileSize()) {
			//文件大小判断
			errorCode = 1003;
			msg = "文件大小超出限制，限制为"+config.getFileSize()+"M";
		}else {
			saveUrl += projectName + "/" + typeUrl + "/" + DateFormartUtil.getDateByFormat(DateFormartUtil.YYYY_MM_DD) + "/";
			String uuidStr = UUID.randomUUID().toString().replace("-", "");
			String targetFileName = uuidStr + "." + suffix;
			// 保存
			try {
				//现在版本：上传到ftp
				ftpUtil = new FtpUtil(config.getFtpUrl(), Integer.valueOf(config.getFtpPort()), config.getFtpUserName(), config.getFtpPassWord());
				boolean uploadFtpFile = ftpUtil.uploadFtpFile(destDir + saveUrl, targetFileName, file.getInputStream());

				if(uploadFtpFile) {
					resultPath = saveUrl + targetFileName;
					//保存附件
					MipAttachment resultFile = new MipAttachment();
					resultFile.setAttId(IdUtils.nextId(resultFile));
					resultFile.setAttName(realFileName);
					resultFile.setAttSize(file.getSize());
					resultFile.setAttPath(resultPath);
					resultFile.setAttIsjunk("0");		//0：正常
					resultFile.setAttDate(new Date());
					resultFile.setAttType(projectName);		//业务表名称
					resultFile.setAttrUser(Tools.getUserId());
					resultFile.setAttFileType(typeCode);
					resultFile.setIsMain("0");
					int insert = mipAttachmentMapmper.insert(resultFile);
					if(insert > 0) {
						errorCode = 0;
						msg = "文件上传成功";
						Map<String,Object> map = new HashMap<String, Object>();
						map.put("src", config.getRootUrl() + resultPath);
						result.setData(map);
					}else {
						errorCode = 1004;
						msg = "文件保存失败";
					}
				}else {
					errorCode = 1;
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
		return result;
	}
	
	/**
	 * projectName : js里面配置的模块名
	 */
	public JsonResult uploadImg(CommonsMultipartFile file, String projectName, HttpServletRequest request) {
		JsonResult result = new JsonResult(0);
		int errorCode = 0;		//错误代码
		String msg = "";		//提示信息
		
		String realFileName = file.getOriginalFilename();		//获取上传文件名称
		String destDir = config.getRootPath();		//获取配置的上传路径
		String suffix = realFileName.substring(realFileName.lastIndexOf(".") + 1).toLowerCase();		//获取文件扩展名，并统一大写
		int typeCode = this.getFileTypeByAttachSuffix(suffix);
		String typeUrl = FileType.getName(typeCode);
		String saveUrl = "";		//保存的相对路径
		String resultPath = "";			//文件保存到服务器后，的相对路径

		if(StringUtil.isBlank(Tools.getUserId())) {
			//判断是否登录
			errorCode = 1001;
			msg = "还未登录";
		}else if(StringUtil.isBlank(typeUrl) || typeCode == -1) {
			//文件类型判断
			errorCode = 1002;
			msg = "文件类型为非法类型";
		}else if(file.getSize() > 1024 * 1024 * config.getFileSize()) {
			//文件大小判断
			errorCode = 1003;
			msg = "文件大小超出限制，限制为"+config.getFileSize()+"M";
		}else {
			int x = 0;
			int y = 0;
			int width = 0;
			int height = 0;

			// 获取缩放或截图后图片的参数
			String tailor = request.getParameter("tailor");
			// 判断是否裁剪
			if (!"false".equals(tailor)) {
				// 裁剪
				net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(tailor);
				Map map = jsonObject;// new HashMap();
				String xString = map.get("x").toString();
				if (xString.contains(".")) {
					Double doubleX = (Double) map.get("x");
					x = Integer.valueOf(doubleX.intValue());
				}else {
					x = (Integer) map.get("x");
				}
				
				String yString = map.get("y").toString();
				if (yString.contains(".")) {
					Double doubleY = (Double) map.get("y");
					y = Integer.valueOf(doubleY.intValue());
				}else {
					y = (Integer) map.get("y");
				}

				String widthStr = map.get("width").toString();
				if (widthStr.contains(".")) {
					Double doubleWidth = (Double) map.get("width");
					width = Integer.valueOf(doubleWidth.intValue());
				}else {
					width = (Integer) map.get("width");
				}
//				
				String heiStr = map.get("height").toString();
				if (heiStr.contains(".")) {
					Double doubleHeight = (Double) map.get("height");
					height = Integer.valueOf(doubleHeight.intValue());
				}else{
					height = (Integer) map.get("height");
				}
			}
			
			saveUrl += projectName + "/" + typeUrl + "/" + DateFormartUtil.getDateByFormat(DateFormartUtil.YYYY_MM_DD) + "/";
			String uuidStr = UUID.randomUUID().toString().replace("-", "");
			String targetFileName = uuidStr + "." + suffix;
			
			Rectangle rect = new Rectangle();
			rect.setBounds(x, y, width, height);
			
			// 保存
			try {
				//上传到临时文件夹中
				String tempPath =System.getProperty("java.io.tmpdir")+File.separator;
				if (!new File(tempPath + destDir + saveUrl).exists()) {
					new File(tempPath + destDir + saveUrl).mkdirs();
				}
//				File targetFile = new File(destDir + saveUrl, targetFileName);
				File srcFile = commonMultipartToFile(file);
				resultPath = saveUrl + targetFileName;
				File targetFile = new File(tempPath + destDir + saveUrl, targetFileName);
				Boolean cutAndscaleImage = ImageHepler.cutAndscaleImage(srcFile, targetFile, width, height, rect);//剪切、缩放图片及保存图片
				if (!cutAndscaleImage) {
					errorCode = 0;
					msg = "图片模式错误，请确保图片模式为RGB颜色";
				}else {
					//现在版本：上传到ftp
					ftpUtil = new FtpUtil(config.getFtpUrl(), Integer.valueOf(config.getFtpPort()), config.getFtpUserName(), config.getFtpPassWord());
					InputStream targetFileIs = new FileInputStream(targetFile);
					boolean uploadFtpFile = ftpUtil.uploadFtpFile(destDir + saveUrl, targetFileName, targetFileIs);
					if(uploadFtpFile) {
						//保存附件
						MipAttachment resultFile = new MipAttachment();
						resultFile.setAttId(IdUtils.nextId(resultFile));
						resultFile.setAttName(realFileName);
						resultFile.setAttSize(file.getSize());
						resultFile.setAttPath(resultPath);
						resultFile.setAttIsjunk("0");		//0：正常
						resultFile.setAttDate(new Date());
						resultFile.setAttType(projectName);		//业务表名称
						resultFile.setAttrUser(Tools.getUserId());
						resultFile.setAttFileType(typeCode);
						resultFile.setIsMain("0");
						int insert = mipAttachmentMapmper.insert(resultFile);
						if(insert > 0) {
							errorCode = 1;
							msg = "文件上传成功";
							Map<String,Object> map = new HashMap<String, Object>();
							map.put("id", resultFile.getAttId());
							map.put("resultPath", resultPath);
							map.put("realFileName", realFileName);
							map.put("isjunk", 0);
							map.put("size", file.getSize());
							map.put("typeCode", typeCode);
							map.put("isnew", "1");
							map.put("absolutePath", config.getRootUrl() + resultPath);
							map.put("width", width);
							map.put("height", height);
							result.setData(map);
						}else {
							errorCode = 1004;
							msg = "文件保存失败";
						}
					}else {
						errorCode = 0;
						msg = "文件上传失败";
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				errorCode = 1005;
				msg = "系统错误";
			}
		}
		
		result.setCode(errorCode);
		result.setMsg(msg);
		
		return result;
	}

	public MipAttachment setMain(MipAttachment mipAttachment) {


		return mipAttachment;
	}

	@Override
	public JsonResult uploadVideo(CommonsMultipartFile file, String projectName, HttpServletRequest request) throws MyException,IOException {

		JsonResult jsonResult = new JsonResult();
		String realFileName = file.getOriginalFilename();
		String destDir = config.getRootPath();
		String saveUrl = "";
		String suffix = realFileName.substring(realFileName.lastIndexOf(".") + 1).toUpperCase();
		try {
			//文件大小拦截
			if (file.getSize() > (10 * 1024 * 1024 * config.getFileSize())) {
				throw new MyException("[ERROR]文件超过最大限制，请上传小于" + (200 * config.getFileSize()) + "M的文件");
			}
			// 检查扩展名
			if (config.getVideoType().indexOf(suffix) < 0 ) {
				throw new MyException("[ERROR]上传文件格式不对");
			}
			saveUrl = projectName+"/" + DateFormartUtil.getDateByFormat(DateFormartUtil.YYYY_MM_DD) + "/";
			String targetFileName = UUID.randomUUID().toString().replace("-", "") + "." + suffix;

			ftpUtil = new FtpUtil(config.getFtpUrl(), Integer.valueOf(config.getFtpPort()), config.getFtpUserName(), config.getFtpPassWord());
			boolean uploadFtpFile = ftpUtil.uploadFtpFile(destDir + saveUrl, targetFileName, file.getInputStream());

			if(!uploadFtpFile){
				throw new MyException("上传失败");
			}

			String resultPath = saveUrl + targetFileName;

			MipAttachment resultFile = new MipAttachment();
			resultFile.setAttId(IdUtils.nextId(resultFile));
			resultFile.setAttName(realFileName);
			resultFile.setAttSize(file.getSize());
			resultFile.setAttPath(resultPath);
			resultFile.setAttIsjunk("0");		//0：正常
			resultFile.setAttDate(new Date());
			resultFile.setAttType(projectName);		//业务表名称
			resultFile.setAttrUser(Tools.getUserId());
			resultFile.setAttFileType(1);
			resultFile.setIsMain("0");
			int insert = mipAttachmentMapmper.insert(resultFile);
			if(insert > 0) {
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("id", resultFile.getAttId());
				map.put("resultPath", resultPath);
				map.put("realFileName", realFileName);
				map.put("isjunk", 0);
				map.put("size", file.getSize());
				map.put("isnew", "1");
				map.put("absolutePath", config.getRootUrl() + resultPath);
				jsonResult.setSuccess(1);
				jsonResult.setData(map);
			}else {
				throw new MyException("保存文件表失败");
			}
		}catch (MyException e){
			e.printStackTrace();
			jsonResult.setSuccess(0);
			jsonResult.setData(e.getMessage());
		}
		return jsonResult;
	}
	@Override
	public void save(MipAttachment mipAttachment) {
		
		mipAttachmentMapmper.insert(mipAttachment);
	}

	@Override
	public int update(MipAttachment mipAttachment) {
		
		mipAttachmentMapmper.updateByPrimaryKeySelective(mipAttachment);
		return 0;
	}

	@Override
	public void batchSave(List<MipAttachment> attachment) {
		for (MipAttachment mipAttachment : attachment) {
			mipAttachmentMapmper.insert(mipAttachment);
		}
	}

	@Override
	public List<MipAttachment> getListByFkid(String attType, String attfkid) {
		MipAttachmentExample example = new MipAttachmentExample();
		Criteria criteria = example.createCriteria();
		criteria.andAttIsjunkEqualTo("0");
		criteria.andAttTypeEqualTo(attType);
		criteria.andAttFkIdEqualTo(attfkid);
		example.setOrderByClause("att_date");
		return mipAttachmentMapmper.selectByExample(example);
	}

	@Override
	public int deleteFile(String attchid) {
		MipAttachment selectByPrimaryKey = mipAttachmentMapmper.selectByPrimaryKey(attchid);
		if(selectByPrimaryKey != null && !StringUtils.isBlank(selectByPrimaryKey.getAttPath())) {
			String resultPath = selectByPrimaryKey.getAttPath();
			String path = config.getRootPath() + resultPath;
			try {
				FileUtil.delFile(path);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return mipAttachmentMapmper.deleteByPrimaryKey(attchid);
	}

	@Override
	public List<MipAttachment> getListByIds(String picids) {
		if(StringUtils.isBlank(picids)){
			return new ArrayList<MipAttachment>();
		}
		String[] ids = picids.split(",");
		List<String> list = Arrays.asList(ids);
		return mipAttachmentMapmper.getListByIds(list);
	}

	@Override
	public void batchUpdate(List<MipAttachment> picList) {
		mipAttachmentMapmper.batchUpdate(picList);
	}

	@Override
	public void batchDelete(String delpicids) {
		String[] ids = delpicids.split(",");
		List<String> list = Arrays.asList(ids);
		mipAttachmentMapmper.batchDelete(list);
	}

	/**
	 * @Author wyf
	 * @Description  转换成File
	 * @Date  2018/9/26 10:50
	 * @Param 
	 * @return java.io.File
	 **/
	private File commonMultipartToFile(CommonsMultipartFile cf) throws IOException {
		File file = null;
		try {
			file=File.createTempFile("tmp", null);
		    cf.transferTo(file);
		    file.deleteOnExit();        
		} catch (IOException e) {
		    e.printStackTrace();
		}
		return file;
	}
	
	public static void main(String[] args) {
		String tempPath =System.getProperty("java.io.tmpdir")+File.separator;
		System.out.println(tempPath);
	}
}
