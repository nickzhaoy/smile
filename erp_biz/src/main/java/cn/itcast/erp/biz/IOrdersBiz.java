package cn.itcast.erp.biz;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.redsun.bos.ws.Waybilldetail;

import cn.itcast.erp.entity.Orders;
/**
 * 订单业务逻辑层接口
 * @author Administrator
 *
 */
public interface IOrdersBiz extends IBaseBiz<Orders>{
	/**
	 * 保存订单
	 * @param orders
	 * @param empuuid
	 */
	public void add(Orders orders,Long empuuid);
	/**
	 * 订单审核
	 */
	public void doCheck(Long id,Long empuuid);
	/**
	 * 订单确认
	 */
	public void doStart(Long id,Long empuuid);
	/**
	 * 订单导出
	 * @param id
	 * @param in
	 * @param out
	 * @throws IOException
	 */
	public void export(Long id,InputStream in,OutputStream out ) throws IOException;
	/**
	 * 根据运单号获取运单详情
	 * @param sn
	 * @return
	 */
	public List<Waybilldetail> getWaybilldetailBySn(Long sn);
}

