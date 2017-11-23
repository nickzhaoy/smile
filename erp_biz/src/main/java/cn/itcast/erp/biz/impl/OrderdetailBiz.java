package cn.itcast.erp.biz.impl;
import java.util.Date;
import java.util.List;

import com.redsun.bos.ws.impl.IWallbillService;

import cn.itcast.erp.biz.IOrderdetailBiz;
import cn.itcast.erp.dao.IOrderdetailDao;
import cn.itcast.erp.dao.IStoredetailDao;
import cn.itcast.erp.dao.IStoreoperDao;
import cn.itcast.erp.dao.ISupplierDao;
import cn.itcast.erp.entity.Orderdetail;
import cn.itcast.erp.entity.Orders;
import cn.itcast.erp.entity.Storedetail;
import cn.itcast.erp.entity.Storeoper;
import cn.itcast.erp.entity.Supplier;
import cn.itcast.erp.exception.ErpException;
/**
 * 订单明细业务逻辑类
 * @author Administrator
 *
 */
public class OrderdetailBiz extends BaseBiz<Orderdetail> implements IOrderdetailBiz {

	private IOrderdetailDao orderdetailDao;
	
	
	private IStoredetailDao storedetailDao;
	
	private IStoreoperDao storeoperDao;
	
	private IWallbillService wallbillService;
	
	private ISupplierDao supplierDao;
	
	public void setSupplierDao(ISupplierDao supplierDao) {
		this.supplierDao = supplierDao;
	}

	public void setWallbillService(IWallbillService wallbillService) {
		this.wallbillService = wallbillService;
	}

	public void setStoreoperDao(IStoreoperDao storeoperDao) {
		this.storeoperDao = storeoperDao;
	}

	public void setStoredetailDao(IStoredetailDao storedetailDao) {
		this.storedetailDao = storedetailDao;
	}

	public void setOrderdetailDao(IOrderdetailDao orderdetailDao) {
		this.orderdetailDao = orderdetailDao;
		setBaseDao(orderdetailDao);
	}
	
	/**
	 * 订单项入库
	 */
	public void doInstore(Long id,Long empuuid,Long storeuuid){
		Orderdetail orderdetail = orderdetailDao.get(id);
//		1、修改订单项数据
//		private java.util.Date endtime;//结束日期
//		private Long ender;//库管员
//		private String state;//状态   采购订单  0未入库 1 已入库
		orderdetail.setEnder(empuuid);
		orderdetail.setEndtime(new Date());
		orderdetail.setState("1");
//		2、修改订单数据
//		条件：判断此订单下的是否还有未入库的订单项
//		select  * from orderdetail where ordersuuid=1 and state=0//判断
		Orderdetail t1 = new Orderdetail();
		Orders orders = orderdetail.getOrders();
		t1.setOrders(orders);
		t1.setState("0");
		long count = orderdetailDao.getCount(t1, null, null);
		if(count==0){ //所有订单项都已入库
//			 如果都已入库
//			 修改订单数据
//			private java.util.Date endtime;//结束日期
//			private Long ender;//库管员
//			private String state;//订单状态    采购订单 0未审核  1已审核  2已确认   3已完成
			orders.setEndtime(new Date());
			orders.setEnder(empuuid);
			orders.setState("3");
		}
//		 如果有未入库的订单项
		 
		
//		 3、库存表
//		 select  * from 库存表 where  商品ID= and 仓库ID=
//		 查询库存表是否有数据
		Storedetail storedetail = new Storedetail(); 
		storedetail.setGoodsuuid(orderdetail.getGoodsuuid());
		storedetail.setStoreuuid(storeuuid);
		List<Storedetail> list = storedetailDao.getList(storedetail, null, null);
//		 如果有数据
		if(list!=null&&list.size()>0){
//		 数量累加
			storedetail = list.get(0);
			storedetail.setNum(storedetail.getNum()+orderdetail.getNum());
		}else{
//			 如果没有数据
//			 插入一条数据
			storedetail.setNum(orderdetail.getNum());
			storedetailDao.add(storedetail);
		}
//		 4、流水表
//		 直接放数据
		Storeoper storeoper = new Storeoper();
//		private Long uuid;//编号
//		private Long empuuid;//员工编号
//		private java.util.Date opertime;//操作日期
//		private Long storeuuid;//仓库编号
//		private Long goodsuuid;//商品编号
//		private Long num;//数量
//		private String type;//类型
		
		storeoper.setEmpuuid(empuuid);
		storeoper.setGoodsuuid(orderdetail.getGoodsuuid());
		storeoper.setNum(orderdetail.getNum());
		storeoper.setOpertime(new Date());
		storeoper.setStoreuuid(storeuuid);
		storeoper.setType("1");
		storeoperDao.add(storeoper);
	}
	
