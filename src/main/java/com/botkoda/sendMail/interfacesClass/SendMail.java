package com.botkoda.sendMail.interfacesClass;

import com.botkoda.sendMail.interfaces.Sendable;
import com.botkoda.sendMail.prepareToSend.ReadyToSend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class SendMail implements Sendable {
    private final Logger log = LoggerFactory.getLogger(ReadyToSend.class);
    @Override
    public String send(String mailTo, String msg, String thema, String from) {
        String answer;
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.host", "10.226.3.43");
        properties.put("mail.smtp.port", "25");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ehlo", "false");

        String user = "uneft\\accountmanager";
        String pass = "Ghjdthrf02";
        String fromMail = from;
        String subj = thema;
        String meseg = msg;
        //списко адресов
        String recepientList = mailTo;//+",dmshagaliev@udmurtneft.ru" ;

        InternetAddress[] recepientAdress = new InternetAddress[0];
        try {
            recepientAdress = InternetAddress.parse(recepientList);
        } catch (AddressException e) {
          //  e.printStackTrace();
            log.error(e.getMessage());
        }

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });

        Message message = prepareMessage(session, recepientAdress, fromMail, subj, meseg);
        try {

            Transport.send(message);
            System.out.println("send"+ Thread.currentThread().getName());
            answer="Отправленно: {}::{}::{}";
        } catch (Exception e) {
           // e.printStackTrace();
            log.error("ERROR!_Transport.send(message) {}", e.getMessage());
            answer="ОШИБКА: {}::{}::{}";
        }
        return answer;

    }

    private Message prepareMessage(Session session, InternetAddress[] recepientAdress, String fromMail, String subj, String meseg) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromMail));
            message.setRecipients(Message.RecipientType.TO, recepientAdress);
            message.setSubject(subj);
//            message.setText(meseg);
            message.setContent(meseg, "text/html;charset=utf-8");
            return message;
        } catch (Exception e) {
          //  e.printStackTrace();
            log.error("ERROR!_prepareMessage {}",e.getMessage());

        }
        return null;
    }
}
