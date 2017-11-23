package cn.itcast.erp.biz.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import cn.itcast.erp.biz.IStorealertBiz;
import cn.itcast.erp.dao.IStorealertDao;
import cn.itcast.erp.entity.Storealert;
import cn.itcast.erp.exception.ErpException;
import cn.itcast.erp.util.MailUtil;

public class StorealertBiz implements IStorealertBiz {

	private IStorealertDao storealertDao;
	
	
	private MailUtil mailUtil;
	
	private String to;
	
	private String subject;
	
	private String text;
	
	
	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public void setMailUtil(MailUtil mailUtil) {
		this.mailUtil = mailUtil;
	}

	public void setStorealertDao(IStorealertDao storealertDao) {
		this.storealertDao = storealertDao;
	}

	@Override
	public List<Storealert> getStorealertList() {
		return storealertDao.getStorealertList();
	}

	/**
	 * 发送库存预警
	 * @throws MessagingException
	 */
	public void sendStorealertMail() throws MessagingException{
		List<Storealert> storealertList = storealertDao.getStorealertList();
		if(storealertList==null||storealertList.size()==0){
			throw new ErpException("不用发送邮件");
		}
		text = text.replace("[num]", storealertList.size()+"");
		subject = subject.replace("[time]", sdf.format(new Date()));
		mailUtil.sendMail(to, subject, text);
		
	}
}
