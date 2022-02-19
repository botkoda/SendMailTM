package com.botkoda.sendMail.prepareToSend;

import com.botkoda.sendMail.interfaces.Connectuble;
import com.botkoda.sendMail.interfaces.Sendable;
import com.botkoda.sendMail.interfacesClass.KeyForMsg;
import com.botkoda.sendMail.interfacesClass.SendMail;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReadyToSend {
    Connectuble connectuble;
    KeyForMsg keyForMsg;
    private final Logger log = LoggerFactory.getLogger(ReadyToSend.class);

    public ReadyToSend(Connectuble conn,KeyForMsg keyForMsg) {
        this.connectuble = conn;
        this.keyForMsg=keyForMsg;
    }

    public void start(String sql, String sqlProc) {
        String from = "accountmanager@udmurtneft.ru";
        GetLetterFromDB getLetterFromDB = new GetLetterFromDB();
        Map<Integer, Letter> letterMap = getLetterFromDB.getFromDB(sql, sqlProc, connectuble,keyForMsg);
        Sendable sendMail = new SendMail();
        letterMap.forEach((well_id, letter) -> {

            try {
                Thread.sleep(10000); //спим 10 секунд иначе почтовый сервер ругается на частую отправку
                String answer=sendMail.send(letter.getTo(), letter.getMsg(), letter.getSubject(), from);
                log.debug(answer, letter.getSubject(), well_id, letter.getTo());
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        });

    }
}
