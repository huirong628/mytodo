package com.android.huirongzhang.todo.email;

import android.app.Activity;
import android.os.Bundle;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class SendEmailActivity extends Activity  {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.send_email_activity);
		new Thread(new Runnable() {
			@Override
			public void run() {
				//sendEmailFrom163();
			}
		}).start();

		new SendThread().start();
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
	/*
	public boolean isNetworkConnected(Context context)
    {
    	if (context != null) 
    	{
	    	ConnectivityManager mConnectivityManager = (ConnectivityManager) context
	    	.getSystemService(Context.CONNECTIVITY_SERVICE);
	    	NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
	    	if (mNetworkInfo != null) 
	    	{
	    		return mNetworkInfo.isAvailable();
	    	}
    	}
    	return false;
    	}
    }*/