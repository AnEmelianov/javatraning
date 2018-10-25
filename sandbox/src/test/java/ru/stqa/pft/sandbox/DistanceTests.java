package ru.stqa.pft.sandbox;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DistanceTests {

  @Test
  public void checkDistance() {
    Point p = new Point(3, 5, 7, 2);
    Assert.assertEquals(p.area(),5.0);
  }

  @Test
  //Данный тест возвращает true/false
  public void checkDistance1() {
    Point p = new Point(3, 5, 7, 2);
    System.out.println(p.area() == 5.0);
  }
}