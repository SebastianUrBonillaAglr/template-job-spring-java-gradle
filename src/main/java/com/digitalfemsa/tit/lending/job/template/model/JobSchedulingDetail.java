package com.digitalfemsa.tit.lending.job.template.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

/**
 * Job scheduling detail.
 */
@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobSchedulingDetail {

    /**
     * Job scheduling status.
     */
    private JobSchedulingStatus jobStatus;

    /**
     * Job scheduling message.
     */
    private String jobMessage;

}
