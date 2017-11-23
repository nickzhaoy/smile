package cn.itcast.erp.action;

import java.util.List;

import javax.mail.MessagingException;

import com.alibaba.fastjson.JSON;

import cn.itcast.erp.biz.IStorealertBiz;
import cn.itcast.erp.entity.Storealert;
import cn.itcast.erp.exception.ErpException;

public class StorealertAction extends BaseAction<Storealert> {
	
	
	private IStorealertBiz  storealertBiz;

	public void setStorealertBiz(IStorealertBiz storealertBiz) {
		this.storealertBiz = storealertBiz;
	}
	
	public void list(){
		List<Storealert> storealertList = storealertBiz.getStorealertList();
		String jsonString = JSON.toJSONString(storealertList);
		write(jsonString);
	}
	
	/**
	 * 发送邮件
	 */
	public void sendMail(){
		try {
			storealertBiz.sendStorealertMail();
			write(ajaxReturn(true, "发送成功"));
		}catch (ErpException e) {
			write(ajaxReturn(false, e.getMessage()));
		} catch (MessagingException e) {
			write(ajaxReturn(false, "发送失败"));
		}
		
	}

}
