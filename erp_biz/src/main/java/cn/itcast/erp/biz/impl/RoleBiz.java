package cn.itcast.erp.biz.impl;
import java.util.ArrayList;
import java.util.List;

import cn.itcast.erp.biz.IRoleBiz;
import cn.itcast.erp.dao.IMenuDao;
import cn.itcast.erp.dao.IRoleDao;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Menu;
import cn.itcast.erp.entity.Role;
import cn.itcast.erp.entity.Tree;
import redis.clients.jedis.Jedis;
/**
 * 角色业务逻辑类
 * @author Administrator
 *
 */
public class RoleBiz extends BaseBiz<Role> implements IRoleBiz {

	private IRoleDao roleDao;
	
	private IMenuDao menuDao;
	
	private Jedis jedis;
	
	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}

	public void setMenuDao(IMenuDao menuDao) {
		this.menuDao = menuDao;
	}

	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
		setBaseDao(roleDao);
	}

	/**
	 * 读取角色权限数据
	 * @return
	 */
	public List<Tree> readRoleMenus(Long id){
		Role role = roleDao.get(id);
		List<Menu> menus = role.getMenus();  //当前角色下所拥有的菜单权限
		
		List<Tree> trees = new ArrayList<Tree>();
		
		Menu menu = menuDao.get("0");
		List<Menu> menus1 = menu.getMenus();  //一级菜单
		for(Menu menu1:menus1){    //循环遍历一级菜单
			Tree tree1 = new Tree();
			tree1.setId(menu1.getMenuid());
			tree1.setText(menu1.getMenuname());
			
			for(Menu menu2:menu1.getMenus()){ //循环遍历二级菜单
				Tree tree2 = new Tree();
				tree2.setId(menu2.getMenuid());
				tree2.setText(menu2.getMenuname());
				if(menus.contains(menu2)){
					tree2.setChecked(true);
				}
				tree1.getChildren().add(tree2);
			}  
			trees.add(tree1);
		}
		return trees;
	}
	
	
	/**
	 * 更新角色权限
	 * @param id
	 * @param idStrs
	 */
	public void updateRoleMenus(Long id,String idStrs){
		Role role = roleDao.get(id);
		role.setMenus(new ArrayList<Menu>());  //原菜单权限的清除
		String[] strs = idStrs.split(",");
		for (String str : strs) {
			Menu menu = menuDao.get(str);
			role.getMenus().add(menu);
		}
		
		for(Emp emp:role.getEmps()){
			jedis.del("menuListByEmpuuid_"+emp.getUuid());
		}
		
	}
}
