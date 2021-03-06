package com.tj720.controller;

import com.tj720.common.duanxin.HttpsRequest;
import com.tj720.controller.base.controller.BaseController;
import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.MyException;
import com.tj720.controller.springbeans.Config;
import com.tj720.dto.PCEsalePubUserDto;
import com.tj720.model.EsaleUserStatistics;
import com.tj720.service.EsaleUserStatisticsService;
import com.tj720.service.MipAttachmentService;
import com.tj720.service.PCEsalePubUserService;
import com.tj720.utils.*;
import com.tj720.utils.common.IdUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("pc/login")
public class PCLoginController extends BaseController {

	@Autowired
	private Config config;

	@Autowired
	private PCEsalePubUserService esalePubUserService;

	@Autowired
	private MipAttachmentService mipAttachmentService;

	@Autowired
	private EsaleUserStatisticsService esaleUserStatisticsService;

	private FtpUtil ftpUtil;


	/**
	 * 后台退出登录
	 */
	@RequestMapping("/loginOut.do")
	public String loginOut(HttpServletRequest request, HttpServletResponse response) {
		String uid = MyCookie.getCookie(Const.COOKIE_USERID, false, request);
		MyCookie.deleteCookie(Const.COOKIE_TOKEN, request, response);
		MyCookie.deleteCookie("sessionAdminName", request, response);
		HttpSession session = request.getSession();
		if (!MyString.isEmpty(session)) {
			// 销毁session
			session.invalidate();
		}
		return "redirect:/login.html";
	}

	/**
	 * 登陆，该方法必须在根目录下，即/login.do 前不能添加其他路径，如：back/login.do，否者设置cookie会失败
	 * 
	 * @param
	 * @return
	 * @throws IOException
	 * @throws MyException
	 */
	@RequestMapping("/frontLogin")
	@ResponseBody
	public JsonResult frontLogin(String phone,String password,String key,HttpServletRequest request,HttpServletResponse response) throws IOException, MyException {
		HttpSession session = request.getSession();
		PCEsalePubUserDto model = new PCEsalePubUserDto();
		model.setPhone(phone);
		model.setPassword(password);
		try {
				/*List<PCEsalePubUserDto> users = null;
			    users = esalePubUserService.getListByPhone(phone);*/
			PCEsalePubUserDto user = esalePubUserService.getListByUserPhone(phone);
			if (user != null) {
					/*PCEsalePubUserDto user = users.get(0);*/
					model.setId(user.getId());
					if (user.getDataState().equals("2")) {
						return new JsonResult(-1, "该账号已被停用！");
					}
					if (user.getDataState().equals("0")) {
						return new JsonResult(-1, "用户名或密码错误！");
					}
					if(!MyString.isEmpty(user.getPassword()) && password.equals(user.getPassword())) {
						System.out.println("====登录=======");
						esalePubUserService.login(model,user,request,response);

						//修改登录时间
						user.setOperationDate(new Date());
						esalePubUserService.updateUser(user);
						session.setMaxInactiveInterval(config.getLoginInforTime());
						session.setAttribute("user", user);
						return new JsonResult(1, model);
					}
					model.setTipMessage("用户名或密码错误");
					session.setMaxInactiveInterval(7200);
					return new JsonResult(0, model);
				} else {
					model.setTipMessage("用户名或密码错误!!");
					session.setMaxInactiveInterval(7200);
					return new JsonResult(0, model);
				}
		} catch (Exception e) {
			e.printStackTrace();
			model.setTipMessage("未知异常，请联系管理员");
			return new JsonResult(0, model);
		}
	}

	/**
	 * 账户设置 - 资料设置
	 */
	@RequestMapping("updateUserInfo")
	@ResponseBody
	public JsonResult updateUserInfo(PCEsalePubUserDto model) throws IOException, MyException {

		return esalePubUserService.updateUserInfo(model);
	}

