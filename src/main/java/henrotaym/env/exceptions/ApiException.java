package henrotaym.env.exceptions;

import henrotaym.env.enums.exceptions.ExceptionType;
import java.time.LocalDateTime;
import java.util.HashMap;
import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record ApiException(
    String message,
    HttpStatus status,
    LocalDateTime timestamp,
    ExceptionType type,
    HashMap<String, ?> data,
    StackTraceElement[] stackTrace) {}
