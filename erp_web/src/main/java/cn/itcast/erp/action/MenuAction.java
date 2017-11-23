package cn.itcast.erp.action;
import com.alibaba.fastjson.JSON;

import cn.itcast.erp.biz.IMenuBiz;
import cn.itcast.erp.entity.Menu;

/**
 * 菜单Action 
 * @author Administrator
 *
 */
public class MenuAction extends BaseAction<Menu> {

	private IMenuBiz menuBiz;
	
	public void setMenuBiz(IMenuBiz menuBiz) {
		this.menuBiz = menuBiz;
		setBaseBiz(menuBiz);
	}
	
	/**
	 * 获取主页的菜单树
	 */
	public void getMenuTree(){
//		Menu menu = menuBiz.get("0");  //这个“0” 是程序员自己定义的数据，可以写死
		Menu menu = menuBiz.getMenuByEmpuuid(getUser().getUuid());
		//获取到一个根菜单的对象，就能把所有的菜单数据取到
		String jsonString = JSON.toJSONString(menu,true);
		write(jsonString);
	}
	
	
}
