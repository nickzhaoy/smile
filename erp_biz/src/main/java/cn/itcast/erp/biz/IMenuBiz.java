package cn.itcast.erp.biz;
import java.util.List;

import cn.itcast.erp.entity.Menu;
/**
 * 菜单业务逻辑层接口
 * @author Administrator
 *
 */
public interface IMenuBiz extends IBaseBiz<Menu>{
	
	/**
	 * 根据当前登录人获取菜单数据
	 * @param empuuid
	 * @return
	 */
	public Menu getMenuByEmpuuid(Long empuuid);
	
	/**
	 * 根据当前登录人获取菜单数据
	 * @param empuuid
	 * @return
	 */
	public List<Menu> getMenuListByEmpuuid(Long empuuid);
}

