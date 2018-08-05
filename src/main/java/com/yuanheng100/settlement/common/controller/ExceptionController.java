package com.yuanheng100.settlement.common.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionController
{

    private Logger logger = Logger.getLogger(ExceptionController.class);

    @ResponseBody
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Throwable throwable)
    {
        logger.error("程序内部出现异常", throwable);
        return "系统内部错误:" + throwable.getMessage();
    }
}
