package com.digitalfemsa.tit.lending.job.template.command;

import static com.digitalfemsa.tit.lending.job.template.model.JobSchedulingStatus.BAD;
import static com.digitalfemsa.tit.lending.job.template.model.JobSchedulingStatus.GOOD;

import com.digitalfemsa.tit.lending.job.template.configuration.JobSchedulingProperties;
import com.digitalfemsa.tit.lending.job.template.dynamodb.repository.CreditApplicationRepository;
import com.digitalfemsa.tit.lending.job.template.model.JobSchedulingDetail;
import com.digitalfemsa.tit.lending.lib.logging.component.BeanLoggingComponent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.List;

/**
 * Command: This class will only have a method to execute the job process.
 */
@Component
@AllArgsConstructor
public class ExampleCommand {

    /**
     * Credit application repository.
     */
    private final CreditApplicationRepository repository;

    /**
     * Update pre-offer status properties.
     */
    private final JobSchedulingProperties properties;

    /**
     * Bean Logging Component.
     */
    private final BeanLoggingComponent beanLoggingComponent;

    /**
     * Execute method.
     */
    public void execute() {
        var jobDetail = JobSchedulingDetail.builder().jobStatus(GOOD).jobMessage(INITIALIZED).build();
        beanLoggingComponent.logBean(jobDetail);
        beanLoggingComponent.logBean(properties);
        try {
            final var limitTime = getIntant(properties.getTimeUnit(), properties.getTimeToBeUpdated());
            final var records = repository.findCreditApplicationByStatusAndCreatedDatetime(properties.getStatus(), limitTime);
            jobDetail.setJobMessage(String.format(FOUND, records.map(List::size).orElse(0)));
            beanLoggingComponent.logBean(jobDetail);
            records.ifPresent(items -> repository.updateNewStatusCreditApplication(items, properties.getNewStatus()));
        } catch (Exception ex) {
            jobDetail.setJobStatus(BAD);
            jobDetail.setJobMessage(ex.getMessage());
            beanLoggingComponent.logBean(jobDetail);
        } finally {
            jobDetail.setJobMessage(COMPLETED);
            beanLoggingComponent.logBean(jobDetail);
        }
    }

    /**
     * Get limit time to update pre-offer status.
     * @param unitTime unit of time.
     * @param timeToBeUpdated time to be updated.
     * @return limit time to update pre-offer status.
     */
    private Instant getIntant(final String unitTime, final Integer timeToBeUpdated) {
        final var instant = Instant.now();
        switch (unitTime) {
            case "MINUTES":
                return instant.minusSeconds(timeToBeUpdated * 60);
            case "HOURS":
                return instant.minusSeconds(timeToBeUpdated * 60 * 60);
            case "DAYS":
                return instant.minusSeconds(timeToBeUpdated * 60 * 60 * 24);
            case "WEEKS":
                return instant.minusSeconds(timeToBeUpdated * 60 * 60 * 24 * 7);
            case "MONTHS":
                return instant.minusSeconds(timeToBeUpdated * 60 * 60 * 24 * 30);
            case "YEARS":
                return instant.minusSeconds(timeToBeUpdated * 60 * 60 * 24 * 365);
            default:
                return instant;
        }
    }

    /**
     * Initialized message.
     */
    private static final String INITIALIZED = "Job Started: Update Pre-Offer Status";

    /**
     * Found message.
     */
    private static final String FOUND = "Found %s records to be updated";

    /**
     * Completed message.
     */
    private static final String COMPLETED = "Job Completed: Update Pre-Offer Status";

}
