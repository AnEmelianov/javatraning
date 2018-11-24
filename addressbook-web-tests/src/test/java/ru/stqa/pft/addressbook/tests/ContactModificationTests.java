package ru.stqa.pft.addressbook.tests;

        import org.testng.annotations.BeforeMethod;
        import org.testng.annotations.Test;
        import ru.stqa.pft.addressbook.model.ContactData;
        import ru.stqa.pft.addressbook.model.Contacts;
        import static org.hamcrest.CoreMatchers.equalTo;
        import static org.hamcrest.MatcherAssert.assertThat;


public class ContactModificationTests extends TestBase {


  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().size() == 0) {
      app.contact().create(new ContactData()
              .withFirstname("firstanme_new0000").withLastname("lastname_new")
              .withAddress("address_new").withEmail("email_new")
              .withMobilePhone("mobile_new").withEmail1("").withEmail2("").withEmail3("").withHomePhone("")
              .withWorkPhone(""), true);

    }
  }


  @Test
  public void testContactModification() {
    Contacts before = app.db().contacts();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData()
            .withId(modifiedContact.getId()).withFirstname("firstanme_new0000")
            .withLastname("lastname_new").withAddress("address_new")
            .withEmail("email_new").withMobilePhone("mobile_new").withEmail1("").withEmail2("").withEmail3("").withHomePhone("")
            .withWorkPhone("");
    app.contact().modify(contact);
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
  }
}
