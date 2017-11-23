package cn.itcast.erp.biz;

import java.util.List;

import javax.mail.MessagingException;

import cn.itcast.erp.entity.Storealert;

public interface IStorealertBiz {
	public List<Storealert> getStorealertList();
	/**
	 * 发送库存预警
	 * @throws MessagingException
	 */
	public void sendStorealertMail() throws MessagingException;
}
