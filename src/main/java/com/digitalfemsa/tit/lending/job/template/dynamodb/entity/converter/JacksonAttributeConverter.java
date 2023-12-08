package com.digitalfemsa.tit.lending.job.template.dynamodb.entity.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.io.UncheckedIOException;

/**
 * Jackson Attribute Converter.
 * @param <T>
 */
public class JacksonAttributeConverter<T> implements AttributeConverter<T> {

	/**
	 * The TypeReference instance used to specify the
	 * type of objects this converter handles.
	 */
	private final TypeReference<T> type;

	/**
	 * The shared ObjectMapper instance used for
	 * JSON serialization and deserialization.
	 */
	private static final ObjectMapper MAPPER = new ObjectMapper();

	static {
		MAPPER.configure(
				DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				true
		);
	}

	/**
	 * Constructs a new JacksonAttributeConverter
	 * with the specified TypeReference.
	 *
	 * @param type The TypeReference specifying
	 * the target type for conversion.
	 */
	public JacksonAttributeConverter(final TypeReference<T> type) {
		this.type = type;
	}

	/**
	 * Constructs a new JacksonAttributeConverter
	 * with the specified Class.
	 *
	 * @param input The Class specifying the target
	 * type for conversion.
	 */
	@Override
	public AttributeValue transformFrom(final T input) {
		try {
			return AttributeValue
				       .builder()
				       .s(MAPPER.writeValueAsString(input))
				       .build();
		} catch (JsonProcessingException e) {
			throw new UncheckedIOException(
					"Unable to serialize object", e);
		}
	}

	/**
	 * Convert the provided {@link AttributeValue} into a Java object.
	 * @param input The AttributeValue to convert.
	 * @return The converted Java object.
	 */
	@Override
	public T transformTo(final AttributeValue input) {
		try {
			return MAPPER.readValue(input.s(), this.type);
		} catch (JsonProcessingException e) {
			throw new UncheckedIOException(
					"Unable to parse object", e);
		}
	}

	/**
	 * The type supported by this converter.
	 * @return The type supported by this converter.
	 */
	@Override
	public EnhancedType type() {
		return EnhancedType.of(this.type.getClass());
	}

	/**
	 * The AttributeValueType supported by this converter.
	 * @return The AttributeValueType supported by this converter.
	 */
	@Override
	public AttributeValueType attributeValueType() {
		return AttributeValueType.S;
	}

}
