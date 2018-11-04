package ru.stqa.pft.addressbook.tests;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.ContactData;


public class ContactModificationTests extends TestBase{



  @Test
  public void testContactModification() {
    if (! app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("firstanme_new", "lastname_new", "address_new", "email_new", "mobile_new", "test1"), true);
    }
    app.getContactHelper().selectContact();
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactForm(new ContactData("firstanme_new", "lastname_new", "address_new", "email_new", "mobile_new", null), false);
    app.getContactHelper().submitContactModification();
    app.getContactHelper().returnToContactPage();
  }


}
