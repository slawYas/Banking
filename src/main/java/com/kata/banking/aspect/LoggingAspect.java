package com.kata.banking.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    // Pointcut pour les méthodes des contrôleurs
    @Pointcut("within(com.kata.banking.controller..*)")
    public void controllerMethods() {}

    // Pointcut pour les méthodes des services
    @Pointcut("within(com.kata.banking.service..*)")
    public void serviceMethods() {}

    // Log début des méthodes
    @Before("controllerMethods() || serviceMethods()")
    public void logMethodStart(JoinPoint joinPoint) {
        log.info("{} - Démarrage de la méthode {}", joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName());
    }

    // Log fin des méthodes sans exception
    @AfterReturning("controllerMethods() || serviceMethods()")
    public void logMethodEnd(JoinPoint joinPoint) {
        log.info("{} - Fin de la méthode {}", joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName());
    }

    // Log exceptions
    @AfterThrowing(pointcut = "controllerMethods() || serviceMethods()", throwing = "exception")
    public void logMethodException(JoinPoint joinPoint, Throwable exception) {
        log.error("Exception dans {}.{}() avec cause = {}",
                joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().getName(),
                exception.getMessage() != null ? exception.getMessage() : "NULL");
    }
}
