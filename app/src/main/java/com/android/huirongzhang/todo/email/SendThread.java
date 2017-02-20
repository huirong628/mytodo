package com.android.huirongzhang.todo.email;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

import android.util.Log;

//与网络连接相关的操作都要在子线程中完成，当然也可以在Service服务里操作
class SendThread extends Thread {
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//super.run();
		try {
			//创建HtmlEmail类
			HtmlEmail email = new HtmlEmail();
			//填写邮件的主机明，我这里使用的是163
			email.setHostName("smtp.163.com");
			//email.setSSL(true);
			//设置字符编码格式，防止中文乱码
			email.setCharset("gbk");
			//设置收件人的邮箱
			email.addTo("879073159@qq.com");
			//设置发件人的邮箱
			email.setFrom("13512953107@163.com");
			//填写发件人的用户名和密码
			email.setAuthentication("13512953107@163.com", "zhang628912");
			//填写邮件主题
			email.setSubject("您好");
			//填写邮件内容
			email.setMsg("s1" + "\n" +"s2");
			//发送邮件
			Log.i("TAG", "---------------->sending");
			email.send();
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			Log.i("TAG", "---------------->" + e.getMessage());
		}

		//sendEmailFrom163();
	}

	private void sendEmailFrom163() {
		HtmlEmail email = new HtmlEmail();
		try {
			// 这里是SMTP发送服务器的名字：163的如下：
			email.setHostName("smtp.163.com");
			// 字符编码集的设置
			email.setCharset("gbk");
			// 收件人的邮箱
			email.addTo("879073159@qq.com");
			// 发送人的邮箱
			email.setFrom("13512953107@163.com", "zhanghuirong");
			// 如果需要认证信息的话，设置认证：用户名-密码。分别为发件人在邮件服务器上的注册名称和密码
			email.setAuthentication("13512953107@163.com", "zhang628912");
			email.setSubject("下午3：00会议室讨论，请准时参加");
			// 要发送的信息，由于使用了HtmlEmail，可以在邮件内容中使用HTML标签
			email.setMsg("<h1 style='color:red'>下午3：00会议室讨论</h1>" + " 请准时参加！");
			// 发送
			email.send();

			System.out.println("邮件发送成功!");
		} catch (EmailException e) {
			e.printStackTrace();
			System.out.println("邮件发送失败!");
		}
	}
}