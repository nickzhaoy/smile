package cn.itcast.erp.util;

import javax.mail.MessagingException;

import cn.itcast.erp.biz.IStorealertBiz;

public class JobUtil {
	
	private IStorealertBiz  storealertBiz;
	
	public void setStorealertBiz(IStorealertBiz storealertBiz) {
		this.storealertBiz = storealertBiz;
	}




	/**
	 * 发送库存预警邮件
	 */
	public void sendStorealertMail(){
		try {
			storealertBiz.sendStorealertMail();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
