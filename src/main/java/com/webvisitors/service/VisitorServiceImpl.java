package com.webvisitors.service;

import com.webvisitors.model.VisitorCount;
import com.webvisitors.repository.VisitorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VisitorServiceImpl implements VisitorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(VisitorServiceImpl.class);
    private static final int FIRST_PAGE = 0;
    private final VisitorRepository visitorRepository;
    private final JobLauncher jobLauncher;
    private final Job job;
    @Value("${page.size}")
    private int pageSize;

    public VisitorServiceImpl(VisitorRepository visitorRepository, JobLauncher jobLauncher, Job job) {
        this.visitorRepository = visitorRepository;
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    @Override
    public List<VisitorCount> getVisitorsStatistic() {
        Pageable paging = PageRequest.of(FIRST_PAGE, pageSize);
        return visitorRepository.getVisitorsStatistic(paging);
    }

    @Override
    public void runReadFileJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException,
            JobParametersInvalidException, JobRestartException {

        Map<String, JobParameter> parametersMap = new HashMap<>();
        parametersMap.put("currentTime", new JobParameter(System.currentTimeMillis()));
        JobParameters parameters = new JobParameters(parametersMap);
        JobExecution jobExecution = jobLauncher.run(job, parameters);

        LOGGER.info("JobExecution: {}", jobExecution.getStatus());
    }

    @Override
    public void removeAllRecords() {
        visitorRepository.deleteAll();
    }
}
