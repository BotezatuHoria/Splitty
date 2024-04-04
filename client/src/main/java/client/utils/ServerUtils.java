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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import commons.*;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import javafx.util.Pair;
import org.glassfish.jersey.client.ClientConfig;

import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

public class ServerUtils {

	private static String server = "http://localhost:8080/";

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

	public static void setServer(String server) {
		ServerUtils.server = server;
	}

	/**
	 * Method for retrieving the quotes.
	 * @return - a list of quotes
	 */
	public List<Quote> getQuotes() {
		return ClientBuilder.newClient(new ClientConfig()) //
				.target(server).path("api/event") //
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
				.target(server).path("api/quotes") //
				.request(APPLICATION_JSON) //
				.accept(APPLICATION_JSON) //
				.post(Entity.entity(quote, APPLICATION_JSON), Quote.class);
	}

	public List<Event> getEvents() {
		return ClientBuilder.newClient(new ClientConfig()) //
				.target(server).path("/api/event") //
				.request(APPLICATION_JSON) //
				.accept(APPLICATION_JSON) //
				.get(new GenericType<>() {
				});
	}

	/**
	 * Should return list of DebtCellData that represents how much people owe to each other.
	 * @param id id of the event
	 * @return list with debts to be displayed on DebtSettlementPage
	 */
	public List<DebtCellData> getOpenDebts(int id){

		List<Person> people = getPeopleInCurrentEvent(id);
		List<Pair<Person, Double>> peoplesToPay = peopleWithPositiveDebt(people);
		List<DebtCellData> debtsList = new ArrayList<>();

      for (Person person : people) {
        if (person.getDebt() < 0) {
			double debt = person.getDebt();
          	while(debt < 0 && !peoplesToPay.isEmpty()){
				  if(Math.abs(peoplesToPay.getLast().getValue()) <= Math.abs(debt)){
					  debt += peoplesToPay.getLast().getValue();
					  debtsList.add(new DebtCellData(person, peoplesToPay.getLast().getKey(), round(Math.abs(peoplesToPay.getLast().getValue()), 2)));
					  peoplesToPay.removeLast();
				  }else{
					  Pair<Person, Double> pair = peoplesToPay.getLast();
					  debtsList.add(new DebtCellData(person, peoplesToPay.getLast().getKey(),round(Math.abs(debt), 2)));
					  peoplesToPay.removeLast();
					  peoplesToPay.addLast(new Pair<>(pair.getKey(), pair.getValue() + debt));
					  debt = 0;
				  }
		  	}
        }
      }
		return debtsList;
	}

	/**
	 * Helper method for the getOpenDebts, returns list of pairs with Person and Integer how much he should be paid.
	 * @param people
	 * @return
	 */
	private static List<Pair<Person, Double>>  peopleWithPositiveDebt(List<Person> people) {
		List<Pair<Person, Double>> peoplesToPay = new  ArrayList<>();
		for (Person person : people) {
		  if (person.getDebt() > 0) {
			peoplesToPay.add(new Pair<>(person, person.getDebt()));
		  }
		}
		return peoplesToPay;
	}

	/**
	 * This method returns the transactions of the event with the given id.
	 * @param id of the event
	 * @return list of transactions
	 */
	public List<Transaction> getTransactions(int id) {
		return ClientBuilder.newClient(new ClientConfig())
				.target(server).path("api/event/" + id + "/expenses")
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.get(new GenericType<List<Transaction>>() {});
	}

	public List<Person> getPeopleInCurrentEvent(int id) {
		return ClientBuilder.newClient(new ClientConfig())
				.target(server).path("api/event/" + id + "/person")
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.get(new GenericType<List<Person>>() {});
	}

	public Transaction addTransactionToCurrentEvent(int idEvent, Transaction transaction) {

		ObjectMapper objectMapper = new ObjectMapper();

		objectMapper.registerModule(new JavaTimeModule());

		return ClientBuilder.newClient(new ClientConfig())
				.target(server).path("api/event/" + idEvent + "/expenses")
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.post(Entity.entity(transaction, APPLICATION_JSON), Transaction.class);
	}


	public Person updatePerson(int personID, Person person){
		return ClientBuilder.newClient(new ClientConfig())
				.target(server).path("api/person/" + personID)
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.put(Entity.entity(person, APPLICATION_JSON), Person.class);
	}

	public Event updateTitleEvent(int id, String title) {
		Event updatedEvent = new Event();
		updatedEvent.setTitle(title);
		System.out.println("To change event # " + id + "with new title " + title);
		return ClientBuilder.newClient(new ClientConfig())
				.target(server).path("api/event/" + id + "/title")
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.put(Entity.entity(updatedEvent, APPLICATION_JSON), Event.class);
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
				.target(server).path("api/event/")
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
				.target(server).path("api/event/" + eventID)
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.get(new GenericType<Event>() {});
	}

