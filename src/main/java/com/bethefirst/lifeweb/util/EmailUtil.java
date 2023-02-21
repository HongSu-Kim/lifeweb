package com.bethefirst.lifeweb.util;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class EmailUtil {

    //참고문서 : https://commons.apache.org/proper/commons-email/apidocs/org/apache/commons/mail/



    // Mail Server 설정
    private static String UTF_8 = "utf-8";
    private static String HOST_SMTP = "smtp.naver.com";
    private static String HOST_SMTP_ID = "네이버계정";
    private static String HOST_SMTP_PWD = "계정비밀번호";
    private static int PORT = 465; // 네이버 메일 이용시 포트는 465
    private static String FROMEMAIL = HOST_SMTP_ID + "@naver.com";

    // 보내는 사람 EMail

    private String fromName; //보내는 사람 이름
    private String subject; //발송 이메일 제목
    private String contentDiv; //발송 이메일 본문 내용

    /**
     * @param fromName 보내는사람 이름
     * @param contentDiv 메일 본문내용 HTML로 작성
     * @param subject 메일 제목
     * @param toEmail 받는사람 이메일
     */
    public void sendEmail(String fromName, String contentDiv, String subject, String toEmail)  {
        HtmlEmail email = setEmailInfo(fromName, contentDiv, subject);//이메일 설정


        try {
            email.addTo(toEmail); //받는사람 셋팅
            email.setFrom(FROMEMAIL, fromName, UTF_8); //보내는사람 셋팅
            email.setSubject(subject); // 이메일 제목 셋팅
            email.setHtmlMsg(contentDiv); //이메일 본문내용 셋팅
            email.send(); //이메일 보내기
        } catch (EmailException e) {
            throw new IllegalArgumentException("잘못된 인코딩 또는 이메일 주소가 잘못 되었습니다.", e);
        }

    }


    /** 보내는 사람 이메일을 세팅합니다 */
    private HtmlEmail setEmailInfo(String fromName, String contentDiv, String subject) {
        this.fromName = fromName;
        this.subject = subject;
        this.contentDiv = contentDiv;

        HtmlEmail email = new HtmlEmail();
        email.setDebug(true); //debug 정보 표시를 위한 셋팅
        email.setCharset(UTF_8); //인코딩 설정
        email.setSSLOnConnect(true); //SMTP 전송시 SSL/TLS 암호화를 활성화 할지 여부를 설정합니다.
        email.setHostName(HOST_SMTP); //보내는 메일 서버의 호스트 이름 설정
        email.setSmtpPort(PORT); //메일서버 포트 설정

        email.setAuthentication(HOST_SMTP_ID, HOST_SMTP_PWD);
        email.setStartTLSEnabled(true); //보안을 위한 TLS 방식 활성화
        return email;
    }


}
