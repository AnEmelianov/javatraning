package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.ContactData;

public class ContactCreationTests extends TestBase {


  @Test
  public void testContactCreation() {
    app.getNavigationHelper().gotoContactPage();
    app.getContactHelper().fillContactForm(new ContactData("firstanme_new", "lastname_new", "adress_new", "email_new", "mobile_new"));
    app.getContactHelper().submitContactCreation();
    app.getContactHelper().returnToContactPage();
  }
}
