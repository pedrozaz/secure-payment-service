package io.github.pedrozaz.paymentcore.infra.configuration;

import io.github.pedrozaz.paymentcore.domain.exception.WalletException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WalletException.class)
    public ProblemDetail handleWalletException(WalletException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        problemDetail.setTitle("Business Rule Violation");
        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleViolationException(MethodArgumentNotValidException e) {
        String errorMsg = e.getBindingResult().getFieldError() != null
                ? e.getBindingResult().getFieldError().getDefaultMessage()
                : "Invalid request content";

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, errorMsg);
        problemDetail.setTitle("Invalid Data");
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception e) {
        e.printStackTrace();
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        problemDetail.setTitle("Internal Server Error");
        return problemDetail;
    }
}
