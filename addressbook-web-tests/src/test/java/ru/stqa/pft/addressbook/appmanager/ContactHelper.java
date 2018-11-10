package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void returnToContactPage() {
    click(By.linkText("home"));
  }

  public void submitContactCreation() {
    click(By.name("submit"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("address"), contactData.getAddress());
    type(By.name("email"), contactData.getEmail());
    type(By.name("mobile"), contactData.getMobile());


//    if (creation) {
//      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getContact());
//    } else {
//      Assert.assertFalse(isElementPresent(By.name("new_group")));
//    }
  }

  public void initContactCreation() {
    click(By.linkText("add new"));
  }


  public void selectContact(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
  }

  public void selectContactById(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
  }

  public void deleteSelectedContacts() {
    click(By.xpath("//input[@value='Delete']"));
  }

//  public void initContactModification(int index) {
//    wd.findElements(By.cssSelector("img[alt='Edit']")).get(index).click();
//  }

  public void initContactModificationById(int id) {
    WebElement element = wd.findElement(By.cssSelector("input[id='" + id + "']"));
    WebElement parentElement = element.findElement(By.xpath("./../.."));
    element = parentElement.findElement(By.cssSelector("td:nth-child(8) a"));
    element.click();
  }

  public void submitContactModification() {
    click(By.name("update"));
  }


  public void create(ContactData contact, boolean creation) {
    initContactCreation();
    fillContactForm(contact, creation);
    submitContactCreation();
    new NavigationHelper(wd).homePage();
  }

  public void modify(ContactData contact) {
    //selectContactById(contact.getId());
    initContactModificationById(contact.getId());
    fillContactForm(contact, true);
    submitContactModification();
    returnToContactPage();
  }


  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    deleteSelectedContacts();
    accept();
    returnToContactPage();
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public int getContactCount() {
    return wd.findElements(By.name("selected[]")).size();
  }


  public Contacts all() {
    Contacts contacts = new Contacts();
    List<WebElement> elements = wd.findElements(By.cssSelector("tr[name='entry']"));
    for (WebElement element : elements) {
      String firstname = element.findElement(By.cssSelector("td:nth-child(3)")).getText();
      String lastname = element.findElement(By.cssSelector("td:nth-child(2)")).getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      contacts.add(new ContactData().withId(id).withFirstname(firstname).withLastname(lastname));
    }
    return contacts;
  }
  public void accept() {
    wd.switchTo().alert().accept();
  }


}
