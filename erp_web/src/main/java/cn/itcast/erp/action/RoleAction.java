package cn.itcast.erp.action;
import java.util.List;

import com.alibaba.fastjson.JSON;

import cn.itcast.erp.biz.IRoleBiz;
import cn.itcast.erp.entity.Role;
import cn.itcast.erp.entity.Tree;

/**
 * 角色Action 
 * @author Administrator
 *
 */
public class RoleAction extends BaseAction<Role> {

	private IRoleBiz roleBiz;
	
	private String idStrs;
	
	public String getIdStrs() {
		return idStrs;
	}
	public void setIdStrs(String idStrs) {
		this.idStrs = idStrs;
	}
	public void setRoleBiz(IRoleBiz roleBiz) {
		this.roleBiz = roleBiz;
		setBaseBiz(roleBiz);
	}
	/**
	 * 读取角色权限数据
	 * @return
	 */
	public void readRoleMenus(){
		List<Tree> list = roleBiz.readRoleMenus(getId());
		String jsonString = JSON.toJSONString(list);
		write(jsonString);
	}
	
	/**
	 * 更新角色权限
	 * @param id
	 * @param idStrs
	 */
	public void updateRoleMenus(){
		try {
			roleBiz.updateRoleMenus(getId(), idStrs);
			write(ajaxReturn(true, "设置成功"));
		} catch (Exception e) {
			write(ajaxReturn(false, "设置失败"));
			e.printStackTrace();
		}
		
	}
}
