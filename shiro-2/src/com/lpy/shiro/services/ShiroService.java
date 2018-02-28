package com.lpy.shiro.services;

import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ShiroService {
	
	@Autowired
	MySessionDao sessionDao;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@RequiresRoles("admin")
	public void testMethod(){
		
		System.out.println("testMethod, time: " + new Date());
		
		Session session = SecurityUtils.getSubject().getSession();
		Object val = session.getAttribute("key");
		
		System.out.println("Service中的sessionVal："+val);
		
		String sql = "select session from sessions where id=?";
		List<String> sessionStrList = jdbcTemplate.queryForList(sql,
				String.class, 1);
		System.out.println("sessionList");
		for (String string : sessionStrList) {
			System.out.println(string);
		}
	}

	public void doCreateSession(Session session) {
		sessionDao.doCreate(session);
	}
}

















