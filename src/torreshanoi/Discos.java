package torreshanoi; // Paquete de la clase Discos

import javafx.scene.canvas.GraphicsContext; // Importa GraphicsContext para dibujar
import javafx.scene.paint.Color; // Importa la clase Color
import javafx.scene.paint.Paint; // Importa la clase Paint
import javafx.scene.shape.Rectangle; // Importa Rectangle para la forma del disco

public class Discos {
    // Array de colores para los discos
    public static final Color COLORS[] = { 
        Color.RED,          // Color disco 1
        Color.YELLOW,       // Color disco 2
        Color.BLUE,         // Color disco 3
        Color.GREEN,        // Color disco 4
        Color.ORANGE,       // Color disco 5
        Color.LIGHTSKYBLUE, // Color disco 6
        Color.AQUA,         // Color disco 7
        Color.PURPLE,       // Color disco 8
        Color.MEDIUMVIOLETRED, // Color disco 9
        Color.PINK // Color disco 10
    };
    
    public static final int HEIGHT = 20; // Altura constante de cada disco
    
    private Rectangle rectangle; // Forma del disco

    // Constructor que inicializa el disco con su forma y color
    public Discos(Rectangle rectangle, Paint fill) {
        this.rectangle = rectangle;
        this.rectangle.setFill(fill); // Establece el color del disco
    }
    
    // Define la posición del disco en X e Y
    public void setLocation(double x, double y) {
        rectangle.setX(x);
        rectangle.setY(y);
    }

    // Retorna el objeto Rectangle del disco
    public Rectangle getRectangle() {
        return rectangle;
    }

    // Obtiene la coordenada X del disco
    public double getX() {
        return rectangle.getX();
    }

    // Obtiene la coordenada Y del disco
    public double getY() {
        return rectangle.getY();
    }

    // Obtiene el ancho del disco
    public double getWidth() {
        return rectangle.getWidth();
    }

    // Obtiene la altura del disco
    public double getHeight() {
        return rectangle.getHeight();
    }

    // Dibuja el disco en el contexto gráfico con esquinas redondeadas
    public void draw(GraphicsContext gc) {
        gc.setFill(rectangle.getFill()); // Establece el color de relleno
        gc.fillRoundRect(
            rectangle.getX(), 
            rectangle.getY(), 
            rectangle.getWidth(), 
            rectangle.getHeight(), 
            20, // Redondez en X
            20  // Redondez en Y
        );
    }
}
