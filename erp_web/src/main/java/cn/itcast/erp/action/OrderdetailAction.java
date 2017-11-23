package cn.itcast.erp.action;
import cn.itcast.erp.biz.IOrderdetailBiz;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Orderdetail;
import cn.itcast.erp.exception.ErpException;

/**
 * 订单明细Action 
 * @author Administrator
 *
 */
public class OrderdetailAction extends BaseAction<Orderdetail> {

	private IOrderdetailBiz orderdetailBiz;
	
	public void setOrderdetailBiz(IOrderdetailBiz orderdetailBiz) {
		this.orderdetailBiz = orderdetailBiz;
		setBaseBiz(orderdetailBiz);
	}
	
	private Long storeuuid;
	
	
	public Long getStoreuuid() {
		return storeuuid;
	}




	public void setStoreuuid(Long storeuuid) {
		this.storeuuid = storeuuid;
	}


	/**
	 * 订单项入库
	 */
	public void doInstore(){
		Emp user = getUser();
		if(user==null){
			write(ajaxReturn(false, "请登录"));
			return;
		}
		try {
			orderdetailBiz.doInstore(getId(), user.getUuid(), storeuuid);
			write(ajaxReturn(true, "入库成功"));
		} catch (Exception e) {
			write(ajaxReturn(false, "入库失败"));
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 出库
	 */
	public void doOutstore(){
		Emp user = getUser();
		if(user==null){
			write(ajaxReturn(false, "请登录"));
			return;
		}
		try {
			orderdetailBiz.doOutstore(getId(), user.getUuid(), storeuuid);
			write(ajaxReturn(true, "出库成功"));
		} catch (ErpException e) {
			write(ajaxReturn(false,e.getMessage()));
			e.printStackTrace();
		}catch (Exception e) {
			write(ajaxReturn(false, "出库失败"));
			e.printStackTrace();
		}
		
		
	}
	
	
}
