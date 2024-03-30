package commons;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class DebtCellDataTest {

  @Test
  void getDebt() {
    // later needs to be put into before each --------------------------------------------------
    Person person1 = new Person("bob@gmail.com", "Artur", "Iurasov",
            "34586");
    Person person2 = new Person("guboshlep@gmail.com", "Vytaras", "Juzonis",
            "33245");
    DebtCellData data = new DebtCellData(person1, person2, 15);
    // later needs to be put into before each --------------------------------------------------
    assertNotNull(data);
  }

  @Test
  void getSender() {
    // later needs to be put into before each --------------------------------------------------
    Person person1 = new Person("bob@gmail.com", "Artur", "Iurasov",
            "34586");
    Person person2 = new Person("guboshlep@gmail.com", "Vytaras", "Juzonis",
            "33245");
    DebtCellData data = new DebtCellData(person1, person2, 15);
    // later needs to be put into before each --------------------------------------------------
    assertEquals(person1, data.getSender());
  }

  @Test
  void getReceiver() {
    // later needs to be put into before each --------------------------------------------------
    Person person1 = new Person("bob@gmail.com", "Artur", "Iurasov",
            "34586");
    Person person2 = new Person("guboshlep@gmail.com", "Vytaras", "Juzonis",
            "33245");
    DebtCellData data = new DebtCellData(person1, person2, 15);
    // later needs to be put into before each --------------------------------------------------
    assertEquals(person2, data.getReceiver());
  }

  @Test
  void getDebtTest() {
    // later needs to be put into before each --------------------------------------------------
    Person person1 = new Person("bob@gmail.com", "Artur", "Iurasov",
            "34586");
    Person person2 = new Person("guboshlep@gmail.com", "Vytaras", "Juzonis",
            "33245");
    DebtCellData data = new DebtCellData(person1, person2, 15);
    // later needs to be put into before each --------------------------------------------------
    assertEquals(15, data.getDebt());
  }
}