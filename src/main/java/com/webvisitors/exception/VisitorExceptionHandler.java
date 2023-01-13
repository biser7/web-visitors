package com.webvisitors.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class VisitorExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(VisitorExceptionHandler.class);

    @ExceptionHandler({JobInstanceAlreadyCompleteException.class, JobExecutionAlreadyRunningException.class,
            JobParametersInvalidException.class, JobRestartException.class})
    public final ModelAndView handleAllExceptions(Exception ex) {

        String errorMessage = (ex != null ? ex.getMessage() : "Unknown error");
        LOGGER.error(errorMessage);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("message", errorMessage);
        return modelAndView;
    }
}
