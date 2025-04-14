package henrotaym.env.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.springframework.http.HttpStatus;

import henrotaym.env.enums.exceptions.ExceptionType;
import lombok.Builder;

@Builder
public record ApiException(
    String message,
    HttpStatus status,
    LocalDateTime timestamp,
    ExceptionType type,
    HashMap<String, ?> data,
    StackTraceElement[] stackTrace
) {}
