package com.lpy.shiro.realms;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class ShiroRealm extends AuthorizingRealm {

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("[FirstRealm] doGetAuthenticationInfo");
		
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
		Object credentials = null; //"fc1709d0a95a6be30bc5926fdb7f22f4";
		if("admin".equals(username)){
			credentials = "038bdaf98f2037b31f1e75b5b4c9b26e";
		}else if("user".equals(username)){
			credentials = "098d2c478e9c11555ce2823231e02ec1";
		}
		String realmName = getName();
		ByteSource credentialsSalt = ByteSource.Util.bytes(username);
		
		SimpleAuthenticationInfo info = null;  //new SimpleAuthenticationInfo(principal, credentials, realmName);
		info = new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);
		return info;
	}
	
	public static void main(String[] args) {
		String algorithmName = "MD5";
		Object credentials = "123456";
		Object salt = ByteSource.Util.bytes("user");
		int hashIterations = 1024;
		
		Object result = new SimpleHash(algorithmName, credentials, salt, hashIterations);
		System.out.println(result);
	}

	//授权的方法
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Object principal = principals.getPrimaryPrincipal();
		Set<String> roles = new HashSet<>();
		roles.add("user");
		if("admin".equals(principal)){
			roles.add("admin");
		}
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
		return info;
	}
}
