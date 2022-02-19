package com.botkoda.sendMail.prepareToSend;

import java.util.Objects;

public class Letter {
    private String to;
    private String from;
    private String subject;
    private String msg;

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    public String getSubject() {
        return subject;
    }

    public String getMsg() {
        return msg;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Letter letter = (Letter) o;
        return subject.equals(letter.subject) && msg.equals(letter.msg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, msg);
    }

    @Override
    public String toString() {
        return "Letter{" +
                "to='" + to + '\'' +
                ", from='" + from + '\'' +
                ", subject='" + subject + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
