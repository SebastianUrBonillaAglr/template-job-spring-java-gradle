package com.digitalfemsa.tit.lending.job.template.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties for update pre-offer status.
 */
@Data
@ConfigurationProperties
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobSchedulingProperties {

    /**
     * Pre-Offer status.
     */
    @Value("${jobScheduling.status.old}")
    private String status;

    /**
     * New status.
     */
    @Value("${jobScheduling.status.new}")
    private String newStatus;

    /**
     * Unit of time.
     */
    @Value("${jobScheduling.createdDatetime.timeUnit}")
    private String timeUnit;

    /**
     * Time to be updated.
     */
    @Value("${jobScheduling.createdDatetime.timeToBeUpdated}")
    private Integer timeToBeUpdated;

}
