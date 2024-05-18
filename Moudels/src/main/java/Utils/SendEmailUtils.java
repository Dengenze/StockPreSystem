package Utils;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 * 发送邮箱验证码工具类
 */
public class SendEmailUtils {
    /**
     * 发送验证码
     * @param email  接收邮箱
     * @param code   验证码
     */
    public static String sendAuthCodeEmail(String email,String code) {
        try {
            HtmlEmail mail = new HtmlEmail();
            //发送邮件的服务器 QQ为smtp.qq.com
            mail.setHostName("smtp.qq.com");
            //不设置发送的消息有可能是乱码
            mail.setCharset("UTF-8");
            //IMAP/SMTP服务的密码 username为你开启发送验证码功能的邮箱号 password为你在qq邮箱获取到的一串字符串
            mail.setAuthentication("2516632593@qq.com", "skjbsgjqqmwidjgh");
            //发送邮件的邮箱和发件人
            mail.setFrom("2516632593@qq.com", "理财大师");
            //使用安全链接
            mail.setSSLOnConnect(true);
            //接收的邮箱
            mail.addTo(email);
            //设置邮件的主题
            mail.setSubject("登录验证码");
            //设置邮件的内容
            mail.setMsg("尊敬的用户:你好! 验证码为:" + code + "(有效期为5分钟)");
            return mail.send();//发送
        } catch (EmailException e) {
            e.printStackTrace();
            return "error";
        }
    }

}