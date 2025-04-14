package henrotaym.env.http.resources.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.springframework.http.HttpStatus;

import henrotaym.env.enums.exceptions.ExceptionType;

public record ApiExceptionResource(
    String message,
    HttpStatus status,
    LocalDateTime timestamp,
    ExceptionType type,
    HashMap<String, ?> data
) {}
