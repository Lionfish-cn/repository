package com.code.repository.email.controller;

import com.code.repository.util.DateUtil;
import com.code.repository.email.info.MailInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.search.AndTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SentDateTerm;
import java.util.Calendar;
import java.util.Properties;

@RestController
@RequestMapping("/email")
public class EmailController {

    @RequestMapping("/parse")
    public void parseEmail() {
        //配置邮箱信息
        String username = "sdairport@headyonder.com";
        String password = "Hdy1234567";
        String host = "pop.exmail.qq.com";//"imap.exmail.qq.com";

        try {
            //配置接口信息
            Properties properties = new Properties();
            properties.setProperty("mail.transport.protocol", "pop3");
            properties.setProperty("mail.pop.host", host);
            properties.setProperty("mail.pop.auth", "true");
            properties.setProperty("mail.pop.port", "995");
            properties.setProperty("mail.pop.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            properties.setProperty("mail.pop.socketFactory.fallback", "false");
            properties.setProperty("mail.pop.socketFactory.port", "995");
            properties.setProperty("mail.pop.ssl.enable", "true");
            properties.setProperty("mail.debug", "true");

            Session s = Session.getDefaultInstance(properties);
            Store store = s.getStore("pop3");
            store.connect(host, username, password);

            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_WRITE);

            Calendar c = Calendar.getInstance();
            c.set(Calendar.DATE, c.get(Calendar.DATE) - 1);
            String d = DateUtil.convertDateToString(c.getTime(), "yyyy-MM-dd");

            String sDate = d +" 00:00:00";
            String eDate = d +" 23:59:59";

            SearchTerm searchTermge = new SentDateTerm(ComparisonTerm.GE,DateUtil.convertStringToDate(sDate,"yyyy-MM-dd HH:mm:ss"));
            SearchTerm searchTermle = new SentDateTerm(ComparisonTerm.LE,DateUtil.convertStringToDate(eDate,"yyyy-MM-dd HH:mm:ss"));
            SearchTerm searchTerm = new AndTerm(searchTermge,searchTermle);

            Message[] messages = folder.search(searchTerm);

            loadMessage(messages);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadMessage(Message[] messages) {
        try{
            for (Message message : messages) {
                if(!message.getFolder().isOpen())
                    message.getFolder().open(Folder.READ_WRITE);
                MailInfo mailInfo = new MailInfo((MimeMessage) message);
                mailInfo.getAttchmentParseExcel((Part) message);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
