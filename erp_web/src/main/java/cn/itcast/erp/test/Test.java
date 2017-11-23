package cn.itcast.erp.test;

import org.apache.shiro.crypto.hash.Md5Hash;

public class Test {

	public static void main(String[] args) {
//		source 原密码, salt 盐, hashIterations 散次列 
		Md5Hash md5 = new Md5Hash("admin", "admin", 2);
		System.out.println(md5.toString());

		System.out.println("这是一个测试");	
		System.out.println("this is a test");
	}

}
