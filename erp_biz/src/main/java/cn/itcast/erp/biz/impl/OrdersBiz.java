package cn.itcast.erp.biz.impl;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.redsun.bos.ws.Waybilldetail;
import com.redsun.bos.ws.impl.IWallbillService;

import cn.itcast.erp.biz.IOrdersBiz;
import cn.itcast.erp.dao.IEmpDao;
import cn.itcast.erp.dao.IOrdersDao;
import cn.itcast.erp.dao.ISupplierDao;
import cn.itcast.erp.dao.impl.SupplierDao;
import cn.itcast.erp.entity.Orderdetail;
import cn.itcast.erp.entity.Orders;
/**
 * 订单业务逻辑类
 * @author Administrator
 *
 */
public class OrdersBiz extends BaseBiz<Orders> implements IOrdersBiz {

	private IOrdersDao ordersDao;
	
	private ISupplierDao supplierDao;
	
	private IEmpDao empDao;
	
	private IWallbillService wallbillService;
	
	public void setWallbillService(IWallbillService wallbillService) {
		this.wallbillService = wallbillService;
	}

	public void setEmpDao(IEmpDao empDao) {
		this.empDao = empDao;
	}

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	public void setSupplierDao(ISupplierDao supplierDao) {
		this.supplierDao = supplierDao;
	}

	public void setOrdersDao(IOrdersDao ordersDao) {
		this.ordersDao = ordersDao;
		setBaseDao(ordersDao);
	}
	
	/**
	 * 根据运单号获取运单详情
	 * @param sn
	 * @return
	 */
	public List<Waybilldetail> getWaybilldetailBySn(Long sn){
		if(sn==null){
			return null;
		}
		return  wallbillService.getWaybilldetailBySn(sn);
	}
	
	/**
	 * 保存订单
	 * @param orders
	 * @param empuuid
	 */
	public void add(Orders orders,Long empuuid) {
		
//		private java.util.Date createtime;//生成日期 ^ ^ 
//		private String type;//订单类型  ^ ^  1采购     2 销售
//		private Long creater;//下单员 ^ ^ 
//		private Double totalmoney;//总金额  ^ ^ 
//		private String state;//订单状态  ^ ^
		orders.setCreatetime(new Date());
		orders.setCreater(empuuid);
		Double totalMoney=0.0;
		for(Orderdetail detail: orders.getOrderdetails()){
			totalMoney+=detail.getMoney();
			detail.setState("0");
		}
		orders.setTotalmoney(totalMoney);
		orders.setState("0");
		
		ordersDao.add(orders);		
	}
	/**
	 * 订单审核
	 */
	@RequiresPermissions("采购审核")
	public void doCheck(Long id,Long empuuid){
		Orders orders = ordersDao.get(id);
//		private java.util.Date checktime;//检查日期
//		private Long checker;//审查员
//		private String state;//订单状态    采购订单 0未审核  1已审核
		orders.setChecktime(new Date());
		orders.setChecker(empuuid);
		orders.setState("1");
//		ordersDao.update(orders);
	}
	/**
	 * 订单确认
	 */
	public void doStart(Long id,Long empuuid){
		Orders orders = ordersDao.get(id);
		orders.setStarttime(new Date());
		orders.setStarter(empuuid);
		orders.setState("2");
	}
	
	/**
	 * 订单导出
	 * @param id
	 * @param in
	 * @param out
	 * @throws IOException
	 */
	public void export(Long id,InputStream in,OutputStream out ) throws IOException{
		Orders orders = ordersDao.get(id);
		HSSFWorkbook book = new HSSFWorkbook(in);
		HSSFSheet sheet = book.getSheetAt(0);
		sheet.getRow(2).getCell(1).setCellValue(supplierDao.get(orders.getSupplieruuid()).getName());//供应商
		sheet.getRow(3).getCell(1).setCellValue(sdf.format(orders.getCreatetime()));  //下单时间

		if(orders.getChecktime()!=null){
			sheet.getRow(4).getCell(1).setCellValue(sdf.format(orders.getChecktime()));//		审核时间
		}
		if(orders.getStarttime()!=null){
			sheet.getRow(5).getCell(1).setCellValue(sdf.format(orders.getStarttime()));//		确认日期
		}
		if(orders.getEndtime()!=null){
			sheet.getRow(6).getCell(1).setCellValue(sdf.format(orders.getEndtime()));//		结束日期
		}
		
		sheet.getRow(3).getCell(3).setCellValue(empDao.get(orders.getCreater()).getName());//		下单员

		if(orders.getChecker()!=null){
			sheet.getRow(4).getCell(3).setCellValue(empDao.get(orders.getChecker()).getName());//		审查员
		}
		if(orders.getStarter()!=null){
			sheet.getRow(5).getCell(3).setCellValue(empDao.get(orders.getStarter()).getName());//		确认人
		}
		if(orders.getEnder()!=null){
			sheet.getRow(6).getCell(3).setCellValue(empDao.get(orders.getEnder()).getName());//		库管员
		}

		List<Orderdetail> orderdetails = orders.getOrderdetails();
		int rowIndex = 9;
		
		HSSFCellStyle cellStyle = sheet.getRow(2).getCell(0).getCellStyle();
		
		for (Orderdetail orderdetail : orderdetails) {
			HSSFRow row = sheet.createRow(rowIndex);
//			商品名称	价格	数量	金额
			HSSFCell cell = row.createCell(0);
			cell.setCellValue(orderdetail.getGoodsname());
			cell.setCellStyle(cellStyle);
			
			HSSFCell cell1 = row.createCell(1);
			cell1.setCellValue(orderdetail.getPrice());
			cell1.setCellStyle(cellStyle);
			
			HSSFCell cell2 = row.createCell(2);
			cell2.setCellValue(orderdetail.getNum());
			cell2.setCellStyle(cellStyle);
			
			HSSFCell cell3 = row.createCell(3);
			cell3.setCellValue(orderdetail.getMoney());
			cell3.setCellStyle(cellStyle);
			
			rowIndex++;
		}

		book.write(out);
		book.close();
		
		
	}
}
