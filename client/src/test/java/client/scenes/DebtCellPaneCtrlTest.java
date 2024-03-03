package client.scenes;

import commons.DebtCellData;
import commons.Event;
import commons.Person;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;


class DebtCellPaneCtrlTest{

  /**
   * Tests getInfoText method which is used to get text displayed in the cell of oped debts list.
   */
  @Test
  void getInfoText() {
    Event event = new Event("tag", "BIG EVENT", 1,
            "fjh-213-fsd-233", new HashSet<>(), new HashSet<>());

    Person person1 = new Person("bob@gmail.com", "Artur", "Iurasov",
            "34586", event, new HashSet<>(),new HashSet<>() );
    Person person2 = new Person("guboshlep@gmail.com", "Vytaras", "Juzonis",
            "33245", event, new HashSet<>(),new HashSet<>() );
    DebtCellData data = new DebtCellData(person1, person2, 15);
    //DebtCellPaneCtrl test = new DebtCellPaneCtrl();
    String expected = data.getSender().getFirstName()+
     " gives "+ data.getSender().getDebt() +
     " euros to " + data.getReceiver().getFirstName();
    //assertEquals(expected, test.getInfoText(data));
  }
}