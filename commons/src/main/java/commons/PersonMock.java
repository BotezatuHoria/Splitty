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

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PersonMock {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long id;

	public String firstName;
	public String lastName;

	@SuppressWarnings("unused")
	private PersonMock() {
		// for object mapper
	}

	/**
	 * Person class.
	 * @param firstName - firstname
	 * @param lastName - lastname
	 */
	public PersonMock(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	/**
	 * Equals method.
	 * @param obj - object to compare with
	 * @return - true if the objects are equal, false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	/**
	 * Hashcode function.
	 * @return - hash representation of the project
	 */
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	/**
	 * ToString method for the PersonMock class.
	 * @return - string representation of all properties.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
	}
}