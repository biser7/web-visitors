package com.webvisitors.service;

import com.webvisitors.model.VisitorCount;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

import java.util.List;

public interface VisitorService {
    List<VisitorCount> getVisitorsStatistic();
    void runReadFileJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException,
            JobParametersInvalidException, JobRestartException;
    void removeAllRecords();
}
