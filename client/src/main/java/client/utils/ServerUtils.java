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
package client.utils;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

import commons.Event;
import commons.Person;
import commons.Transaction;
import org.glassfish.jersey.client.ClientConfig;

import commons.Quote;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ServerUtils {

	private static final String SERVER = "http://localhost:8080/";

	/**
	 * Method for getting the quotes the hard way.
	 * @throws IOException - IO exception
	 * @throws URISyntaxException - Syntax exception
	 */
	public void getQuotesTheHardWay() throws IOException, URISyntaxException {
		var url = new URI("http://localhost:8080/api/quotes").toURL();
		var is = url.openConnection().getInputStream();
		var br = new BufferedReader(new InputStreamReader(is));
		String line;
		while ((line = br.readLine()) != null) {
			System.out.println(line);
		}
	}

	/**
	 * Method for retrieving the quotes.
	 * @return - a list of quotes
	 */
	public List<Quote> getQuotes() {
		return ClientBuilder.newClient(new ClientConfig()) //
				.target(SERVER).path("api/event") //
				.request(APPLICATION_JSON) //
				.accept(APPLICATION_JSON) //
                .get(new GenericType<List<Quote>>() {});
	}

	/**
	 * Add quote api call.
	 * @param quote - quote
	 * @return - quote
	 */
	public Quote addQuote(Quote quote) {
		return ClientBuilder.newClient(new ClientConfig()) //
				.target(SERVER).path("api/quotes") //
				.request(APPLICATION_JSON) //
				.accept(APPLICATION_JSON) //
				.post(Entity.entity(quote, APPLICATION_JSON), Quote.class);
	}

	public List<Event> getEvents() {
		return ClientBuilder.newClient(new ClientConfig()) //
				.target(SERVER).path("/api/event") //
				.request(APPLICATION_JSON) //
				.accept(APPLICATION_JSON) //
				.get(new GenericType<>() {
				});
	}

	public Set<Transaction> getTransactions(int id) {
		return ClientBuilder.newClient(new ClientConfig())
				.target(SERVER).path("api/event/" + id + "/expenses")
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.get(new GenericType<Set<Transaction>>() {});
	}

	public Set<Person> getPeopleInCurrentEvent(int id) {
		return ClientBuilder.newClient(new ClientConfig())
				.target(SERVER).path("api/event/" + id + "/people")
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.get(new GenericType<Set<Person>>() {});
	}

	public Transaction addTransactionToCurrentEvent(int idEvent, Transaction transaction) {

		ObjectMapper objectMapper = new ObjectMapper();

		objectMapper.registerModule(new JavaTimeModule());

		return ClientBuilder.newClient(new ClientConfig())
				.target(SERVER).path("api/event/" + idEvent + "/expenses/create")
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.post(Entity.entity(transaction, APPLICATION_JSON), Transaction.class);
	}


	/**
	 * This method adds the given event.
	 * @param event of the event you want to add
	 * @return
	 */
	public Event addEvent(Event event) {
		ObjectMapper objectMapper = new ObjectMapper();

		objectMapper.registerModule(new JavaTimeModule());

		return ClientBuilder.newClient(new ClientConfig())
				.target(SERVER).path("api/event/")
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.post(Entity.entity(event, APPLICATION_JSON), Event.class);
	}

	/**
	 * This method gets and event by id.
	 * @param eventID of the event you want to get
	 * @return
	 */
	public Event getEventByID(int eventID) {
		return ClientBuilder.newClient(new ClientConfig())
				.target(SERVER).path("api/event/" + eventID)
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.get(new GenericType<Event>() {});
	}
}