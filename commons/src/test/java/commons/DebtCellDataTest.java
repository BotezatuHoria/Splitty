package commons;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DebtCellDataTest {

  @Test
  void getDebt() {
    // later needs to be put into before each --------------------------------------------------
    Event event = new Event("tag", "BIG EVENT", 1,
            "fjh-213-fsd-233", new ArrayList<>(), new ArrayList<>());

    Person person1 = new Person("bob@gmail.com", "Artur", "Iurasov",
            "34586", event, new ArrayList<>(),new ArrayList<>() );
    Person person2 = new Person("guboshlep@gmail.com", "Vytaras", "Juzonis",
            "33245", event, new ArrayList<>(),new ArrayList<>() );
    DebtCellData data = new DebtCellData(person1, person2, 15);
    // later needs to be put into before each --------------------------------------------------
    assertNotNull(data);
  }

  @Test
  void getSender() {
    // later needs to be put into before each --------------------------------------------------
    Event event = new Event("tag", "BIG EVENT", 1,
            "fjh-213-fsd-233", new ArrayList<>(), new ArrayList<>());

    Person person1 = new Person("bob@gmail.com", "Artur", "Iurasov",
            "34586", event, new ArrayList<>(),new ArrayList<>() );
    Person person2 = new Person("guboshlep@gmail.com", "Vytaras", "Juzonis",
            "33245", event, new ArrayList<>(),new ArrayList<>() );
    DebtCellData data = new DebtCellData(person1, person2, 15);
    // later needs to be put into before each --------------------------------------------------
    assertEquals(person1, data.getSender());
  }

  @Test
  void getReceiver() {
    // later needs to be put into before each --------------------------------------------------
    Event event = new Event("tag", "BIG EVENT", 1,
            "fjh-213-fsd-233", new ArrayList<>(), new ArrayList<>());

    Person person1 = new Person("bob@gmail.com", "Artur", "Iurasov",
            "34586", event, new ArrayList<>(),new ArrayList<>() );
    Person person2 = new Person("guboshlep@gmail.com", "Vytaras", "Juzonis",
            "33245", event, new ArrayList<>(),new ArrayList<>() );
    DebtCellData data = new DebtCellData(person1, person2, 15);
    // later needs to be put into before each --------------------------------------------------
    assertEquals(person2, data.getReceiver());
  }

  @Test
  void getDebtTest() {
    // later needs to be put into before each --------------------------------------------------
    Event event = new Event("tag", "BIG EVENT", 1,
            "fjh-213-fsd-233", new ArrayList<>(), new ArrayList<>());

    Person person1 = new Person("bob@gmail.com", "Artur", "Iurasov",
            "34586", event, new ArrayList<>(),new ArrayList<>() );
    Person person2 = new Person("guboshlep@gmail.com", "Vytaras", "Juzonis",
            "33245", event, new ArrayList<>(),new ArrayList<>() );
    DebtCellData data = new DebtCellData(person1, person2, 15);
    // later needs to be put into before each --------------------------------------------------
    assertEquals(15, data.getDebt());
  }
}