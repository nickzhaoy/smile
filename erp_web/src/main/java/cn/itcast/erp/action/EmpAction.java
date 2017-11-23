package cn.itcast.erp.action;
import java.util.List;

import org.apache.shiro.crypto.hash.Md5Hash;

import com.alibaba.fastjson.JSON;

import cn.itcast.erp.biz.IEmpBiz;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Tree;
import cn.itcast.erp.exception.ErpException;

/**
 * 员工Action 
 * @author Administrator
 *
 */
public class EmpAction extends BaseAction<Emp> {

	private IEmpBiz empBiz;
	
	private String pwd;
	
	private String idStrs;
	
	public String getIdStrs() {
		return idStrs;
	}
	public void setIdStrs(String idStrs) {
		this.idStrs = idStrs;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	private String oldPwd;
	
	private String newPwd;
	public String getOldPwd() {
		return oldPwd;
	}
	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}
	public String getNewPwd() {
		return newPwd;
	}
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}
	public void setEmpBiz(IEmpBiz empBiz) {
		this.empBiz = empBiz;
		setBaseBiz(empBiz);
	}
	/**
	 * 指定用户角色树形结构
	 * @param id
	 * @return
	 */
	public void readEmpRoles(){
		List<Tree> list = empBiz.readEmpRole(getId());
		String jsonString = JSON.toJSONString(list);
		write(jsonString);
	}
	
	/**
	 * 更新用户角色数据
	 * @param id
	 * @param idStrs
	 */
	public void updateEmpRoles(){
		try {
			empBiz.updateEmpRoles(getId(), idStrs);
			write(ajaxReturn(true, "设置成功"));
		} catch (Exception e) {
			write(ajaxReturn(false, "设置失败"));
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 增加
	 */
	public void add(){
		Emp emp = super.getT();
		
//		source 原密码, salt 盐, hashIterations 加几次盐
		Md5Hash md5 = new Md5Hash(emp.getUsername(), emp.getUsername(), 2);  //初始化员工数据时默认密码和用户名相同
		emp.setPwd(md5.toString());
		try {
			empBiz.add(emp);
			write(ajaxReturn(true, "增加成功"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			write(ajaxReturn(false, "增加失败"));
		}
	}
	
	/**
	 * 修改密码
	 */
	public void updatePwd(){
		Emp user = getUser();
		if(user==null){
			write(ajaxReturn(false, "请登录"));
			return;
		}
		
		try {
			empBiz.updatePwd(user.getUuid(), oldPwd, newPwd);
			write(ajaxReturn(true, "修改成功"));
		} catch (ErpException e) {
			write(ajaxReturn(false, e.getMessage()));
		} catch (Exception e) {
			write(ajaxReturn(false, "修改失败"));
		}
	}
	
	/**
	 * 重置密码
	 * @param id
	 * @param pwd
	 */
	public void updatePwd_reset(){
		
		try {
			empBiz.updatePwd_reset(getId(), pwd);
			write(ajaxReturn(true, "重置成功"));
		} catch (Exception e) {
			write(ajaxReturn(false, "重置失败"));
		}
		
		
	}
	
}
