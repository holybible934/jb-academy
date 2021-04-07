import java.util.List;

class Sort {
    public static void sortShapes(Shape[] array,
                                  List<Shape> shapes,
                                  List<Polygon> polygons,
                                  List<Square> squares,
                                  List<Circle> circles) {
        // write your code here
        for (Shape element : array) {
            if (element.getClass() == Shape.class) {
                shapes.add(element);
            } else if (element.getClass() == Polygon.class) {
                polygons.add((Polygon) element);
            } else if (element.getClass() == Circle.class) {
                circles.add((Circle) element);
            } else {
                squares.add((Square) element);
            }
        }
    }
}

//Don't change classes below
class Shape { }
class Polygon extends Shape { }
class Square extends Polygon { }
class Circle extends Shape { }