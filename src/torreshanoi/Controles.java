package torreshanoi;

import java.util.List;
import java.util.Optional;
import java.util.Stack;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.animation.AnimationTimer;
import javafx.scene.text.FontWeight;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;


/**
* Clase principal que controla la lógica y la interfaz gráfica del juego Torres de Hanoi
* @author LuissxD
*/
public class Controles {
    
   // Canvas donde se dibuja el juego
   private Canvas VentanaPrincipal;

   // Agregar después de las variables existentes
   
   private long startTime;
   private String timeElapsed = "00:00";
   private AnimationTimer timer;
   
   // Número inicial de discos y contador de movimientos
   private int numberOfDisks = 3;
   private int moves;
   
   // Array que almacena las tres torres del juego
   private Torre[] pegs = new Torre[3];

   // Variables para el control del arrastre de discos
   private Discos diskSelected;    // Disco actualmente seleccionado
   private int towerSelected;      // Índice de la torre seleccionada
   private double w, h;            // Variables para el control de posición del arrastre
   private boolean dragging;       // Indica si se está arrastrando un disco

    public Controles() {
    }
    // Agregar estas nuevas variables para la solución automática
    private solucion_Automatica solucionAuto;
    private Timeline animacionSolucion;
    private List<solucion_Automatica.Movimiento> movimientosSolucion;
    private int movimientoActual;
    private boolean solucionEnProgreso;
    /**
    * Constructor: Inicializa el juego con el canvas proporcionado
    */
  public Controles(Canvas canvas) {
    this.VentanaPrincipal = canvas;
    
    // Inicializar solucionAuto
    this.solucionAuto = new solucion_Automatica();
    
    // Establecer un nuevo tamaño para el MARCO
    this.VentanaPrincipal.setWidth(1100);//X
    this.VentanaPrincipal.setHeight(600);//Y
    startTime = System.currentTimeMillis();
    initializeTimer();
    initialize();
    init(numberOfDisks);
    draw();
}
   
    
private void moverDisco(Discos disco, double newX, double newY) {
    Rectangle rect = disco.getRectangle();
    
    // Centrar el disco directamente en la posición calculada
    double discoAncho = rect.getWidth();
    rect.setX(newX - (discoAncho / 2));
    rect.setY(newY);
}
    
    
private static final double DISCO_ANCHO = 40;
private static final double DISCO_ALTO = 20;
private static final double MARGEN_BASE = 65;

// Agregar estas constantes al inicio de la clase Controles
private static final double TORRE1_X = 185; // Posición X de la primera torre
private static final double TORRE2_X = 550; // Posición X de la segunda torre
private static final double TORRE3_X = 915; // Posición X de la tercera torre

// Modificar el método calcularXTorre para usar estas posiciones fijas
private double calcularXTorre(int torre) {
    switch (torre) {
        case 0: return TORRE1_X;
        case 1: return TORRE2_X;
        case 2: return TORRE3_X;
        default: return TORRE1_X;
    }
}


private double calcularYTorre(int torre) {
    double baseY = VentanaPrincipal.getHeight() - MARGEN_BASE;
    int numDiscos = pegs[torre].getDisks().size();
    return baseY - ((numDiscos - 1) * DISCO_ALTO);
}




private void ejecutarSiguienteMovimiento() {
    if (movimientoActual < movimientosSolucion.size()) {
        solucion_Automatica.Movimiento mov = movimientosSolucion.get(movimientoActual);
        
        // Obtener el disco a mover
        Discos disco = pegs[mov.getOrigen()].getDisks().pop();
        
        // Obtener la posición X exacta de la torre destino
        double newX = calcularXTorre(mov.getDestino());
        double newY = calcularYTorre(mov.getDestino());
        
        // Mover el disco
        disco.setLocation(newX - (disco.getWidth() / 2), newY);
        
        // Agregar el disco a la torre destino
        pegs[mov.getDestino()].getDisks().push(disco);
        
        moves++;
        draw();
        movimientoActual++;
    }
}
    
    
public void mostrarSolucionAutomatica() {
    // Verificar si solucionAuto está inicializado
    if (solucionAuto == null) {
        solucionAuto = new solucion_Automatica();
    }

    if (solucionEnProgreso) {
        detenerSolucionAutomatica();
        return;
    }

    try {
        movimientosSolucion = solucionAuto.resolverTorres(numberOfDisks);
        if (movimientosSolucion == null || movimientosSolucion.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error en la solución");
            alert.setContentText("No se pudieron generar los movimientos para la solución.");
            alert.showAndWait();
            return;
        }

        movimientoActual = 0;
        solucionEnProgreso = true;

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Solución Automática");
        alert.setHeaderText("Iniciando solución automática");
        alert.setContentText("Se mostrará la solución paso a paso.\n" + "Total de movimientos: " + movimientosSolucion.size());
        alert.showAndWait();

        animacionSolucion = new Timeline(
            new KeyFrame(Duration.seconds(0.5), e -> ejecutarSiguienteMovimiento())  //---------------TIEMPO EN MOVER----------------
        );
        animacionSolucion.setCycleCount(movimientosSolucion.size());
        animacionSolucion.setOnFinished(e -> finalizarSolucionAutomatica());
        animacionSolucion.play();

        mostrarVentanaPasos();
    } 
    catch (Exception e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error en la solución automática");
        alert.setContentText("Ocurrió un error: " + e.getMessage());
        alert.showAndWait();
    }
}
    




    
    /**
     * Detiene la solución automática en curso
     */
    private void detenerSolucionAutomatica() {
        if (animacionSolucion != null) {
            animacionSolucion.stop();
        }
        solucionEnProgreso = false;
    }
    
