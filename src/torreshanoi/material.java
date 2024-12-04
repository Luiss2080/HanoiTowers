package torreshanoi;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javax.sound.sampled.*;
import java.io.File;

public class material {
    private ImageView imagen1;
    private ImageView imagen2;
    private Clip audioClip;

    // Rutas relativas desde la raíz del proyecto
    private static final String RUTA_IMAGEN1 = "src/imagenes/UPDS_FX.png";
    private static final String RUTA_IMAGEN2 = "src/imagenes/LOGO_FX.png";
    private static final String RUTA_SONIDO = "src/sonidos/Sonido_Fondo.wav"; // Nota: debe ser .wav

    public material(Pane panelPrincipal) {
        if (panelPrincipal == null) {
            throw new IllegalArgumentException("El panel principal no puede ser null");
        }
        inicializarImagenes();
        inicializarSonido();
        colocarImagenesEnPanel(panelPrincipal);
    }

    private void inicializarImagenes() {
        try {
            // Cargar imágenes usando File
            File file1 = new File(RUTA_IMAGEN1);
            File file2 = new File(RUTA_IMAGEN2);

            if (!file1.exists()) {
                System.err.println("No se encuentra la imagen 1 en: " + file1.getAbsolutePath());
                return;
            }
            if (!file2.exists()) {
                System.err.println("No se encuentra la imagen 2 en: " + file2.getAbsolutePath());
                return;
            }

            Image img1 = new Image(file1.toURI().toString());
            Image img2 = new Image(file2.toURI().toString());

            imagen1 = new ImageView(img1);
            imagen2 = new ImageView(img2);

            // Configurar tamaño
            imagen1.setFitWidth(200);//x
            imagen1.setFitHeight(200);//y
            imagen2.setFitWidth(100);
            imagen2.setFitHeight(100);

            // Mantener proporción
            imagen1.setPreserveRatio(true);
            imagen2.setPreserveRatio(true);

        } catch (Exception e) {
            System.err.println("Error al cargar las imágenes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void inicializarSonido() {
        try {
            File archivoSonido = new File(RUTA_SONIDO);
            
            if (!archivoSonido.exists()) {
                System.err.println("No se encuentra el archivo de sonido en: " + archivoSonido.getAbsolutePath());
                return;
            }

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(archivoSonido);
            audioClip = AudioSystem.getClip();
            audioClip.open(audioInputStream);
            audioClip.loop(Clip.LOOP_CONTINUOUSLY);
            audioClip.start();

        } catch (Exception e) {
            System.err.println("Error al cargar el sonido: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void colocarImagenesEnPanel(Pane panel) {
        if (imagen1 != null && imagen2 != null) {
            imagen1.setLayoutX(400);
            imagen1.setLayoutY(10);
            imagen2.setLayoutX(600);
            imagen2.setLayoutY(10);
            panel.getChildren().addAll(imagen1, imagen2);
        } else {
            System.err.println("No se pudieron cargar las imágenes para colocarlas en el panel.");
        }
    }

    public void detenerMusica() {
        if (audioClip != null && audioClip.isRunning()) {
            audioClip.stop();
            audioClip.close();
        }
    }
}