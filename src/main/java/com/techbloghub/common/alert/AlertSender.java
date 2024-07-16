package com.techbloghub.common.alert;

/**
 * 알림 메시지를 전송하는 인터페이스
 */
public interface AlertSender {

    void send(String message);
}
