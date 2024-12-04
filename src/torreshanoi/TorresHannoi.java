package torreshanoi;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class TorresHannoi extends Application {
    private material material; // Clase para manejar recursos como imágenes y sonido
    
    @Override
    public void start(Stage stage) throws Exception {
        try {
            // Contenedor principal
            BorderPane root = new BorderPane();

            // Panel para el lienzo de dibujo del juego
            Pane wrapperPane = new Pane();
            Canvas canvas = new Canvas(700, 300); // Lienzo con tamaño definido
            wrapperPane.getChildren().add(canvas);

            // Controlador para las acciones del juego
            Controles controller = new Controles(canvas);

            // Inicializa material gráfico y sonido
            material = new material(wrapperPane);
            
            // Barra de menú
            MenuBar menuBar = new MenuBar();

            // Menú para iniciar un nuevo juego
            Menu newGameMenu = new Menu("-Nueva Partida-");
            MenuItem newGameItem = new MenuItem("Iniciar Nuevo Juego");
            newGameItem.setOnAction(event -> controller.newGame()); // Acción para iniciar juego
            newGameItem.setAccelerator(KeyCombination.keyCombination("Ctrl+N")); // Atajo de teclado
            newGameMenu.getItems().add(newGameItem);

            // Menú para reiniciar el juego
            Menu resetGameMenu = new Menu("-Reiniciar Juego-");
            MenuItem resetGameItem = new MenuItem("Reiniciar");
            resetGameItem.setOnAction(event -> controller.resetGame()); // Acción para reiniciar
            resetGameItem.setAccelerator(KeyCombination.keyCombination("Ctrl+R")); // Atajo de teclado
            resetGameMenu.getItems().add(resetGameItem);

            // Menú para salir de la aplicación
            Menu exitMenu = new Menu("-Salir del Programa-");
            MenuItem exitItem = new MenuItem("Salir de la Aplicación");
            exitItem.setOnAction(event -> {
                material.detenerMusica(); // Detener música antes de cerrar
                Platform.exit();
            });
            exitMenu.getItems().add(exitItem);

            // Botón para la solución automática
            Button solucionButton = new Button("Solución Automática");
            solucionButton.setOnAction(event -> controller.mostrarSolucionAutomatica()); // Acción de solución automática
            
            HBox buttonBox = new HBox(10); // Contenedor horizontal para el botón
            buttonBox.getChildren().add(solucionButton);
            buttonBox.setStyle("-fx-padding: 10; -fx-alignment: center;");

            // Contenedor central con lienzo y botón
            VBox centerContent = new VBox(10);
            centerContent.getChildren().addAll(wrapperPane, buttonBox);
           
            // Agrega los menús a la barra
            menuBar.getMenus().addAll(newGameMenu, resetGameMenu, exitMenu);

            // Añade la barra de menú y el contenido central al diseño principal
            root.setTop(menuBar);
            root.setCenter(centerContent);

            // Configuración de la escena y la ventana principal
            Scene scene = new Scene(root);
            stage.setTitle("-Torres de Hanoi-");
            stage.setScene(scene);
            stage.setResizable(false);

            // Manejar el cierre de la ventana
            stage.setOnCloseRequest((WindowEvent event) -> {
                material.detenerMusica(); // Detener música antes de cerrar
                Platform.exit();
            });

            stage.show(); // Muestra la ventana
            
        } catch (Exception e) {
            System.err.println("Error al iniciar la aplicación: " + e.getMessage());
            e.printStackTrace();
            Platform.exit(); // Cierra la aplicación en caso de error
        }
    }

    @Override
    public void stop() {
        if (material != null) {
            material.detenerMusica(); // Asegura que la música se detenga al cerrar
        }
    }

    public static void main(String[] args) {
        launch(args); // Inicia la aplicación
    }
}