	/**
	 * 账户设置 - 安全设置 - 修改安全手机
	 */
	@RequestMapping("updateUserPhone")
	@ResponseBody
	public JsonResult updateUserPhone(PCEsalePubUserDto model) throws IOException, MyException {

		return esalePubUserService.updateUserPhone(model);
	}
	/**
	 * 用户注册
	 */
	@RequestMapping("userRegiste")
	@ResponseBody
	public JsonResult userRegiste(PCEsalePubUserDto model) throws IOException, MyException {

		PCEsalePubUserDto user = esalePubUserService.getListByUserPhone(model.getPhone());
		if(user != null){
			if(StringUtils.isNotEmpty(user.getPassword())){
				return new JsonResult(0,"该手机号已经注册！");
			}else{
				try {
					user.setUserName(model.getUserName());
					String encrytMD5 = MD5.encrytMD5(model.getPassword());
					user.setPassword(encrytMD5);
					user.setUpdateDate(new Date());
					esalePubUserService.updateUser(user);
					return new JsonResult(1,"注册成功");
				}catch (Exception e){
					return new JsonResult(0,"系统异常");
				}
			}
		}else{
			JsonResult result = esalePubUserService.userRegiste(model);
			// 存入浏览记录
			EsaleUserStatistics esaleUserStatistics = new EsaleUserStatistics();
			esaleUserStatistics.setIpAddress(NetworkUtil.getIpAddress(request));
			esaleUserStatistics.setType(2);
			esaleUserStatistics.setId(IdUtils.nextId(esaleUserStatistics));
			esaleUserStatisticsService.insertUserStatistics(esaleUserStatistics);
			return result;
		}
	}


	/**
	 * 忘记密码
	 */
	@RequestMapping("forgetPassword")
	@ResponseBody
	public JsonResult forgetPassword(PCEsalePubUserDto model) throws IOException, MyException {

		return esalePubUserService.forgetPassword(model);
	}
	/**
	 * 查看用户详情
	 */
	@RequestMapping("queryUserById")
	@ResponseBody
	public JsonResult queryUserById(String uid) throws IOException, MyException {


		return esalePubUserService.queryUserById(uid);
	}

// 用户注册
	@RequestMapping("/sendSecretCode")
	@ResponseBody
	public JsonResult sendSecretCode(String phone,HttpServletRequest request){

		try {
			//发送短信
			String regCode = config.getRegCode();
			String regPasswod = config.getRegPasswod();
			HttpsRequest httpRequest = new HttpsRequest();
			String sendSms = httpRequest.sendSms("POST", phone, regCode, regPasswod, request);
			HttpSession session = request.getSession();
			System.out.println("sendSms   ======= "+sendSms);
			session.setAttribute("mobile_"+phone, ""+sendSms);
			return new JsonResult(1, sendSms);

		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult("2");
		}
	}


	/**
	 * 修改密码
	 *
	 */
	@RequestMapping("updatePassword")
	@ResponseBody
	public JsonResult updatePassword(String id,String password) throws IOException, MyException {
		PCEsalePubUserDto pcEsalePubUserDto = esalePubUserService.queryUserById02(id);
		String encrytMD5 = MD5.encrytMD5(password);
		if(pcEsalePubUserDto.getPassword().equals(encrytMD5)){
			JsonResult j = new JsonResult();
			j.setMsg("系统异常");
			j.setCode(2);
			//手机号存在或系统异常
			return j;
		}
		pcEsalePubUserDto.setPassword(encrytMD5);
		esalePubUserService.updateUser(pcEsalePubUserDto);

		return new JsonResult(1);
	}

	@RequestMapping(value = "/upload")
	@ResponseBody
	public JsonResult upload(@RequestParam(value = "file", required = false) CommonsMultipartFile file,
							 String tableName, String tableId,String resultPath, String source, HttpServletRequest request) {
		JsonResult saveAttachment = esalePubUserService.saveAttachment(file, tableName, tableId, source);
/*		PCEsalePubUserDto userDto = esalePubUserService.queryUserById02(tableId);
		userDto.setAvatarUrl(resultPath);
		esalePubUserService.updateUser(userDto);*/
		return saveAttachment;
	}


	/**
	 * 浏览足迹
	 */
	@RequestMapping(value = "/footprintBrowsing")
	@ResponseBody
	public JsonResult footprintBrowsing(String uid, @RequestParam(defaultValue = "12") Integer size,
										@RequestParam(defaultValue = "1") Integer currentPage) {
		return esalePubUserService.queryFootprintBrowsing(uid,size,currentPage);
	}

	/**
	 * 清空浏览足迹
	 */
	@RequestMapping(value = "/delFootprintBrowsing")
	@ResponseBody
	public JsonResult delFootprintBrowsing(String uid) {
		return esalePubUserService.delFootprintBrowsing(uid);
	}





}
