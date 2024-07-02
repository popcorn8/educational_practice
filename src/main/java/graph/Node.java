//package graph;
//
//import java.awt.*;
//
//public class Node {
//    private Point point;
//    private int radius = 10;
//    private Color color; // Новый параметр цвета
//
//    public Node(int x, int y) {
//        this.point = new Point(x, y);
//        this.color = Color.BLACK; // Цвет по умолчанию чёрный
//    }
//
//    public Point getPoint() {
//        return point;
//    }
//
//    public int getRadius() {
//        return radius;
//    }
//
//    public Color getColor() {
//        return color;
//    }
//
//    public void setColor(Color color) {
//        this.color = color;
//    }
//
//    public void setPoint(int x, int y) {
//        this.point.setLocation(x, y);
//    }
//
//    public boolean contains(int x, int y) {
//        return point.distance(x, y) <= radius;
//    }
//}
