package commons;

public class DebtCellData {

  private PersonTemporary sender;
  private PersonTemporary receiver;
  private int debt;

  /**
   * constructor which initializes all attributes.
   * @param sender
   * @param receiver
   * @param debt
   */
  public DebtCellData(PersonTemporary sender, PersonTemporary receiver, int debt){
    this.sender = sender;
    this.receiver = receiver;
    this.debt = debt;
 }

  /**
   * Getter for the amount of debt.
   * @return
   */
  public int getDebt() {
    return debt;
  }

  /**
   * Getter for the sender Person.
   * @return
   */
  public PersonTemporary getSender() {
    return sender;
  }
  /**
   * Getter for the receiver Person.
   * @return
   */
  public PersonTemporary getReceiver() {
    return receiver;
  }
}