    /**
     * Finaliza la solución automática
     */
   private void finalizarSolucionAutomatica() {
        solucionEnProgreso = false;
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Solución Completada");
        alert.setHeaderText("¡Solución Finalizada!");
        alert.setContentText("Se han completado todos los movimientos necesarios.\n" + "Total de movimientos: " + moves);
        alert.showAndWait();
    }
    
     /**
     * Muestra una ventana con los pasos de la solución
     */
    private void mostrarVentanaPasos() {
        Stage ventanaPasos = new Stage();
        ventanaPasos.setTitle("Pasos de la Solución");
        
        TextArea areaPasos = new TextArea();
        areaPasos.setEditable(false);
        areaPasos.setWrapText(true);
        
        StringBuilder pasos = new StringBuilder();
        for (int i = 0; i < movimientosSolucion.size(); i++) {
            pasos.append("Paso ").append(i + 1).append(": ")
                 .append(movimientosSolucion.get(i).toString())
                 .append("\n");
        }
        areaPasos.setText(pasos.toString());
        
        VBox root = new VBox(areaPasos);
        Scene scene = new Scene(root, 400, 300);
        ventanaPasos.setScene(scene);
        ventanaPasos.show();
    }
   
   
    
   private void initializeTimer() {
    timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            long elapsedMillis = System.currentTimeMillis() - startTime;
            long minutes = (elapsedMillis / 1000) / 60;
            long seconds = (elapsedMillis / 1000) % 60;
            timeElapsed = String.format("%02d:%02d", minutes, seconds);
            draw(); // Redibujar para actualizar el tiempo
        }
    };
    timer.start();
}
   
   /**
    * Inicializa los eventos del mouse para controlar el juego
    */
   private void initialize() {
       // Evento cuando se presiona el mouse
       VentanaPrincipal.setOnMousePressed(event -> {
           Point2D point = new Point2D(event.getX(), event.getY());
           int tower = currentTower(point);
           Stack<Discos> disks = pegs[tower].getDisks();

           // Verifica si hay discos en la torre seleccionada
           if (!disks.empty()) {
               diskSelected = disks.peek();

               // Verifica si se hizo clic sobre el disco superior
               if (diskSelected.getRectangle().contains(point)) {
                   disks.pop();
                   // Guarda el offset para el arrastre
                   w = point.getX() - diskSelected.getX();
                   h = point.getY() - diskSelected.getY();
                   towerSelected = tower;
                   dragging = true;
               } else {
                   diskSelected = null;
                   dragging = false;
               }
           }
       });

       // Evento cuando se arrastra el mouse
       VentanaPrincipal.setOnMouseDragged(event -> {
           if (diskSelected != null && dragging == true) {
               // Actualiza la posición del disco mientras se arrastra
               diskSelected.setLocation(event.getX() - w, event.getY() - h);
               draw();
           }
       });

       // Evento cuando se suelta el mouse
       VentanaPrincipal.setOnMouseReleased(event -> {
           if (diskSelected != null && dragging == true) {
               int currentTower = currentTower(new Point2D(event.getX(), event.getY()));

               // Si se suelta en la misma torre o fuera del área, regresa a la torre original
               if (towerSelected == currentTower || currentTower == -1) {
                   currentTower = towerSelected;
                   moves--;
               }

               double x, y;
               Stack<Discos> disks = pegs[currentTower].getDisks();

               // Calcula la posición Y donde colocar el disco
               if (disks.empty()) {
                   y = VentanaPrincipal.getHeight() - 44.8;
               } else if (diskSelected.getWidth() < disks.peek().getWidth()) {
                   y = disks.peek().getY() - Discos.HEIGHT;
               } else {
                   // Movimiento inválido: disco más grande sobre uno más pequeño
                   moves--;
                   Alert alert = new Alert(AlertType.ERROR);
                   alert.setHeaderText(null);
                   alert.setTitle("Torres de Hanoi");
                   alert.setContentText("Movimiento Incorrecto!!!!!!");
                   alert.show();

                   currentTower = towerSelected;
                   disks = pegs[currentTower].getDisks();

                   if (!disks.empty()) {
                       y = disks.peek().getY() - Discos.HEIGHT;
                   } else {
                       y = VentanaPrincipal.getHeight() - 44.8;
                   }
               }

               // Calcula la posición X centrada en la torre
               x = (int) (VentanaPrincipal.getWidth() / 6 + (VentanaPrincipal.getWidth() / 3) * currentTower
                       - diskSelected.getWidth() / 2);

               // Coloca el disco en su nueva posición
               diskSelected.setLocation(x, y);
               disks.push(diskSelected);
               moves++;
               diskSelected = null;
               dragging = false;
               draw();

               // Verifica si se completó el juego
               if (currentTower == 2 && disks.size() == numberOfDisks) {
                   timer.stop(); //detener cronometro
                   Alert alert = new Alert(AlertType.NONE);
                   alert.setTitle("-Torres de Hanoi-");

                   // Verifica si se logró en el número mínimo de movimientos
                   if (moves == Math.pow(2, numberOfDisks) - 1) {
                       alert.setHeaderText("Perfectooo!!!!");
                       alert.setContentText("Movimientos: " + moves + " (Optima Solucion)");
                   } else {
                       alert.setHeaderText("Bien Hecho!!!!");
                       alert.setContentText("Movimientos: " + moves);
                   }

                   // Opciones para nuevo juego o reiniciar
                   ButtonType newGameButtonType = new ButtonType("-Nuevo Juego-");
                   ButtonType resetGameButtonType = new ButtonType("-Reiniciar Juego-");
                   alert.getButtonTypes().setAll(newGameButtonType, resetGameButtonType);

                   Optional<ButtonType> result = alert.showAndWait();

                   if (result.get() == newGameButtonType) {
                       newGame();
                   } else {
                       resetGame();
                   }
               }
           }
       });
   }

  
    //Determina sobre qué torre está el punto especificado
   private int currentTower(Point2D point) {
       // Define las áreas rectangulares de cada torre
       Rectangle tower1 = new Rectangle(0, 0, VentanaPrincipal.getWidth() / 3, VentanaPrincipal.getHeight());
       Rectangle tower2 = new Rectangle(VentanaPrincipal.getWidth() / 3, 0, VentanaPrincipal.getWidth() / 3, VentanaPrincipal.getHeight());
       Rectangle tower3 = new Rectangle(2 * VentanaPrincipal.getWidth() / 3, 0, VentanaPrincipal.getWidth() / 3, VentanaPrincipal.getHeight());

       // Retorna el índice de la torre correspondiente
       if (tower1.contains(point)) {
           return 0;
       } else if (tower2.contains(point)) {
           return 1;
       } else if (tower3.contains(point)) {
           return 2;
       }

       return -1;  // Fuera de todas las torres
   }

   /**
    * Inicia un nuevo juego permitiendo seleccionar el número de discos
    */
   public void newGame() {
       detenerSolucionAutomatica();
       // Diálogo para seleccionar el número de discos
       ChoiceDialog<Integer> choiceDialog = new ChoiceDialog<Integer>(numberOfDisks,
               List.of(1, 2, 3, 4, 5, 6, 7, 8, 9,10));
       choiceDialog.setTitle("-Torres de Hanoi-");
       choiceDialog.setHeaderText(null);
       choiceDialog.setContentText("Numero de Discos:");
       Optional<Integer> result = choiceDialog.showAndWait();

       if (result.isPresent()) {
           numberOfDisks = result.get();
           resetGame();
       }
   }

   /**
    * Reinicia el juego con la configuración actual
    */
   public void resetGame() {
       detenerSolucionAutomatica();
       moves = 0;
       startTime = System.currentTimeMillis();
       initializeTimer();   // Reiniciar el cronómetro-----------0:0
       init(numberOfDisks);
       draw();
   }

   //Inicializa las torres y discos para empezar el juego
   private void init(int disks) {
       // Crea las tres torres vacías
       pegs[0] = new Torre();
       pegs[1] = new Torre();
       pegs[2] = new Torre();

       // Coloca los discos en la primera torre
       for (int i = 0; i < disks; i++) {
           double x = VentanaPrincipal.getWidth() / 6;
           x = (x == 0) ? 109 : x;

           
        // Aumenta el tamaño de los discos (ejemplo: cada disco será más ancho y alto)
        double rectangleWidth = disks * 25 - 18 * i; // Cambia el ancho de los discos
        double rectangleHeight = 90; // Aumenta la altura del disco si lo deseas

           
          

           // Crea y posiciona el nuevo disco
           Rectangle rectangle = new Rectangle(x - rectangleWidth / 2, 
                                            (VentanaPrincipal.getHeight() - 44.8) - i * Discos.HEIGHT,
                                            rectangleWidth, 
                                            Discos.HEIGHT);

           pegs[0].getDisks().push(new Discos(rectangle, Discos.COLORS[i]));
       }

       // Reinicia variables de control
       diskSelected = null;
       w = 0.0;
       h = 0.0;
       dragging = false;
   }

   /**
    * Dibuja el estado actual del juego en el canvas
    */
   private void draw() {
    GraphicsContext gc = VentanaPrincipal.getGraphicsContext2D();

    // Limpia el canvas y dibuja el fondo negro
    gc.clearRect(0, 0, VentanaPrincipal.getWidth(), VentanaPrincipal.getHeight());
    gc.setFill(Color.BLACK);
    gc.fillRect(0, 0, VentanaPrincipal.getWidth(), VentanaPrincipal.getHeight());

    // Calcula posiciones para las torres (ajusta la altura si es necesario)
    int xHolder = (int) (VentanaPrincipal.getWidth() / 6);
    int y1Holder = (int) (VentanaPrincipal.getHeight() - 290); // Aumenta la altura de las torres
    int y2Holder = (int) (VentanaPrincipal.getHeight());

    // Configura el estilo de las torres
    gc.setLineCap(StrokeLineCap.ROUND);
    gc.setLineWidth(20); // Aumenta el grosor de las torres
    gc.setStroke(new LinearGradient(1, 0, 1, 1, true, CycleMethod.REFLECT, 
                    new Stop(0, Color.rgb(222, 233, 236)),
                    new Stop(1, Color.rgb(93, 162, 176))));

    // Dibuja las líneas verticales de las torres
    gc.strokeLine(xHolder, y1Holder, xHolder, y2Holder);
    gc.strokeLine(3 * xHolder, y1Holder, 3 * xHolder, y2Holder);
    gc.strokeLine(5 * xHolder, y1Holder, 5 * xHolder, y2Holder);

   // Dibuja las etiquetas de las torres
gc.setFill(Color.WHITESMOKE);
gc.setFont(new Font("Arial Black", 20)); // Cambia el tipo de letra y aumenta el tamaño
gc.fillText("- Torre Inicial -", xHolder - gc.getFont().getSize() * 5, y1Holder - 160); // Ajusta la posición
gc.fillText("- Torre Auxiliar -", xHolder * 3 - gc.getFont().getSize() * 5, y1Holder - 160); // Ajusta la posición
gc.fillText("- Torre Destino -", xHolder * 5 - gc.getFont().getSize() * 5, y1Holder - 160); // Ajusta la posición


    // Dibuja la base horizontal de las torres (ajusta la línea si es necesario)
    gc.setLineWidth(65);//ajustar la base de discos
    gc.setStroke(new LinearGradient(VentanaPrincipal.getWidth(), 50, VentanaPrincipal.getWidth(), 50 + 100, false, 
            CycleMethod.REFLECT, new Stop(0, Color.rgb(250, 236, 210)), 
            new Stop(1, Color.rgb(231, 215, 179))));
    gc.strokeLine(0, y2Holder, VentanaPrincipal.getWidth(), y2Holder);

   
    
  
    
    
    // Dibuja el contador de movimientos y el cronómetro
gc.setFill(Color.YELLOW); // Color más llamativo
gc.setFont(Font.font("Arial Black", FontWeight.BOLD, 25)); // Fuente más grande y negrita

// Contador de movimientos (lado izquierdo)
String movesText = "Movimientos: " + moves;
gc.fillText(movesText, 20, 50);

// Cronómetro (lado derecho)
String timeText = "Tiempo: " + timeElapsed;
double timeX = VentanaPrincipal.getWidth() - gc.getFont().getSize() * 8; // Ajusta la posición
gc.fillText(timeText, timeX, 50);

// Agregar sombra para hacer el texto más llamativo
gc.setFill(Color.rgb(255, 255, 0, 0.3)); // Sombra amarilla semi-transparente
gc.fillText(movesText, 22, 52);
gc.fillText(timeText, timeX + 2, 52);
    
    
    
    
    
    
    // Dibuja los discos en cada torre
    for (int tower = 0; tower < 3; tower++) {
        Torre peg = pegs[tower];
        if (!peg.getDisks().empty()) {
            int totalDisks = peg.getDisks().size();
            for (int i = 0; i < totalDisks; i++) {
                peg.getDisks().get(i).draw(gc);
            }
        }
    }

    // Dibuja el disco que se está arrastrando
    if (dragging == true && diskSelected != null) {
        diskSelected.draw(gc);
    }
   }
}