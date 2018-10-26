package ru.stqa.pft.sandbox;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DistanceTests {

  @Test
  public void checkDistance() {
    Point p1 = new Point(3,5);
    Point p2 = new Point(7,2);
    Assert.assertEquals(p1.distance(p2),5.0);
  }

  @Test
  //Данный тест возвращает true/false
  public void checkDistance1() {
    Point p1 = new Point(3,5);
    Point p2 = new Point(7, 2);
    System.out.println(p1.distance(p2) == 5.0);
  }
}