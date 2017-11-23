package cn.itcast.erp.util;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailUtil {

	private JavaMailSender mailSender;

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	private String from;
	
	public String getFrom() {
		return from;
	}




	public void setFrom(String from) {
		this.from = from;
	}




	public void sendMail(String to,String subject,String text) throws MessagingException{
		//邮件对象
		MimeMessage mime = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mime);
		
		helper.setFrom(from);  //发送方 邮箱
		helper.setTo(to);
		helper.setSubject(subject); //主题
		helper.setText(text);
		
		mailSender.send(mime);
	}
	
	
}
