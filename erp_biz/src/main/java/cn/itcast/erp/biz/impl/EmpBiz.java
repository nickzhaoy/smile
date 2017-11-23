package cn.itcast.erp.biz.impl;
import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.crypto.hash.Md5Hash;

import cn.itcast.erp.biz.IEmpBiz;
import cn.itcast.erp.dao.IEmpDao;
import cn.itcast.erp.dao.IRoleDao;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Role;
import cn.itcast.erp.entity.Tree;
import cn.itcast.erp.exception.ErpException;
import redis.clients.jedis.Jedis;
/**
 * 员工业务逻辑类
 * @author Administrator
 *
 */
public class EmpBiz extends BaseBiz<Emp> implements IEmpBiz {

	private IEmpDao empDao;
	
	private IRoleDao roleDao;
	
	
	private Jedis jedis;
	
	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}

	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public void setEmpDao(IEmpDao empDao) {
		this.empDao = empDao;
		setBaseDao(empDao);
	}

	/**
	 * 通过用户名和密码查找用户
	 * @param username
	 * @param pwd
	 * @return
	 */
	public Emp findByUsernameAndPwd(String username,String pwd){
		return empDao.findByUsernameAndPwd(username, pwd);
	}
	
	/**
	 * @param id
	 * @param oldPwd
	 * @param newPwd
	 */
	public void updatePwd(Long id,String oldPwd,String newPwd){
		Emp emp = empDao.get(id);
		
		Md5Hash md5 = new Md5Hash(oldPwd, emp.getUsername(), 2);
		if(emp.getPwd().equals(md5.toString())){
			Md5Hash _md5 = new Md5Hash(newPwd, emp.getUsername(), 2);
			emp.setPwd(_md5.toString());
//			empDao.update(emp);
		}else{
			//自定义异常
			throw new ErpException("原密码不正确！");
			
		}
		
	}
	/**
	 * 重置密码
	 * @param id
	 * @param pwd
	 */
	public void updatePwd_reset(Long id,String pwd){
		Emp emp = empDao.get(id);
		Md5Hash _md5 = new Md5Hash(pwd, emp.getUsername(), 2);
		emp.setPwd(_md5.toString());
	}
	
	/**
	 * 指定用户角色树形结构
	 * @param id
	 * @return
	 */
	public List<Tree> readEmpRole(Long id){
		Emp emp = empDao.get(id);
		List<Role> roles = emp.getRoles(); //用户下的所有角色
		List<Tree> trees  = new ArrayList<Tree>();
		List<Role> roleList = roleDao.getList(null, null, null);  //所有角色、
		for (Role role : roleList) {
			Tree tree = new Tree();
			tree.setId(role.getUuid()+"");
			tree.setText(role.getName());
			if(roles.contains(role)){
				tree.setChecked(true);
			}
			trees.add(tree);
		}
		return trees;
	}
	
	/**
	 * 更新用户角色数据
	 * @param id
	 * @param idStrs
	 */
	public void updateEmpRoles(Long id,String idStrs){
		Emp emp = empDao.get(id);
		emp.setRoles(new ArrayList<Role>());
		
		String[] strs = idStrs.split(",");
		
		for (String str : strs) {
			Role role = roleDao.get(Long.parseLong(str));
			emp.getRoles().add(role);
		}
		
		jedis.del("menuListByEmpuuid_"+id);
	}
}
