package cn.itcast.erp.dao;

import java.util.List;

import cn.itcast.erp.entity.Menu;
/**
 * 菜单数据访问接口
 * @author Administrator
 *
 */
public interface IMenuDao extends IBaseDao<Menu>{
	
	/**
	 * 根据当前登录人获取菜单数据
	 * @param empuuid
	 * @return
	 */
	public List<Menu> getMenuListByEmpuuid(Long empuuid);
}
