package com.lpy.shiro.realms;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.util.ByteSource;

public class SecondRealm extends AuthenticatingRealm {

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("[SecondRealm] doGetAuthenticationInfo");
		
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
			credentials = "ce2f6417c7e1d32c1d81a797ee0b499f87c5de06";
		}else if("user".equals(username)){
			credentials = "073d4c3ae812935f23cb3f2a71943f49e082a718";
		}
		String realmName = getName();
		ByteSource credentialsSalt = ByteSource.Util.bytes(username);
		
		SimpleAuthenticationInfo info = null;  //new SimpleAuthenticationInfo(principal, credentials, realmName);
		info = new SimpleAuthenticationInfo("SecondRealmName", credentials, credentialsSalt, realmName);
		return info;
	}
	
	public static void main(String[] args) {
		String algorithmName = "SHA1";
		Object credentials = "123456";
		Object salt = ByteSource.Util.bytes("admin");
		int hashIterations = 1024;
		
		Object result = new SimpleHash(algorithmName, credentials, salt, hashIterations);
		System.out.println(result);
	}
}
