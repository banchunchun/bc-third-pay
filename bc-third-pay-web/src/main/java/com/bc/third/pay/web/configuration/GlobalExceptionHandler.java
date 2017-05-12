package com.bc.third.pay.web.configuration;

import com.bc.cloud.feign.vo.error.ParamError;
import com.bc.cloud.feign.vo.error.PermissionError;
import com.bc.cloud.feign.vo.error.ResourceError;
import com.bc.cloud.feign.vo.error.SystemError;
import com.bc.cloud.feign.vo.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-05-11
 * Time:  下午 3:40.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
@RestControllerAdvice(basePackages = {"com.bc.ebiz.user.web"})
public class GlobalExceptionHandler {


    @ExceptionHandler(value = {Exception.class,Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse exceptionHandler(Exception e){
        ErrorResponse response = ErrorResponse.newInstance();
        response.setBizCode(SystemError.SYSTEM_ERROR);
        response.setMsg(String.format("系统错误：%s",e.getMessage()));
        return response;
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse exception400Handler(Exception e){
        ErrorResponse response = ErrorResponse.newInstance();
        response.setBizCode(ParamError.PARAM_INCORRECT);
        response.setMsg(String.format("参数错误：%s",e.getMessage()));
        return response;
    }

    @ExceptionHandler(value = {AuthorizationServiceException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse exception401Handler(Exception e){
        ErrorResponse response = ErrorResponse.newInstance();
        response.setBizCode(PermissionError.PERMISSION_UNAUTHorized);
        response.setMsg(String.format("权限错误：%s",e.getMessage()));
        return response;
    }

    @ExceptionHandler(value = {NoHandlerFoundException.class, HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse exception404Handler(Exception e){
        ErrorResponse response = ErrorResponse.newInstance();
        response.setBizCode(ResourceError.RESOURCE_NOT_FOUND);
        response.setMsg(String.format("404：%s",e.getMessage()));
        return response;
    }
}
