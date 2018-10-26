package ru.stqa.pft.sandbox;


public class Distance {
  public static void main(String[] args) {

    Point p1 = new Point(3, 5);
    Point p2 = new Point(7, 2);
    double d = p1.distance(p2);

    System.out.println("Расстояние между двумя точками равно " + d);
  }
}


