package cn.itcast.erp.biz;
import java.util.List;

import cn.itcast.erp.entity.Role;
import cn.itcast.erp.entity.Tree;
/**
 * 角色业务逻辑层接口
 * @author Administrator
 *
 */
public interface IRoleBiz extends IBaseBiz<Role>{
	/**
	 * 读取角色权限数据
	 * @return
	 */
	public List<Tree> readRoleMenus(Long id);
	
	/**
	 * 更新角色权限
	 * @param id
	 * @param idStrs
	 */
	public void updateRoleMenus(Long id,String idStrs);
	
}

