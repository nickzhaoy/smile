package cn.itcast.erp.biz.impl;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import cn.itcast.erp.biz.ISupplierBiz;
import cn.itcast.erp.dao.ISupplierDao;
import cn.itcast.erp.dao.impl.SupplierDao;
import cn.itcast.erp.entity.Supplier;
import cn.itcast.erp.exception.ErpException;
/**
 * 供应商业务逻辑类
 * @author Administrator
 *
 */
public class SupplierBiz extends BaseBiz<Supplier> implements ISupplierBiz {

	private ISupplierDao supplierDao;
	
	public void setSupplierDao(ISupplierDao supplierDao) {
		this.supplierDao = supplierDao;
		setBaseDao(supplierDao);
	}
	
	/**
	 * 导出
	 */
	public  void export(Supplier t1,OutputStream out){
		HSSFWorkbook book = new HSSFWorkbook();
		HSSFSheet sheet = book.createSheet();
		sheet.setColumnWidth(0, 5000);
		sheet.setColumnWidth(1, 10000);
		sheet.setColumnWidth(2, 5000);
		sheet.setColumnWidth(3, 5000);
		sheet.setColumnWidth(4, 8000);
		
		HSSFRow titleRow = sheet.createRow(0);
		titleRow.createCell(0).setCellValue("名称");
		titleRow.createCell(1).setCellValue("地址");
		titleRow.createCell(2).setCellValue("联系人");
		titleRow.createCell(3).setCellValue("电话");
		titleRow.createCell(4).setCellValue("Email");
		List<Supplier> list = supplierDao.getList(t1, null, null);
		
		int rowIndex = 1;
		for (Supplier supplier : list) {
			HSSFRow row = sheet.createRow(rowIndex);
			row.createCell(0).setCellValue(supplier.getName());
			row.createCell(1).setCellValue(supplier.getAddress());
			row.createCell(2).setCellValue(supplier.getContact());
			row.createCell(3).setCellValue(supplier.getTele());
			row.createCell(4).setCellValue(supplier.getEmail());
			rowIndex++;
		}
		try {
			book.write(out);
			book.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 数据导入
	 * @param in
	 * @throws IOException
	 */
	public void doImport(InputStream in) throws IOException{
		HSSFWorkbook book = new HSSFWorkbook(in);
		HSSFSheet sheet = book.getSheetAt(0);
		String type = "";
		
		String sheetName = sheet.getSheetName();
		
		if(sheetName.equals("供应商")){
			type="1";
		}
		if(sheetName.equals("客户")){
			type="2";
		}
		if("".equals(type)){
			throw new ErpException("导入的文件不正确！");
		}
		
//		名称	地址	联系人	电话	email
		int lastRowNum = sheet.getLastRowNum();
		for (int i = 1; i <= lastRowNum; i++) {
			Supplier supplier = new Supplier();
			HSSFRow row = sheet.getRow(i);
			supplier.setName(row.getCell(0).getStringCellValue());//		名称
			
			List<Supplier> list = supplierDao.getList(supplier, null, null);
			
			if(list!=null&&list.size()>0){
				supplier = list.get(0);
			}
			
			supplier.setAddress(row.getCell(1).getStringCellValue());//		地址
			supplier.setContact(row.getCell(2).getStringCellValue());//		联系人
			supplier.setTele(row.getCell(3).getStringCellValue());//		电话
			supplier.setEmail(row.getCell(4).getStringCellValue());//		email
			supplier.setType(type);
			
			if(list==null||list.size()==0){
				supplierDao.add(supplier);
			}
			
		}
		
	}
	
}
