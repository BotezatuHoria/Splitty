package server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordGeneratorTest {

  @Mock
  private Logger logger;

  @InjectMocks
  private PasswordGenerator passwordGenerator;

  @Captor
  ArgumentCaptor<String> captor;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  /**
   * Tests if the password is generated.
   */
  @Test
  public void testGeneratePassword() {
    passwordGenerator.generatePassword();
    String password = PasswordGenerator.getPassword();
    assertEquals(8, password.length());
  }

  /**
   * Tests if the password is logged.

   */
  @Test
  public void testGetPassword() {
    passwordGenerator.generatePassword();
    String password = PasswordGenerator.getPassword();
    assertTrue(password.matches("[a-zA-Z0-9]{8}"));
  }

}