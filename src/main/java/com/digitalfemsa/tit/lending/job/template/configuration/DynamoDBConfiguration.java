package com.digitalfemsa.tit.lending.job.template.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;
import java.util.Optional;

/**
 * Dynamo DB Configuration.
 */
@Configuration
public class DynamoDBConfiguration {

    /**
     * AWS: Region.
     */
    @Value("${aws.region}")
    private String awsRegion;

    /**
     * AWS: Endpoint url.
     */
    @Value("${aws.dynamodb.endpointUrl:#{null}}")
    private String endpointUrl;

    /**
     * Bean of DynamoDbClient class.
     * @return DynamoDbClient class.
     */
    @Bean
    public DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.builder()
                .region(Region.of(awsRegion))
                .credentialsProvider(DefaultCredentialsProvider.create())
                .endpointOverride(Optional.ofNullable(endpointUrl)
                        .map(URI::create)
                        .orElse(null))
                .build();
    }

    /**
     * Bean of DynamoDbEnhancedClient class.
     * @param dynamoDbClient DynamoDbClient class.
     * @return DynamoDbEnhancedClient class.
     */
    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient(final DynamoDbClient dynamoDbClient) {
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
    }

}
