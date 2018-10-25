package ru.stqa.pft.sandbox;

public class Point {

  public int ap1;
  public int ap2;
  public int bp1;
  public int bp2;


  public Point(int ap1, int ap2, int bp1, int bp2) {
    this.ap1 = ap1;
    this.ap2 = ap2;
    this.bp1 = bp1;
    this.bp2 = bp2;

  }

  public double area() {
    return Math.sqrt(Math.pow(this.bp1 - this.ap1, 2) + Math.pow(this.bp2 - this.ap2, 2));
  }
}

