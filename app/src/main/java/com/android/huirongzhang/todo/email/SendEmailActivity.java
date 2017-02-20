package com.android.huirongzhang.todo.email;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.huirongzhang.todo.R;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class SendEmailActivity extends Activity implements View.OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_email_activity);
		Button send = (Button) findViewById(R.id.btn_send_email);
		send.setOnClickListener(this);
	}

	private void sendEmailFrom163() {
		HtmlEmail email = new HtmlEmail();
		try {
			// 这里是SMTP发送服务器的名字：163的如下：
			email.setHostName("smtp.163.com");
			// 字符编码集的设置
			email.setCharset("gbk");
			// 收件人的邮箱
			email.addTo("zhanghuirong@360.cn");
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


	@Override
	public void onClick(View v) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				sendEmailFrom163();
			}
		}).start();
	}
}