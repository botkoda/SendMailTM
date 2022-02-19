package com.botkoda.sendMail.interfaces;

import com.botkoda.sendMail.interfacesClass.KeyForMsg;
import com.botkoda.sendMail.prepareToSend.Letter;

import java.util.Map;

public interface FromDbGetuble {
    Map<Integer, Letter> getFromDB(String sql, String sqlProc, Connectuble connecter, KeyForMsg keyForMsg);
}
