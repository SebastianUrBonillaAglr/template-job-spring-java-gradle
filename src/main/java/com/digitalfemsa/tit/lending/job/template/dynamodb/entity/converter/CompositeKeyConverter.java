package com.digitalfemsa.tit.lending.job.template.dynamodb.entity.converter;

import com.digitalfemsa.tit.lending.job.template.dynamodb.entity.CompositeKey;
import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

/**
 * Composite key converter.
 */
public class CompositeKeyConverter implements AttributeConverter<CompositeKey> {

	/**
	 * Convert the provided Java object into an {@link AttributeValue}.
	 * This will raise a {@link RuntimeException} if the
	 * conversion fails, or the input is null.
	 *
	 * <p>
	 * Example:
	 * <pre>
	 * {@code
	 * InstantAsStringAttributeConverter converter =
	 * 			InstantAsStringAttributeConverter.create();
	 * assertEquals(converter.transformFrom(Instant.EPOCH),
	 *              EnhancedAttributeValue.fromString(
	 *              "1970-01-01T00:00:00Z").toAttributeValue());
	 * }
	 * </pre>
	 *
	 * @param input
	 */
	@Override
	public AttributeValue transformFrom(final CompositeKey input) {
		return AttributeValue.fromS(input.toString());
	}

	/**
	 * Convert the provided {@link AttributeValue} into a Java object.
	 * This will raise a {@link RuntimeException} if the
	 * conversion fails, or the input is null.
	 *
	 * <p>
	 * <pre>
	 * Example:
	 * {@code
	 * InstantAsStringAttributeConverter converter =
	 * 		InstantAsStringAttributeConverter.create();
	 *
	 * assertEquals(
	 * 		converter.transformTo(
	 * 			EnhancedAttributeValue.fromString(
	 * 			"1970-01-01T00:00:00Z").toAttributeValue()
	 * 		), Instant.EPOCH);
	 * }
	 * </pre>
	 *
	 * @param input
	 */
	@Override
	public CompositeKey transformTo(final AttributeValue input) {
		return CompositeKey.fromString(input.s());
	}

	/**
	 * The type supported by this converter.
	 */
	@Override
	public EnhancedType<CompositeKey> type() {
		return EnhancedType.of(CompositeKey.class);
	}

	/**
	 * The {@link AttributeValueType} that a converter
	 * stores and reads values from DynamoDB via
	 * the {@link AttributeValue} class.
	 */
	@Override
	public AttributeValueType attributeValueType() {
		return AttributeValueType.S;
	}

}
