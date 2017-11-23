package cn.itcast.erp.action;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.redsun.bos.ws.Waybilldetail;

import cn.itcast.erp.biz.IOrdersBiz;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Orderdetail;
import cn.itcast.erp.entity.Orders;

/**
 * 订单Action 
 * @author Administrator
 *
 */
public class OrdersAction extends BaseAction<Orders> {

	private IOrdersBiz ordersBiz;
	
	public void setOrdersBiz(IOrdersBiz ordersBiz) {
		this.ordersBiz = ordersBiz;
		setBaseBiz(ordersBiz);
	}
	
	
	private Long sn;
	
	
	public Long getSn() {
		return sn;
	}


	public void setSn(Long sn) {
		this.sn = sn;
	}


	private String orderdetailsJSON;
	
	
	public String getOrderdetailsJSON() {
		return orderdetailsJSON;
	}





	public void setOrderdetailsJSON(String orderdetailsJSON) {
		this.orderdetailsJSON = orderdetailsJSON;
	}


	public void myListByPage(){
		Orders t1 = getT1();
		t1.setCreater(getUser().getUuid());
		super.listByPage();
		
	}

	/**
	 * 
		根据运单号获取运单详情
	 */
	public void waybillList(){
		List<Waybilldetail> list = ordersBiz.getWaybilldetailBySn(sn);
		String jsonString = JSON.toJSONString(list);
		write(jsonString);
	}


	/**
	 * 添加订单
	 */
	public void add(){
		Emp user = getUser();
		if(user==null){
			write(ajaxReturn(false, "请登录"));
			return;
		}
		Orders orders = getT();
		Subject subject = SecurityUtils.getSubject();
		if(orders.getType().equals("1")){  //采购订单
			if(!subject.isPermitted("采购申请")){
				write(ajaxReturn(false, "没有此权限"));
				return;
			}
		}
		if(orders.getType().equals("2")){  //销售订单
			if(!subject.isPermitted("销售订单录入")){
				write(ajaxReturn(false, "没有此权限"));
				return;
			}
		}
		
		
		if(!orders.getType().equals("2")&&!orders.getType().equals("1")){
			write(ajaxReturn(false, "非法权限"));
			return;
		}
		
//		把json字符串转成list集合
		List<Orderdetail> orderdetails = JSON.parseArray(orderdetailsJSON, Orderdetail.class);
		orders.setOrderdetails(orderdetails);
		try {
			ordersBiz.add(orders, user.getUuid());
			write(ajaxReturn(true, "保存成功"));
		} catch (Exception e) {
			write(ajaxReturn(false, "保存失败"));
			e.printStackTrace();
		}
	}
	
	/**
	 * 订单审核
	 */
	public void doCheck(){
		Emp user = getUser();
		if(user==null){
			write(ajaxReturn(false, "请登录"));
			return;
		}
		try {
			ordersBiz.doCheck(getId(), user.getUuid());
			write(ajaxReturn(true, "审核成功"));
		} catch (UnauthorizedException e) {
			write(ajaxReturn(false, "没有此权限"));
		} catch (Exception e) {
			write(ajaxReturn(false, "审核失败"));
		}
	}
	
	/**
	 * 订单确认
	 */
	public void doStart(){
		Emp user = getUser();
		if(user==null){
			write(ajaxReturn(false, "请登录"));
			return;
		}
		try {
			ordersBiz.doStart(getId(), user.getUuid());
			write(ajaxReturn(true, "确认成功"));
		} catch (Exception e) {
			write(ajaxReturn(false, "确认失败"));
		}
	}

	/**
	 * 	导出
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void export() throws FileNotFoundException, IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setHeader("content-disposition", "attchement;filename=orders.xls");
		ServletOutputStream out = response.getOutputStream();
//		File.separator;  \       /
		String filepath = ServletActionContext.getServletContext().getRealPath(File.separator)+"template"+File.separator+"orders.xls";  //工程部署的路径
//		filepath 就是模板所在的路径
		ordersBiz.export(getId(), new FileInputStream(filepath), out);
	}
	
}
