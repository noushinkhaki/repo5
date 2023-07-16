package net.betvictor.loripsum.controller;

import net.betvictor.loripsum.exception.InvalidParameterException;
import net.betvictor.loripsum.model.ErrorResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    private static final Logger ERRLOGGER = LogManager.getLogger(ErrorHandler.class);

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException exception, HttpHeaders headers, HttpStatus status, WebRequest request
    ) {
        ERRLOGGER.error(exception.getMessage());

        return ResponseEntity.badRequest().body(
                ErrorResponse.generateErrResponse("Missing parameter", exception.getMessage())
        );
    }

    /**
     * Handle missing "l" parameter.
     *  @return ResponseEntity with message about invalid "l" value
     */
    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<Object> handleMissingLParam(InvalidParameterException exception) {
        ERRLOGGER.error(exception.getMessage());

        return ResponseEntity.badRequest().body(
                ErrorResponse.generateErrResponse("Invalid \"l\" parameter", exception.getMessage())
        );
    }

}
