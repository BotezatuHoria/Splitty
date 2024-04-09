package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class DebtCellDataTest {
  Person person1;
  Person person2;
  DebtCellData data;
  @BeforeEach
    void setUp() {
    person1 = new Person("bob@gmail.com", "Artur", "Iurasov",
            "34586");
    person2 = new Person("guboshlep@gmail.com", "Vytaras", "Juzonis",
            "33245");
    data = new DebtCellData(person1, person2, 15);
  }

  @Test
  void getDebt() {
    assertNotNull(data);
  }

  @Test
  void getSender() {
    assertEquals(person1, data.getSender());
  }

  @Test
  void getReceiver() {
    assertEquals(person2, data.getReceiver());
  }

  @Test
  void getDebtTest() {
    assertEquals(15, data.getDebt());
  }
}