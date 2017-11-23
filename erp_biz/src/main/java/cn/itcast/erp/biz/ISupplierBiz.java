package cn.itcast.erp.biz;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cn.itcast.erp.entity.Supplier;
/**
 * 供应商业务逻辑层接口
 * @author Administrator
 *
 */
public interface ISupplierBiz extends IBaseBiz<Supplier>{
	/**
	 * 导出
	 */
	public  void export(Supplier t1,OutputStream out);
	
	/**
	 * 数据导入
	 * @param in
	 * @throws IOException
	 */
	public void doImport(InputStream in) throws IOException;
}

