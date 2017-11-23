package cn.itcast.erp.action;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;

import cn.itcast.erp.biz.ISupplierBiz;
import cn.itcast.erp.entity.Supplier;
import cn.itcast.erp.exception.ErpException;

/**
 * 供应商Action 
 * @author Administrator
 *
 */
public class SupplierAction extends BaseAction<Supplier> {
	
	
	
	private File file ;
	
	
	private String fileFileName;
	
	
	private String fileContentType;
	

	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	private ISupplierBiz supplierBiz;
	
	private String q;
	
	public String getQ() {
		return q;
	}
	public void setQ(String q) {
		this.q = q;
	}
	public void setSupplierBiz(ISupplierBiz supplierBiz) {
		this.supplierBiz = supplierBiz;
		setBaseBiz(supplierBiz);
	}
	
	public void list(){
		getT1().setName(q);
		super.list();
	}
	
	public void listByPage(){
		if(getT1().getType().equals("2")){
			Subject subject = SecurityUtils.getSubject();
			if(!subject.isPermitted("客户")){
				System.out.println("没有访问客户管理的权限");
				write("");
			}
		}
	}
	
	/**
	 * 导出
	 */
	public void export(){
		HttpServletResponse response = ServletActionContext.getResponse();
		Supplier t1 = getT1();
		String fileName="";
		if(t1.getType().equals("1")){
			fileName="供应商.xls";
		}else{
			fileName="客户.xls";
		}
		
		try {
			System.out.println("fileName:"+fileName);
			response.setHeader("content-disposition", "attachement;fileName="+ (new String(fileName.getBytes(),"ISO-8859-1")));
			ServletOutputStream out;
			out = response.getOutputStream();
			supplierBiz.export(t1, out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	/**
	 * 数据导入
	 * @param in
	 * @throws IOException
	 */
	public void doImport() {
		
		try {
			supplierBiz.doImport(new FileInputStream(file));
			write(ajaxReturn(true,  "导入成功"));
		}catch (ErpException e) {
			write(ajaxReturn(false,  e.getMessage()));
		} catch (FileNotFoundException e) {
			write(ajaxReturn(false,  "导入失败"));
			e.printStackTrace();
		} catch (IOException e) {
			write(ajaxReturn(false, "导入失败"));
			e.printStackTrace();
		}
		
	}
}
