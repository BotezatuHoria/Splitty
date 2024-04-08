package server.services.implementations;

import commons.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import server.database.PersonRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonServiceImplementationTest extends Person{

  @Mock
  private PersonRepository personRepository;

  @Mock
  private SimpMessagingTemplate simpMessagingTemplate;

  private PersonServiceImplementation personServiceImplementation;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    personServiceImplementation = new PersonServiceImplementation(personRepository, simpMessagingTemplate);
  }

  /**
   * Test the getAll method.
   */
  @Test
  void testGetAll() {
    List<Person> people = Arrays.asList(new Person(), new Person());
    when(personRepository.findAll()).thenReturn(people);

    ResponseEntity<List<Person>> response = personServiceImplementation.getAll();

    assertEquals(people, response.getBody());
  }
  /**
   * Test the getById method.
   */
    @Test
    void testGetById() {
        Person person = new Person();
        when(personRepository.findById(1)).thenReturn(java.util.Optional.of(person));

        ResponseEntity<Person> response = personServiceImplementation.getById(1);

        assertEquals(person, response.getBody());
    }
    /**
     * Test the add method.
     */
    @Test
    void testAdd() {
      Person person = new Person("test", "test", "test", "test");
        when(personRepository.save(person)).thenReturn(person);

        ResponseEntity<Person> response = personServiceImplementation.add(person);

        assertEquals(person, response.getBody());
    }
    /**
     * Test the deleteById method.
     */
    @Test
    void testDeleteById() {
        Person person = new Person("test", "test", "test", "test");
        when(personRepository.findById(1)).thenReturn(java.util.Optional.of(person));
      when(personRepository.existsById(1)).thenReturn(true);
        ResponseEntity<Person> response = personServiceImplementation.deleteById(1);

        assertEquals(person, response.getBody());
    }
    /**
     * Test the updateById method with invalid id.
     */
    @Test
    void testUpdateByIdInvalidId() {
        Person person = new Person("test", "test", "test", "test");
        when(personRepository.existsById(1)).thenReturn(false);

        ResponseEntity<Person> response = personServiceImplementation.updateById(1, person);

        assertEquals(ResponseEntity.badRequest().build(), response);
    }

}