package ru.stqa.pft.mantis.appmanager;


import org.openqa.selenium.By;

public class ResetHelper extends HelperBase {

  public ResetHelper(ApplicationManager app) {
    super(app);
  }

  public void start(String username, String password) {
    wd.get(app.getProperty("web.baseUrl") + "/manage_user_page.php");
    type(By.name("username"), username);
    type(By.name("password"), password);
    click(By.cssSelector("input[value='Login']"));
  }

  public void selectUser(String username) {
    click(By.linkText(username));
  }

  public void resetPassword() {
    click(By.cssSelector("input[value='Reset Password']"));
  }

  public void finish(String сonfirmationLink, String password) {
    wd.get(сonfirmationLink);
    type(By.name("password"), password);
    type(By.name("password_confirm"), password);
    click(By.cssSelector("input[value='Update User']"));
  }
}
