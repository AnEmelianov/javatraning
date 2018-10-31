package ru.stqa.pft.addressbook.tests;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.ContactData;


public class ContactModificationTests extends TestBase{



  @Test
  public void testContactModification() {
    app.getContactHelper().selectContact();
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactForm(new ContactData("firstanme_new", "lastname_new", "adress_new", "email_new", "mobile_new"));
    app.getGroupHelper().submitGroupModification();
    app.getContactHelper().returnToContactPage();
  }


}
