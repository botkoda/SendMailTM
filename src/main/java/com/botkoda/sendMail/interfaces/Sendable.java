package com.botkoda.sendMail.interfaces;

public interface Sendable {
    String send(String ToMail, String msg,String thema, String from);
}
