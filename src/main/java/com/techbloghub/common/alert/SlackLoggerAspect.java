package com.techbloghub.common.alert;

import static com.techbloghub.common.alert.ExceptionWrapper.extractExceptionWrapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * {@code @SlackLogger} 어노테이션이 적용된 메서드에 대한 Aspect
 * <p>
 * 예외와 실패한 현재 사용자의 정보와 알림 이벤트를 Slack으로 로그 전송
 */
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class SlackLoggerAspect {

    private final AlertSender alertSender;

    /**
     * {@code @SlackLogger} 어노테이션이 적용된 메서드 실행 전, 예외 정보나 실패한 알림 이벤트를 로그로 전송
     * 예외의 경우 예외 래퍼(ExceptionWrapper)를 추출하여 알림을 전송
     * @param joinPoint Aspect가 적용된 메서드의 조인 포인트
     */

    @Before("@annotation(com.techbloghub.common.alert.SlackLogger)")
    public void sendLogForError(final JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args.length != 1) {
            log.warn("Slack Logger Failed : Invalid Used");
            return;
        }

        if (args[0] instanceof Exception) {
            ExceptionWrapper exceptionWrapper = extractExceptionWrapper((Exception) args[0]);
            alertSender.send(MessageGenerator.generate(exceptionWrapper));
            return;
        }

        if (args[0] instanceof SlackAlarmFailedEvent) { // TODO: Event 발행 추가 
            alertSender.send(
                MessageGenerator.generateFailedAlarmMessage((SlackAlarmFailedEvent) args[0]));
        }
    }
}
