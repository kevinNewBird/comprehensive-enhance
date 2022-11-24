package com.base.util.email;

import lombok.*;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * description  EmailUtil <BR>
 * <p>
 * author: zhao.song
 * date: created in 8:37  2022/8/11
 * company: TRS信息技术有限公司
 * version 1.0
 */
public class EmailUtil {


    /**
     * description   发送纯文本邮件  <BR>
     *
     * @param :
     * @return
     * @author zhao.song  2022/8/11  9:09
     */
    public static boolean sendSimpleEmail(Payload payload) throws EmailException {
        if (Objects.isNull(payload)) {
            System.out.println("负载参数为空！");
            return false;
        }
        Email email = new SimpleEmail();
        email.setHostName(payload.getHost());
        email.setSmtpPort(payload.getPort());
        email.setAuthenticator(new DefaultAuthenticator(payload.getFromEmail(), payload.getFromPassword()));
        email.setSSLOnConnect(true);
        email.setFrom(payload.getFromEmail());
        email.setSubject(payload.getTitle());
        email.setMsg(payload.getMsg());
        email.addTo(payload.getToEmails());
        email.send();
        return true;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Payload {
        // 发送端邮件相关参数
        private String host;
        private int port;
        private String fromEmail;
        private String fromPassword;


        // 接收端相关邮件参数
        private String title;
        private String msg;
        private String[] toEmails;

    }


    public static void main(String[] args) throws EmailException {
        Payload payload = Payload.builder()
                .host("smtp.qq.com")
                .port(465)
                .fromEmail("1170500835@qq.com")
                .fromPassword("lnzreqwekisibaac")
                .toEmails(Stream.of("song0586@126.com", "zhaosong@vastdata.com.cn").toArray(String[]::new))
                .title("TestMail2")
                .msg("This is a test mail 22222... :-)")
                .build();
        EmailUtil.sendSimpleEmail(payload);
    }
}
