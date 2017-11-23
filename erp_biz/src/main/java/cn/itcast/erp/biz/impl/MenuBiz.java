package cn.itcast.erp.biz.impl;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

import cn.itcast.erp.biz.IMenuBiz;
import cn.itcast.erp.dao.IMenuDao;
import cn.itcast.erp.entity.Menu;
import redis.clients.jedis.Jedis;
/**
 * 菜单业务逻辑类
 * @author Administrator
 *
 */
public class MenuBiz extends BaseBiz<Menu> implements IMenuBiz {

	private IMenuDao menuDao;
	
	private Jedis jedis;
	
	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}

	public void setMenuDao(IMenuDao menuDao) {
		this.menuDao = menuDao;
		setBaseDao(menuDao);
	}

	/**
	 * 根据当前登录人获取菜单数据
	 * @param empuuid
	 * @return
	 */
	public List<Menu> getMenuListByEmpuuid(Long empuuid){
		String string = jedis.get("menuListByEmpuuid_"+empuuid);
		List<Menu> list = null;
		if(string==null){
			list = menuDao.getMenuListByEmpuuid(empuuid);
			System.out.println("*************************从数据库中获取数据");
			String jsonString = JSON.toJSONString(list);
			jedis.set("menuListByEmpuuid_"+empuuid, jsonString);
		}else{
			System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&从REDIS中获取数据");
			list = JSON.parseArray(string, Menu.class);
		}
		return list;
	}
	
	/**
	 * 根据当前登录人获取菜单数据
	 * @param empuuid
	 * @return
	 */
	public Menu getMenuByEmpuuid(Long empuuid){
		 Menu menu = menuDao.get("0");
		 List<Menu> list = menuDao.getMenuListByEmpuuid(empuuid);
		 List<Menu> removeList2 = null;  //要remove的二级菜单数据
		 List<Menu> removeList1 = new ArrayList<Menu>();  //要remove的一级菜单数据
		 List<Menu> menus1 = menu.getMenus(); //一级菜单
		 for (Menu menu1 : menus1) {
			 removeList2 = new ArrayList<Menu>();
			 for (Menu menu2 : menu1.getMenus()) { //二级菜单
				if(!list.contains(menu2)){
					removeList2.add(menu2);
				}
			}
		   menu1.getMenus().removeAll(removeList2);
			
		   if(menu1.getMenus().size()==0){
			   removeList1.add(menu1);
		   }
		 }
		 menu.getMenus().removeAll(removeList1);
		 
		 return menu;
	}
	
}
