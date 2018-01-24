package com.lpy.shiro.realms;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.Realm;

public class ShiroRealm extends AuthenticatingRealm {

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("doGetAuthenticationInfo: " + token.hashCode());
		UsernamePasswordToken uptoken = (UsernamePasswordToken) token;
		
		String username = uptoken.getUsername();
		System.out.println("从数据库中获取：" + username + "所对应的用户信息");
		
		if("unkown".equals(username)){
			throw new UnknownAccountException("用户不存在！");
		}
		
		if("monster".equals(username)){
			throw new LockedAccountException("用户被锁定！");
		}
		Object principal = username;
		Object credentials = "123456";
		String realmName = getName();
		
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, credentials, realmName);
		return info;
	}

	

}
