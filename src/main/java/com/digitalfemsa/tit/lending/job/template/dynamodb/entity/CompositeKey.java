package com.digitalfemsa.tit.lending.job.template.dynamodb.entity;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Composite key.
 */
public class CompositeKey implements Comparable<CompositeKey>, Serializable {

	/**
	 * Delimiter String.
	 */
	public static final String DELIMITER = "#";

	/**
	 * Arguments array.
	 */
	private final String[] keyParts;

	/**
	 * All-args constructor.
	 * @param keyElements The individual components of the composite key.
	 */
	public CompositeKey(final String ...keyElements) {
		this.keyParts = keyElements;
	}

	/**
	 * toString method.
	 * @return string value.
	 */
	@Override
	public String toString() {
		return String.join(DELIMITER, keyParts);
	}

	/**
	 * compareTo method.
	 * @param compositeKey the object to be compared.
	 * @return integer value.
	 */
	@Override
	public int compareTo(final CompositeKey compositeKey) {
		return this.toString().compareTo(compositeKey.toString());
	}

	/**
	 * equals method.
	 * @param o object value.
	 * @return boolean value.
	 */
	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		CompositeKey that = (CompositeKey) o;
		return Arrays.equals(keyParts, that.keyParts);
	}

	/**
	 * hashCode method.
	 * @return integer value.
	 */
	@Override
	public int hashCode() {
		return Arrays.hashCode(keyParts);
	}

	/**
	 * fromString method.
	 * @param rawCompositeKey string value.
	 * @return CompositeKey object.
	 */
	public static CompositeKey fromString(final String rawCompositeKey) {
		return new CompositeKey(rawCompositeKey.split(DELIMITER));
	}

}