	/**
	 * 出库
	 */
	public void doOutstore(Long id,Long empuuid,Long storeuuid){
//		1、修改订单项的状态
		Orderdetail orderdetail = orderdetailDao.get(id);
//		private java.util.Date endtime;//结束日期
//		private Long ender;//库管员
//		private Long storeuuid;//仓库编号
//		private String state;//状态   采购订单  0未入库 1 已入库    销售订单 0未出库 1 已出库
		orderdetail.setEnder(empuuid);
		orderdetail.setEndtime(new Date());
		orderdetail.setStoreuuid(storeuuid);
		orderdetail.setState("1");
		
//		2、修改订单
		Orders orders = orderdetail.getOrders();
		Orderdetail t1 = new Orderdetail();
		t1.setOrders(orders);
		t1.setState("0");
//		判断此订单下的所有商品是否都已出库
//		select  * from orderdetail where ordersuuid=1 and state=0//条件：判断此订单下的是否还有未出库的订单项
//		2、修改订单数据
		long count = orderdetailDao.getCount(t1, null, null);
		if(count==0){ //所有订单项都已出库
//			 修改订单数据
//			private java.util.Date endtime;//结束日期
//			private Long ender;//库管员
//			private String state;//订单状态    采购订单 0未审核  1已审核  2已确认   3已完成  0  3
			
//			Long userid, String toaddress, String addressee, String tele, String info
			Supplier supplier = supplierDao.get(orders.getSupplieruuid());
			
			String info="";
			
			for(Orderdetail detail : orders.getOrderdetails()){
				info+=detail.getGoodsname()+":"+detail.getNum()+" ";
			}
			Long sn = wallbillService.addWaybill(1L, supplier.getAddress(), supplier.getName(), supplier.getTele(), info);
			orders.setEndtime(new Date());
			orders.setEnder(empuuid);
			orders.setState("3");
			orders.setSn(sn);
		}
//		 3、库存表
//		 select  * from 库存表 where  商品ID= and 仓库ID=
//		 查询库存表是否有数据
		Storedetail storedetail = new Storedetail(); 
		storedetail.setGoodsuuid(orderdetail.getGoodsuuid());
		storedetail.setStoreuuid(storeuuid);
		List<Storedetail> list = storedetailDao.getList(storedetail, null, null);
//		 如果有数据
		if(list!=null&&list.size()>0){
//		 数量减
			storedetail = list.get(0);
//			1、目前的数量够不够出库
			storedetail.setNum(storedetail.getNum()-orderdetail.getNum());
			if(storedetail.getNum()<0){
				throw new ErpException("库存不足！");	
			}
			
		}else{
//			 如果没有数据
//			 插入一条数据
			throw new ErpException("次仓库中没有这种商品！");
		}
//		 4、流水表
//		 直接放数据
		Storeoper storeoper = new Storeoper();
//		private Long uuid;//编号
//		private Long empuuid;//员工编号
//		private java.util.Date opertime;//操作日期
//		private Long storeuuid;//仓库编号
//		private Long goodsuuid;//商品编号
//		private Long num;//数量
//		private String type;//类型
		
		storeoper.setEmpuuid(empuuid);
		storeoper.setGoodsuuid(orderdetail.getGoodsuuid());
		storeoper.setNum(orderdetail.getNum());
		storeoper.setOpertime(new Date());
		storeoper.setStoreuuid(storeuuid);
		storeoper.setType("2");
		storeoperDao.add(storeoper);
		  
		
	}

	
}
