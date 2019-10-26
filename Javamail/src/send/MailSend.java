package send;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class MailSend {
    //发件人账号密码
    public static String senderAddress = "";
    public static String senderPassword = "";

    //收件人账号密码
    public static String receiveAddress = "";



    public static void main(String[] args){
        //1.连接邮件服务器的参数配置
        Properties properties = new Properties();

        //设置用户的认证方式
        properties.setProperty("mail.smtp.auth", "true");

        //设置发件人的SMTP服务器地址
        properties.setProperty("mail.smtp.host", "smtp.163.com");

        //2.创建定义整个应用程序所需的环境信息的Session对象
        Session session = Session.getInstance(properties);

        //设置调试信息在控制台打印出来
        session.setDebug(true);


        //3.创建邮件的实例对象
        Message message = getMimeMessage(session);



        //设置发件人的账号名号密码
        try {
            //4.根据session对象获取邮件传输对象Transport
            Transport transport = session.getTransport();

            //设置发件人的账户名和密码
            transport.connect(senderAddress,senderPassword);

            //发送邮件，并发送到所有收件人地址，messafe.getAllRecipients()获得到的是在创建邮件对象
            //添加的所有收件人、抄送人、密送人
            //transport.sendMessage(message,message.getAllRecipients());

            //发送邮件给指定的人
            transport.sendMessage(message,new Address[]{new InternetAddress(receiveAddress)});

            //关闭邮件连接
            transport.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static MimeMessage getMimeMessage(Session session){
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            //设置发件人的e-mail信息
            mimeMessage.setFrom(new InternetAddress(senderAddress));
            /*
            *   设置收件人地（可以增加多个收件人，抄送、密送）
            *   MineMessage.RecipientType.TO：发送
            *   MineMessage.RecipientType.CC：抄送
            *   MineMessage.RecipientType.BCC:密送
            * */
            //设置发送邮件的类型，以及接受者的e-mail地址
            mimeMessage.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveAddress));

            //设置邮件的主题
            mimeMessage.setSubject("邮件的主题", "UTF-8");

            String content = "这是我通过java来时实现E-mail的收发!";
            //设置邮件的正文
            mimeMessage.setContent(content,"text/html;charset=UTF-8");
            mimeMessage.setSentDate(new Date());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return mimeMessage;

    }
}
