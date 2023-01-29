package com.webvisitors.config;

import com.webvisitors.model.Visitor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    private static final int CONCURRENCY_LIMIT = 10;
    private static final int CHUNK_SIZE = 50;
    @Value("${csv.file.path}")
    private String csvResourceFileName;
    @Value("${csv.file.delimiter}")
    private String csvDelimiter;

    /**
     * Will be used for building of Job for our operation
     * @return FlatFileItemReader
     */
    @Bean
    public FlatFileItemReader<Visitor> itemReader() {
        FlatFileItemReader<Visitor> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new ClassPathResource(csvResourceFileName));
        flatFileItemReader.setName("visitors-csv-reader");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(lineMapper());
        return flatFileItemReader;
    }

    /**
     * Method which return LineMapper needed for our ItemReader
     * @return LineMapper
     */
    @Bean
    public LineMapper<Visitor> lineMapper() {
        DefaultLineMapper<Visitor> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(csvDelimiter);
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("email", "phone", "source");

        BeanWrapperFieldSetMapper<Visitor> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Visitor.class);
        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);
        return defaultLineMapper;
    }

    /**
     *
     * @param jobBuilderFactory get from Spring
     * @param stepBuilderFactory get from Spring
     * @param itemReader implemented for CSV read
     * @param itemProcessor implemented to put into lowercase
     * @param itemWriter implemented to write to DB
     * @return Job needed for the operation
     */
    @Bean
    public Job job(final JobBuilderFactory jobBuilderFactory, final StepBuilderFactory stepBuilderFactory,
                   final ItemReader<Visitor> itemReader, final ItemProcessor<Visitor, Visitor> itemProcessor,
                   final ItemWriter<Visitor> itemWriter) {

        Step step = stepBuilderFactory.get("visitors-parse-step")
                .<Visitor, Visitor>chunk(CHUNK_SIZE)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .taskExecutor(taskExecutor())
                .build();

        return jobBuilderFactory.get("visitors-job")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    /**
     * Will create a bean of TaskExecutor, which allow to run several Threads
     * @return TaskExecutor
     */
    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor sate = new SimpleAsyncTaskExecutor();
        sate.setConcurrencyLimit(CONCURRENCY_LIMIT);
        return sate;
    }
}
