package cn.itcast.erp.realm;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import cn.itcast.erp.biz.IEmpBiz;
import cn.itcast.erp.biz.IMenuBiz;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Menu;

public class ErpRealm extends AuthorizingRealm {
	
	private IEmpBiz empBiz;
	
	private IMenuBiz menuBiz;
	
	public void setMenuBiz(IMenuBiz menuBiz) {
		this.menuBiz = menuBiz;
	}

	public void setEmpBiz(IEmpBiz empBiz) {
		this.empBiz = empBiz;
	}

	/**
	 * 授权
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("+++++++++++++++++++++++++++开始授权方法");
		
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		Emp emp = (Emp) principals.getPrimaryPrincipal();  //获取主角
		
		List<Menu> list = menuBiz.getMenuListByEmpuuid(emp.getUuid());
		for (Menu menu : list) {
			info.addStringPermission(menu.getMenuname());
		}
		return info;
	}

	/**
	 * 认证
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken agr0) throws AuthenticationException {
		System.out.println("--------------------------开始认证方法");
		UsernamePasswordToken token = (UsernamePasswordToken) agr0;
		String username = token.getUsername();
		String pwd = new String(token.getPassword());
		Emp emp = empBiz.findByUsernameAndPwd(username, pwd);
		
		if(emp==null){
			return null;
		}else{
//			principal   主角, credentials 密码, realmName 当前的类名
			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(emp, pwd, getName());
			return info;
		}
	}

}
