package com.lpy.shiro.handlers;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.lpy.shiro.services.ShiroService;

@RequestMapping("/shiro")
@Controller
public class ShiroHandler {
	
	@Autowired
	private ShiroService shiroService; 
	
	@RequestMapping("/testAnnocation")
	public String testAnnocation(HttpSession session){
		session.setAttribute("key", "value12345");
		shiroService.testMethod();
		return "redirect:/list.jsp";
	}
	
	@RequestMapping("/login")
	public String Login(@RequestParam("username") String username, @RequestParam("password") String password) {
		Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(true);
            try {
                currentUser.login(token);
                //shiroService.doCreateSession(currentUser.getSession());
            } catch (AuthenticationException ae) {
            	System.out.println("登陆失败：" + ae.getMessage());
            }
        }
		return "redirect:/list.jsp";
	}
	
	@ExceptionHandler(org.apache.shiro.authz.AuthorizationException.class)
	public String exceptionHandler(){
		System.out.println("权限不足！");
		return "unauthorized";
	}
}
