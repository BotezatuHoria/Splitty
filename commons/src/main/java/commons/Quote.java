/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package commons;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Quote {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long id;

	@OneToOne(cascade = CascadeType.PERSIST)
	public PersonMock person;
	public String quote;

	/**
	 * Blank constructor for the quote class.
	 */
	@SuppressWarnings("unused")
	public Quote() {
		// for object mappers
	}

	/**
	 * Constructor for the Quote.
	 * @param person - person
	 * @param quote - quote
	 */
	public Quote(PersonMock person, String quote) {
		this.person = person;
		this.quote = quote;
	}

	/**
	 * Equals method for quote class.
	 * @param obj - to compare with.
	 * @return - true if the object are equal, false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	/**
	 * Hashcode function.
	 * @return - hashcode representation of the object.
	 */
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	/**
	 * ToString function.
	 * @return - string representation of thw quote class.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
	}
}