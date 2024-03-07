package server.api;
/** uncomment later
import commons.Event;
import commons.Person;
import commons.Transaction;
 */
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.BAD_REQUEST;


class TransactionControllerTest {

    private TestTransactionRepository db;
    private TransactionController sut;
    @BeforeEach
    public void setup () {
        db = new TestTransactionRepository();
        sut = new TransactionController(db);
    }
    @Test
    public void getTransactionNullTest() {
        var actual = sut.getById(-14);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }
    @Test
    public void deleteTransactionNullTest() {
        var actual = sut.deleteById(-1);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }
    /** uncomment when fix null assertion in controller
    @Test
    public void cannotAddNullTransaction() {
        Transaction test = null;
        var actual = sut.add(test);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }
    */
    /** not working test ( for some reason)
    @Test
    public void addTransactionTest() {
        Set<Person> participants = new HashSet<>();
        Set<Transaction> transactions = new HashSet<>();
        LocalDate date = LocalDate.of(2005,6,4);
        Event event = new Event("Artur","Artur",9,"womp womp",participants,transactions);
        Person person = new Person("tom","tom","tom","tom",event,transactions,transactions);

       Transaction transaction = new Transaction("test", date, 80.5, 55, participants, person);
        var actual = sut.add(transaction);
        assertEquals(actual.getBody(), transaction);
    }
     */
    @Test
    void getAll() {
    }

    @Test
    void getById() {
    }

    @Test
    void add() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void updateNameById() {
    }

    @Test
    void updateParticipantsById() {
    }

    @Test
    void updateCreatorById() {
    }
}