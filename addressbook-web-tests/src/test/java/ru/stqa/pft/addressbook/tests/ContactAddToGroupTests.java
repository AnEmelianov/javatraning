package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class ContactAddToGroupTests extends TestBase {

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
  public void testContactAddToGroup() {
    Groups groups = app.db().groups();

    Contacts before = app.db().contacts();
    ContactData before_contact = before.iterator().next();
    Groups before_groups = before_contact.getGroups();
    if (before_groups.size() == groups.size()) {
      // add new group
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1"));
      app.goTo().homePage();
      groups = app.db().groups();
    }

    GroupData added_group = null;
    for (GroupData group : groups) {
      if (!before_groups.contains(group)) {
        added_group = group;
        break;
      }
    }

    Groups after_groups = before_contact.getGroups().withAdded(added_group);
    app.contact().selectContactById(before_contact.getId());
    app.contact().addContactToGroup(added_group.getId());
    Contacts after = app.db().contacts();
    for (ContactData contact : after) {
      if (contact.getId() == before_contact.getId()) {
        assertThat(contact.getGroups(), equalTo(after_groups));
      }
    }
  }

}

