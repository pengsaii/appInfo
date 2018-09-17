package cn.appsys.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.appsys.pojo.DevUser;
import cn.appsys.service.devuser.DevUserService;

@Controller
public class LoginController {
	@Resource
	private DevUserService devUserService;
	
	/**
	 * 后台管理和开发平台登录入口跳转
	 *
	 */
	@RequestMapping(value="/dev/login")
	public String toDevLogin() {
		return "devlogin";
	}
	
	@RequestMapping(value="/dev/main")
	public String toDevMain() {
		return "developer/main";
	}
	
	/**
	 * 开发者登录验证
	 */
	@RequestMapping(value="/dev/dologin")
	public String doDevLogin(HttpServletRequest request,
								@RequestParam String devCode,
								@RequestParam String devPassword) {
		DevUser loginUser = devUserService.login(devCode,devPassword);
		if(loginUser == null) {
			request.setAttribute("error", "开发者用户名或密码错误！");
			return "devlogin";
		}
		request.getSession().setAttribute("devLoginUser", loginUser);
		return "redirect:/dev/main";
	}
	
	/**
	 * 发开者用户注销
	 */
	@RequestMapping("/dev/logout")
	public String doDevLogout(HttpSession session) {
		session.invalidate();
		return "redirect:/dev/login";
	}
}
