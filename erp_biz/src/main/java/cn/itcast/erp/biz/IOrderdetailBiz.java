package cn.itcast.erp.biz;
import cn.itcast.erp.entity.Orderdetail;
/**
 * 订单明细业务逻辑层接口
 * @author Administrator
 *
 */
public interface IOrderdetailBiz extends IBaseBiz<Orderdetail>{
	
	/**
	 * 订单项入库
	 */
	public void doInstore(Long id,Long empuuid,Long storeuuid);
	
	/**
	 * 出库
	 */
	public void doOutstore(Long id,Long empuuid,Long storeuuid);
}

