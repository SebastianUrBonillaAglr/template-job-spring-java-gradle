package com.digitalfemsa.tit.lending.job.template.dynamodb;

import com.digitalfemsa.tit.lending.job.template.dynamodb.entity.CreditApplicationDetailEntity;
import com.digitalfemsa.tit.lending.job.template.dynamodb.entity.EntityConstant;
import com.digitalfemsa.tit.lending.job.template.dynamodb.repository.CreditApplicationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.TransactUpdateItemEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Credit application repository adapter.
 */
@Component
@AllArgsConstructor
public class CreditApplicationRepositoryAdapter implements CreditApplicationRepository {

    /**
     * DynamoDb Enhanced client.
     */
    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;

    /**
     * Find all credit application by status.
     * @param status status of the credit application.
     * @param createdDateTime created datetime of credit application.
     * @return credit applications list optional class.
     */
    @Override
    public Optional<List<CreditApplicationDetailEntity>> findCreditApplicationByStatusAndCreatedDatetime(
            final String status,
            final Instant createdDateTime
    ) {
        final var table = dynamoDbEnhancedClient.table(CREDIT_APPLICATION_TABLE, TableSchema.fromBean(CreditApplicationDetailEntity.class));
        final var expressionValues = new HashMap<String, AttributeValue>();
        expressionValues.put(SK_EXPRESSION_VALUE, AttributeValue.builder()
                .s(EntityConstant.CREDIT_APPLICATION_DETAILS)
                .build());
        expressionValues.put(CREATED_DATE_TIME_EXPRESSION_VALUE, AttributeValue.builder()
                .s(createdDateTime.toString())
                .build());
        expressionValues.put(STATUS_EXPRESSION_VALUE, AttributeValue.builder()
                .s(status)
                .build());
        final var result = table.scan(ScanEnhancedRequest.builder()
                        .filterExpression(Expression.builder()
                                .expression(SEARCH_EXPRESSION)
                                .expressionNames(SEARCH_EXPRESSION_NAMES)
                                .expressionValues(expressionValues)
                                .build())
                        .build())
                .items()
                .stream()
                .toList();
        return result.isEmpty() ? Optional.empty() : Optional.of(result);
    }

    /**
     * Update new status in credit application.
     * @param entities credit applications to update.
     * @param newStatus new status to be updated.
     */
    @Override
    public void updateNewStatusCreditApplication(
            final List<CreditApplicationDetailEntity> entities,
            final String newStatus
    ) {
        Optional.ofNullable(entities)
                .ifPresent(items -> {
                    final var table = dynamoDbEnhancedClient.table(CREDIT_APPLICATION_TABLE, TableSchema.fromBean(CreditApplicationDetailEntity.class));
                    items.forEach(item -> {
                            item.setStatus(newStatus);
                            item.setLastModifiedDateTime(Instant.now());
                            var transactionRequest = TransactUpdateItemEnhancedRequest.builder(CreditApplicationDetailEntity.class)
                                    .item(item)
                                    .ignoreNulls(true)
                                    .conditionExpression(Expression.builder()
                                            .expression(UPDATE_EXPRESSION)
                                            .build())
                                    .build();
                            dynamoDbEnhancedClient.transactWriteItems(t -> t.addUpdateItem(table, transactionRequest));
                        }
                    );
                });
    }

    /**
     * Credit application table.
     */
    private static final String CREDIT_APPLICATION_TABLE = "CreditApplication";

    /**
     * Update expression.
     */
    private static final String UPDATE_EXPRESSION = "attribute_exists(pk) AND attribute_exists(sk)";

    /**
     * Search expression.
     */
    private static final String SEARCH_EXPRESSION = "attribute_exists(#sk) AND attribute_exists(#createdAt) AND attribute_exists(#st) AND #sk = :sk AND #createdAt < :createdDateTime AND #st = :st";

    /**
     * Search expression names.
     */
    private static final Map<String, String> SEARCH_EXPRESSION_NAMES = Map.of(
            "#sk", "sk",
            "#createdAt", "createdDateTime",
            "#st", "status"
    );

    /**
     * SK expression value.
     */
    private static final String SK_EXPRESSION_VALUE = ":sk";

    /**
     * Created date time expression value.
     */
    private static final String CREATED_DATE_TIME_EXPRESSION_VALUE = ":createdDateTime";

    /**
     * Status expression value.
     */
    private static final String STATUS_EXPRESSION_VALUE = ":st";

}
