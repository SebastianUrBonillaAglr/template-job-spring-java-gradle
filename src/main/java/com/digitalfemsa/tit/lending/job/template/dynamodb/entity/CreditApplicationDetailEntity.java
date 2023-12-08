package com.digitalfemsa.tit.lending.job.template.dynamodb.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

/**
 * Credit Application Detail Entity.
 * Primary Key: CAP.
 * Sort Key: CAD.
 */
@Data
@DynamoDbBean
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditApplicationDetailEntity extends Entity {

    /**
     * Primary key: CAP.
     */
    public static final String PARTITION_ENTITY_ID = EntityConstant.CREDIT_APPLICATION;

    /**
     * Sort key: CAD.
     */
    public static final String SORTING_ENTITY_ID = EntityConstant.CREDIT_APPLICATION_DETAILS;

    /**
     * Status.
     */
    private String status;

}
