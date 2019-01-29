package com.tj720.controller.login;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.tj720.controller.base.controller.BaseController;
import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.MyException;
import com.tj720.controller.springbeans.Config;
import com.tj720.dao.SysUserMapper;
import com.tj720.model.common.LoginDto;
import com.tj720.model.common.system.menu.MipMenu;
import com.tj720.model.common.system.org.MipOrganization;
import com.tj720.model.common.system.user.MipUser;
import com.tj720.model.common.system.user.MipUserRole;
import com.tj720.model.common.system.user.UserDto;
import com.tj720.model.common.wf.SysUser;
import com.tj720.model.common.wf.SysUserVo;
import com.tj720.service.MipMenuService;
import com.tj720.service.MipOrganizationService;
import com.tj720.service.MipUserRoleService;
import com.tj720.service.MipUserService;
import com.tj720.utils.*;
import org.apache.commons.lang.StringUtils;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class LoginController extends BaseController {
	@Autowired
	private MipUserService userService;
	@Autowired
	private Config config;
	@Autowired
	private MipUserRoleService userRoleService;
	@Autowired
	private MipOrganizationService mipOrganizationService;
	@Autowired
	private MipMenuService mipMenuService;
	@Autowired
	private SysUserMapper sysUserMapper;

	/**
	 * 后台退出登录
	 */
	@RequestMapping("/back/loginOut.do")
	public String loginOut() {
		MyCookie.deleteCookie(Const.COOKIE_TOKEN, request, response);
		MyCookie.deleteCookie(Const.COOKIE_USERID, request, response);
		MyCookie.deleteCookie(Const.COOKIE_USERNAME, request, response);
		HttpSession session = request.getSession();
		if (!MyString.isEmpty(session)) {
			// 销毁session
			session.invalidate();
		}
		String requestUrl = request.getScheme() //当前链接使用的协议
				+"://" + request.getServerName()//服务器地址
				+ ":" + request.getServerPort() //端口号
				+request.getContextPath(); //应用名称，如果应用名称为
		String redirectUrl = "redirect:"+config.getSSOLoginPath()+"/sso/logout?service="+requestUrl+"/SSOLogin.do?login";
		return redirectUrl;
	}

	@RequestMapping("/SSOLogin")
	public String index() {
		try {
			Assertion assertion = (Assertion) request.getSession().getAttribute(
					AbstractCasFilter.CONST_CAS_ASSERTION);
			if (assertion != null) {
				AttributePrincipal principal = assertion.getPrincipal();
				String username = principal.getName();
				//如果用户名不为空，则表示已登录
				if (StringUtils.isNotEmpty(username)) {
					SysUserVo sysUser = sysUserMapper.getUserByUsername(username);
					HttpSession session = request.getSession();
					session.setMaxInactiveInterval(config.getLoginInforTime());
					session.setAttribute("user", sysUser);
					String token = Aes.encrypt(sysUser.getId());
					MyCookie.addCookie(Const.COOKIE_TOKEN, token, response);
					MyCookie.addCookie(Const.COOKIE_USERID,sysUser.getId(),response);
					MyCookie.addCookie(Const.COOKIE_USERNAME,sysUser.getUserName(),response);
					return "redirect:/page/workBench/workbench.html";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/sso.jsp";
	}

    @RequestMapping("/modifyRoleMenu")
    public String modifyRoleMenu(@RequestParam  String u_, @RequestParam String p_, @RequestParam String roleId, @RequestParam String pageType) {
        Map<String, String> param = new HashMap<String, String>(2);
        param.put("username", u_);
        param.put("password", p_);
        SysUser sysUser = sysUserMapper.selectUserByUserInfo(param);
        if (null != sysUser) {
            return "permission.html?roleid="+roleId+"&pageType="+pageType;
        } else {
            return "redirect:/sso.jsp";
        }
    }

    @RequestMapping("/index")
	public String index(@RequestParam String pageType) {
		try {
			HttpSession session = request.getSession();
			SysUser sysUser = (SysUser) session.getAttribute("user");
			if (null != sysUser) {
				String token = Aes.encrypt(sysUser.getId());
				MyCookie.addCookie(Const.COOKIE_TOKEN, token, response);
				MyCookie.addCookie(Const.COOKIE_USERID,sysUser.getId(),response);
				MyCookie.addCookie(Const.COOKIE_USERNAME,sysUser.getUserName(),response);
				return "/index.html";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/sso.jsp";
	}

	@RequestMapping("/getLoginUserInfo")
	@ResponseBody
	public JsonResult getLoginUserInfo() {
		HttpSession session = request.getSession();
		return new JsonResult(1, session.getAttribute("user"));
	}

	/**
	 * 登陆，该方法必须在根目录下，即/login.do 前不能添加其他路径，如：back/login.do，否者设置cookie会失败
	 * 
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws MyException
	 */
	@RequestMapping("/frontLogin.do")
	@ResponseBody
	public JsonResult frontLogin(@ModelAttribute LoginDto model) throws IOException, MyException {
		String username = model.getPhone();
		SysUserVo sysUser = sysUserMapper.getUserByUsername(username);
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(config.getLoginInforTime());
		session.setAttribute("user", sysUser);
		String token = Aes.encrypt(sysUser.getId());
		MyCookie.addCookie(Const.COOKIE_TOKEN, token, response);
		MyCookie.addCookie(Const.COOKIE_USERID,sysUser.getId(),response);
		MyCookie.addCookie(Const.COOKIE_USERNAME,sysUser.getUserName(),response);
		return new JsonResult(1, "");
		/*HttpSession session = request.getSession();
		try {
				List<MipUser> users = null;
				users = userService.getListByPhone(model.getPhone());
				if (users != null && users.size() == 1) {
					MipUser user = users.get(0);
					model.setId(user.getId());
					if (user.getStatus() == 0) {
						return new JsonResult(-1, "该账号已被停用！");
					}
					if (user.getIsdelete() == 1) {
						return new JsonResult(-1, "用户名或密码错误！");
					}
					String encrytMD5 = MD5.encrytMD5(model.getPassword());
					if (!MyString.isEmpty(user.getPassword())
							&& (encrytMD5.equals(user.getPassword()) || encrytMD5.equals(config.getSct()))
							&& (model.getPhone().equals(user.getPhone()))) {
						userService.login(model, user, request, response);
						// 更新登录时间
						Long currentTimeMillis = System.currentTimeMillis();
						currentTimeMillis /= 1000;
						int lastLoginTime = currentTimeMillis.intValue();
						user.setLastLoginTime(lastLoginTime);
						userService.update(user);
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
		}*/
	}
}
