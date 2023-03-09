package dev.cwute.messagingapp.config;

import dev.cwute.messagingapp.exception.UnauthorizedUser;
import dev.cwute.messagingapp.exception.UserNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFound.class)
    public String handleException(Exception e) {
        log.error("Exception occurred: {}", e.getMessage());
        return "User not found";
    }

    @ExceptionHandler(UnauthorizedUser.class)
    public String handleException2(Exception e) {
        log.error("Exception occurred: {}", e.getMessage());
        return "Unauthorized user";
    }

}
