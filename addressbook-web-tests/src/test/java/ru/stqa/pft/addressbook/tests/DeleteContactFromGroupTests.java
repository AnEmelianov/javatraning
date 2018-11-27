package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class DeleteContactFromGroupTests extends TestBase {


  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().size() == 0) {
      app.contact().create(new ContactData()
              .withFirstname("firstanme_new0000").withLastname("lastname_new")
              .withAddress("address_new").withEmail("email_new")
              .withMobilePhone("mobile_new"), true);

    }
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1"));
      app.goTo().homePage();
    }
  }

  @Test
  public void testDeleteContactFromGroup() {
    Groups groups = app.db().groups();
    GroupData removed_group = null;

    Contacts before = app.db().contacts();
    ContactData before_contact = before.iterator().next();
    Groups before_groups = before_contact.getGroups();
    if (before_groups.size() == 0) {
      removed_group = groups.iterator().next();
      app.contact().selectContactById(before_contact.getId());
      app.contact().addContactToGroup(removed_group.getId());
      app.goTo().homePage();

      before = app.db().contacts();
      before_contact = before.iterator().next();
      before_groups = before_contact.getGroups();
    }
    removed_group = before_groups.iterator().next();
    Groups after_groups = before_contact.getGroups().without(removed_group);

    app.contact().deleteContactFromGroup(removed_group.getId(), before_contact.getId());

    Contacts after = app.db().contacts();
    for (ContactData contact : after) {
      if (contact.getId() == before_contact.getId()) {
        assertThat(contact.getGroups(), equalTo(after_groups));
      }
    }

  }
}
