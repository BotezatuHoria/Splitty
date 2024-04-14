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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import client.Config;
import client.Main;
import commons.*;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.stage.Modality;
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

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ServerUtils {

	private static String server = "http://localhost:8080/";
	private static final String EMAIL_USERNAME = "";
	private static final String EMAIL_PASSWORD = "";
	private static LanguageManager languageManager;
	private static Config config;
	public static void setServer(String server) {
		ServerUtils.server = server;
	}

	public void setLanguageManager(LanguageManager languageManager) {
		ServerUtils.languageManager = languageManager;
	}


	public static void setConfig(Config config) {
		ServerUtils.config = config;
		server = config.getClientsServer();
		String path= "";
		try {
			path = Main.class
					.getProtectionDomain()
					.getCodeSource()
					.getLocation()
					.toURI()
					.getPath();
			path = path + "/client/config.json";
		} catch (URISyntaxException ex) {
			System.out.println("URISyntaxException: " + ex.getMessage());
		}
		var file = new File(path);

		try (FileWriter writer = new FileWriter(file, false)) {
			String server = config.getClientsServer() != null ? "\"" + config.getClientsServer() + "\"" : "\"http://localhost:8080/\"";
			String email = config.getClientsEmailAddress() != null ? "\"" + config.getClientsEmailAddress() + "\"" : "\"example@gmail.com\"";
			String password = config.getPassword() != null ? "\"" + config.getPassword() + "\"" : "\"password\"";
			String language = config.getClientsLanguage() != null ? "\"" + config.getClientsLanguage() + "\"" : "\"en\"";

			writer.write("{\n" +
					"  \"server\": " + server + ",\n" +
					"  \"emailAddress\": " + email + ",\n" +
					"  \"password\": " + password + ",\n" +
					"  \"language\":" + language + "\n" +
					"}");
		} catch (IOException e) {
			System.out.println("An error occurred while creating the file: " + e.getMessage());
		}
	}

	public static Config getConfig() { return config; }


	public List<Event> getEvents() {
		return ClientBuilder.newClient(new ClientConfig()) //
				.target(server).path("/api/event") //
				.request(APPLICATION_JSON) //
				.accept(APPLICATION_JSON) //
				.get(new GenericType<>() {
				});
	}
	public List<DebtCellData> getDebtsOfPerson(int id, int idPerson) {
		List<DebtCellData> openDebts = getOpenDebts(id);
		List<DebtCellData> openDebtsForSpecificPerson = new ArrayList<>();
		for (DebtCellData debt : openDebts) {
			if (debt.getSender().getId() == idPerson){ //|| debt.getReceiver().getId() == idPerson (depends what we need)
				openDebtsForSpecificPerson.add(debt);
			}
		}
		return openDebtsForSpecificPerson;

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

	/**
	 * Gets an event from the server using the invite token.
	 * @param token the token of the event
	 * @return the event
	 */
	public Event getEventByToken(String token){
		return ClientBuilder.newClient(new ClientConfig())
				.target(server).path("api/event/token/" + token)
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.get(new GenericType<Event>() {});
	}

	public Tag addTag(Tag tag, int id) {
		Response response = ClientBuilder.newClient().target(server)
				.path("api/event/" + id + "/tag")
				.request(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.post(Entity.entity(tag, MediaType.APPLICATION_JSON));
		if (response.getStatus() == Response.Status.OK.getStatusCode()) {
			return response.readEntity(Tag.class);
		} else {
			throw new RuntimeException("Failed to add person. Status code: " + response.getStatus());
		}
	}

	public Person removeTag(int tagID, int eventID){
		Response response = ClientBuilder.newClient().target(server)
				.path("api/event/" + eventID + "/tag")
				.queryParam("id", tagID)
				.request(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.delete();
		if (response.getStatus() == Response.Status.OK.getStatusCode()) {
			return response.readEntity(Person.class);
		} else {
			throw new RuntimeException("Failed to remove person. Status code: " + response.getStatus());
		}
	}

	public Tag updateTag(int tagID, Tag tag){
		return ClientBuilder.newClient(new ClientConfig())
				.target(server).path("api/tag/" + tagID)
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.put(Entity.entity(tag, APPLICATION_JSON), Tag.class);
	}

	public void sendEmail(String to, String subject, String message) {
		if (to.isEmpty() || to  == null) {
			sendBadEmailAlert();
			return;
		}
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.debug", "true");

		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(config.getClientsEmailAddress(), config.getPassword());
			}
		});

		try {
			Message mimeMessage = new MimeMessage(session);
			mimeMessage.setFrom(new InternetAddress(config.getClientsEmailAddress()));
			mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			mimeMessage.setSubject(subject);
			mimeMessage.setText(message);

			Transport.send(mimeMessage);
			sendConfirmAlert();
		} catch (MessagingException e) {
			sendFailAlert(e);
		}
	}

	public void sendConfirmAlert() {
		ResourceBundle resourceBundle = languageManager.getResourceBundle();
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.setContentText(resourceBundle.getString("email.sent"));
		alert.showAndWait();
	}

	public void sendFailAlert(MessagingException e) {
		ResourceBundle resourceBundle = languageManager.getResourceBundle();
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.setContentText(resourceBundle.getString("email.failed") + e.getMessage());
		alert.showAndWait();
	}

	public void sendBadEmailAlert() {
		ResourceBundle resourceBundle = languageManager.getResourceBundle();
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.setContentText(resourceBundle.getString("email.invalid"));
		alert.showAndWait();
	}

	public void initializeLanguages(ComboBox languageSelector) {
		// Push all the languages to the combobox
		languageSelector.getItems().addAll(FlagListCell.getLanguages());

		// Responsible for setting the flags and changing languages
		languageSelector.setCellFactory(lv -> new FlagListCell());
		languageSelector.setButtonCell(new FlagListCell());

		languageSelector.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
			if (newVal != null) {
				languageManager.setLanguage((org.apache.commons.lang3.tuple.Pair<String, Image>) newVal);
				languageManager.setLanguageText();

				Config config = ServerUtils.getConfig();
				if (config != null) {
					config.setLanguage(((org.apache.commons.lang3.tuple.Pair<String, Image>) newVal).getKey());
					ServerUtils.setConfig(config);
				}
			}
		});

		// Show current language
		org.apache.commons.lang3.tuple.Pair<String, Image> currentLanguage = languageManager.getLanguage();
		languageSelector.getSelectionModel().select(currentLanguage);
	}

	public void setLanguageSelector(ComboBox languageSelector) {
		org.apache.commons.lang3.tuple.Pair<String, Image> currentLanguage = languageManager.getLanguage();
		languageSelector.getSelectionModel().select(currentLanguage);
	}

}