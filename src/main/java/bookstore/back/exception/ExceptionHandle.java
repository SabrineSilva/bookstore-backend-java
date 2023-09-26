package bookstore.back.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionHandle extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleCustomException(BusinessException error, WebRequest request){
        ErrorException errorResponse = new ErrorException(HttpStatus.BAD_REQUEST.value(), error.getMessage());
        return handleExceptionInternal(error, errorResponse, new HttpHeaders(),HttpStatus.BAD_REQUEST, request);
    }

}
