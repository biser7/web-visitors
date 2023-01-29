package com.webvisitors.service;

import com.webvisitors.model.SourceCount;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

import java.util.List;

public interface VisitorService {
    List<SourceCount> getSourceUniqueVisitors();

    void runReadFileJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException,
            JobParametersInvalidException, JobRestartException;

    void removeAllRecords();
}
