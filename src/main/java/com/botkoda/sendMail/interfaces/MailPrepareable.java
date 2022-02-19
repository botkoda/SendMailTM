package com.botkoda.sendMail.interfaces;

import com.botkoda.sendMail.prepareToSend.Letter;

import java.util.List;
import java.util.Map;

public interface MailPrepareable {
    Map<String, List<String>> getSendler(Map<Integer, Letter> letterMap);
}
