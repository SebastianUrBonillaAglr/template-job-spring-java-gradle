package com.digitalfemsa.tit.lending.job.template.dynamodb.repository;

import com.digitalfemsa.tit.lending.job.template.dynamodb.entity.CreditApplicationDetailEntity;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Credit application repository.
 */
public interface CreditApplicationRepository {

    /**
     * Find all credit application by status.
     * @param status status of the credit application.
     * @param createdDateTime created datetime of credit application.
     * @return credit applications list optional class.
     */
    Optional<List<CreditApplicationDetailEntity>> findCreditApplicationByStatusAndCreatedDatetime(String status, Instant createdDateTime);

    /**
     * Update new status in credit application.
     * @param entities credit applications to update.
     * @param newStatus new status to be updated.
     */
    void updateNewStatusCreditApplication(List<CreditApplicationDetailEntity> entities, String newStatus);

}
