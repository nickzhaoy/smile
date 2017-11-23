package cn.itcast.erp.biz;
import java.util.List;

import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Tree;
/**
 * 员工业务逻辑层接口
 * @author Administrator
 *
 */
public interface IEmpBiz extends IBaseBiz<Emp>{
	
	/**
	 * 通过用户名和密码查找用户
	 * @param username
	 * @param pwd
	 * @return
	 */
	public Emp findByUsernameAndPwd(String username,String pwd);
	/**
	 * @param id
	 * @param oldPwd
	 * @param newPwd
	 */
	public void updatePwd(Long id,String oldPwd,String newPwd);
	/**
	 * 重置密码
	 * @param id
	 * @param pwd
	 */
	public void updatePwd_reset(Long id,String pwd);
	
	/**
	 * 指定用户角色树形结构
	 * @param id
	 * @return
	 */
	public List<Tree> readEmpRole(Long id);
	
	/**
	 * 更新用户角色数据
	 * @param id
	 * @param idStrs
	 */
	public void updateEmpRoles(Long id,String idStrs);
}

