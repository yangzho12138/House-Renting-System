package com.house.utils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSendUtil {
    public static void send(String email) throws MessagingException {
        // 1. 连接上发送邮件的服务器
        // 创建属性文件对象
        Properties pro = new Properties();

        // 邮件服务器主机
        pro.setProperty("mail.host","smtp.qq.com");
        // 邮件传输协议
        pro.setProperty("mail.transport.protocol","smtp");
        // 设置邮件发送需要认证
        pro.put("mail.smtp.auth","true");
        // 连接邮件的服务器，会话
        Session session = Session.getDefaultInstance(pro);
        // 获取到传输对象
        Transport transport = session.getTransport();
        // 校验账号和授权码
        transport.connect("官方邮箱","授权码");

        // 2. 构建出一封邮件（设置收件人、设置主题和设置正文）
        // 有邮件的类
        MimeMessage message = new MimeMessage(session);
        // 设置发件人
        message.setFrom(new InternetAddress("官方邮箱"));

        // 设置收件人
        // Message.RecipientType.TO 表示收件人
        // Message.RecipientType.CC 表示抄送给XXX
        // Message.RecipientType.BCC 表示暗送
        message.setRecipient(Message.RecipientType.TO,new InternetAddress(email));

        // message.setRecipient(Message.RecipientType.CC,new InternetAddress(""));

        // 设置主题
        message.setSubject("缴费通知");
        // 设置正文
        message.setContent("检测到您在xx租赁平台上有订单尚未支付，请您尽快支付","text/html;charset=UTF-8");

        // 3. 发送邮件
        transport.sendMessage(message,message.getAllRecipients());
        // 关闭连接
        transport.close();
    }
}
