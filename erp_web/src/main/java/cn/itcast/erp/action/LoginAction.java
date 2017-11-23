package cn.itcast.erp.action;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.erp.biz.IEmpBiz;
import cn.itcast.erp.entity.Emp;

public class LoginAction extends BaseAction<Emp> {
	
	private String username;
	
	private String pwd;
	
	private IEmpBiz empBiz;
	
	public void setEmpBiz(IEmpBiz empBiz) {
		this.empBiz = empBiz;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getPwd() {
		return pwd;
	}



	public void setPwd(String pwd) {
		this.pwd = pwd;
	}



	/**
	 * 查询用户
	 */
	public void checkUser(){
		
		Md5Hash md5 = new Md5Hash(pwd, username, 2);
//		Emp emp = empBiz.findByUsernameAndPwd(username, md5.toString());
//		1、创建令牌
		UsernamePasswordToken token  = new UsernamePasswordToken(username, md5.toString());
//		2、获取主题  subject 就是应用程序和shiro框架的入口
		Subject subject = SecurityUtils.getSubject();
//		3、开始认证
		try {
			subject.login(token);
			write(ajaxReturn(true, "登录成功！"));
		} catch (AuthenticationException e) {
			write(ajaxReturn(false, "用户名或密码错误！"));
			e.printStackTrace();
		}
		
//		if(emp==null){
//			write(ajaxReturn(false, "用户名或密码错误！"));
//		}
//		else{
//			write(ajaxReturn(true, "登录成功！"));
////			ServletActionContext.getRequest().getSession().setAttribute(arg0, arg1);
//			ActionContext.getContext().getSession().put("emp", emp);  //放入session
//			
//		}
		
		
	}  

	/**
	 * 从session中获取真实姓名
	 */
	public void getName(){
//		Emp emp = (Emp) ActionContext.getContext().getSession().get("emp");
		Emp emp = (Emp) SecurityUtils.getSubject().getPrincipal();
		if(emp==null){
			write(ajaxReturn(false, "请登录！"));
		}else{
			write(ajaxReturn(true,emp.getName()));
		}
	}
	
	/**
	 * 退出
	 */
	public void logout(){
		SecurityUtils.getSubject().logout();
//		ActionContext.getContext().getSession().remove("emp");
	}
}