	public Person addPerson(Person person, int id) {
		Response response = ClientBuilder.newClient().target(server)
				.path("api/event/" + id + "/person")
				.request(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.post(Entity.entity(person, MediaType.APPLICATION_JSON));
		if (response.getStatus() == Response.Status.OK.getStatusCode()) {
			return response.readEntity(Person.class);
		} else {
			throw new RuntimeException("Failed to add person. Status code: " + response.getStatus());
		}
	}

	public Person removePerson(int personID, int eventID){
		Response response = ClientBuilder.newClient().target(server)
				.path("api/event/" + eventID + "/person")
				.queryParam("id", personID)
				.request(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.delete();
		if (response.getStatus() == Response.Status.OK.getStatusCode()) {
			return response.readEntity(Person.class);
		} else {
			throw new RuntimeException("Failed to remove person. Status code: " + response.getStatus());
		}
	}

	public Event updateEventById(Event event, int id) {
		Response response = ClientBuilder.newClient().target(server)
				.path("api/event/" + id)
				.request(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.put(Entity.entity(event, MediaType.APPLICATION_JSON));
		if (response.getStatus() == Response.Status.OK.getStatusCode()) {
			return response.readEntity(Event.class);
		} else {
			throw new RuntimeException("Failed to update event. Status code: " + response.getStatus());
		}
	}

	public Event deleteEventById(Event event, int id) {
		Response response = ClientBuilder.newClient().target(server)
				.path("api/event/" + id)
				.request(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.delete();
		if (response.getStatus() == Response.Status.OK.getStatusCode()) {
			return response.readEntity(Event.class);
		} else {
			throw new RuntimeException("Failed to remove event. Status code: " + response.getStatus());
		}
	}

	/**
	 * Gets the admin password for the server.
	 * @return returns the current admin password
	 */
	public String getPassword(){
		Client client = ClientBuilder.newClient();
		Response response = client.target(server).path("api/login/").request().get();

		String password = response.readEntity(String.class);
		return password;
	}

	public void sendPassword(){
		Client client = ClientBuilder.newClient();
		Response response = client.target(server).path("api/login/log").request().get();
	}

	private static final ExecutorService EXECUTOR_SERVER = Executors.newSingleThreadExecutor();

	public void registerForUpdates(Consumer<Transaction> consumer) {
		EXECUTOR_SERVER.submit(() -> {
			while (!Thread.interrupted()) {
				var res = ClientBuilder.newClient(new ClientConfig()) //
						.target(server).path("/api/transaction/transactions") //
						.request(APPLICATION_JSON) //
						.accept(APPLICATION_JSON) //
						.get(Response.class);
				if (res.getStatus() == 204) {
					continue;
				}
				Transaction e = res.readEntity(Transaction.class);
				consumer.accept(e);
			}
		});
	}

	public void stop() {
		EXECUTOR_SERVER.shutdownNow();
	}

	private StompSession session = connect("ws://localhost:8080/websocket");

	private StompSession connect(String url) {
		StandardWebSocketClient client = new StandardWebSocketClient();
		WebSocketStompClient stompClient =  new WebSocketStompClient(client);
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());
		try {
			// add stomp adapter error handling
			return stompClient.connectAsync(url, new StompSessionHandlerAdapter() {}).get();
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		catch (ExecutionException executionException) {
			throw new RuntimeException(executionException);
		}
		throw new IllegalStateException();
	}

	/**
	 * Register for messages.
	 * @param dest
	 * @param type
	 * @param consumer
	 * @param <T>
	 */
	public <T> void registerForMessages(String dest, Class<T> type, Consumer<T> consumer) {
		session.subscribe(dest, new StompFrameHandler() {
			@Override
			public Type getPayloadType(StompHeaders headers) {
				return type;
			}

			@Override
			public void handleFrame(StompHeaders headers, Object payload) {
				consumer.accept((T) payload);
			}
		});
	}

	/**
	 * Method that deletes te transaction from the current event.
	 * @param idEvent
	 * @param idTransaction
	 * @return
	 */
	public Transaction deleteTransactionFromCurrentEvent(int idEvent, int idTransaction) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		Response response = ClientBuilder.newClient().target(server)
				.path("api/event/" + idEvent + "/expenses")
				.queryParam("id", idTransaction)
				.request(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.delete();
		if (response.getStatus() == Response.Status.OK.getStatusCode()) {
			return response.readEntity(Transaction.class);
		} else {
			throw new RuntimeException("Failed to remove event. Status code: " + response.getStatus());
		}

	}

	/**
	 * Gets the transaction with the following id.
	 * @param idTransaction of the transaction you want.
	 * @return a transaciton
	 */
	public Transaction getTransactionByID(int idTransaction) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		return ClientBuilder.newClient(new ClientConfig())
				.target(server).path("api/transaction/" + idTransaction)
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.get(new GenericType<Transaction>() {});
	}

	public Transaction updateTransactionByID(Transaction transaction, int idTransaction) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		Response response = ClientBuilder.newClient().target(server)
				.path("api/transaction/" + idTransaction)
				.request(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.put(Entity.entity(transaction, MediaType.APPLICATION_JSON));
		if (response.getStatus() == Response.Status.OK.getStatusCode()) {
			return response.readEntity(Transaction.class);
		} else {
			throw new RuntimeException("Failed to update event. Status code: " + response.getStatus());
		}

	}

	public Object send(String dest, Object o) {
		session.send(dest, o);
		return o;
	}

	/**
	 * Method for rounding a double to a certain amount of places.
	 * @param value - the value to round
	 * @param places - the amount of places to round to
	 * @return - the rounded value
	 */
	public static double round(double value, int places) {
		if (places < 0) {
			throw new IllegalArgumentException();
		}

		BigDecimal bd = BigDecimal.valueOf(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}


	/**
	 * Gets the person with the following id.
	 * @param idPerson of the transaction you want.
	 * @return a person
	 */
	public Person getPersonByID(int idPerson) {

		return ClientBuilder.newClient(new ClientConfig())
				.target(server).path("api/person/" + idPerson)
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.get(new GenericType<Person>() {});
	}

}