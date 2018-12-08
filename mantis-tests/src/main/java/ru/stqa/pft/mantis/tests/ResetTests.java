package ru.stqa.pft.mantis.tests;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;

import java.io.IOException;
import java.sql.*;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class ResetTests extends TestBase {

//  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void testReset() throws IOException, MessagingException, javax.mail.MessagingException {
    UserData userdata = getUserFromDb();
    String user = userdata.username;
    String email = userdata.email;
    String passwordnew = "password_new";
    String email_pass = "password";

    if (!app.james().doesUserExist(user)) {
      app.james().createUser(user, email_pass);
    }
    app.james().drainEmail(user, email_pass);

    app.reseth().start("administrator", "root");
    app.reseth().selectUser(user);
    app.reseth().resetPassword();
//     List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
    List<MailMessage> mailMessages = app.james().waitForMail(user, email_pass, 60000);
    String сonfirmationLink = findConfirmationLink(mailMessages, email);
    app.reseth().finish(сonfirmationLink, passwordnew);
    assertTrue(app.newSession().login(user, passwordnew));
  }

  private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }

  public UserData getUserFromDb() {
    Connection conn = null;

    try {
      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bugtracker?user=root&password=" );
      Statement st = conn.createStatement();
      ResultSet rs = st.executeQuery("select id, username, email from mantis_user_table");
      UserData userdata = null;
      while (rs.next()) {
        String username = rs.getString("username");
        if (!username.equals("administrator")) {
          userdata = new UserData(username, rs.getString("email"));
          break;
        }
      }
      rs.close();
      st.close();
      conn.close();
      return userdata;
    } catch (SQLException ex) {
      System.out.println("SQLException: " + ex.getMessage());
      System.out.println("SQLState: " + ex.getSQLState());
      System.out.println("VendorError: " + ex.getErrorCode());
    }
    return null;
  }

//   @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }
}
