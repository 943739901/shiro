package com.lpy.shiro.factory;

import java.util.LinkedHashMap;

public class filterChainDefinitionMapBuilder {
	public LinkedHashMap<String, String> buildfilterChainDefinitionMap(){
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		//map一定要按照顺序
		map.put("/login.jsp", "anon");
		map.put("/shiro/login", "anon");
		map.put("/shiro/logout", "logout");
		map.put("/user.jsp", "authc,roles[user]");
		map.put("/admin.jsp", "authc,roles[admin]");
		map.put("/list.jsp", "user");//用户已登陆或记住我
		
		map.put("/**", "authc");
		return map;
	}
}
